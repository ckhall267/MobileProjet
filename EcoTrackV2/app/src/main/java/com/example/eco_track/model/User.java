package com.example.eco_track.model;

public class User {
    private Long id;
    private String email;
    private String password;
    private String token;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
