package com.example.codeassistant.model;

import java.io.Serializable;

public class Memo implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Integer isCompleted;
    private String createTime;

    // Getters and Setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Integer getIsCompleted() { return isCompleted; }
}