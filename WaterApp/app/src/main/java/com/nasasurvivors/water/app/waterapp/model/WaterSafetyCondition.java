package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by tommaso on 3/12/17.
 */

public enum WaterSafetyCondition {
    SAFE("Safe"), TREATABLE("Treatable"), UNSAFE("Unsafe");

    private String value;

    WaterSafetyCondition(String v) {
        value = v;
    }

    public String toString() {
        return value;
    }
}
