package com.devLinks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devLinks.model.DeveloperProfile;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
    Optional<DeveloperProfile> findByUserId(Long userId);
}
