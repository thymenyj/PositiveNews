package com.example.thymen.positivenews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

    private float business, entertainment, health, science, sports, technology;

    private Switch businessSwitch, entertainmentSwitch, healthSwitch, scienceSwitch, sportsSwitch, technologySwitch;

    private float startingValue = 100;

    private String firstLogin;


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
                    if (businessSwitch.isChecked()) {
                        business = startingValue;
                    }
                    else {
                        business = 1;
                    }

                    if (entertainmentSwitch.isChecked()) {
                        entertainment = startingValue;
                    }
                    else {
                        entertainment = 1;
                    }

                    if (healthSwitch.isChecked()) {
                        health = startingValue;
                    }
                    else {
                        health = 1;
                    }

                    if (scienceSwitch.isChecked()) {
                        science = startingValue;
                    }
                    else {
                        science = 1;
                    }

                    if (sportsSwitch.isChecked()) {
                        sports = startingValue;
                    }
                    else {
                        sports = 1;
                    }
                    if (technologySwitch.isChecked()) {
                        technology = startingValue;
                    }
                    else {
                        technology = 1;
                    }

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
                    businessSwitch = findViewById(R.id.walkthrough_category_business);
                    entertainmentSwitch = findViewById(R.id.walkthrough_category_entertainment);
                    healthSwitch = findViewById(R.id.walkthrough_category_health);
                    scienceSwitch = findViewById(R.id.walkthrough_category_science);
                    sportsSwitch = findViewById(R.id.walkthrough_category_sports);
                    technologySwitch = findViewById(R.id.walkthrough_category_technology);
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
