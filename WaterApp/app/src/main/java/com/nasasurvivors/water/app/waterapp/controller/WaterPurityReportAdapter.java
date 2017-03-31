package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.WaterPurityReport;

import java.util.List;

/**
 * Created by tommaso on 3/1/17.
 */

/**
 * Custom list view for water reports class
 */
public class WaterPurityReportAdapter extends ArrayAdapter<WaterPurityReport> {

    /**
     * constructor with a textview
     * @param context current context
     * @param textViewResourceId textview resource
     */
    public WaterPurityReportAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /**
     * constructor with layout resource and list of items
     * @param context current context
     * @param resource layout resource
     * @param items list of items
     */
    public WaterPurityReportAdapter(Context context, int resource, List<WaterPurityReport> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_view, null);
        }

        WaterPurityReport r = getItem(position);

        if (r != null) {
            TextView title = (TextView) v.findViewById(R.id.adaptTitle);
            TextView lat = (TextView) v.findViewById(R.id.adaptLat);
            TextView lon = (TextView) v.findViewById(R.id.adaptLong);
            TextView type = (TextView) v.findViewById(R.id.adaptType);
            TextView virusAndContaminant = (TextView) v.findViewById(R.id.adaptCond);

            if (title != null) {
                title.setText("Report ID: " + r.getId()
                        + "\n\nDate: " + r.getDate()
                        + "\n\nTime: " + r.getTime().toString()
                        + "\n\nAuthor: " + r.getAuthor());
            }

            if (lat != null) {
                lat.setText("Latitude: " + String.valueOf(r.getLocation().getLatitude()));
            }

            if (lon != null) {
                lon.setText("Longitude: " + String.valueOf(r.getLocation().getLongitude()));
            }

            if (type != null) {
                type.setText("Overall Condition: " + r.getOverallCondition().toString());
            }

            if (virusAndContaminant != null) {
                virusAndContaminant.setText("Virus PPM: " + r.getVirusPPM()
                                            + "\nContaminant PPM: " + r.getContaminantPPM());
            }
        }

        return v;
    }

}
