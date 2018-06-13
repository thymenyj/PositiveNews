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
import java.util.Random;

public class PersonalFeedUrlRequest {
    public Callback activity;
    public Context context;
    private DatabaseReference databaseReference;
    private String userId;
    private String category;
    private FirebaseDatabase firebaseDatabase;


    private float businessPreference, entertainmentPreference, healthPreference, sciencePreference, sportsPreference, technologyPreference, totalPreference;
    private float businessScore, entertainmentScore, healthScore, scienceScore, sportsScore, technologyScore;

    public interface Callback {
        void gotPersonalFeedUrl(String url);
        void gotPersonalFeedUrlError(String message);
    }

    public PersonalFeedUrlRequest(Context context) {
        this.context = context;
    }

    public void getPersonalFeedUrl(final Callback activity) {
        this.activity = activity;

        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setLogLevel(Logger.Level.DEBUG);

        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("preferences");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                businessPreference = dataSnapshot.child("business").getValue(Float.class);
                entertainmentPreference = (dataSnapshot.child("entertainment").getValue(Float.class) + businessPreference);
                healthPreference = (dataSnapshot.child("health").getValue(Float.class) + entertainmentPreference);
                sciencePreference = (dataSnapshot.child("science").getValue(Float.class) + healthPreference);
                sportsPreference = (dataSnapshot.child("sports").getValue(Float.class) + sciencePreference);
                technologyPreference = (dataSnapshot.child("technology").getValue(Float.class) + sportsPreference);
                totalPreference = technologyPreference;

                Log.d("bpref", Float.toString(businessPreference));
                Log.d("entpref", Float.toString(entertainmentPreference));
                Log.d("heapref", Float.toString(healthPreference));
                Log.d("scipref", Float.toString(sciencePreference));
                Log.d("sportpref", Float.toString(sportsPreference));
                Log.d("techpref", Float.toString(technologyPreference));
                Log.d("totalpref", Float.toString(totalPreference));

                businessScore = businessPreference/ totalPreference;
                entertainmentScore = entertainmentPreference/ totalPreference;
                healthScore = healthPreference/ totalPreference;
                scienceScore = sciencePreference/ totalPreference;
                sportsScore = sportsPreference/ totalPreference;
                technologyScore = technologyPreference/ totalPreference;

                Log.d("busscore", Float.toString(businessScore));
                Log.d("entscore", Float.toString(entertainmentScore));
                Log.d("heascore", Float.toString(healthScore));
                Log.d("sciscore", Float.toString(scienceScore));
                Log.d("sportscore", Float.toString(sportsScore));
                Log.d("techscore", Float.toString(technologyScore));

                float rangeMin = 0.0f;
                float rangeMax = 1.0f;
                Random r = new Random();
                float createdRanNum = rangeMin + (rangeMax - rangeMin) * r.nextFloat();
                Log.d("randomFloat", Float.toString(createdRanNum));

                if (createdRanNum >= 0 && createdRanNum < businessScore) {
                    category = "business";
                }
                else if (createdRanNum >= businessScore && createdRanNum < entertainmentScore) {
                    category = "entertainment";
                }
                else if (createdRanNum >= entertainmentScore && createdRanNum < healthScore) {
                    category = "health";
                }
                else if (createdRanNum >= healthScore && createdRanNum < scienceScore) {
                    category = "science";
                }
                else if (createdRanNum >= scienceScore && createdRanNum < sportsScore) {
                    category = "sports";
                }
                else if (createdRanNum >= sportsScore && createdRanNum < technologyScore) {
                    category = "entertainment";
                }
                else {
                    category = "technology";
                }
                Log.d("category", category);
                activity.gotPersonalFeedUrl(category);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotPersonalFeedUrlError(message);
            }
        });

    }

}
