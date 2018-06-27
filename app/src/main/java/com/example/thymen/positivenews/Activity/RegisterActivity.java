/*
    RegisterActivity creates a new Firebase user  and adds a (User.class) to the database
 */

package com.example.thymen.positivenews.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thymen.positivenews.R;
import com.example.thymen.positivenews.Object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private TextView registerName, registerEmail, registerPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    private String userName, userEmail;
    private RelativeLayout rellay1, rellay2;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        handler.postDelayed(runnable, 750); //750 is the timeout for the splash

        initializeDatabase();
        initializeVariables();
    }

    public Boolean validate() {
        Boolean result = false;

        String validateName = registerName.getText().toString();
        String validateEmail = registerEmail.getText().toString();
        String validatePassword = registerPassword.getText().toString();

        if (validateName.isEmpty() || validateEmail.isEmpty() || validatePassword.isEmpty()) {
            Toast.makeText(this, "please enter all details", Toast.LENGTH_SHORT).show();
        } else if(validatePassword.length() < 6) {
            Toast.makeText(this, "password requires 6 characters", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    public void initializeDatabase() {
        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void initializeVariables() {
        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
    }

    public void startRegister(View view) {
        if (validate()) {
            userName = registerName.getText().toString().trim();
            userEmail = registerEmail.getText().toString().trim();
            String userPassword = registerPassword.getText().toString().trim();


            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "registerd successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
                        User newUser = new User();
                        newUser.setName(userName);
                        newUser.setEmail(userEmail);
                        newUser.setFirstLogin("true");
                        database.child("users").child(userId).setValue(newUser);
                        database.child("users").child(userId).child("preferences").child("business").setValue(100);
                        database.child("users").child(userId).child("preferences").child("entertainment").setValue(100);
                        database.child("users").child(userId).child("preferences").child("health").setValue(100);
                        database.child("users").child(userId).child("preferences").child("science").setValue(100);
                        database.child("users").child(userId).child("preferences").child("sports").setValue(100);
                        database.child("users").child(userId).child("preferences").child("technology").setValue(100);
                        database.child("users").child(userId).child("preferences").child("general").setValue(100);
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}