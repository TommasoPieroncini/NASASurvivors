package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.CredentialVerification;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // UI Components
        Spinner typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        Button registerBtn = (Button) findViewById(R.id.register_btn);

        // Populate user types spinner
        List<String> userTypes = Arrays.asList("What type of user are you?", "User", "Worker", "Manager", "Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText user = (EditText) findViewById(R.id.username_input);
                EditText pass = (EditText) findViewById(R.id.password_input);

                String userStr = user.getText().toString();
                String passStr = pass.getText().toString();

                if (CredentialVerification.getInstance().addCreds(userStr, passStr)) {
                    Intent registered = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(registered);
                    Toast.makeText(getBaseContext(), "Registered " + userStr, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Something is wrong!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
