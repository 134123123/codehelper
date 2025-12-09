package com.example.codeassistant.model; // 必须加上这一行！

public class UserLoginDTO {
    private String username;
    private String password;

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 添加 Getter/Setter 以防 Retrofit/Gson 序列化需要
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}