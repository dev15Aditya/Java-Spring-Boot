package com.example.blogs.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private List<String> comments;
}
