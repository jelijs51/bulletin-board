package com.example.bulletin_board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bulletin_board.model.Post;
import com.example.bulletin_board.model.PostViewResponse;
import com.example.bulletin_board.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    private PostViewResponse mapToDto(Post post) {
        return new PostViewResponse(post.getPostId(), post.getTitle(), post.getContent(), post.getCreationDate(),
                post.getModificationDate(), post.getViews(), post.getAuthor(), post.isDeleted());
    }

    @PostMapping
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        postService.createPost(post);
        return new ResponseEntity<>("Post successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<?> getPostById(@PathVariable("id") Long postId) {
        Post post = postService.getPostById(postId);
        if (post != null) {
            PostViewResponse postViewResponse = mapToDto(post);
            return ResponseEntity.ok(postViewResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
    }

    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<List<PostViewResponse>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostViewResponse> postViewResponses = new ArrayList<>();
        for (Post post : posts) {
            postViewResponses.add(mapToDto(post));
        }
        return ResponseEntity.ok(postViewResponses);
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<String> updatePost(@PathVariable("id") Long postId, @RequestBody Post post) {
        post.setPostId(postId);
        postService.updatePost(post);
        return ResponseEntity.ok("successfully update post");
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId, @RequestBody Post post) {
        post.setPostId(postId);
        postService.deletePost(post);
        return ResponseEntity.ok("successfully delete post");
    }

    @PutMapping("/{id}/increment-views")
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> incrementViews(@PathVariable("id") Long postId) {
        postService.incrementViews(postId);
        return ResponseEntity.ok("successfully increase views");
    }
}
