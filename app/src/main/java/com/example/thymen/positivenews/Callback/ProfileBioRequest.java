package com.example.thymen.positivenews.Callback;

import android.content.Context;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfileBioRequest {
    public Callback activity;
    public Context context;
    private DatabaseReference databaseReference;
    private String userId;
    private String name, email;


    public interface Callback {
        void gotBio(String name, String email);
        void gotBioError (String message);
    }

    public ProfileBioRequest(Context context) {
        this.context = context;
    }

    public void getBio (final Callback activity) {
        this.activity = activity;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId);
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                name = snapshot.child("name").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                activity.gotBio(name, email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotBioError(message);
            }

        });
    }

}
