package com.nasasurvivors.water.app.waterapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.nasasurvivors.water.app.waterapp.R;

/**
 * Created by zachschlesinger on 4/2/17.
 */

public class GraphActivity extends AppCompatActivity {


    // declare ui components
    private EditText longEdit;
    private EditText latEdit;
    private EditText yearEdit;
    private Button submitBtn;
    private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //initialize ui components
        longEdit = (EditText) findViewById(R.id.longInput);
        latEdit = (EditText) findViewById(R.id.purityLong);
        yearEdit = (EditText) findViewById(R.id.yearInput);
        submitBtn = (Button) findViewById(R.id.generateGraphButton);
        graphView = (GraphView) findViewById(R.id.graph);

        graphView.setVisibility(View.INVISIBLE);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphView graph = (GraphView) findViewById(R.id.graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 1),
                        new DataPoint(1, 5),
                        new DataPoint(2, 3),
                        new DataPoint(3, 2),
                        new DataPoint(4, 6)
                });
                graph.addSeries(series);
                graphView.setVisibility(View.VISIBLE);
            }
        });
    }
}
