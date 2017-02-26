package com.nasasurvivors.water.app.waterapp.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.WaterCondition;
import com.nasasurvivors.water.app.waterapp.model.WaterReportData;
import com.nasasurvivors.water.app.waterapp.model.WaterType;

public class WaterReportActivity extends AppCompatActivity {
    private EditText date;
    private EditText time;
    private EditText longitude;
    private EditText lat;
    private Spinner waterTypeSpinner;
    private Spinner condSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        date = (EditText) findViewById(R.id.dateInput);
        time = (EditText) findViewById(R.id.timeInput);
        longitude = (EditText) findViewById(R.id.longInput);
        lat = (EditText) findViewById(R.id.latInput);
        waterTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        condSpinner = (Spinner) findViewById(R.id.conditionSpinner);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterType.values());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> condAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterCondition.values());
        condAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condSpinner.setAdapter(condAdapter);

        int dateInput;
        int timeInput;
        int longInput;
        int latInput;
        WaterType typeInput;
        WaterCondition condInput;


        dateInput = Integer.valueOf(date.getText().toString());
        timeInput = Integer.valueOf(time.getText().toString());
        longInput = Integer.valueOf(longitude.getText().toString());
        latInput = Integer.valueOf(lat.getText().toString());
        typeInput = (WaterType) waterTypeSpinner.getSelectedItem();
        condInput = (WaterCondition) condSpinner.getSelectedItem();

        WaterReportData report = new WaterReportData(dateInput, timeInput, AppSingleton.getInstance().getUsername(), longInput, latInput, typeInput, condInput);





    }

}
