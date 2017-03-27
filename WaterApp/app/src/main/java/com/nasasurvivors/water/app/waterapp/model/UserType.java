package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 2/21/17.
 */

/**
 * User Type model class
 */
public enum UserType {
    USER("User"), WORKER("Worker"), MANAGER("Manager"), ADMIN("Admin");

    private String value;

    /**
     * contructor which sets value
     * @param v value to assign
     */
    UserType(String v) {
        value = v;
    }

    /**
     * overriding tostring method
     * @return return value
     */
    public String toString() {
        return value;
    }
}
