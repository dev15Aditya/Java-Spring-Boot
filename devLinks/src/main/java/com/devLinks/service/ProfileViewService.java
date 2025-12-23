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

    public void recordProfileView(Long viewerId, Long profileOwnerId) {
        try {
            User viewer = userRepository.findById(viewerId)
                    .orElseThrow(() -> new RuntimeException("Not a valid User"));

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
            throw new RuntimeException("Failed to record profile view", e);
        }
    }

    public long getTotalViews(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not a valid User"));

        return profileViewRepository.getTotalProfileViews(user.getId());
    }

    public List<ProfileView> recentViewer(Long userId, int limit){
        int safeLimit = Math.min(Math.max(limit, 1), 20);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not a valid User"));

        Pageable pageable = PageRequest.of(0, safeLimit);

//        return profileViewRepository.findAllByProfileOwnerIdOrderByViewedAtDesc(user.getId(), pageable);
        return profileViewRepository.getRecentProfileViews(userId);
    }

    public List<ProfileViewStatsDTO> viewsStats(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not a valid User"));

        return profileViewRepository.getProfileViewStats(user.getId());
    }
}
