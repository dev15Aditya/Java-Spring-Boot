package com.devLinks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotNull
    private String title;
    private String description;
    private String techStack;
    private String githubUrl;
    private String liveDemoUrl;
}
