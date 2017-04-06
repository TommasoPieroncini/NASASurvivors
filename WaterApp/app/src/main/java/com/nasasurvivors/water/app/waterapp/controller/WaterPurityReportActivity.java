package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nasasurvivors.water.app.waterapp.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.WaterPurityReport;
import com.nasasurvivors.water.app.waterapp.model.WaterSafetyCondition;

import java.util.Date;

/**
 * create and submit a new report through this activity
 */
public class WaterPurityReportActivity extends AppCompatActivity {

    private EditText latInput;
    private EditText longInput;
    private EditText virusInput;
    private EditText contaminantInput;
    private Spinner safetySpinner;
    private Location currentLocation;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("WaterPurityReports");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_purity_report);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WaterPurityReport.currPurityReportID = dataSnapshot.child("id").getValue(Integer.class);
                //Log.e("TESTING", "GETTING ID " + WaterPurityReport.currPurityReportID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button submit = (Button) findViewById(R.id.puritySubmit);
        latInput = (EditText) findViewById(R.id.purityLat);
        longInput = (EditText) findViewById(R.id.purityLong);
        virusInput = (EditText) findViewById(R.id.virusPPM);
        contaminantInput = (EditText) findViewById(R.id.contaminantPPM);
        safetySpinner = (Spinner) findViewById(R.id.overallCondSpinner);

        final ArrayAdapter<String> safetyAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, WaterSafetyCondition.values());
        safetyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        safetySpinner.setAdapter(safetyAdapter);

        if (currentLocation != null) {
            latInput.setText(String.valueOf(currentLocation.getLatitude()));
            longInput.setText(String.valueOf(currentLocation.getLongitude()));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();

                if (latInput.getText().toString().equals("")) {
                    latInput.setError("You left a field empty!");
                    latInput.requestFocus();
                    return;
                }

                if (longInput.getText().toString().equals("")) {
                    longInput.setError("You left a field empty!");
                    longInput.requestFocus();
                    return;
                }

                if (!latInput.getText().toString().contains(".")) {
                    latInput.setError("This is not a coordinate!");
                    latInput.requestFocus();
                    return;
                }

                if (!longInput.getText().toString().contains(".")) {
                    longInput.setError("This is not a coordinate!");
                    longInput.requestFocus();
                    return;
                }

                double latitude = Double.valueOf(latInput.getText().toString());
                double longitude = Double.valueOf(longInput.getText().toString());
                LatLng position = new LatLng(latitude, longitude);
                WaterSafetyCondition safetyType = (WaterSafetyCondition) safetySpinner.getSelectedItem();
                int virus = Integer.valueOf(virusInput.getText().toString());
                int contaminant = Integer.valueOf(contaminantInput.getText().toString());

                WaterPurityReport report = new WaterPurityReport(date,
                        AppSingleton.getInstance().getCurrentUser().getUsername(),
                        position, safetyType, virus, contaminant, WaterPurityReport.currPurityReportID++);

                AppSingleton.getInstance().addPurityReport(report);

                myRef.child("Report " + report.getId()).setValue(report);
                myRef.child("id").setValue(WaterPurityReport.currPurityReportID);

                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
            }
        });
    }
}
