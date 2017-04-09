package com.nasasurvivors.water.app.waterapp.controller;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.User;
import com.nasasurvivors.water.app.waterapp.model.UserType;


/**
 * Main page
 */
public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private TextView project;
    private Button addReport;
    private Button viewReports;
    private Button viewMap;
    private Button viewGraph;
    private LocationManager locationManager;
    private Location currentLocation;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private AppSingleton app = AppSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        addReport = (Button) findViewById(R.id.add_report_button);
        viewReports = (Button) findViewById(R.id.view_reports_button);
        viewMap = (Button) findViewById(R.id.view_water_map);
        viewGraph = (Button) findViewById(R.id.view_graph);

        addReport.setVisibility(View.INVISIBLE);
        viewReports.setVisibility(View.INVISIBLE);
        viewMap.setVisibility(View.INVISIBLE);
        viewGraph.setVisibility(View.INVISIBLE);

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(firebaseUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                app.setCurrentUser(u);
                while (app.getCurrentUser() == null) {
                }
                app.setLoggedOut(false);
                welcome.setText("Welcome, " + app.getCurrentUser().getUsername() + "!");



                addReport.setVisibility(View.VISIBLE);
                viewReports.setVisibility(View.VISIBLE);
                viewMap.setVisibility(View.VISIBLE);
                if (app.getCurrentUser().getUserType().equals(UserType.MANAGER)
                        || app.getCurrentUser().getUserType().equals(UserType.ADMIN)) {
                    viewGraph.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        currentLocation = location;
                        AppSingleton.setLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });



        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        anim1.reset();
        welcome = (TextView) findViewById(R.id.welcome);

        welcome.clearAnimation();
        welcome.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        anim2.reset();
        project = (TextView) findViewById(R.id.project_text);
        project.clearAnimation();
        project.startAnimation(anim2);

        // old handler code
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcome.setText("NASASurvivors Project");
            }
        }, 3000);*/
        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app.getCurrentUser() != null) {
                    UserType currUserType = app.getCurrentUser().getUserType();
                    if (!currUserType.equals(UserType.USER)) {
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Add Report")
                                .setMessage("Choose which report you want to create.")
                                .setPositiveButton("Water Source Report", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent toSourceReport = new Intent(getBaseContext(), WaterSourceReportActivity.class);
                                        startActivity(toSourceReport);
                                    }
                                })
                                .setNegativeButton("Water Purity Report", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent toPurityReport = new Intent(getBaseContext(), WaterPurityReportActivity.class);
                                        startActivity(toPurityReport);
                                    }
                                }).create();

                        dialog.show();
                    } else {
                        Intent toSourceReport = new Intent(getBaseContext(), WaterSourceReportActivity.class);
                        startActivity(toSourceReport);
                    }
                }
            }
        });

        viewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app.getCurrentUser() != null) {
                    UserType currUserType = app.getCurrentUser().getUserType();
                    if (currUserType.equals(UserType.MANAGER) || currUserType.equals(UserType.ADMIN)) {
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("View Reports")
                                .setMessage("Choose which reports you want to view.")
                                .setPositiveButton("Water Source Reports", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent toSourceReport = new Intent(getBaseContext(), ViewSourceReportsActivity.class);
                                        startActivity(toSourceReport);
                                    }
                                })
                                .setNegativeButton("Water Purity Reports", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent toPurityReport = new Intent(getBaseContext(), ViewPurityReportsActivity.class);
                                        startActivity(toPurityReport);
                                    }
                                }).create();

                        dialog.show();
                    } else {
                        Intent toViewReports = new Intent(getBaseContext(), ViewSourceReportsActivity.class);
                        startActivity(toViewReports);
                    }
                }
            }
        });

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app.getCurrentUser() != null) {
                    Intent toMap = new Intent(getBaseContext(), WaterMarkersMapActivity.class);
                    startActivity(toMap);
                }
            }
        });

        viewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app.getCurrentUser() != null) {
                    Intent toGraph = new Intent(getBaseContext(), GraphGenerateActivity.class);
                    startActivity(toGraph);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.editProfile:
                editProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * move to edit profile activity
     */
    private void editProfile() {
        Intent edit = new Intent(this, EditProfileActivity.class);
        startActivity(edit);
    }

    /**
     * logout and set all private data to null
     */
    private void logout() {
        Intent loggedOut = new Intent(this, LoginActivity.class);
        loggedOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Goodbye " + app.getCurrentUser().getUsername() + ".", Toast.LENGTH_SHORT).show();
        AppSingleton.logout();
        mAuth.signOut();
        startActivity(loggedOut);
    }

    //@Override
    //public void onBackPressed() {
    //    moveTaskToBack(true);
    //}
}
