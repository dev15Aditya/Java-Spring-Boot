package com.devLinks.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devLinks.dto.AuthRequest;
import com.devLinks.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request) {
        String token = authService.signup(request);
        return token;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return token;
    }
}
