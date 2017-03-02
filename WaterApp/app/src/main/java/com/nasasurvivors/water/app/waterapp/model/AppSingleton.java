package com.nasasurvivors.water.app.waterapp.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tommaso on 2/12/17.
 */
public class AppSingleton {
    private static AppSingleton currInstance = new AppSingleton();;
    private static Context currContext;
    private static User currentUser;
    private static ArrayList<WaterReportData> reports = new ArrayList<>();

    public static AppSingleton getInstance(Context c) {
        currContext = c;
        return currInstance;
    }

    public static AppSingleton getInstance() {
        return currInstance;
    }

    private AppSingleton() {
    }

    // use for incomplete methods.
    public void incompleteMethod() {
        Toast.makeText(currContext, "Functionality not implemented (YET)", Toast.LENGTH_SHORT).show();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void addReport(WaterReportData r) {
        reports.add(r);
    }

    public ArrayList<WaterReportData> getReports() {
        return reports;
    }
}
