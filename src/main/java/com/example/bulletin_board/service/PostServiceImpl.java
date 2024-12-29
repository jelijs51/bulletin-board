package com.example.bulletin_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bulletin_board.mapper.PostMapper;
import com.example.bulletin_board.model.Post;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    public boolean verifyPassword(Long postId, String password) {
        Post post = postMapper.getPostById(postId);
        return post != null && post.getPassword().equals(password);
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
        postMapper.insertPost(post);
    }

    @Override
    public void updatePost(Post post) {
        String password = post.getPassword();
        Long id = post.getPostId();
        if (!verifyPassword(id, password)) {
            throw new IllegalArgumentException("Password does not match");
        }
        postMapper.updatePost(post);
    }

    @Override
    public void deletePost(Long postId) {
        postMapper.deletePost(postId);
    }

    @Override
    public void incrementViews(Long postId) {
        postMapper.incrementViews(postId);
    }

}
