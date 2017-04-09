package com.nasasurvivors.water.app.waterapp.model;

/**
 * Water condition model class
 */
public enum WaterCondition {
        WASTE ("Waste"), TREATABLE_CLEAR ("Treatable Clear"), TREATABLE_MUDDY ("Treatable Muddy"),
        POTABLE ("Potable");

        private final String value;

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
