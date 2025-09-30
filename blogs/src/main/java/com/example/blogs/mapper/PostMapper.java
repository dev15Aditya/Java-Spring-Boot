package com.example.blogs.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.blogs.dto.PostRequestDTO;
import com.example.blogs.dto.PostResponseDTO;
import com.example.blogs.model.Comment;
import com.example.blogs.model.Post;

@Mapper(componentModel="spring")
public interface PostMapper {
    Post toEntity(PostRequestDTO dto);

    @Mapping(target="comments", source="comments")
    PostResponseDTO toDto(Post post);

    default List<String> mapComments(List<Comment> comments) {
        return comments.stream().map(Comment::getText).toList();
    }
}
