package com.nasasurvivors.water.app.waterapp;

import android.content.Intent;
import android.net.Credentials;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class RegistrationActivity extends AppCompatActivity {
    private CredentialVerification creds = new CredentialVerification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText user = (EditText) findViewById(R.id.email_input);
        EditText pass = (EditText) findViewById(R.id.password_input);

        final String userStr = user.getText().toString();
        final String passStr = pass.getText().toString();

        Button registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creds.addCreds(userStr, passStr);
                Intent registered = new Intent(getBaseContext(), MainActivity.class);
                startActivity(registered);
                Toast.makeText(getBaseContext(), "Registered!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
