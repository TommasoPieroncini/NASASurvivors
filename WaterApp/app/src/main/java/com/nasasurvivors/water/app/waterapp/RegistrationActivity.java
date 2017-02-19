package com.nasasurvivors.water.app.waterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class RegistrationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registerBtn = (Button) findViewById(R.id.register_btn);
    }
}
