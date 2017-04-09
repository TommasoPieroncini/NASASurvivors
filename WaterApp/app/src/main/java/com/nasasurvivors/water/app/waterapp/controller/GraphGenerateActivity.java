package com.nasasurvivors.water.app.waterapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

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

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference();
    private final AppSingleton app = AppSingleton.getInstance();

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
                    if (("WaterPurityReports").equals(ds.getKey())) {
                        app.getPurityReports().clear();
                        for (DataSnapshot r: ds.getChildren()) {
                            if (!("id").equals(r.getKey())) {
                                WaterPurityReport report = r.getValue(WaterPurityReport.class);
                                app.addPurityReport(report);
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
                if ((!("").equals(yearInput.getText().toString())) && (!("").
                        equals(latInput.getText().toString())) && (!("").
                        equals(longInput.getText().toString())) &&
                        (virOrCont.getCheckedRadioButtonId() != -1)) {
                    graph.removeAllSeries();
                    int lat = Double.valueOf(latInput.getText().toString()).intValue();
                    int lng = Double.valueOf(longInput.getText().toString()).intValue();
                    // String year = yearInput.getText().toString();
                    int id = virOrCont.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(id);
                    String type = rb.getText().toString();

                    List<DataPoint> virusData = new ArrayList<>();
                    List<DataPoint> contaminantData = new ArrayList<>();
                    List<WaterPurityReport> selectedReports = new ArrayList<>();
                    int i = 0;

                    for (WaterPurityReport p : app.getPurityReports()) {
                        if (((int) p.getLocation().getLatitude() == lat) &&
                                ((int) p.getLocation().getLongitude() == lng)) {
                            virusData.add(new DataPoint(i, p.getVirusPPM()));
                            contaminantData.add(new DataPoint(i, p.getContaminantPPM()));
                            selectedReports.add(p);
                            i++;
                        }
                    }

                    DataPoint[] data = new DataPoint[virusData.size()];
                    PointsGraphSeries<DataPoint> series = null;
                    if (("Virus").equals(type)) {
                        series = new PointsGraphSeries<>(virusData.toArray(data));
                    } else if (("Contaminant").equals(type)) {
                        series = new PointsGraphSeries<>(contaminantData.toArray(data));
                    }
                    for (WaterPurityReport r : selectedReports) {
                        Toast.makeText(getBaseContext(), r.toString(), Toast.LENGTH_SHORT).show();
                    }
                    graph.addSeries(series);
                } else {
                    Toast.makeText(getBaseContext(), "Insert some information first!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
