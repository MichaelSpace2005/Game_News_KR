package com.example.sngnews.models;

import java.util.List;

public class DataBaseUser {

    private String username;
    private String email;

    private List<Integer> banIds;

    private DataBaseUser(){}

    public DataBaseUser(String username, String email, List<Integer> banIds){
        this.username = username;
        this.email = email;
        this.banIds = banIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getBanIds() {
        return banIds;
    }

    public void setBanIds(List<Integer> banIds) {
        this.banIds = banIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
