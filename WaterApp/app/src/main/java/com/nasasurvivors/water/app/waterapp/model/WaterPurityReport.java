package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 3/12/17.
 */

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

/**
 * model class for a water purity report
 */
public class WaterPurityReport {

    private Date date;
    private int id;
    private User worker;
    private LatLng location;
    private WaterSafetyCondition overallCondition;
    private int virusPPM;
    private int contaminantPPM;

    public WaterPurityReport(Date date, User worker,
                             LatLng location, WaterSafetyCondition overallCondition,
                             int virusPPM, int contaminantPPM) {
        this.date = date;
        this.worker = worker;
        this.location = location;
        this.overallCondition = overallCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * getter for date
     * @return date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * getter for time
     * @return time string
     */
    public String getTime() {
        return DateFormat.getTimeInstance().format(date);
    }

    /**
     * getter for date string
     * @return date string
     */
    public String getMonthDayYear() {
        return DateFormat.getDateInstance().format(date);
    }

    /**
     * getter for location
     * @return location
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter for worker
     * @return worker
     */
    public User getAuthor() {
        return worker;
    }

    /**
     * getter for safety condition
     * @return safety condition
     */
    public WaterSafetyCondition getOverallCondition() {
        return overallCondition;
    }

    /**
     * getter for virus PPM
     * @return virus PPM
     */
    public int getVirusPPM() {
        return virusPPM;
    }

    /**
     * getter for contaminant PPM
     * @return contaminant PPM
     */
    public int getContaminantPPM() {
        return contaminantPPM;
    }

    public void setId(int id) {
        this.id = id;
    }
}
