package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by elf82 on 2/25/2017.
 */

public enum WaterCondition {
        WASTE ("Waste"), TREATABLECLEAR ("Treatable Clear"), TREATABLEMUDDY ("Treatable Muddy"), POTABLE ("Potable");

        private String value;

        WaterCondition(String v) {
                value = v;
        }

        public String toString() {
                return value;
        }
}
