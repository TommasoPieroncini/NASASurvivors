package com.nasasurvivors.water.app.waterapp.model;

/**
 * Created by elf82 on 2/25/2017
 */

/**
 * Water condition model class
 */
public enum WaterCondition {
        WASTE ("Waste"), TREATABLECLEAR ("Treatable Clear"), TREATABLEMUDDY ("Treatable Muddy"), POTABLE ("Potable");

        private String value;

        /**
         * constructor which sets value
         * @param v value to assign
         */
        WaterCondition(String v) {
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
