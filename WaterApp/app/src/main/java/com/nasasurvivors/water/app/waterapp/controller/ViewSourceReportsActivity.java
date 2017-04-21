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
import com.nasasurvivors.water.app.waterapp.model.WaterSourceReport;

/**
 * View reports class
 */
public class ViewSourceReportsActivity extends AppCompatActivity {

    private ListView reportsList;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        reportsList = (ListView) findViewById(R.id.reports_view);
        WaterSourceReportAdapter adapter = new WaterSourceReportAdapter(this, R.layout.adapter_view,
                AppSingleton.getInstance().getSourceReports());

        DatabaseReference myRef = database.getReference("WaterSourceReports");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppSingleton.getInstance().getSourceReports().clear();
                for (DataSnapshot r : dataSnapshot.getChildren()) {
                    //Log.e("TESTING", r.toString());
                    if (!("id").equals(r.getKey())) {
                        WaterSourceReport report = r.getValue(WaterSourceReport.class);
                        AppSingleton.getInstance().addSourceReport(report);
                    }
                }
                WaterSourceReportAdapter adapter = new WaterSourceReportAdapter(getBaseContext(),
                        R.layout.adapter_view,
                        AppSingleton.getInstance().getSourceReports());
                reportsList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reportsList.setAdapter(adapter);
    }
}
