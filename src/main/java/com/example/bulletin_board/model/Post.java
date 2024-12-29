package com.example.bulletin_board.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Post {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime creationDate = LocalDateTime.now(); 
    private LocalDateTime modificationDate = LocalDateTime.now();
    private Integer views = 0;
    private String author;
    private String password;
    private boolean isDeleted = false;
}
