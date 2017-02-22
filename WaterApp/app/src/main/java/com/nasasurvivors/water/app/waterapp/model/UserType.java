package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 2/21/17.
 */

public enum UserType {
    NONE("Select a user type"), USER("User"), WORKER("Worker"), MANAGER("Manager"), ADMIN("Admin");

    private String value;

    UserType(String v) {
        value = v;
    }

    public String toString() {
        return value;
    }
}
