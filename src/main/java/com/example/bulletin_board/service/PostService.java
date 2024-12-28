package com.example.bulletin_board.service;

import java.util.List;

import com.example.bulletin_board.model.Post;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(Long postId);

    void createPost(Post post);

    void updatePost(Post post);

    void deletePost(Long postId);

    void incrementViews(Long postId);
}
