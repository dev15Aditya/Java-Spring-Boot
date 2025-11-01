package com.devLinks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devLinks.dto.ProjectRequest;
import com.devLinks.dto.ProjectResponse;
import com.devLinks.model.DeveloperProfile;
import com.devLinks.model.Project;
import com.devLinks.model.User;
import com.devLinks.repository.DeveloperProfileRepository;
import com.devLinks.repository.ProjectRepository;
import com.devLinks.repository.UserRepository;
import com.devLinks.util.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperProfileRepository developerProfileRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    private final Mapper mapper;

    public ProjectResponse addProject(ProjectRequest projectRequest){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

        DeveloperProfile profile = developerProfileRepository.findByUserId(user.getId())
                                    .orElseThrow(() -> new RuntimeException("Profile not found"));

        Project project = Project.builder()
                        .title(projectRequest.getTitle())
                        .description(projectRequest.getDescription())
                        .techStack(projectRequest.getTechStack())
                        .githubUrl(projectRequest.getGithubUrl())
                        .liveDemoUrl(projectRequest.getLiveDemoUrl())
                        .developerProfile(profile)
                        .build();

        Project saveProject = projectRepository.save(project);
        ProjectResponse response = mapper.mapProjectToResponse(saveProject);

        return response;
    }

    public List<ProjectResponse> getProjectsByUser(){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

        DeveloperProfile profile = developerProfileRepository.findByUserId(user.getId())
                                    .orElseThrow(() -> new RuntimeException("Profile not found"));
        List<Project> project = projectRepository.findByDeveloperProfileId(profile.getId());
        List<ProjectResponse> response = new ArrayList<>();
        for(Project p: project){
            response.add(mapper.mapProjectToResponse(p));
        }

        return response;
    }

    public void deleteProject(Long projectId){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

        DeveloperProfile profile = developerProfileRepository.findByUserId(user.getId())
                                    .orElseThrow(() -> new RuntimeException("Profile not found"));
        Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project not found"));

        if(!project.getDeveloperProfile().getId().equals(profile.getId())){
            throw new RuntimeException("You can't delete this project");
        }

        projectRepository.delete(project);
    }
}
