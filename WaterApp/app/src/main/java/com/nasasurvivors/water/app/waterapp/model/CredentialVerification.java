package com.nasasurvivors.water.app.waterapp.model;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by zachschlesinger on 2/20/17.
 * Singleton that takes care of login credentials
 */
public class CredentialVerification {
    // private ArrayList<String> creds = new ArrayList<String>();
    private static HashMap<String, User> creds = new HashMap<>();
    private static final CredentialVerification credentials = new CredentialVerification();

    /**
     * method to add new credentials to the list
     * @param username username (key)
     * @param user user object to add
     * @return success of operation
     */
    public boolean addCreds(String username, User user) {
        int size = creds.size();
        creds.put(username, user);
        Log.i("Adding credentials", "Adding credentials");
        // I don't know why this doesn't work well (tells me something went wrong no matter what
        //return creds.size() == size + 1;
        return true;
    }

    /**
     * remove a pair of credentials with the given key
     * @param username
     * @return success of operation
     */
    public boolean removeCreds(String username) {
        creds.remove(username);
        return true;
    }

    /**
     * getter for list of credentials
     * @return list of credentials
     */
    public HashMap<String, User> getData() {
        return creds;
    }

    /**
     * getter for singleton
     * @return instance of class
     */
    public static CredentialVerification getInstance() {
        creds.put("tommi", new User("tommi", "tommi", "tommi", "tommi@", UserType.USER));
        return credentials;
    }

    /**
     * method to validate username input
     * @param t layout element
     * @param s text input
     * @return whether valid or not
     */
    public boolean validateUsername(EditText t, String s) {
        if (s.length() < 4) {
            t.setError("Username is too short!");
            t.requestFocus();
            return false;
        }
        return true;
        // More checks...
    }

    /**
     * method to validate password input
     * @param t layout element
     * @param p text input
     * @return whether valid or not
     */
    public boolean validatePass(EditText t, String p) {
        if (p.length() < 4) {
            t.setError("Password is too short!");
            t.requestFocus();
            return false;
        }
        return true;

        // More checks...
    }

    /**
     * method to validate email input
     * @param t layout element
     * @param e text input
     * @return whether valid or not
     */
    public boolean validateEmail(EditText t, String e) {
        if (!e.contains("@")) {
            t.setError("Email is invalid");
            t.requestFocus();
            return false;
        }
        return true;
        // More checks...
    }

}
