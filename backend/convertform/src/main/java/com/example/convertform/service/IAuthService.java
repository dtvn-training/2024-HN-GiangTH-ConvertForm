package com.example.convertform.service;

import com.example.convertform.dto.request.AuthRequest;
import com.example.convertform.dto.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> signUp(AuthRequest authRequest);
    ResponseEntity<?> signIn(AuthRequest authRequest);
    ResponseEntity<?> refreshToken(TokenRefreshRequest tokenRefreshRequest);
}
