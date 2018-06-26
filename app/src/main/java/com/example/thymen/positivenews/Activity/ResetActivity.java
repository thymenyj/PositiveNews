package com.example.thymen.positivenews.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.thymen.positivenews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
    private EditText resetEmail;
    private Button resetBack, resetReset;
    private FirebaseAuth firebaseAuth;
    private String userEmail;
    private RelativeLayout rellay1, rellay2;

    android.os.Handler handler = new android.os.Handler();
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
        setContentView(R.layout.activity_reset);
        handler.postDelayed(runnable, 750); //750 is the timeout for the splash

        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        resetEmail = findViewById(R.id.resetEmail);
        resetReset = findViewById(R.id.resetReset);
        resetBack = findViewById(R.id.resetBack);

        firebaseAuth = FirebaseAuth.getInstance();

        resetReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = resetEmail.getText().toString().trim();

                if (userEmail.equals("")) {
                    Toast.makeText(ResetActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetActivity.this, "Password reset, email is sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ResetActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ResetActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        resetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
