package com.example.codeassistant.model;

public class UserRegisterDTO {
    private String username;
    private String password;
    private String nickname;

    public UserRegisterDTO(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}