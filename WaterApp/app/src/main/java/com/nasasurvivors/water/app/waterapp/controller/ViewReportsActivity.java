package com.nasasurvivors.water.app.waterapp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;

public class ViewReportsActivity extends AppCompatActivity {

    private ListView reportsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        reportsList = (ListView) findViewById(R.id.reports_view);

        WaterReportAdapter adapter = new WaterReportAdapter(this, R.layout.adapter_view, AppSingleton.getInstance().getReports());
        reportsList.setAdapter(adapter);
    }
}
