package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nasasurvivors.water.app.waterapp.R;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome, " + AppSingleton.getInstance().getUsername() + "!");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView welcome = (TextView) findViewById(R.id.welcome);
                welcome.setText("NASASurvivors Project");
            }
        }, 3000);
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

    private void editProfile() {
        Intent edit = new Intent(this, EditProfileActivity.class);
        edit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Editing time!", Toast.LENGTH_SHORT).show();
        startActivity(edit);
    }

    private void logout() {
        Intent loggedOut = new Intent(this, MainActivity.class);
        loggedOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Goodbye " + AppSingleton.getInstance().getUsername() + ".", Toast.LENGTH_SHORT).show();
        AppSingleton.getInstance().setUsername(null);
        startActivity(loggedOut);
    }
}
