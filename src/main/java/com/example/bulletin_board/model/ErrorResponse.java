package com.example.bulletin_board.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String statusCode;
    private LocalDateTime date;
}
