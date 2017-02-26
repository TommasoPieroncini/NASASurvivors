package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by elf82 on 2/25/2017.
 */

public class WaterReportData {
    private int date;
    private int time;
    private String reporter;
    private int longitude;
    private int latitude;
    private WaterType type;
    private WaterCondition condition;

    public WaterReportData (int date, int time, String user, int longi, int lat, WaterType typ, WaterCondition con) {

        this.date = date;
        this.time = time;
        reporter = user;
        longitude = longi;
        latitude = lat;
        type = typ;
        condition = con;
    }

    public int getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    public String getReporter() {
        return reporter;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public WaterType getType() {
        return type;
    }

    public WaterCondition getCondition() {
        return condition;
    }
}
