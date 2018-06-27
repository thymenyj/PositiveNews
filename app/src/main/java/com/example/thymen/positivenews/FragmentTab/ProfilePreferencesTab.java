/*
    ProfilePreferences shows the preferences of the user and creates the option
    to change the preferences.
 */

package com.example.thymen.positivenews.FragmentTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thymen.positivenews.Request.ProfilePreferencesRequest;
import com.example.thymen.positivenews.Object.Preferences;
import com.example.thymen.positivenews.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePreferencesTab extends Fragment implements ProfilePreferencesRequest.Callback{
    private View view;
    private TextView business_score, entertainment_score, health_score, science_score, sports_score, technology_score;
    private Button business_minus, entertainment_minus, health_minus, science_minus, sports_minus, technology_minus;
    private Button business_plus, entertainment_plus, health_plus, science_plus, sports_plus, technology_plus;
    private Button savePreferences;
    private float oldScore;
    private String newScore;
    private DatabaseReference databaseReference;
    private String userId;

    public ProfilePreferencesTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_profile_preferences, container, false);

        initializeDatabase();
        initializeVariables();

        ProfilePreferencesRequest profilePreferencesRequest = new ProfilePreferencesRequest(getContext());
        profilePreferencesRequest.getPreferences(this);

        setUpdateScore();
        updatePreferences();

        return view;
    }

    public void gotPreferences(Preferences preferences) {
        String business = Float.toString(preferences.getBusiness());
        String entertainment = Float.toString(preferences.getEntertainment());
        String health = Float.toString(preferences.getHealth());
        String science = Float.toString(preferences.getScience());
        String sports = Float.toString(preferences.getSports());
        String technology = Float.toString(preferences.getTechnology());

        business_score.setText(business);
        entertainment_score.setText(entertainment);
        health_score.setText(health);
        science_score.setText(science);
        sports_score.setText(sports);
        technology_score.setText(technology);
    }

    public void gotPreferencesError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }

    public void initializeDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void initializeVariables() {
        business_score = view.findViewById(R.id.business_score);
        entertainment_score = view.findViewById(R.id.entertainment_score);
        health_score = view.findViewById(R.id.health_score);
        science_score = view.findViewById(R.id.science_score);
        sports_score = view.findViewById(R.id.sports_score);
        technology_score = view.findViewById(R.id.technology_score);
        savePreferences = view.findViewById(R.id.savePreferences);

        business_minus = view.findViewById(R.id.business_minus);
        entertainment_minus = view.findViewById(R.id.entertainment_minus);
        health_minus = view.findViewById(R.id.health_minus);
        science_minus = view.findViewById(R.id.science_minus);
        sports_minus = view.findViewById(R.id.sports_minus);
        technology_minus = view.findViewById(R.id.technology_minus);
        business_plus = view.findViewById(R.id.business_plus);
        entertainment_plus = view.findViewById(R.id.entertainment_plus);
        health_plus = view.findViewById(R.id.health_plus);
        sports_plus = view.findViewById(R.id.sports_plus);
        science_plus = view.findViewById(R.id.science_plus);
        technology_plus = view.findViewById(R.id.technology_plus);
    }

    public void setUpdateScore() {
        business_minus.setOnClickListener(updatePreferencesScore);
        business_plus.setOnClickListener(updatePreferencesScore);
        entertainment_minus.setOnClickListener(updatePreferencesScore);
        entertainment_plus.setOnClickListener(updatePreferencesScore);
        health_minus.setOnClickListener(updatePreferencesScore);
        health_plus.setOnClickListener(updatePreferencesScore);
        science_minus.setOnClickListener(updatePreferencesScore);
        science_plus.setOnClickListener(updatePreferencesScore);

        sports_minus.setOnClickListener(updatePreferencesScore);
        sports_plus.setOnClickListener(updatePreferencesScore);

        technology_minus.setOnClickListener(updatePreferencesScore);
        technology_plus.setOnClickListener(updatePreferencesScore);
    }

