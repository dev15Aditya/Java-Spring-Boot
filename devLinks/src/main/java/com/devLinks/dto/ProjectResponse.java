package com.devLinks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String techStack;
    private String githubUrl;
    private String liveDemoUrl;
}
