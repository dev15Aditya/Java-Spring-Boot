package com.example.blogs.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogs.dto.CommentRequestDTO;
import com.example.blogs.service.CommentService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public void addComment(@PathVariable long postId, @RequestBody CommentRequestDTO dto) {
        commentService.addComment(postId, dto);
    }
    
}
