package com.TransferApp.MoneyTransfer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
public class CurrencyConverter {

    private final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final String API_KEY = "71b533adff8f50f4d51115cf";  // Replace with your actual API key

    public double getConversionRate(String baseCurrency, String targetCurrency) {
        System.out.println("baseCurrency: " + baseCurrency);
        String url = UriComponentsBuilder.fromHttpUrl(API_URL + baseCurrency)
                .queryParam("symbols", targetCurrency)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);  // Example for Bearer Token

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CurrencyResponse.class);

        if (response.getBody() != null && response.getBody().getRates() != null) {
            return response.getBody().getRates().get(targetCurrency);
        }
        throw new RuntimeException("Failed to get conversion rate");
    }

    private static class CurrencyResponse {
        private String base;
        private Map<String, Double> rates;

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}