//        switch (view.getId()) {
//            case R.id.business_minus:
//                oldScore = Float.parseFloat(business_score.getText().toString());
//                if (oldScore > 0) {
//                    newScore = Float.toString(oldScore - 1);
//                    business_score.setText(newScore);
//                } else {
//                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
//                }
//                break;


    public void updatePreferences() {
        savePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Preferences preferences = new Preferences();
                float business = Float.parseFloat(business_score.getText().toString());
                float entertainment = Float.parseFloat(entertainment_score.getText().toString());
                float health = Float.parseFloat(health_score.getText().toString());
                float science = Float.parseFloat(science_score.getText().toString());
                float sports = Float.parseFloat(sports_score.getText().toString());
                float technology = Float.parseFloat(technology_score.getText().toString());
                preferences.setBusiness(business);
                preferences.setEntertainment(entertainment);
                preferences.setHealth(health);
                preferences.setScience(science);
                preferences.setSports(sports);
                preferences.setTechnology(technology);
                preferences.setGeneral(1);

                databaseReference = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userId = user.getUid();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        databaseReference.child("users").child(userId).child("preferences").setValue(preferences);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private View.OnClickListener updatePreferencesScore = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.business_minus:
                    oldScore = Float.parseFloat(business_score.getText().toString());
                    if (oldScore > 0) {
                        newScore = Float.toString(oldScore - 1);
                        business_score.setText(newScore);
                    } else {
                        Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.business_plus:
                    oldScore = Float.parseFloat(business_score.getText().toString());
                    newScore = Float.toString(oldScore + 1);
                    business_score.setText(newScore);
                    break;
                case R.id.entertainment_minus:
                    oldScore = Float.parseFloat(entertainment_score.getText().toString());
                    if (oldScore > 0) {
                        newScore = Float.toString(oldScore - 1);
                        entertainment_score.setText(newScore);
                    } else {
                        Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.entertainment_plus:
                    oldScore = Float.parseFloat(entertainment_score.getText().toString());
                    newScore = Float.toString(oldScore + 1);
                    entertainment_score.setText(newScore);
                    break;
                case R.id.health_minus:
                    oldScore = Float.parseFloat(health_score.getText().toString());
                    if (oldScore > 0) {
                        newScore = Float.toString(oldScore - 1);
                        health_score.setText(newScore);
                    } else {
                        Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.health_plus:
                    oldScore = Float.parseFloat(health_score.getText().toString());
                    newScore = Float.toString(oldScore + 1);
                    health_score.setText(newScore);
                    break;
                case R.id.science_minus:
                    oldScore = Float.parseFloat(science_score.getText().toString());
                    if (oldScore > 0) {
                        newScore = Float.toString(oldScore - 1);
                        science_score.setText(newScore);
                    } else {
                        Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.science_plus:
                    oldScore = Float.parseFloat(science_score.getText().toString());
                    newScore = Float.toString(oldScore + 1);
                    science_score.setText(newScore);
                    break;
                case R.id.sports_minus:
                    oldScore = Float.parseFloat(sports_score.getText().toString());
                    if (oldScore > 0) {
                        newScore = Float.toString(oldScore - 1);
                        sports_score.setText(newScore);
                    } else {
                        Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.sports_plus:
                    oldScore = Float.parseFloat(sports_score.getText().toString());
                    newScore = Float.toString(oldScore + 1);
                    sports_score.setText(newScore);
                    break;
                case R.id.technology_minus:
                    oldScore = Float.parseFloat(technology_score.getText().toString());
                    if (oldScore > 0) {
                        newScore = Float.toString(oldScore - 1);
                        technology_score.setText(newScore);
                    } else {
                        Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.technology_plus:
                    oldScore = Float.parseFloat(technology_score.getText().toString());
                    newScore = Float.toString(oldScore + 1);
                    technology_score.setText(newScore);
                    break;
            }
        }
    };
}
