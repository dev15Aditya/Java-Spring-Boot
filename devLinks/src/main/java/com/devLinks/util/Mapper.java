package com.devLinks.util;

import org.springframework.stereotype.Component;

import com.devLinks.dto.DeveloperProfileResponse;
import com.devLinks.dto.ProjectResponse;
import com.devLinks.model.DeveloperProfile;
import com.devLinks.model.Project;

@Component
public class Mapper {
    public DeveloperProfileResponse mapProfileToResponse(DeveloperProfile profile) {
        return DeveloperProfileResponse.builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .websiteUrl(profile.getWebsiteUrl())
                .skills(profile.getSkills())
                .email(profile.getUser().getEmail())
                .name(profile.getUser().getName())
                .userId(profile.getUser().getId())
                .build();
    }

    public ProjectResponse mapProjectToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .techStack(project.getTechStack())
                .githubUrl(project.getGithubUrl())
                .liveDemoUrl(project.getLiveDemoUrl())
                .build();
    }

}
