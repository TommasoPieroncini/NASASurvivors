package com.nasasurvivors.water.app.waterapp.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.text.DateFormat;
import java.util.Date;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e1) {
            Log.e("ERROR", "Failed to get current location: " + e1);
        }

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

        lat.setText(String.valueOf(location.getLatitude()));
        longitude.setText(String.valueOf(location.getLongitude()));



        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {

                String date = DateFormat.getDateInstance().format(new Date());
                String time = DateFormat.getTimeInstance().format(new Date());

                double longInput = Double.valueOf(longitude.getText().toString());
                double latInput = Double.valueOf(lat.getText().toString());
                WaterType typeInput = (WaterType) waterTypeSpinner.getSelectedItem();
                WaterCondition condInput = (WaterCondition) condSpinner.getSelectedItem();

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
