package com.example.codeassistant.model;

import java.io.Serializable;

public class Memo implements Serializable {
    private Long id;
    private String title;
    private String content;
    // 后端数据库 mysql.txt 中是 is_completed, 对应实体 Integer isCompleted
    private Integer isCompleted;
    private String createTime;

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Integer getIsCompleted() { return isCompleted; }
    public String getCreateTime() { return createTime; }
}