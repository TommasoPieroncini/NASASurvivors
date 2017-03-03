package com.nasasurvivors.water.app.waterapp.model;

import java.text.DateFormat;

/**
 * Created by elf82 on 2/25/2017.
 */

public class WaterReportData {
    private String date;
    private String time;
    private String reporter;
    private Double longitude;
    private Double latitude;
    private WaterType type;
    private WaterCondition condition;
    private int id;

    /**
     * constructor with all water data
     * @param date day when the report was submitted
     * @param time time when the report was submitted
     * @param user user that submitted the report
     * @param longi location's longitude
     * @param lat location's latitude
     * @param typ water source type
     * @param con water source condition
     */
    public WaterReportData (String date, String time, String user, Double longi, Double lat, WaterType typ, WaterCondition con) {

        this.date = date;
        this.time = time;
        reporter = user;
        longitude = longi;
        latitude = lat;
        type = typ;
        condition = con;
    }

    /**
     * date getter
     * @return returns date
     */
    public String getDate() {
        return date;
    }

    /**
     * time getter
     * @return returns time
     */
    public String getTime() {
        return time;
    }

    /**
     * getter for author
     * @return author
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * getter for longitude
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * getter for latitude
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
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
}
