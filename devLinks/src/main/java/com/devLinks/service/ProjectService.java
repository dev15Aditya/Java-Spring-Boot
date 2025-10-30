package com.devLinks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devLinks.dto.ProjectRequest;
import com.devLinks.model.DeveloperProfile;
import com.devLinks.model.Project;
import com.devLinks.repository.DeveloperProfileRepository;
import com.devLinks.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperProfileRepository developerProfileRepository;

    public Project addProject(Long userId, ProjectRequest projectRequest){
        DeveloperProfile profile = developerProfileRepository.findByUserId(userId)
                                    .orElseThrow(() -> new RuntimeException("Profile not found"));

        Project project = Project.builder()
                        .title(projectRequest.getTitle())
                        .description(projectRequest.getDescription())
                        .techStack(projectRequest.getTechStack())
                        .githubUrl(projectRequest.getGithubUrl())
                        .liveDemoUrl(projectRequest.getLiveDemoUrl())
                        .developerProfile(profile)
                        .build();

        return projectRepository.save(project);
    }

    public List<Project> getProjectsByUser(Long userId){
        DeveloperProfile profile = developerProfileRepository.findByUserId(userId)
                                    .orElseThrow(() -> new RuntimeException("Profile not found"));
        return projectRepository.findByDeveloperProfileId(profile.getId());
    }

    public void deleteProject(Long projectId, Long userId){
        DeveloperProfile profile = developerProfileRepository.findByUserId(userId)
                                    .orElseThrow(() -> new RuntimeException("Profile not found"));
        Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project not found"));

        if(!project.getDeveloperProfile().getId().equals(profile.getId())){
            throw new RuntimeException("You can't delete this project");
        }

        projectRepository.delete(project);
    }
}
