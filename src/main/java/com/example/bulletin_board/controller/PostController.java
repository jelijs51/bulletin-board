package com.example.bulletin_board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bulletin_board.model.Post;
import com.example.bulletin_board.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void createPost(@RequestBody Post post) {
        postService.createPost(post);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable("id") Long postId, @RequestBody Post post) {
        post.setPostId(postId);
        postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
    }

    @PutMapping("/{id}/increment-views")
    public void incrementViews(@PathVariable("id") Long postId) {
        postService.incrementViews(postId);
    }
}
