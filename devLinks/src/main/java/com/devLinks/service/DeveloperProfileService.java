package com.devLinks.service;

import org.springframework.stereotype.Service;

import com.devLinks.dto.DeveloperProfileRequest;
import com.devLinks.model.DeveloperProfile;
import com.devLinks.model.User;
import com.devLinks.repository.DeveloperProfileRepository;
import com.devLinks.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DeveloperProfileService {
    private final DeveloperProfileRepository developerProfileRepository;
    private final UserRepository userRepository;

    public DeveloperProfile createOrUpdateProfile(Long userId, DeveloperProfileRequest request){
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

        DeveloperProfile profile = developerProfileRepository.findByUserId(userId)
                                    .orElse(DeveloperProfile.builder().user(user).build());

        profile.setBio(request.getBio());
        profile.setLocation(request.getLocation());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setWebsiteUrl(request.getWebsiteUrl());
        profile.setSkills(request.getSkills());
        
        return developerProfileRepository.save(profile);
    }

    public DeveloperProfile getProfileByUserId (Long userId){
        return developerProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Profile not found for this user"));
    }
}
