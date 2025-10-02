package com.example.blogs.service;

import org.springframework.stereotype.Service;

import com.example.blogs.configuration.ResourceNotFoundException;
import com.example.blogs.dto.CommentRequestDTO;
import com.example.blogs.model.Comment;
import com.example.blogs.model.Post;
import com.example.blogs.repository.CommentRepository;
import com.example.blogs.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void addComment(Long postId, CommentRequestDTO dto){
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setPost(post);

        commentRepository.save(comment);
    }
}
