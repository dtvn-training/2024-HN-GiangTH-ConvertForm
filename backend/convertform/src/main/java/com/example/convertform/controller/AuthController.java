package com.example.convertform.controller;

import com.example.convertform.dto.request.AuthRequest;
import com.example.convertform.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/welcome")
    public String welcome() {
        return "You can see that without login!";
    }

    @PostMapping("/registry")
    public ResponseEntity<?> addNewUser(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest.toString());
        return authService.signUp(authRequest);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return authService.signIn(authRequest);
    }
}
