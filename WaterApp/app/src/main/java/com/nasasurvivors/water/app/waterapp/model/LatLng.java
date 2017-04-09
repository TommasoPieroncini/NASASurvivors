package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by Tommaso on 3/30/17.
 * LatLng class to handle location objects
 */
public class LatLng {
    private double latitude;
    private double longitude;

    /**
     * no params constructor for fireBase object translation
     */
    public LatLng() {
    }

    /**
     * latLng constructor
     * @param lt latitude
     * @param lg longitude
     */
    public LatLng(double lt, double lg) {
        latitude = lt;
        longitude = lg;
    }

    /**
     * getter for latitude
     * @return latitude
     */
    public double getLatitude() {return latitude;}

    /**
     * getter for longitude
     * @return longitude
     */
    public double getLongitude() {return longitude;}

    /**
     * overrides toString method
     * @return string representation of latLng object
     */
    public String toString() {
        return "lat: " + latitude + ", " + "long: " + longitude;
    }
}
