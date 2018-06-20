package com.example.thymen.positivenews;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PositiveWordsRequest {
    public Callback activity;
    public Context context;
    private DatabaseReference databaseReference;
    private String positiveWords;
    private FirebaseDatabase firebaseDatabase;
    private String userId;

    public interface Callback {
        void gotPositiveWords(String positiveWords);
        void gotPositiveWordsError(String message);
    }

    public PositiveWordsRequest(Context context) {
        this.context = context;
    }

    public void getPositiveWords(final Callback activity) {
        this.activity = activity;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Query getPositiveWords =  databaseReference.child("postiveWords");
        getPositiveWords.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshotSavedArticles: snapshot.getChildren()) {
                    positiveWords = snapshotSavedArticles.getValue(String.class);

                    activity.gotPositiveWords(positiveWords);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotPositiveWordsError(message);
            }
        });

    }

}
