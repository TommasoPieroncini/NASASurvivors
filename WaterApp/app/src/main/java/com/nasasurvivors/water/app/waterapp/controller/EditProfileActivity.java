package com.nasasurvivors.water.app.waterapp.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditProfileActivity extends AppCompatActivity {

    private Button submit;
    private EditText username;
    private EditText password;
    private EditText name;
    private EditText email;
    private Spinner type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        submit = (Button) findViewById(R.id.submit);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        type = (Spinner) findViewById(R.id.type);

        // Populate user types spinner
        List<UserType> userTypes = Arrays.asList(UserType.USER, UserType.WORKER,
                UserType.MANAGER, UserType.ADMIN);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);


        final User currUser = AppSingleton.getInstance().getCurrentUser();

        username.setText(currUser.getUsername());
        password.setText(currUser.getPassword());
        name.setText(currUser.getName());
        email.setText(currUser.getEmail());
        type.setSelection(userTypes.indexOf(currUser.getUserType()));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserType userType = (UserType) type.getSelectedItem();
                final String userStr = username.getText().toString();
                final String passStr = password.getText().toString();
                final String nameStr = name.getText().toString();
                final String emailStr = email.getText().toString();

                if (CredentialVerification.getInstance().validateUsername(username, userStr) &&
                        CredentialVerification.getInstance().validatePass(password, passStr) &&
                        CredentialVerification.getInstance().validateEmail(email, emailStr)) {

                    if (!userStr.equals(currUser.getUsername()) && CredentialVerification.getInstance().getData().keySet().contains(userStr)) {
                        Toast.makeText(getBaseContext(), "Username already in use!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Alert user and modify data
                    AlertDialog dialog = new AlertDialog.Builder(EditProfileActivity.this)
                            .setTitle("Edit Profile?")
                            .setMessage("Are you sure you want to edit your profile with this information?")
                            .setPositiveButton("Yes, I'm sure.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String user = currUser.getUsername();

                                    CredentialVerification.getInstance().removeCreds(user);
                                    User newProfile = new User(userStr, passStr, nameStr, emailStr, userType);
                                    CredentialVerification.getInstance().addCreds(userStr, newProfile);
                                    AppSingleton.getInstance().setCurrentUser(newProfile);

                                    Toast.makeText(getBaseContext(), "You've edited your profile!", Toast.LENGTH_SHORT).show();
                                    Intent back = new Intent(getBaseContext(), MainActivity.class);
                                    back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(back);
                                }
                            })
                            .setNegativeButton("Never mind.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();

                    dialog.show();
                }
            }
        });
    }
}
