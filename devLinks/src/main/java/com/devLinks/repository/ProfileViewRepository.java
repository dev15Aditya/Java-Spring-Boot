package com.devLinks.repository;

import com.devLinks.dto.ProfileViewStatsDTO;
import com.devLinks.model.ProfileView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileViewRepository extends JpaRepository<ProfileView, Long> {
    @Query("""
            SELECT COUNT(pv)
               FROM ProfileView pv
               WHERE pv.profileOwner.id = :ownerId
            """)
    long getTotalProfileViews(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT pv
            FROM ProfileView pv
            WHERE pv.profileOwner.id = :ownerId
            ORDER BY pv.viewedAt DESC
            """)
    List<ProfileView> getRecentProfileViews(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT new com.devLinks.dto.ProfileViewStatsDTO(
                DATE(pv.viewedAt),
                COUNT(pv)
           )
           FROM ProfileView pv
           WHERE pv.profileOwner.id = :ownerId
           GROUP BY DATE(pv.viewedAt)
           ORDER BY DATE(pv.viewedAt)
           """)
    List<ProfileViewStatsDTO> getProfileViewStats(@Param("ownerId") Long ownerId);

    List<ProfileView> findAllByProfileOwnerIdOrderByViewedAtDesc(
            Long profileOwnerId,
            Pageable pageable
    );

}
