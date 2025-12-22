package com.devLinks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
public class ProfileView {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User viewer;

    @ManyToOne
    private User profileOwner;

    private LocalDateTime viewedAt;
    private String ipAddress;
}
