package com.devLinks.dto;

import lombok.Data;

@Data
public class UserProfileResponseDto {
    private Long id;
    private String email;
    private String name;
    private String role;
}
