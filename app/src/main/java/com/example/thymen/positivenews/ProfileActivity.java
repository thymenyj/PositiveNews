package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    return true;
                case R.id.navigation_home:
                    Intent intentPersonal = new Intent(ProfileActivity.this, PersonalFeedActivity.class);
                    startActivity(intentPersonal);
                    return true;
                case R.id.navigation_trending:
                    Intent intentTrending = new Intent(ProfileActivity.this, TrendingFeedActivity.class);
                    startActivity(intentTrending);
                    return true;
            }
            return false;
        }
    };
}
