package com.TransferApp.MoneyTransfer.service;

import org.springframework.stereotype.Component;

@Component
public class InputSanitizer {

    public String sanitize(String input) {
        if (input == null) {
            return null;
        }
        // Example of removing potentially harmful characters
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
    public Long sanitizeLong(String input) {
        return Long.parseLong(sanitize(input));
    }

    public Double sanitizeDouble(String input) {
        return Double.parseDouble(sanitize(input));
    }
}
