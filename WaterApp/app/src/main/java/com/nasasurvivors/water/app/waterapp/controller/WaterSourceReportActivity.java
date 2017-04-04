package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import java.util.Date;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nasasurvivors.water.app.waterapp.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.WaterCondition;
import com.nasasurvivors.water.app.waterapp.model.WaterSourceReport;
import com.nasasurvivors.water.app.waterapp.model.WaterType;

/**
 * Water report creation class
 */
public class WaterSourceReportActivity extends AppCompatActivity {
    private EditText longitude;
    private EditText lat;
    private Spinner waterTypeSpinner;
    private Spinner condSpinner;
    private Button submit;
    private Location currentLocation;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("WaterSourceReports");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WaterSourceReport.currSourceReportID = dataSnapshot.child("id").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentLocation = AppSingleton.getLocation();

        submit = (Button) findViewById(R.id.puritySubmit);
        longitude = (EditText) findViewById(R.id.latInput);
        lat = (EditText) findViewById(R.id.purityLong);
        waterTypeSpinner = (Spinner) findViewById(R.id.overallCondSpinner);
        condSpinner = (Spinner) findViewById(R.id.conditionSpinner);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, WaterType.values());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> condAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, WaterCondition.values());
        condAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condSpinner.setAdapter(condAdapter);


        /*if (isGpsLocationProviderEnabled()) {
            Log.e("TEST", "ENABLED");
        }*/

        /*int counter = 0;
        while (currentLocation == null && counter < 3000) {
            try {
                counter++;
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
                Log.e("ERROR", "Failed to get current location: " + e);
            }
        }*/

        if (currentLocation != null) {
            lat.setText(String.valueOf(currentLocation.getLatitude()));
            longitude.setText(String.valueOf(currentLocation.getLongitude()));
        }


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Date date = new Date();

                if (lat.getText().toString().equals("")) {
                    lat.setError("You left a field empty!");
                    lat.requestFocus();
                    return;
                }

                if (longitude.getText().toString().equals("")) {
                    longitude.setError("You left a field empty!");
                    longitude.requestFocus();
                    return;
                }

                if (!lat.getText().toString().contains(".")) {
                    lat.setError("This is not a coordinate!");
                    lat.requestFocus();
                    return;
                }

                if (!longitude.getText().toString().contains(".")) {
                    longitude.setError("This is not a coordinate!");
                    longitude.requestFocus();
                    return;
                }

                double longInput = Double.valueOf(longitude.getText().toString());
                double latInput = Double.valueOf(lat.getText().toString());
                LatLng position = new LatLng(latInput, longInput);
                WaterType typeInput = (WaterType) waterTypeSpinner.getSelectedItem();
                WaterCondition condInput = (WaterCondition) condSpinner.getSelectedItem();

                WaterSourceReport report = new WaterSourceReport(date,
                        AppSingleton.getInstance().getCurrentUser().getUsername(),
                        position, typeInput, condInput, WaterSourceReport.currSourceReportID);
                WaterSourceReport.currSourceReportID++;

                AppSingleton.getInstance().addSourceReport(report);

                myRef.child("Report " + report.getId()).setValue(report);
                myRef.child("id").setValue(WaterSourceReport.currSourceReportID);

                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
            }
        });
    }

    /*private boolean isGpsLocationProviderEnabled() {
        try {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Log.d("ERROR_TEST", e.getMessage());
        }
        return false;
    }*/
}
