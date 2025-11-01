package com.devLinks.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="developer_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperProfile {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String location;
    private String githubUrl;
    private String linkedinUrl;
    private String websiteUrl;

    private List<String> skills;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy="developerProfile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Project> projects = new ArrayList<>();
}
