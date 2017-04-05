package com.nasasurvivors.water.app.waterapp.controller;

import android.app.Activity;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nasasurvivors.water.app.waterapp.R;
import com.nasasurvivors.water.app.waterapp.model.AppSingleton;
import com.nasasurvivors.water.app.waterapp.model.CredentialVerification;
import com.nasasurvivors.water.app.waterapp.model.User;
import com.nasasurvivors.water.app.waterapp.model.UserType;

import java.util.Arrays;
import java.util.List;

/**
 * Edit profile class
 */
public class EditProfileActivity extends AppCompatActivity {

    //declare firebase components
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser fbuser;


    // declare UI components
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

        mAuth = FirebaseAuth.getInstance();


        // Populate user types spinner
        List<UserType> userTypes = Arrays.asList(UserType.USER, UserType.WORKER,
                UserType.MANAGER, UserType.ADMIN);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);


//        final User currUser = AppSingleton.getInstance().getCurrentUser();
        fbuser = mAuth.getCurrentUser();
//        email.setText(fbuser.getEmail());
        DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot o : dataSnapshot.getChildren()) {
                    if (o.getKey().equals(fbuser.getUid())) {
                        String emailStr = (String) o.child("email").getValue();
                        String nameStr = (String) o.child("name").getValue();
                        String passwordStr = (String) o.child("password").getValue();
                        String userTypeStr = (String) o.child("userType").getValue();
                        String usernameStr = (String) o.child("username").getValue();
                        username.setText(usernameStr);
                        password.setText(passwordStr);
                        name.setText(nameStr);
                        email.setText(emailStr);
                        UserType userType = UserType.valueOf(userTypeStr);
                        int position = 0;
                        boolean found = false;
                        while (!found) {
                            if (UserType.values()[position].equals(userType)) {
                                found = true;
                            } else {
                                position++;
                            }
                        }
                        type.setSelection(position);
                        // need to set the type selection
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserType userType = (UserType) type.getSelectedItem();
                final String userStr = username.getText().toString();
                final String passStr = password.getText().toString();
                final String nameStr = name.getText().toString();
                final String emailStr = email.getText().toString();

                if (CredentialVerification.verifyEmail(emailStr) &&
                        CredentialVerification.verifyPassword(passStr).equals("")) {

//                    if (!userStr.equals(currUser.getUsername()) && CredentialVerification.getInstance().getData().keySet().contains(userStr)) {
//                        Toast.makeText(getBaseContext(), "Username already in use!", Toast.LENGTH_LONG).show();
//                        return;
//                    }


                    // Alert user and modify data
                    AlertDialog dialog = new AlertDialog.Builder(EditProfileActivity.this)
                            .setTitle("Edit Profile?")
                            .setMessage("Are you sure you want to edit your profile with this information?")
                            .setPositiveButton("Yes, I'm sure.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    String user = currUser.getUsername();

                                    User newProfile = new User(userStr, passStr, nameStr, emailStr, userType);
//                                    AppSingleton.getInstance().setCurrentUser(newProfile);
                                    User u = new User(userStr, passStr, nameStr, emailStr, userType);
                                    DatabaseReference ref = database.getReference(fbuser.getUid());
                                    ref.setValue(u);

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
                } else {
                    Toast.makeText(getBaseContext(), "Something is invalid! Check again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
