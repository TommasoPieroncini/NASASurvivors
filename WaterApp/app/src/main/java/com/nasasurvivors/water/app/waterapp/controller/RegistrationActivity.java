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
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.CredentialVerification;
import com.nasasurvivors.water.app.waterapp.model.User;
import com.nasasurvivors.water.app.waterapp.model.UserType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class RegistrationActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private EditText name;
    private EditText email;
    private Spinner typeSpinner;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // UI Components
        typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        registerBtn = (Button) findViewById(R.id.register_btn);
        user = (EditText) findViewById(R.id.username_input);
        pass = (EditText) findViewById(R.id.password_input);
        name = (EditText) findViewById(R.id.name_input);
        email = (EditText) findViewById(R.id.email_input);

        // Populate user types spinner
        List<UserType> userTypes = Arrays.asList(UserType.NONE, UserType.USER, UserType.WORKER,
                UserType.MANAGER, UserType.ADMIN);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserType type = (UserType) typeSpinner.getSelectedItem();
                String userStr = user.getText().toString();
                String passStr = pass.getText().toString();
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();

                if (CredentialVerification.getInstance().validateUsername(user, userStr) &&
                CredentialVerification.getInstance().validatePass(pass, passStr) &&
                CredentialVerification.getInstance().validateEmail(email, emailStr)) {

                    // Create user
                    User newUser = new User(userStr, passStr, nameStr, emailStr, type);

                    if (CredentialVerification.getInstance().getData().keySet().contains(userStr)) {
                        Toast.makeText(getBaseContext(), "Username already in use!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (CredentialVerification.getInstance().addCreds(userStr, newUser)) {
                        Intent registered = new Intent(getBaseContext(), MainActivity.class);

                        // Set new user
                        AppSingleton.getInstance().setCurrentUser(newUser);

                        registered.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(registered);

                        Toast.makeText(getBaseContext(), "You registered, " + userStr + "!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Something went wrong! Try again!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
