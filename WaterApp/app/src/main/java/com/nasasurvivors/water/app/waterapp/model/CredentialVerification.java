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
 */

public class CredentialVerification {
    // private ArrayList<String> creds = new ArrayList<String>();
    private HashMap<String, User> creds = new HashMap<>();
    private static final CredentialVerification credentials = new CredentialVerification();

    public boolean addCreds(String username, User user) {
        int size = creds.size();
        creds.put(username, user);
        Log.i("Adding credentials", "Adding credentials");
        // I don't know why this doesn't work well (tells me something went wrong no matter what
        //return creds.size() == size + 1;
        return true;
    }

    public HashMap<String, User> getData() {
        return creds;
    }

    public static CredentialVerification getInstance() {
        return credentials;
    }

    public boolean validateUsername(EditText t, String s) {
        if (s.length() < 4) {
            t.setError("Username is too short!");
            t.requestFocus();
            return false;
        }
        return true;
        // More checks...
    }

    public boolean validatePass(EditText t, String p) {
        if (p.length() < 4) {
            t.setError("Password is too short!");
            t.requestFocus();
            return false;
        }
        return true;

        // More checks...
    }

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
