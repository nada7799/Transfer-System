package com.TransferApp.MoneyTransfer.logging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class UserActivityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserActivityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        // Wrap the request to cache the body
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);

        chain.doFilter(wrappedRequest, response);

        if (session != null) {
            String user = wrappedRequest.getUserPrincipal() != null ? wrappedRequest.getUserPrincipal().getName() : "Anonymous";
            String method = wrappedRequest.getMethod();
            String url = wrappedRequest.getRequestURI();
            LocalDateTime timestamp = LocalDateTime.now();

            // Read cached request body
            String requestBody = new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
            String amount = "";
            String recipient = "";

            if (url.startsWith("/api/transactions/transfer")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode jsonNode = objectMapper.readTree(requestBody);
                    amount = jsonNode.path("amount").asText("N/A");
                    recipient = jsonNode.path("destinationAccountId").asText("N/A");
                } catch (IOException e) {
                    logger.error("Error parsing request body: {}", e.getMessage());
                }
                logger.info("User: {}, URL: {}, Method: {}, Amount: {}, Recipient: {}, Timestamp: {}",
                        user, url, method, amount, recipient, timestamp);
            } else {
                logger.info("User: {}, URL: {}, Method: {}, Timestamp: {}",
                        user, url, method, timestamp);
            }
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
