package com.example.thymen.positivenews;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class ArticleLikeRequest {
    public Callback activity;
    public Context context;
    private DatabaseReference databaseReference;
    private String userId;
    private String category;
    private float oldScore;


    public interface Callback {
        void gotArticleLike (float oldScore);
        void gotArticleLikeError (String message);
    }

    public ArticleLikeRequest(Context context) {
        this.context = context;
    }

    public void getArticleLike (final Callback activity) {
        this.activity = activity;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("preferences");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("preferences");
                updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot postSnapshot) {
                        oldScore = postSnapshot.child("business").getValue(Float.class);

                        activity.gotArticleLike(oldScore);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotArticleLikeError(message);
            }
        });

    }

}
