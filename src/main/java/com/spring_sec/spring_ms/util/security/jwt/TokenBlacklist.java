package com.spring_sec.spring_ms.util.security.jwt;

public interface TokenBlacklist {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}