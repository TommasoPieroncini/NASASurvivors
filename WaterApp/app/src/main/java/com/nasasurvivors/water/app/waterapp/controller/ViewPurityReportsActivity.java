package com.nasasurvivors.water.app.waterapp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.WaterPurityReport;

/**
 * activity with purity reports list view
 */
public class ViewPurityReportsActivity extends AppCompatActivity {

    private ListView reportsList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purity_reports);

        reportsList = (ListView) findViewById(R.id.purityReports);
        WaterPurityReportAdapter adapter = new WaterPurityReportAdapter(this,
                R.layout.adapter_view, AppSingleton.getInstance().getPurityReports());

        DatabaseReference myRef = database.getReference("WaterPurityReports");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppSingleton.getInstance().getPurityReports().clear();
                for (DataSnapshot r : dataSnapshot.getChildren()) {
                    //Log.e("TESTING", r.toString());
                    if (!r.getKey().equals("id")) {
                        WaterPurityReport report = r.getValue(WaterPurityReport.class);
                        AppSingleton.getInstance().addPurityReport(report);
                    }
                }
                WaterPurityReportAdapter adapter = new WaterPurityReportAdapter(getBaseContext(),
                        R.layout.adapter_view, AppSingleton.getInstance().getPurityReports());
                reportsList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        reportsList.setAdapter(adapter);
    }
}
