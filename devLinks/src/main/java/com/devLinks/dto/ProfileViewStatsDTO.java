package com.devLinks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProfileViewStatsDTO {

    private LocalDate date;
    private Long viewCount;
}
