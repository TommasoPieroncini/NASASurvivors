package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class RegistrationActivity extends AppCompatActivity {

    // Initialize UI variables
    private EditText user;
    private EditText pass;
    private EditText name;
    private EditText email;
    private Spinner typeSpinner;
    private Button registerBtn;

    // Initialize firebase variables
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseuser;

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

        // Firebase components
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseuser = firebaseAuth.getCurrentUser();


        // Populate user types spinner
        List<UserType> userTypes = Arrays.asList(UserType.USER, UserType.WORKER,
                UserType.MANAGER, UserType.ADMIN);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setPrompt("Select your user type");


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserType type = (UserType) typeSpinner.getSelectedItem();
                String userStr = user.getText().toString();
                String passStr = pass.getText().toString();
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();

                registerUser(emailStr, passStr);
                // adds info to database
                saveUserInformation();
                Intent goToWait = new Intent(getBaseContext(), WaitActivity.class);
                startActivity(goToWait);

                // Create user with database information
//                User newUser = new User(userStr, passStr, nameStr, emailStr, type);

            }
        });
    }

    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent success = new Intent(getBaseContext(), MainActivity.class);
                        Intent failure = new Intent(getBaseContext(), RegistrationActivity.class);
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(success);
                        } else {
                            Log.e("firebase", task.getException().getMessage().toString());
                            Toast.makeText(getBaseContext(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            startActivity(failure);
                        }
                    }
                });
    }

    private void saveUserInformation() {
        final String userStr = user.getText().toString();
        final String passStr = pass.getText().toString();
        final String nameStr = name.getText().toString();
        final String emailStr = email.getText().toString();
        final UserType type = UserType.valueOf(typeSpinner.getSelectedItem().toString().toUpperCase());

        User user = new User(userStr, passStr, nameStr, emailStr, type);

        // Information from database

        databaseReference.child(firebaseuser.getUid()).setValue(user);

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