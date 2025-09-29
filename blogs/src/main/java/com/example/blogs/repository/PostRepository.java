package com.example.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogs.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
