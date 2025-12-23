package com.devLinks.repository;

import com.devLinks.dto.ProfileViewStatsDTO;
import com.devLinks.model.ProfileView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileViewRepository extends JpaRepository<ProfileView, Long> {

    // Total views count
    @Query("""
        SELECT COUNT(pv)
        FROM ProfileView pv
        WHERE pv.profileOwner.id = :ownerId
    """)
    long getTotalProfileViews(@Param("ownerId") Long ownerId);

    // Recent viewers (ordered)
    @Query("""
        SELECT pv
        FROM ProfileView pv
        WHERE pv.profileOwner.id = :ownerId
        ORDER BY pv.viewedAt DESC
    """)
    List<ProfileView> getRecentProfileViews(@Param("ownerId") Long ownerId);

    // Views grouped by date
    @Query("""
    SELECT new com.devLinks.dto.ProfileViewStatsDTO(
        CAST(pv.viewedAt AS date),
        COUNT(pv)
    )
    FROM ProfileView pv
    WHERE pv.profileOwner.id = :ownerId
    GROUP BY CAST(pv.viewedAt AS date)
    ORDER BY CAST(pv.viewedAt AS date)
""")
    List<ProfileViewStatsDTO> getProfileViewStats(@Param("ownerId") Long ownerId);

//    List<ProfileView> findAllByProfileOwnerIdOrderByViewedAtDesc(
//            Long profileOwnerId,
//            Pageable pageable
//    );
}
