package com.example.convertform.service.impl;

import com.example.convertform.dto.request.AuthRequest;
import com.example.convertform.dto.request.TokenRefreshRequest;
import com.example.convertform.dto.response.SignInResponseDTO;
import com.example.convertform.dto.response.TokenRefreshResponse;
import com.example.convertform.entity.RefreshToken;
import com.example.convertform.entity.User;
import com.example.convertform.jwt.JwtTokenProvider;
import com.example.convertform.service.IAuthService;
import com.example.convertform.entity.CustomUserDetails;
import com.example.convertform.service.impl.storage.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<?> signUp(AuthRequest authRequest) {
        if (userService.isUserExist(authRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.IM_USED).body("Username existed!");
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRoles("ROLE_USER");
        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Signup successfully!");
    }

    @Override
    public ResponseEntity<?> signIn(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (!authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(authRequest.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(customUserDetails.getId());

        SignInResponseDTO signInResponseDTO = SignInResponseDTO.builder()
                .userName(customUserDetails.getUsername())
                .uid(customUserDetails.getId())
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .roles(customUserDetails.getAuthorities())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(signInResponseDTO);
    }

    @Override
    public ResponseEntity<?> refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken).orElse(null);

        if (refreshToken != null) {
            String userName = userService.getUserFromId(refreshToken.getUserId()).getUsername();
            String token = jwtTokenProvider.generateToken(userName);

            return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        }

        throw new AuthenticationServiceException("User not found!");
    }
}
