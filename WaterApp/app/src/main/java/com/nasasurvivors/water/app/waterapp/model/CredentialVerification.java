package com.nasasurvivors.water.app.waterapp.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zachschlesinger on 2/20/17.
 */

public class CredentialVerification {
    private ArrayList<String> creds = new ArrayList<String>();

    public boolean addCreds(String user, String pass) {
        int size = creds.size();
        creds.add(user + ":" + pass);
        Log.i("Adding credentials", "Adding credentials");
        return creds.size() == size + 1;
    }

    public ArrayList<String> getData() {
        return creds;
    }

    private static final CredentialVerification credentials = new CredentialVerification();

    public static CredentialVerification getInstance() {
        return credentials;
    }

}
