package com.nasasurvivors.water.app.waterapp.model;

/**
 * water overall condition
 */
public enum WaterSafetyCondition {
    SAFE("Safe"), TREATABLE("Treatable"), UNSAFE("Unsafe");

    private final String value;

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
