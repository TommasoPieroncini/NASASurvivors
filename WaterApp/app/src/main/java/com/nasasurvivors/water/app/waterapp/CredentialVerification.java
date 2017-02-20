package com.nasasurvivors.water.app.waterapp;

import java.util.ArrayList;

/**
 * Created by zachschlesinger on 2/20/17.
 */

public class CredentialVerification {
    private ArrayList<String> creds = new ArrayList<String>();

    public void addCreds(String user, String pass) {
        creds.add(user + ":" + pass);
    }

    public ArrayList<String> toArrayList() {
        return creds;
    }
}
