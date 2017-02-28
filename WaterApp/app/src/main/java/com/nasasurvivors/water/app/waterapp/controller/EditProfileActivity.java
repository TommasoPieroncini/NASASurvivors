package com.nasasurvivors.water.app.waterapp.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.nasasurvivors.water.app.waterapp.model.CredentialVerification;
import com.nasasurvivors.water.app.waterapp.model.User;
import com.nasasurvivors.water.app.waterapp.model.UserType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private Button submit;
    private EditText username;
    private EditText password;
    private EditText name;
    private EditText email;
    private Spinner type;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseuser;


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

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseuser = firebaseAuth.getCurrentUser();

        // Populate user types spinner
        List<UserType> userTypes = Arrays.asList(UserType.USER, UserType.WORKER,
                UserType.MANAGER, UserType.ADMIN);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);


        final User currUser = AppSingleton.getInstance().getCurrentUser();

        username.setText(currUser.user);
        password.setText(currUser.password);
        name.setText(currUser.name);
        email.setText(currUser.email);
        type.setSelection(userTypes.indexOf(currUser.user));

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

                    if (!userStr.equals(currUser.user) && CredentialVerification.getInstance().getData().keySet().contains(userStr)) {
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
                                    String user = currUser.user;

//                                    CredentialVerification.getInstance().removeCreds(user);
//                                    User newProfile = new User(userStr, passStr, nameStr, emailStr, userType);
//                                    CredentialVerification.getInstance().addCreds(userStr, newProfile);
//                                    AppSingleton.getInstance().setCurrentUser(newProfile);
                                    saveUserInformation();

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

    private void saveUserInformation() {
        final String userStr = username.getText().toString();
        final String passStr = password.getText().toString();
        final String nameStr = name.getText().toString();
        final String emailStr = email.getText().toString();
        final UserType type1 = UserType.valueOf(type.getSelectedItem().toString().toUpperCase());

        User user = new User(userStr, passStr, nameStr, emailStr, type1);

        // Information from database

        databaseReference.child(firebaseuser.getUid()).setValue(user);
        databaseReference.push().getKey();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    HashMap<String, String> userInfoHashMap = (HashMap<String, String>) child.getValue();

                    if (userInfoHashMap.get("email").equals(emailStr)) {
                        String dbUser = userInfoHashMap.get("user");
                        String dbPass = userInfoHashMap.get("password");
                        String dbName = userInfoHashMap.get("name");
                        String dbEmail = userInfoHashMap.get("email");
                        String dbType = userInfoHashMap.get("type");
                        User newUser = new User(dbUser, dbPass, dbName, dbEmail, UserType.valueOf(dbType));
                        AppSingleton.getInstance().setCurrentUser(newUser);
                        Toast.makeText(getBaseContext(), "Information Saved...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Houston, we have a problem", Toast.LENGTH_SHORT).show();
                Log.e("Database error", databaseError.getMessage().toString());
            }
        });

    }
}