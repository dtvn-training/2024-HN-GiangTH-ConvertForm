package com.example.convertform.controller;

import com.example.convertform.dto.request.AuthRequest;
import com.example.convertform.entity.User;
import com.example.convertform.jwt.JwtTokenProvider;
import com.example.convertform.service.impl.storage.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "You can see that without login!";
    }

    @PostMapping("/registry")
    public String addNewUser(@RequestBody User userInfo) {
        System.out.println(userInfo.toString());
        return userService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtTokenProvider.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
