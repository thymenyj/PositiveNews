package com.example.thymen.positivenews;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }
}







//package com.example.thymen.positivenews;
//
//import android.app.Fragment;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//public class ProfileFragment extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_profile);
//
//        BottomNavigationView navigation = findViewById(R.id.navigationView);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_profile:
//                    return true;
//                case R.id.navigation_home:
//                    Intent intentPersonal = new Intent(ProfileFragment.this, PersonalFeedFragment.class);
//                    startActivity(intentPersonal);
//                    return true;
//                case R.id.navigation_trending:
//                    Intent intentTrending = new Intent(ProfileFragment.this, TrendingFeedFragment.class);
//                    startActivity(intentTrending);
//                    return true;
//            }
//            return false;
//        }
//    };
//}
