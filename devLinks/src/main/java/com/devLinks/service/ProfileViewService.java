package com.devLinks.service;

import com.devLinks.dto.ProfileViewStatsDTO;
import com.devLinks.model.ProfileView;
import com.devLinks.model.User;
import com.devLinks.repository.ProfileViewRepository;
import com.devLinks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
    private final ProfileViewRepository profileViewRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public void recordProfileView(Long profileOwnerId) {
        try {
            String email = authService.getAuthenticatedUserEmail();
            User viewer = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            User owner = userRepository.findById(profileOwnerId)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));

            if(viewer.getId().equals(owner.getId())){
                return;
            }

            ProfileView view = ProfileView.builder()
                    .viewer(viewer)
                    .profileOwner(owner)
                    .viewedAt(LocalDateTime.now())
                    .build();

            profileViewRepository.save(view);
        } catch (Exception e){
            System.err.println("Failed to record profile view: " + e.getMessage());
        }
    }

    public long getTotalViews(){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return profileViewRepository.getTotalProfileViews(user.getId());
    }


    public List<ProfileView> recentViewer(int limit){
        int safeLimit = Math.min(Math.max(limit, 1), 20);

        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(0, safeLimit);

        return profileViewRepository.findAllByProfileOwnerIdOrderByViewedAtDesc(user.getId(), pageable);
    }

    public List<ProfileViewStatsDTO> viewsStats(){
        String email = authService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return profileViewRepository.getProfileViewStats(user.getId());
    }
}
