package com.example.thymen.positivenews;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ProfileFragment extends Fragment {
    public DatabaseReference databaseReference;
    public TextView profileName;
    public TextView profileEmail;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        tabLayout = view.findViewById(R.id.profileTabLayout);
        appBarLayout = view.findViewById(R.id.profileTabBar);
        viewPager = view.findViewById(R.id.viewPager);

        ProfileTabAdapter profileTabAdapter = new ProfileTabAdapter(getFragmentManager());
        profileTabAdapter.addFragment(new ProfileBioTab(), "Bio");
        profileTabAdapter.addFragment(new ProfileSavedArticlesTab(), "Saved Articles");
        profileTabAdapter.addFragment(new ProfilePreferencesTab(), "Preferences");

        viewPager.setAdapter(profileTabAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        ValueEventListener postListener = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                String userId = user.getUid();
//                String name = dataSnapshot.child("users").child(userId).child("name").getValue().toString();
//                String email = dataSnapshot.child("users").child(userId).child("email").getValue().toString();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("TAG", "something went wrong", databaseError.toException());
//            }
//        };
//        databaseReference.addValueEventListener(postListener);

        return view;
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
