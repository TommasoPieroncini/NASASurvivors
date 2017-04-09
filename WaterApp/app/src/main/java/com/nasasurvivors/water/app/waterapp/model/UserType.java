package com.nasasurvivors.water.app.waterapp.model;

/**
 * User Type model class
 */
public enum UserType {
    USER("User"), WORKER("Worker"), MANAGER("Manager"), ADMIN("Admin");

    private final String value;

    /**
     * constructor which sets value
     * @param v value to assign
     */
    UserType(String v) {
        value = v;
    }

    /**
     * overriding toString method
     * @return return value
     */
    public String toString() {
        return value;
    }
}
