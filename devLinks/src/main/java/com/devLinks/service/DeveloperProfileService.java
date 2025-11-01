package com.devLinks.service;

import org.springframework.stereotype.Service;

import com.devLinks.dto.DeveloperProfileRequest;
import com.devLinks.dto.DeveloperProfileResponse;
import com.devLinks.model.DeveloperProfile;
import com.devLinks.model.User;
import com.devLinks.repository.DeveloperProfileRepository;
import com.devLinks.repository.UserRepository;
import com.devLinks.util.Mapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DeveloperProfileService {
    private final DeveloperProfileRepository developerProfileRepository;
    private final UserRepository userRepository;

    private final Mapper mapper;

    private final AuthService authService;

    public DeveloperProfileResponse createOrUpdateProfile(DeveloperProfileRequest request){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

        DeveloperProfile profile = developerProfileRepository.findByUserId(user.getId())
                                    .orElse(DeveloperProfile.builder().user(user).build());

        profile.setBio(request.getBio());
        profile.setLocation(request.getLocation());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setWebsiteUrl(request.getWebsiteUrl());
        profile.setSkills(request.getSkills());
        
        DeveloperProfile saveProfile = developerProfileRepository.save(profile);

        DeveloperProfileResponse response = mapper.mapProfileToResponse(saveProfile);
        return response;
    }

    public DeveloperProfileResponse getMyProfile() {
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DeveloperProfile profile = developerProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        DeveloperProfileResponse response = mapper.mapProfileToResponse(profile);
        return response;
    }

    public DeveloperProfile getProfileByUserId(Long userId){
        return developerProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Profile not found for this user"));
    }
}
