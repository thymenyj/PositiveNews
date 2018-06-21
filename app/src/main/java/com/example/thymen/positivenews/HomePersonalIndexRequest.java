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
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HomePersonalIndexRequest {
    public Callback activity;
    public Context context;
    private DatabaseReference databaseReference;
    private String userId;
    private String category;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Integer> lengthsList;
    private ArrayList<Integer> indexList;
    private static int LISTSIZE = 60;


    private float businessPreference, entertainmentPreference, healthPreference, sciencePreference, sportsPreference, technologyPreference, totalPreference;
    private float businessScore, entertainmentScore, healthScore, scienceScore, sportsScore, technologyScore;

    private int listStart = 1, listLength1 = 2, listLength2 = 3, listLength3 = 4, listLength4 = 5, listLength5 = 6, listLength6 = 7;

    public interface Callback {
        void gotPersonalIndex(ArrayList<Integer> indexListUsed);

        void gotPersonalIndexError(String message);
    }

    public HomePersonalIndexRequest(Context context) {
        this.context = context;
    }

    public void getPersonalIndex(final Callback activity, final ArrayList<Integer> lengthsList) {
        this.activity = activity;
        this.lengthsList = lengthsList;
        Log.d("lengthsListBeginIndex", lengthsList.toString());

        indexList = new ArrayList<>();

        listStart = 1;
        listLength1 = lengthsList.get(0);
        listLength2 = lengthsList.get(1) + listLength1;
        listLength3 = lengthsList.get(2) + listLength2;
        listLength4 = lengthsList.get(3) + listLength3;
        listLength5 = lengthsList.get(4) + listLength4;
        listLength6 = lengthsList.get(5) + listLength5;

        Log.d("check1", "check1");

        firebaseDatabase = FirebaseDatabase.getInstance();
        //        firebaseDatabase.setLogLevel(Logger.Level.DEBUG);

        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Log.d("check1", "check2");
        Query updatePreferenceScore = databaseReference.child("users").child(userId).child("preferences");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("check1", "check3");
                businessPreference = dataSnapshot.child("business").getValue(Float.class);
                entertainmentPreference = (dataSnapshot.child("entertainment").getValue(Float.class) + businessPreference);
                healthPreference = (dataSnapshot.child("health").getValue(Float.class) + entertainmentPreference);
                sciencePreference = (dataSnapshot.child("science").getValue(Float.class) + healthPreference);
                sportsPreference = (dataSnapshot.child("sports").getValue(Float.class) + sciencePreference);
                technologyPreference = (dataSnapshot.child("technology").getValue(Float.class) + sportsPreference);
                totalPreference = technologyPreference;
                Log.d("totalPreference", Float.toString(totalPreference));
                Log.d("check1", "check3.1");


                businessScore = businessPreference / totalPreference;
                entertainmentScore = entertainmentPreference / totalPreference;
                healthScore = healthPreference / totalPreference;
                scienceScore = sciencePreference / totalPreference;
                sportsScore = sportsPreference / totalPreference;
                technologyScore = technologyPreference / totalPreference;
                Log.d("check1", "check3.2");


                ArrayList<Integer> list1 = new ArrayList<>();
                for (int i = 0; i < listLength1; i++) {
                    list1.add(new Integer(i));
                }
                Collections.shuffle(list1);
                for (int i = 0; i < Math.round(LISTSIZE * businessScore); i++) {
                    if (i > listLength1 - 1) {
                        break;
                    }
                    indexList.add(list1.get(i));
                }
                Log.d("check1.1", indexList.toString());

                ArrayList<Integer> list2 = new ArrayList<>();
                for (int i = 0; i < listLength2 - listLength1; i++) {
                    list2.add(new Integer(i + listLength1));
                }
                Collections.shuffle(list2);
                for (int i = 0; i < Math.round(LISTSIZE * entertainmentScore); i++) {
                    if (i > listLength2 - listLength1 - 1) {
                        break;
                    }
                    indexList.add(list2.get(i));
                }
                Log.d("check1.1", indexList.toString());

                ArrayList<Integer> list3 = new ArrayList<>();
                for (int i = 0; i < listLength3 - listLength2; i++) {
                    list3.add(new Integer(i + listLength2));
                }
                Collections.shuffle(list3);
                for (int i = 0; i < Math.round(LISTSIZE * healthScore); i++) {
                    if (i > listLength3 - listLength2 - 1) {
                        break;
                    }
                    indexList.add(list3.get(i));
                }
                Log.d("check1.1", indexList.toString());

                ArrayList<Integer> list4 = new ArrayList<>();
                for (int i = 0; i < listLength4 - listLength3; i++) {
                    list4.add(new Integer(i + listLength3));
                }
                Collections.shuffle(list4);
                for (int i = 0; i < Math.round(LISTSIZE * scienceScore); i++) {
                    if (i > listLength4 - listLength3 - 1) {
                        break;
                    }
                    indexList.add(list4.get(i));
                }
                Log.d("check1.1", indexList.toString());

                ArrayList<Integer> list5 = new ArrayList<>();
                for (int i = 0; i < listLength5 - listLength4; i++) {
                    list5.add(new Integer(i + listLength4));
                }
                Collections.shuffle(list5);
                for (int i = 0; i < Math.round(LISTSIZE * sportsScore); i++) {
                    if (i > listLength5 - listLength4 - 1) {
                        break;
                    }
                    indexList.add(list5.get(i));
                }
                Log.d("check1.1", indexList.toString());

                ArrayList<Integer> list6 = new ArrayList<>();
                for (int i = 0; i < listLength6 - listLength5; i++) {
                    list6.add(new Integer(i + listLength5));
                }
                Collections.shuffle(list6);
                for (int i = 0; i < Math.round(LISTSIZE * technologyScore); i++) {
                    if (i > listLength6 - listLength5 - 1) {
                        break;
                    }
                    indexList.add(list6.get(i));
                }
                Log.d("check1.1", indexList.toString());
                Collections.shuffle(indexList);

                Log.d("indexListEnd", indexList.toString());
                activity.gotPersonalIndex(indexList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotPersonalIndexError(message);
            }
        });
    }
}