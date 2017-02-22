package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 2/21/17.
 */

public class User {

    private String username;
    private String password;
    private String name;
    private String email;
    private UserType userType;

    public User(String username, String password, String name, String email, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType u) {
        this.userType = u;
    }
}
