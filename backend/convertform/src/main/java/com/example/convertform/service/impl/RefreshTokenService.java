package com.example.convertform.service.impl;

import com.example.convertform.entity.RefreshToken;
import com.example.convertform.sqlmapper.RefreshTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${convertform.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Autowired
    RefreshTokenMapper refreshTokenMapper;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenMapper.findByToken(token);
    }

    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        refreshTokenMapper.insert(refreshToken);
        return refreshToken;
    }

//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenMapper.deleteById(token.getId());
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
//        }
//
//        return token;
//    }

    public int deleteByUserId(Long userId) {
        return refreshTokenMapper.deleteByUserId(userId);
    }
}
