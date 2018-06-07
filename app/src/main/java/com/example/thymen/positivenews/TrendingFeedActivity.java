package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class TrendingFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_feed);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    Intent intentTrending = new Intent(TrendingFeedActivity.this, ProfileActivity.class);
                    startActivity(intentTrending);
                    return true;
                case R.id.navigation_home:
                    Intent intentPersonal = new Intent(TrendingFeedActivity.this, PersonalFeedActivity.class);
                    startActivity(intentPersonal);
                    return true;
                case R.id.navigation_trending:
                    return true;
            }
            return false;
        }
    };
}
