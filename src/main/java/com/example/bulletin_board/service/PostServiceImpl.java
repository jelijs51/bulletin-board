package com.example.bulletin_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.bulletin_board.mapper.PostMapper;
import com.example.bulletin_board.model.Post;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean validatePassword(Post post) {
        String password = post.getPassword();
        Long id = post.getPostId();
        Post validPost = postMapper.getPostById(id);
        return passwordEncoder.matches(password, validPost.getPassword());
    }

    public boolean checkValidPost(Long postId) {
        Post post = postMapper.getPostById(postId);
        return post != null;
    }

    @Autowired
    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> getAllPosts() {
        return postMapper.getAllPosts();
    };

    @Override
    public Post getPostById(Long postId) {
        return postMapper.getPostById(postId);
    }

    @Override
    public void createPost(Post post) {
        String password = post.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        post.setPassword(encodedPassword);
        postMapper.insertPost(post);
    }

    @Override
    public void updatePost(Post post) {
        if (!checkValidPost(post.getPostId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        if (!validatePassword(post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }
        postMapper.updatePost(post);
    }

    @Override
    public void deletePost(Post post) {
        if (!checkValidPost(post.getPostId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        if (!validatePassword(post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }
        postMapper.deletePost(post);
    }

    @Override
    public void incrementViews(Long postId) {
        if (!checkValidPost(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        postMapper.incrementViews(postId);
    }

}
