package com.nasasurvivors.water.app.waterapp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;

public class ViewPurityReportsActivity extends AppCompatActivity {

    private ListView reportsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purity_reports);

        reportsList = (ListView) findViewById(R.id.purityReports);

        WaterPurityReportAdapter adapter = new WaterPurityReportAdapter(this,
                R.layout.adapter_view, AppSingleton.getInstance().getPurityReports());
        reportsList.setAdapter(adapter);
    }
}
