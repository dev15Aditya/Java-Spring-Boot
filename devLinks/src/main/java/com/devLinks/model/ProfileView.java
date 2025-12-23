package com.devLinks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="profile_view")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
