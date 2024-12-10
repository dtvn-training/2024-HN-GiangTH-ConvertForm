package com.example.convertform.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class SignInResponseDTO {
    Integer uid;
    String token;
    String refreshToken;
    String userName;
    Collection<? extends GrantedAuthority> roles;
}
