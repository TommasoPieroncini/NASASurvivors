package com.nasasurvivors.water.app.waterapp.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tommaso on 2/12/17.
 * AppSingleton that includes a reference to the current user,
 * a list of water reports and global methods for the app
 */
public class AppSingleton {
    private static AppSingleton currInstance = new AppSingleton();;
    private static Context currContext;
    private static User currentUser;
    private static ArrayList<WaterReportData> reports = new ArrayList<>();
    int currentReportID = 0;

    /**
     * getter with current context
     * @param c current context
     * @return instance of class
     */
    public static AppSingleton getInstance(Context c) {
        currContext = c;
        return currInstance;
    }

    /**
     * getter without params
     * @return instance of class
     */
    public static AppSingleton getInstance() {
        return currInstance;
    }

    /**
     * method use to mark incomplete implementation
     */
    public void incompleteMethod() {
        Toast.makeText(currContext, "Functionality not implemented (YET)", Toast.LENGTH_SHORT).show();
    }

    /**
     * method to get current user
     * @return current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * setter for current user
     * @param user user to set
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * method to add a new water report to the list
     * @param r water report
     */
    public void addReport(WaterReportData r) {
        r.setId(currentReportID);
        currentReportID++;
        reports.add(r);
    }

    /**
     * getter for the water reports
     * @return list of water reports
     */
    public ArrayList<WaterReportData> getReports() {
        return reports;
    }
}
