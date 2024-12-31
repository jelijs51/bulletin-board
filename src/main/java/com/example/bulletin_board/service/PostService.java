package com.example.bulletin_board.service;

import java.util.List;

import com.example.bulletin_board.model.Post;

public interface PostService {
    List<Post> getAllPosts(Long lastPostId);

    Post getPostById(Long postId);

    Post createPost(Post post);

    void updatePost(Post post);

    void deletePost(Post post);
}
