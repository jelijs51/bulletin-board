package com.example.bulletin_board.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "authors")
public class authors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id", columnDefinition = "SERIAL PRIMARY KEY")
    private Long authorId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "password", length = 30, nullable = false)
    private String password;
}
