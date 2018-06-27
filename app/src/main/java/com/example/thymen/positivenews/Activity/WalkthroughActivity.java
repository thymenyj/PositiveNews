/*
    WalkthroughActivity sets all the slides for the walkthrough. Updates the buttons to match
    the current slide. Updates the first preferences of the user when submitted.
 */

package com.example.thymen.positivenews.Activity;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import com.example.thymen.positivenews.Adapter.WalkthroughSlideAdapter;
import com.example.thymen.positivenews.Object.Preferences;
import com.example.thymen.positivenews.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private int[]layouts;
    private Button buttonSkip;
    private Button buttonNext;
    private DatabaseReference database;

    private float businessScore = 1, entertainmentScore = 1, healthScore = 1, scienceScore = 1, sportsScore = 1, technologyScore = 1, generalScore = 1;

    private ImageView businessPhoto, entertainmentPhoto, healthPhoto, sciencePhoto, sportsPhoto, technologyPhoto;
    private String firstLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent();

        initializeDatabase();
        checkFirstLogin();

        setContentView(R.layout.activity_walkthrough);

        initializeVariables();
        initializeAdapter();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // last page
                if(position == layouts.length-1){
                    buttonNext.setText("START");
                    buttonSkip.setVisibility(View.GONE);
                }
                // third page including preference
                else if (position == layouts.length-2){
                    buttonNext.setText("NEXT");
                    buttonSkip.setVisibility(View.VISIBLE);
                    initializeVariablesSlide3();
                    getPreferences();

                } else {
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
        TextView[] dotstv = new TextView[layouts.length];
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

    private void setStatusBarTransparent(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public void checkFirstLogin() {
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                firstLogin = dataSnapshot.child("users").child(userId).child("firstLogin").getValue().toString();
                if (firstLogin.equals("false")) {
                    startMainActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "something went wrong", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);
    }

    public void goToNextSlide(View view) {
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
                    preferences.setBusiness(businessScore);
                    preferences.setEntertainment(entertainmentScore);
                    preferences.setHealth(healthScore);
                    preferences.setScience(scienceScore);
                    preferences.setSports(sportsScore);
                    preferences.setTechnology(technologyScore);
                    preferences.setGeneral(generalScore);
                    database.child("users").child(userId).child("preferences").setValue(preferences);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("TAG", "something went wrong", databaseError.toException());
                }
            };
            database.addValueEventListener(postListener);
            viewPager.setCurrentItem(currentPage);
        } else {
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

    public void initializeAdapter() {
        layouts = new int[]{R.layout.walkthrough_slide_1,R.layout.walkthrough_slide_2, R.layout.walkthrough_slide_3, R.layout.walkthrough_slide_4};
        WalkthroughSlideAdapter walkthroughSlideAdapter = new WalkthroughSlideAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(walkthroughSlideAdapter);
    }

    public void getPreferences() {
            businessPhoto.setOnClickListener(setScore);
            entertainmentPhoto.setOnClickListener(setScore);
            healthPhoto.setOnClickListener(setScore);
            sciencePhoto.setOnClickListener(setScore);
            sportsPhoto.setOnClickListener(setScore);
            technologyPhoto.setOnClickListener(setScore);
    }

    public void initializeVariablesSlide3() {
        businessPhoto = findViewById(R.id.business);
        entertainmentPhoto = findViewById(R.id.entertainment);
        healthPhoto = findViewById(R.id.health);
        sciencePhoto = findViewById(R.id.science);
        sportsPhoto = findViewById(R.id.sports);
        technologyPhoto = findViewById(R.id.technology);

        businessPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
        entertainmentPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
        healthPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
        sciencePhoto.setColorFilter(Color.argb(200, 200, 200, 200));
        sportsPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
        technologyPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
    }

    public void initializeDatabase() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void initializeVariables() {
        viewPager = findViewById(R.id.view_pager);
        layoutDot = findViewById(R.id.dotLayout);
        buttonNext = findViewById(R.id.next);
        buttonSkip = findViewById(R.id.skip);
    }

    public void goToHome(View view) {
        startMainActivity();
    }

    public void startMainActivity() {
        Intent intent = new Intent(WalkthroughActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private View.OnClickListener setScore = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.business:
                    if (businessScore == 1) {
                        businessPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                        businessScore = 50;
                    } else if (businessScore == 50) {
                        businessPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                        businessScore = 100;
                    } else if (businessScore == 100) {
                        businessPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                        businessScore = 1;
                    }
                    break;
                case R.id.entertainment:
                    if (entertainmentScore == 1) {
                        entertainmentPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                        entertainmentScore = 50;
                    } else if (entertainmentScore == 50) {
                        entertainmentPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                        entertainmentScore = 100;
                    } else if (entertainmentScore == 100) {
                        entertainmentPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                        entertainmentScore = 1;
                    }
                    break;
                case R.id.health:
                    if (healthScore == 1) {
                        healthPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                        healthScore = 50;
                    } else if (healthScore == 50) {
                        healthPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                        healthScore = 100;
                    } else if (healthScore == 100) {
                        healthPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                        healthScore = 1;
                    }
                    break;
                case R.id.science:
                    if (scienceScore == 1) {
                        sciencePhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                        scienceScore = 50;
                    } else if (scienceScore == 50) {
                        sciencePhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                        scienceScore = 100;
                    } else if (scienceScore == 100) {
                        sciencePhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                        scienceScore = 1;
                    }
                    break;
                case R.id.sports:
                    if (sportsScore == 1) {
                        sportsPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                        sportsScore = 50;
                    } else if (sportsScore == 50) {
                        sportsPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                        sportsScore = 100;
                    } else if (sportsScore == 100) {
                        sportsPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                        sportsScore = 1;
                    }
                    break;
                case R.id.technology:
                    if (technologyScore == 1) {
                        technologyPhoto.setColorFilter(Color.argb(100, 100, 100, 100));
                        technologyScore = 50;
                    } else if (technologyScore == 50) {
                        technologyPhoto.setColorFilter(Color.argb(0, 0, 0, 0));
                        technologyScore = 100;
                    } else if (technologyScore == 100) {
                        technologyPhoto.setColorFilter(Color.argb(200, 200, 200, 200));
                        technologyScore = 1;
                    }
                    break;
            }
        }
    };
}
