package com.TransferApp.MoneyTransfer.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {
    private Set<String> blackListedTokens = new HashSet<>();
    public void blackListToken(String token)
    {
        blackListedTokens.add(token);
    }
    public boolean isTokenBlacklisted(String token)
    {
        return blackListedTokens.contains(token);
    }
}
