package com.nasasurvivors.water.app.waterapp.controller;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.CredentialVerification;
import com.nasasurvivors.water.app.waterapp.model.User;
import com.nasasurvivors.water.app.waterapp.model.UserType;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Zach Schlesinger on 3/10/17.
 */

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    // declare UI components
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextDisplayName;
    private EditText editTextFirst;
    private EditText editTextLast;
    private Button registerBtn;
    private Spinner spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextEmail = (EditText) findViewById(R.id.email_input);
        editTextPassword = (EditText) findViewById(R.id.password_input);
        editTextDisplayName = (EditText) findViewById(R.id.username_input);
        editTextFirst = (EditText) findViewById(R.id.name_input);
        registerBtn = (Button) findViewById(R.id.register_btn);
        spinnerType = (Spinner) findViewById(R.id.type_spinner);

        List<UserType> userTypes = Arrays.asList(UserType.USER, UserType.WORKER,
                UserType.MANAGER, UserType.ADMIN);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setPrompt("Select your user type");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Pickup", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Pickup", "onAuthStateChanged:signed_out");
                }
            }
        };

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!CredentialVerification.verifyEmail(email)) {
                    Toast.makeText(getBaseContext(), "Invalid Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String message = CredentialVerification.verifyPassword(password);
                if (!message.isEmpty()) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }
                createAccount(email, password);
                signIn(email,password);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * registers account with fireBase
     * @param email email to register
     * @param password password to register
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Pickup", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                DatabaseReference myRef = database.getReference(user.getUid());
                                UserType type = (UserType) spinnerType.getSelectedItem();
                                User u = new User(editTextDisplayName.getText().toString(), editTextPassword.getText().toString(), editTextFirst.getText().toString(), editTextEmail.getText().toString(), type);
                                myRef.setValue(u);
                            }
                            Toast.makeText(getBaseContext(), "Authentication succeeded",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    /**
     * sign in with fireBase
     * @param email email to sign in
     * @param password password to sign in
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Pickup", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Authentication succeeded",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        } else {
                            Log.w("Pickup", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}