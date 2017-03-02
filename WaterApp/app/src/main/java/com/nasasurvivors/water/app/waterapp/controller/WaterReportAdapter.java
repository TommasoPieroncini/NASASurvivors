package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.WaterReportData;

import java.util.List;

/**
 * Created by tommaso on 3/1/17.
 */

public class WaterReportAdapter extends ArrayAdapter<WaterReportData> {

    public WaterReportAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public WaterReportAdapter(Context context, int resource, List<WaterReportData> items) {
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

        WaterReportData r = getItem(position);

        if (r != null) {
            TextView title = (TextView) v.findViewById(R.id.adaptTitle);
            TextView lat = (TextView) v.findViewById(R.id.adaptLat);
            TextView lon = (TextView) v.findViewById(R.id.adaptLong);
            TextView type = (TextView) v.findViewById(R.id.adaptType);
            TextView cond = (TextView) v.findViewById(R.id.adaptCond);

            if (title != null) {
                title.setText("Report ID: " + r.getId()
                        + "\n\nDate: " + r.getDate()
                        + "\n\nTime: " + r.getTime().toString()
                        + "\n\nAuthor: " + r.getReporter());
            }

            if (lat != null) {
                lat.setText("Latitude: " + String.valueOf(r.getLatitude()));
            }

            if (lon != null) {
                lon.setText("Longitude: " +String.valueOf(r.getLongitude()));
            }

            if (type != null) {
                type.setText("Type: " + r.getType().toString());
            }

            if (cond != null) {
                cond.setText("Condition: " + r.getCondition().toString());
            }
        }

        return v;
    }

}
