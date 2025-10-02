package com.example.blogs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blogs.configuration.ResourceNotFoundException;
import com.example.blogs.dto.PostRequestDTO;
import com.example.blogs.dto.PostResponseDTO;
import com.example.blogs.mapper.PostMapper;
import com.example.blogs.model.Post;
import com.example.blogs.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostResponseDTO createPost(PostRequestDTO dto) {
        Post post = postMapper.toEntity(dto);
        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    public PostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        return postMapper.toDto(post);
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .toList();
    }
}
