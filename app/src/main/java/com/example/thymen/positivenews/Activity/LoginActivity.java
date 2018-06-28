/*
    LoginActivity is the startactivity of the app and logs the user in. When logged in
    the user is directed to their profile. From this activity, the user is able to go
    to the register activity and the reset activity.
 */

package com.example.thymen.positivenews.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thymen.positivenews.Globals.MyApplication;
import com.example.thymen.positivenews.Request.PositiveWordsRequest;
import com.example.thymen.positivenews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements PositiveWordsRequest.Callback {
    private TextView loginUsername, loginPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private RelativeLayout rellay1, rellay2;

    // set animation for login
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
        setContentView(R.layout.activity_login);
        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        initializePositiveWords();
        initializeVariables();
        initializeAuthentication();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.addAuthStateListener(authStateListener);
        }
    }

    public void validate(String username, String password) {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, WalkthroughActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gotPositiveWords(String positiveWords) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(positiveWords.split(", ")));
        HashMap<String, String> updateHashmap = ((MyApplication) this.getApplication()).getPositiveWords();
        for (int i = 0; i < list.size(); i++) {
            String word = list.get(i);
            updateHashmap.put(word, word);
        }
        ((MyApplication) this.getApplication()).setPositiveWords(updateHashmap);
    }

    public void gotPositiveWordsError(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    public void startLogin(View view) {
        if (loginUsername.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "please enter email and password", Toast.LENGTH_SHORT).show();
        } else {
            String password = loginPassword.getText().toString();
            String name = loginUsername.getText().toString();
            validate(name, password);
        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }



    public void goToReset(View view) {
        Intent intent = new Intent(LoginActivity.this, ResetActivity.class);
        startActivity(intent);
    }

    public void initializePositiveWords() {
        HashMap<String, String> initHashMap = new HashMap<>();
        ((MyApplication) this.getApplication()).setPositiveWords(initHashMap);

        PositiveWordsRequest positiveWordsRequest = new PositiveWordsRequest(this);
        positiveWordsRequest.getPositiveWords(this);
    }

    public void initializeAuthentication() {

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("signed in", "onAuthStateCHanged:signed_in:" + user.getUid());
                }
                else {
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void initializeVariables() {
        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
    }
}