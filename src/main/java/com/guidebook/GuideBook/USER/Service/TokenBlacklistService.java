package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.BlacklistedToken;
import com.guidebook.GuideBook.USER.Repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    private final BlacklistRepository blacklistRepository;

    @Autowired
    public TokenBlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public void blacklistToken(String token) {
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(token);
        blacklistRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistRepository.existsById(token);
    }
}
