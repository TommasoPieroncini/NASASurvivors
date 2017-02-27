package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;


public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private TextView project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("schlesinger", "schlesinger");
        setContentView(R.layout.activity_welcome);

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        anim1.reset();
        welcome = (TextView) findViewById(R.id.welcome);
        // welcome.setText("Welcome, " + AppSingleton.getInstance().getCurrentUser().getUsername() + "!");
        welcome.setText("Welcome, " + "Zach" + "!");

        welcome.clearAnimation();
        welcome.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        anim2.reset();
        project = (TextView) findViewById(R.id.project_text);
        project.clearAnimation();
        project.startAnimation(anim2);

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcome.setText("NASASurvivors Project");
            }
        }, 3000);*/
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
        startActivity(edit);
    }

    private void logout() {
        Intent loggedOut = new Intent(this, LoginActivity.class);
        loggedOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Toast.makeText(this, "Goodbye " + AppSingleton.getInstance().getCurrentUser().getUsername() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Goodbye " + "zach" + ".", Toast.LENGTH_SHORT).show();
        AppSingleton.getInstance().setCurrentUser(null);
        startActivity(loggedOut);
    }
}
