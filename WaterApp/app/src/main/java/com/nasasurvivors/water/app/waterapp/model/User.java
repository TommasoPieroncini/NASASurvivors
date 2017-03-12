package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 2/21/17.
 */

/**
 * User model class
 */
public class User {

    private String username;
    private String password;
    private String name;
    private String email;
    private UserType userType;

    /**
     *
     * @param username
     * @param password
     * @param name
     * @param email
     * @param userType
     */
    public User(String username, String password, String name, String email, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    /**
     * getter for username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username
     * @param u username
     */
    public void setUsername(String u) {
        this.username = u;
    }

    /**
     * getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param p password
     */
    public void setPassword(String p) {
        this.password = p;
    }

    /**
     * getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     * @param n name
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * getter for email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for email
     * @param e email
     */
    public void setEmail(String e) {
        this.email = e;
    }

    /**
     * getter for user type
     * @return user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * setter for user type
     * @param u user type
     */
    public void setUserType(UserType u) {
        this.userType = u;
    }

    public String toString() {
        return username + ", " + userType;
    }
}
