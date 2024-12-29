package com.example.bulletin_board.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostViewResponse {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Integer views;
    private String author;
    private boolean isDeleted;
}
