package com.devLinks.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeveloperProfileResponse {
    private Long id;
    private String bio;
    private String location;
    private String githubUrl;
    private String linkedinUrl;
    private String websiteUrl;
    private List<String> skills;
    private String email;
    private String name;
    private Long userId;
}
