package com.nasasurvivors.water.app.waterapp.model;

import java.text.DateFormat;

/**
 * Created by elf82 on 2/25/2017.
 */

public class WaterReportData {
    private DateFormat date;
    private DateFormat time;
    private String reporter;
    private Double longitude;
    private Double latitude;
    private WaterType type;
    private WaterCondition condition;

    public WaterReportData (DateFormat date, DateFormat time, String user, Double longi, Double lat, WaterType typ, WaterCondition con) {

        this.date = date;
        this.time = time;
        reporter = user;
        longitude = longi;
        latitude = lat;
        type = typ;
        condition = con;
    }

    public DateFormat getDate() {
        return date;
    }

    public DateFormat getTime() {
        return time;
    }

    public String getReporter() {
        return reporter;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public WaterType getType() {
        return type;
    }

    public WaterCondition getCondition() {
        return condition;
    }
}
