package com.example.bulletin_board.service;

import java.util.List;

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

    private boolean isValidLength(String text, int maxKorean, int maxNormal) {
        if (text == null) {
            return false;
        }
        boolean isKorean = text.matches(".*\\p{IsHangul}.*");
        return isKorean ? text.length() <= maxKorean : text.length() <= maxNormal;
    }

    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> getAllPosts(Long lastPostId) {
        return postMapper.getAllPosts(lastPostId);
    };

    @Override
    public Post getPostById(Long postId) {
        postMapper.incrementViews(postId);
        return postMapper.getPostById(postId);
    }

    @Override
    public Post createPost(Post post) {
        if (!isValidLength(post.getTitle(), 50, 100)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title exceeds the allowed length.");
        }

        if (!isValidLength(post.getAuthor(), 10, 20)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author name exceeds the allowed length.");
        }
        String password = post.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        post.setPassword(encodedPassword);
        postMapper.insertPost(post);
        return post;
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
}
