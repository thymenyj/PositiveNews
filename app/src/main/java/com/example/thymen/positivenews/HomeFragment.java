package com.example.thymen.positivenews;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {
    public DatabaseReference databaseReference;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        tabLayout = view.findViewById(R.id.homeTabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        HomeTabAdapter homeTabAdapter = new HomeTabAdapter(getChildFragmentManager());
        homeTabAdapter.addFragment(new HomePersonalTab(), "Personal");
        homeTabAdapter.addFragment(new HomeCountryTab(), "Country");

        viewPager.setAdapter(homeTabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
