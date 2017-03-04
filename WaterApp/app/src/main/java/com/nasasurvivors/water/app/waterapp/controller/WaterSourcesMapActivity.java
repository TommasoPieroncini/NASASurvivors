package com.nasasurvivors.water.app.waterapp.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.WaterReportData;

import java.util.ArrayList;
import java.util.HashMap;

public class WaterSourcesMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Integer, Marker> markers;
    private ArrayList<WaterReportData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_sources_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers = new HashMap<>();
        data = AppSingleton.getInstance().getReports();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarkers();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                createDialog(marker);
            }
        });
    }

    /**
     * method that creates a dialog if info window is clicked
     * @param marker clicked marker
     */
    private void createDialog(Marker marker) {
        AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(WaterSourcesMapActivity.this,R.style.MyAlertDialogTheme))
                .setTitle(marker.getTitle())
                .setMessage(marker.getSnippet())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        dialog.show();
    }

    /**
     * helper method to add markers to map
     */
    private void addMarkers() {

        for (WaterReportData wr : data) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(wr.getLatitude(), wr.getLongitude()))
                    .title("Report #" + (wr.getId() + 1))
                    .snippet(wr.getDate()
                            + "\nCreated: " + wr.getTime()
                            + "\nBy: " + wr.getReporter()
                            + "\nType: " + wr.getType()
                            + "\nCondition: " + wr.getCondition())
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.nasa_logo1))
            );

            markers.put(wr.getId(), marker);
        }

        if (data.size() != 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(data.get(0).getLatitude(), data.get(0).getLongitude())));
        } else {
            Toast.makeText(getBaseContext(), "No water reports yet!", Toast.LENGTH_SHORT).show();
        }
    }
}
