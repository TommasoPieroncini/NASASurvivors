package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 3/12/17
 */

/**
 * water overall condition
 */
public enum WaterSafetyCondition {
    SAFE("Safe"), TREATABLE("Treatable"), UNSAFE("Unsafe");

    private String value;

    /**
     * enum constructor
     * @param v string value
     */
    WaterSafetyCondition(String v) {
        value = v;
    }

    /**
     * overwritten toString method
     * @return string representation of water overall condition
     */
    public String toString() {
        return value;
    }
}
