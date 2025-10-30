package com.devLinks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devLinks.dto.DeveloperProfileRequest;
import com.devLinks.model.DeveloperProfile;
import com.devLinks.service.DeveloperProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class DeveloperProfileController {
    private final DeveloperProfileService developerProfileService;

    @PostMapping
    public ResponseEntity<DeveloperProfile> createOrUpdateProfile(@RequestBody DeveloperProfileRequest request) {
        DeveloperProfile profile = developerProfileService.createOrUpdateProfile(request);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me")
    public ResponseEntity<DeveloperProfile> getMyProfile() {
        DeveloperProfile profile = developerProfileService.getMyProfile();
        return ResponseEntity.ok(profile);
    }
}
