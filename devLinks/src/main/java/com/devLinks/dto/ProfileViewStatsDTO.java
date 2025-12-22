package com.devLinks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProfileViewStatsDTO {
    private LocalDateTime date;
    private Long viewCount;
}
