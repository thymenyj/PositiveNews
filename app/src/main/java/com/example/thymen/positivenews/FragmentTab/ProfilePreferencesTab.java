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
    View view;
    TextView business_score, entertainment_score, health_score, science_score, sports_score, technology_score;
    private Button business_minus, entertainment_minus, health_minus, science_minus, sports_minus, technology_minus;
    private Button business_plus, entertainment_plus, health_plus, science_plus, sports_plus, technology_plus;
    private Button savePreferences;
    private float oldScore, newScore;
    private DatabaseReference databaseReference;
    private String userId;

    public ProfilePreferencesTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_profile_preferences, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        business_score = view.findViewById(R.id.business_score);
        entertainment_score = view.findViewById(R.id.entertainment_score);
        health_score = view.findViewById(R.id.health_score);
        science_score = view.findViewById(R.id.science_score);
        sports_score = view.findViewById(R.id.sports_score);
        technology_score = view.findViewById(R.id.technology_score);

        savePreferences = view.findViewById(R.id.savePreferences);

        ProfilePreferencesRequest profilePreferencesRequest = new ProfilePreferencesRequest(getContext());
        profilePreferencesRequest.getPreferences(this);

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

        business_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(business_score.getText().toString());
                if (oldScore > 0) {
                    newScore = oldScore - 1;
                    business_score.setText(Float.toString(newScore));
                } else {
                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        business_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(business_score.getText().toString());
                newScore = oldScore + 1;
                business_score.setText(Float.toString(newScore));
            }
        });

        entertainment_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(entertainment_score.getText().toString());
                if (oldScore > 0) {
                    newScore = oldScore - 1;
                    entertainment_score.setText(Float.toString(newScore));
                } else {
                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        entertainment_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(entertainment_score.getText().toString());
                newScore = oldScore + 1;
                entertainment_score.setText(Float.toString(newScore));
            }
        });

        health_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(health_score.getText().toString());
                if (oldScore > 0) {
                    newScore = oldScore - 1;
                    health_score.setText(Float.toString(newScore));
                } else {
                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        health_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(health_score.getText().toString());
                newScore = oldScore + 1;
                health_score.setText(Float.toString(newScore));
            }
        });

        science_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(science_score.getText().toString());
                if (oldScore > 0) {
                    newScore = oldScore - 1;
                    science_score.setText(Float.toString(newScore));
                } else {
                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        science_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(science_score.getText().toString());
                newScore = oldScore + 1;
                science_score.setText(Float.toString(newScore));
            }
        });

        sports_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(sports_score.getText().toString());
                if (oldScore > 0) {
                    newScore = oldScore - 1;
                    sports_score.setText(Float.toString(newScore));
                } else {
                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sports_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(sports_score.getText().toString());
                newScore = oldScore + 1;
                sports_score.setText(Float.toString(newScore));
            }
        });

        technology_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(technology_score.getText().toString());
                if (oldScore > 0) {
                    newScore = oldScore - 1;
                    technology_score.setText(Float.toString(newScore));
                } else {
                    Toast.makeText(getContext(), "No negative score allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        technology_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldScore = Float.parseFloat(technology_score.getText().toString());
                newScore = oldScore + 1;
                technology_score.setText(Float.toString(newScore));
            }
        });

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
