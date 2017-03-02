package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by elf82 on 2/25/2017.
 */

public enum WaterType {
    BOTTLED ("Bottled"), WELL ("Well"), STREAM ("Stream"), LAKE ("Lake"), SPRING ("Spring"), OTHER ("Other");

    private String value;

    WaterType(String v) {
        value = v;
    }

    public String toString() {
        return value;
    }
}
