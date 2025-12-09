package com.example.codeassistant.model;

public class MemoUpdateDTO {
    private Long id;
    private String title;
    private String content;

    public MemoUpdateDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}