package com.nasasurvivors.water.app.waterapp.model;

import com.nasasurvivors.water.app.waterapp.model.UserType;

/**
 * Created by zachschlesinger on 2/27/17.
 */

public class User {

    public String email;
    public String password;
    public String name;
    public String user;
    public UserType type;

    public User() {

    }

    public User(String user, String password, String name, String email, UserType type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.user = user;
        this.type = type;
    }
}
