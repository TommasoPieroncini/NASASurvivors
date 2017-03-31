package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 3/30/17.
 */

public class LatLng {
    private double latitude;
    private double longitude;

    public LatLng() {
    }

    public LatLng(double lt, double lg) {
        latitude = lt;
        longitude = lg;
    }

    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
}
