package com.example.blogs.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogs.dto.PostRequestDTO;
import com.example.blogs.dto.PostResponseDTO;
import com.example.blogs.service.PostService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    
    @PostMapping
    public PostResponseDTO createPost(@RequestBody PostRequestDTO dto) {
        return postService.createPost(dto);
    }

    @GetMapping("/{id}")
    public PostResponseDTO getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping
    public List<PostResponseDTO> getAllPost() {
        return postService.getAllPosts();
    }
    
    
}
