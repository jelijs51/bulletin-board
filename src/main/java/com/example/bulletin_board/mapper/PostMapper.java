package com.example.bulletin_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.bulletin_board.model.Post;

@Mapper
public interface PostMapper {
    List<Post> getAllPosts(Long lastPostId);

    Post getPostById(Long postId);

    void insertPost(Post post);

    void updatePost(Post post);

    void deletePost(Post post);

    void incrementViews(Long postId);

    void verifyPassword(Long postId);
}
