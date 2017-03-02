package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.WaterCondition;
import com.nasasurvivors.water.app.waterapp.model.WaterReportData;
import com.nasasurvivors.water.app.waterapp.model.WaterType;

public class WaterReportActivity extends AppCompatActivity {
    private EditText longitude;
    private EditText lat;
    private Spinner waterTypeSpinner;
    private Spinner condSpinner;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report);

        submit = (Button) findViewById(R.id.submitButton);
        longitude = (EditText) findViewById(R.id.longInput);
        lat = (EditText) findViewById(R.id.latInput);
        waterTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        condSpinner = (Spinner) findViewById(R.id.conditionSpinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, WaterType.values());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> condAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, WaterCondition.values());
        condAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condSpinner.setAdapter(condAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {

                String date = DateFormat.getDateInstance().format(new Date());
                String time = DateFormat.getTimeInstance().format(new Date());
                double longInput;
                double latInput;
                WaterType typeInput;
                WaterCondition condInput;

                longInput = Double.valueOf(longitude.getText().toString());
                latInput = Double.valueOf(lat.getText().toString());
                typeInput = (WaterType) waterTypeSpinner.getSelectedItem();
                condInput = (WaterCondition) condSpinner.getSelectedItem();

                if (!longitude.getText().toString().contains(".")) {
                    longitude.setError("This is not a coordinate!");
                    longitude.requestFocus();
                }

                if (!lat.getText().toString().contains(".")) {
                    lat.setError("This is not a coordinate!");
                    lat.requestFocus();
                }

                WaterReportData report = new WaterReportData(date, time,
                        AppSingleton.getInstance().getCurrentUser().getUsername(),
                        longInput, latInput , typeInput, condInput);

                AppSingleton.getInstance().addReport(report);

                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
            }
        });




    }

}
