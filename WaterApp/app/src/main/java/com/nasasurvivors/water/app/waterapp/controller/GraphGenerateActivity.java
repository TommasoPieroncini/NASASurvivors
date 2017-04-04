package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.WaterPurityReport;

import java.util.ArrayList;

/**
 * class to generate history report graph
 */
public class GraphGenerateActivity extends AppCompatActivity {

    private Button generate;
    private GraphView graph;
    private EditText latInput;
    private EditText longInput;
    private EditText yearInput;
    private RadioGroup virOrCont;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_generate);

        generate = (Button) findViewById(R.id.generateGraphButton);
        graph = (GraphView) findViewById(R.id.graph);
        latInput = (EditText) findViewById(R.id.graphLat);
        longInput = (EditText) findViewById(R.id.graphLong);
        yearInput = (EditText) findViewById(R.id.yearInput);
        virOrCont = (RadioGroup) findViewById(R.id.radioGroup);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (ds.getKey().toString().equals("WaterPurityReports")) {
                        AppSingleton.getInstance().getPurityReports().clear();
                        for (DataSnapshot r: ds.getChildren()) {
                            if (!r.getKey().toString().equals("id")) {
                                WaterPurityReport report = r.getValue(WaterPurityReport.class);
                                AppSingleton.getInstance().addPurityReport(report);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!yearInput.getText().toString().equals("") && !latInput.getText().toString().equals("") && !longInput.getText().toString().equals("") && virOrCont.getCheckedRadioButtonId() != -1) {
                    graph.removeAllSeries();
                    int lat = Double.valueOf(latInput.getText().toString()).intValue();
                    int lng = Double.valueOf(longInput.getText().toString()).intValue();
                    String year = yearInput.getText().toString();
                    int id = virOrCont.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(id);
                    String type = rb.getText().toString();

                    ArrayList<DataPoint> virusData = new ArrayList<>();
                    ArrayList<DataPoint> contaminantData = new ArrayList<>();
                    int i = 0;

                    for (WaterPurityReport p : AppSingleton.getInstance().getPurityReports()) {
                        if ((int) p.getLocation().getLatitude() == lat && (int) p.getLocation().getLongitude() == lng) {
                            Log.e("TESTING", p.toString());
                            // virusData.add(new DataPoint((int) p.getDate().getTime(), p.getVirusPPM()));
                            // contaminantData.add(new DataPoint((int) p.getDate().getTime(), p.getContaminantPPM()));
                            virusData.add(new DataPoint(i, p.getVirusPPM()));
                            contaminantData.add(new DataPoint(i, p.getContaminantPPM()));
                            i++;
                        }
                    }

                    DataPoint[] data = new DataPoint[virusData.size()];
                    PointsGraphSeries<DataPoint> series = null;
                    if (type.equals("Virus")) {
                        series = new PointsGraphSeries<>(virusData.toArray(data));
                    } else if (type.equals("Contaminant")) {
                        series = new PointsGraphSeries<>(contaminantData.toArray(data));
                    }
                    Log.e("TESTING", series.toString());
                    graph.addSeries(series);
                    //graph.getLegendRenderer().setVisible(true);
                } else {
                    Toast.makeText(getBaseContext(), "Insert some information first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
