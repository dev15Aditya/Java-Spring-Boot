package com.example.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogs.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
