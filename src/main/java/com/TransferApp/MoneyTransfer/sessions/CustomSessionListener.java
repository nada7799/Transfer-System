package com.TransferApp.MoneyTransfer.sessions;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Handle session creation
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
