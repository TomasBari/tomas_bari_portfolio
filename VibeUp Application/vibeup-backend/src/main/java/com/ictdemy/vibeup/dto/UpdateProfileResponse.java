package com.ictdemy.vibeup.dto;

public class UpdateProfileResponse {

    private UserDTO user;
    private String token;

    public UpdateProfileResponse(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
