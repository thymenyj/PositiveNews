package com.example.thymen.positivenews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfilePreferencesTab extends Fragment implements ProfilePreferencesRequest.Callback{
    View view;
    TextView business_score, entertainment_score, health_score, science_score, sports_score, technology_score;

    public ProfilePreferencesTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_profile_preferences, container, false);

        business_score = view.findViewById(R.id.business_score);
        entertainment_score = view.findViewById(R.id.entertainment_score);
        health_score = view.findViewById(R.id.health_score);
        science_score = view.findViewById(R.id.science_score);
        sports_score = view.findViewById(R.id.sports_score);
        technology_score = view.findViewById(R.id.technology_score);

        ProfilePreferencesRequest profilePreferencesRequest = new ProfilePreferencesRequest(getContext());
        profilePreferencesRequest.getPreferences(this);

        return view;
    }

    public void gotPreferences(Preferences preferences) {
        business_score.setText(Float.toString(preferences.getBusiness()));
        entertainment_score.setText(Float.toString(preferences.getEntertainment()));
        health_score.setText(Float.toString(preferences.getHealth()));
        science_score.setText(Float.toString(preferences.getScience()));
        sports_score.setText(Float.toString(preferences.getSports()));
        technology_score.setText(Float.toString(preferences.getTechnology()));
    }

    public void gotPreferencesError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }


}
