package com.meetusar.task.dto;

public class AuthResponse {
    private String accessToken;
    public AuthResponse() {}
    public AuthResponse(String token) { this.accessToken = token; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
