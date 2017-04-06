package com.nasasurvivors.water.app.waterapp.model;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

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
    private static ArrayList<WaterSourceReport> sourceReports = new ArrayList<>();
    private static ArrayList<WaterPurityReport> purityReports = new ArrayList<>();
    private static Location currentLocation;
    public static boolean loggedOut = true;

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
     * method to add a new water source report to the list
     * @param r water source report
     */
    public void addSourceReport(WaterSourceReport r) {
        sourceReports.add(r);
    }

    /**
     * method to add a new water purity report to the list
     * @param r water purity report
     */
    public void addPurityReport(WaterPurityReport r) {
        purityReports.add(r);
    }

    /**
     * getter for the water reports
     * @return list of water reports
     */
    public ArrayList<WaterSourceReport> getSourceReports() {
        return sourceReports;
    }

    /**
     * getter for the water reports
     * @return list of water reports
     */
    public ArrayList<WaterPurityReport> getPurityReports() {
        return purityReports;
    }

    /**
     * setter for location
     * @param l current location
     */
    public static void setLocation(Location l) {
        currentLocation = l;
    }

    /**
     * gett for location
     * @return current location
     */
    public static Location getLocation() {
        return currentLocation;
    }

    /**
     * set loggedOut bit
     * @param logOut
     */
    public void setLoggedOut(boolean logOut) {
        loggedOut = logOut;
    }

    /**
     * return whether user is loggedOut
     * @return loggedOut boolean
     */
    public boolean getLoggedOut() {
        return loggedOut;
    }

    public static boolean logout() {
        AppSingleton.getInstance().setCurrentUser(null);
        AppSingleton.getInstance().setLoggedOut(true);
        AppSingleton.getInstance().getSourceReports().clear();
        AppSingleton.getInstance().getPurityReports().clear();
        AppSingleton.setLocation(null);
        return true;
    }
}
