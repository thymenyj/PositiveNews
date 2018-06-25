package com.example.thymen.positivenews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView[]dotstv;
    private int[]layouts;
    private Button buttonSkip;
    private Button buttonNext;
    private WalkthroughSlideAdapter walkthroughSlideAdapter;
    public DatabaseReference database;

    private float business = 1, entertainment = 1, health = 1, science = 1, sports = 1, technology = 1;

    private TextView welcome;
    private ImageView businessPhoto, entertainmentPhoto, healthPhoto, sciencePhoto, sportsPhoto, technologyPhoto;

    private float startingValue = 100;

    private String firstLogin, nameUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                firstLogin = dataSnapshot.child("users").child(userId).child("firstLogin").getValue().toString();
                nameUser = dataSnapshot.child("users").child(userId).child("name").getValue(String.class);
                if (firstLogin.equals("false")) {
                    startMainActivity();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "something went wrong", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);

        setStatusBarTransparent();

        setContentView(R.layout.activity_walkthrough);

        viewPager = findViewById(R.id.view_pager);
        layoutDot = findViewById(R.id.dotLayout);
        buttonNext = findViewById(R.id.next);
        buttonSkip = findViewById(R.id.skip);


        //When user press skip, start Main Activity
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startMainActivity();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem()+1;
                if(currentPage < layouts.length - 1) {
                    //move to next page
                    viewPager.setCurrentItem(currentPage);
                } else if (currentPage == layouts.length - 1) {
                    ValueEventListener postListener = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();
                            Preferences preferences = new Preferences();
                            preferences.setBusiness(business);
                            preferences.setEntertainment(entertainment);
                            preferences.setHealth(health);
                            preferences.setScience(science);
                            preferences.setSports(sports);
                            preferences.setTechnology(technology);
                            preferences.setGeneral(1);
                            database.child("users").child(userId).child("preferences").setValue(preferences);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TAG", "something went wrong", databaseError.toException());
                        }
                    };
                    database.addValueEventListener(postListener);
                    viewPager.setCurrentItem(currentPage);
                }
                else {
                    ValueEventListener postListener = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();
                            database.child("users").child(userId).child("firstLogin").setValue("false");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TAG", "something went wrong", databaseError.toException());
                        }
                    };
                    database.addValueEventListener(postListener);
                    startMainActivity();
                }
            }
        });

        layouts = new int[]{R.layout.walkthrough_slide_1,R.layout.walkthrough_slide_2, R.layout.walkthrough_slide_3, R.layout.walkthrough_slide_4};
        walkthroughSlideAdapter = new WalkthroughSlideAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(walkthroughSlideAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == layouts.length-1){
                    //LAST PAGE
                    buttonNext.setText("START");
                    buttonSkip.setVisibility(View.GONE);
                } else if (position == layouts.length-2){
                    buttonNext.setText("NEXT");
                    buttonSkip.setVisibility(View.VISIBLE);

                    // instantiate switches
                    businessPhoto = findViewById(R.id.business_photo);
                    entertainmentPhoto = findViewById(R.id.entertainment_photo);
                    healthPhoto = findViewById(R.id.health_photo);
                    sciencePhoto = findViewById(R.id.science_photo);
                    sportsPhoto = findViewById(R.id.sports_photo);
                    technologyPhoto = findViewById(R.id.technology_photo);

                    businessPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                    entertainmentPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                    healthPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                    sciencePhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                    sportsPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                    technologyPhoto.setColorFilter(Color.argb(200, 200, 200, 200));

                    businessPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (business == 1) {
                                businessPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                                business = 50;
                            } else if (business == 50) {
                                businessPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                                business = 100;
                            } else if (business == 100) {
                                businessPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                                business = 1;
                            }
                        }
                    });
                    entertainmentPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (entertainment == 1) {
                                entertainmentPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                                entertainment = 50;
                            } else if (entertainment == 50) {
                                entertainmentPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                                entertainment = 100;
                            } else if (entertainment == 100) {
                                entertainmentPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                                entertainment = 1;
                            }
                        }
                    });
                    healthPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (health == 1) {
                                healthPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                                health = 50;
                            } else if (health == 50) {
                                healthPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                                health = 100;
                            } else if (health == 100) {
                                healthPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                                health = 1;
                            }
                        }
                    });
                    sciencePhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (science == 1) {
                                sciencePhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                                science = 50;
                            } else if (science == 50) {
                                sciencePhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                                science = 100;
                            } else if (science == 100) {
                                sciencePhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                                science = 1;
                            }
                        }
                    });
                    sportsPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (sports == 1) {
                                sportsPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                                sports = 50;
                            } else if (sports == 50) {
                                sportsPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                                sports = 100;
                            } else if (sports == 100) {
                                sportsPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                                sports = 1;
                            }
                        }
                    });
                    technologyPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (technology == 1) {
                                technologyPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                                technology = 50;
                            } else if (technology == 50) {
                                technologyPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                                technology = 100;
                            } else if (technology == 100) {
                                technologyPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                                technology = 1;
                            }
                        }
                    });
                }
                else {
                    buttonNext.setText("NEXT");
                    buttonSkip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotStatus(0);
    }



    private void setDotStatus(int page){
        layoutDot.removeAllViews();
        dotstv =new TextView[layouts.length];
        for (int i = 0; i < dotstv.length; i++) {
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            layoutDot.addView(dotstv[i]);
        }
        //Set current dot active
        if(dotstv.length>0){
            dotstv[page].setTextColor(Color.parseColor("#000000"));
        }
    }
    private void startMainActivity(){
//        setFirstTimeStartStatus(false);
        startActivity(new Intent(WalkthroughActivity.this, HomeActivity.class));
        finish();
    }
    private void setStatusBarTransparent(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

    }
}
