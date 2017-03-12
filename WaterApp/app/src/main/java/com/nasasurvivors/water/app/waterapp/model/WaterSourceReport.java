package com.nasasurvivors.water.app.waterapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by elf82 on 2/25/2017.
 */

/**
 * Water report model class
 */
public class WaterSourceReport {
    private Date date;
    private String reporter;
    private LatLng location;
    private WaterType type;
    private WaterCondition condition;
    private int id;

    // future improvement - purity report for each source report
    private boolean hasPurityReport;

    /**
     * constructor with all water data
     * @param date day when the report was submitted
     * @param user user that submitted the report
     * @param location location of the water source
     * @param typ water source type
     * @param con water source condition
     */
    public WaterSourceReport(Date date, String user, LatLng location, WaterType typ, WaterCondition con) {

        this.date = date;
        reporter = user;
        this.location = location;
        type = typ;
        condition = con;
    }

    /**
     * date getter
     * @return returns date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * time getter
     * @return returns time string
     */
    public String getTime() {
        return DateFormat.getTimeInstance().format(date);
    }

    /**
     * date string getter
     * @return returns date string
     */
    public String getMonthDayYear() {
        return DateFormat.getDateInstance().format(date);
    }

    /**
     * getter for author
     * @return author
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * getter for location
     * @return location
     */
    public LatLng getLocation() {
        return location;
    }
    /**
     * getter for water type
     * @return water type
     */
    public WaterType getType() {
        return type;
    }

    /**
     * getter for water condition
     * @return water condition
     */
    public WaterCondition getCondition() {
        return condition;
    }

    /**
     * setter for report id
     * @param id report id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for report id
     * @return report id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for hasPurityReport
     * @param hasPurityReport whether a purity report has been submitted for this source report
     */
    public void setHasPurityReport(boolean hasPurityReport) {
        this.hasPurityReport = hasPurityReport;
    }
}
