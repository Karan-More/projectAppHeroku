package com.capstone.kanbantool.exception;

public class UsernameExistInDatabaseResponse {

    private String username;

    public UsernameExistInDatabaseResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
