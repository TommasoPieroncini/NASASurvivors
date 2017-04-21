package com.nasasurvivors.water.app.waterapp.controller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.UserType;
import com.nasasurvivors.water.app.waterapp.model.WaterPurityReport;
import com.nasasurvivors.water.app.waterapp.model.WaterSourceReport;

import java.util.List;

/**
 * Map of water reports class
 */
public class WaterMarkersMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SparseArray<Marker> markers;
    private List<WaterSourceReport> sourceData;
    private List<WaterPurityReport> purityData;
    private UserType currUserType;
    private final AppSingleton app = AppSingleton.getInstance();

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_sources_map);
        currUserType = app.getCurrentUser().getUserType();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if ("WaterSourceReports".equals(ds.getKey())) {
                            app.getSourceReports().clear();
                            for (DataSnapshot r : ds.getChildren()) {
                                if (!"id".equals(r.getKey())) {
                                    WaterSourceReport report = r.getValue(WaterSourceReport.class);
                                    Log.e("TESTING", r.getKey() + "  " +
                                            String.valueOf(report.getId()));
                                    app.addSourceReport(report);
                                }
                            }
                        }
                        if ("WaterPurityReports".equals(ds.getKey())) {
                            app.getPurityReports().clear();
                            for (DataSnapshot r : ds.getChildren()) {
                                if (!"id".equals(r.getKey())) {
                                    WaterPurityReport report = r.getValue(WaterPurityReport.class);
                                    Log.e("TESTING", r.getKey() + "  " + String.valueOf(
                                            report.getId()));
                                    app.addPurityReport(report);
                                }
                            }
                        }
                }
                addMarkers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * method that creates a dialog if info window is clicked
     * @param marker clicked marker
     */
    private void createDialog(Marker marker) {
        AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(
                WaterMarkersMapActivity.this,R.style.MyAlertDialogTheme))
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
        markers = new SparseArray<>();
        sourceData = app.getSourceReports();
        purityData = app.getPurityReports();
        if (mMap != null) {
            mMap.clear();
        }

        for (WaterSourceReport swr : sourceData) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(swr.getLocation().getLatitude(), swr.getLocation().
                            getLongitude()))
                    .title("Source Report #" + (swr.getId()))
                    .snippet(swr.getMonthDayYear()
                            + "\nCreated: " + swr.getTime()
                            + "\nBy: " + swr.getReporter()
                            + "\nType: " + swr.getType()
                            + "\nCondition: " + swr.getCondition())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.nasa_logo1))
            );

            markers.put(swr.getId(), marker);
        }

        if (currUserType.equals(UserType.MANAGER) || currUserType.equals(UserType.ADMIN)) {
            for (WaterPurityReport pwr : purityData) {
                Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(pwr.getLocation().getLatitude(),
                                        pwr.getLocation().getLongitude()))
                                .title("Purity Report #" + (pwr.getId()))
                                .snippet(pwr.getMonthDayYear()
                                        + "\nCreated: " + pwr.getTime()
                                        + "\nBy: " + pwr.getReporter()
                                        + "\nType: " + pwr.getOverallCondition()
                                        + "\nVirusPPM: " + pwr.getVirusPPM()
                                        + "\nContaminantPPM: " + pwr.getContaminantPPM())
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_GREEN))
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.nasa_logo1))
                );

                markers.put(sourceData.size() + pwr.getId(), marker);
            }
        }

        if (!sourceData.isEmpty()) {
            com.nasasurvivors.water.app.waterapp.model.LatLng loc = sourceData.get(0).getLocation();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(),
                    loc.getLongitude())));
        } else if (!purityData.isEmpty()
                && (currUserType.equals(UserType.MANAGER) || currUserType.equals(UserType.ADMIN))) {
            com.nasasurvivors.water.app.waterapp.model.LatLng loc = purityData.get(0).getLocation();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(),
                    loc.getLongitude())));
        }
    }
}
