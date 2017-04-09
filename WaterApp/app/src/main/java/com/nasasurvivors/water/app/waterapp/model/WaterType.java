package com.nasasurvivors.water.app.waterapp.model;

/**
 * Water type model class
 */
public enum WaterType {
    BOTTLED ("Bottled"), WELL ("Well"), STREAM ("Stream"), LAKE ("Lake"), SPRING ("Spring"),
    OTHER ("Other");

    private final String value;

    /**
     * constructor which sets value
     * @param v value to assign
     */
    WaterType(String v) {
        value = v;
    }

    /**
     * overriding toString method
     * @return return value
     */
    public String toString() {
        return value;
    }
}
