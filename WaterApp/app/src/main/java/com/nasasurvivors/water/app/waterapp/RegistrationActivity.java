package com.nasasurvivors.water.app.waterapp;

import android.net.Credentials;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class RegistrationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText user = (EditText) findViewById(R.id.email_input);
        EditText pass = (EditText) findViewById(R.id.password_input);
        Button registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
