package com.devLinks.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devLinks.dto.ProjectRequest;
import com.devLinks.dto.ProjectResponse;
import com.devLinks.service.ProjectService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> addProject(@RequestBody ProjectRequest project) {
        ProjectResponse newProject = projectService.addProject(project);
        return ResponseEntity.ok(newProject);
    }
    

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        List<ProjectResponse> projects = projectService.getProjectsByUser();
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject (@PathVariable Long id){
        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
}
