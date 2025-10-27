package com.devLinks.dto;

import java.util.List;

import lombok.Data;

@Data
public class DeveloperProfileRequest {
    private String bio;
    private String location;
    private String githubUrl;
    private String linkedinUrl;
    private String websiteUrl;
    private List<String> skills;
}
