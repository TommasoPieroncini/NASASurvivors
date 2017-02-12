package com.nasasurvivors.water.app.waterapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tommaso on 2/12/17.
 */
public class AppSingleton {
    private static AppSingleton currInstance = new AppSingleton();;
    private static Context currContext;
    private static String username;

    public static AppSingleton getInstance(Context c) {
        currContext = c;
        return currInstance;
    }

    public static AppSingleton getInstance() {
        return currInstance;
    }

    private AppSingleton() {
    }

    public void incompleteMethod() {
        Toast.makeText(currContext, "Functionality not implemented (YET)", Toast.LENGTH_SHORT).show();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
