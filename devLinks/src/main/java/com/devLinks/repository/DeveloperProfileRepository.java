package com.devLinks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devLinks.model.DeveloperProfile;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
    Optional<DeveloperProfile> findByUserId(Long userId);

    List<DeveloperProfile> findByLocationContainingIgnoreCase(String location);
    List<DeveloperProfile> findByUser_NameContainingIgnoreCase(String name);
}
