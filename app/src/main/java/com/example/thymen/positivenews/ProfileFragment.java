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

        ProfileTabAdapter profileTabAdapter = new ProfileTabAdapter(getChildFragmentManager());
        profileTabAdapter.addFragment(new ProfileBioTab(), "Bio");
        profileTabAdapter.addFragment(new ProfileSavedArticlesTab(), "Saved Articles");
        profileTabAdapter.addFragment(new ProfilePreferencesTab(), "Preferences");

        viewPager.setAdapter(profileTabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
