package com.spring_sec.spring_ms.util.security.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.spring_sec.spring_ms.util.security.jwt.TokenBlacklist;

@Service
public class InMemoryTokenBlacklist implements TokenBlacklist {
    private Set<String> blacklist = new HashSet<>();

    @Override
    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}