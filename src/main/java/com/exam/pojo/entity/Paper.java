package com.exam.pojo.entity;

import lombok.Data;

@Data
public class Paper {
    private Long id;
    private String title;
    private String author;

    public Paper(String title, String author) {
        this.title = title;
        this.author = author;
    }
}