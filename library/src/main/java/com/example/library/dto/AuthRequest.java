package com.example.library.dto;

public record AuthRequest (
    String username, String password, String email
) {}
