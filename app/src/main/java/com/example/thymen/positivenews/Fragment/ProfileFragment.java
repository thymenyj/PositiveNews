package com.example.thymen.positivenews.Fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thymen.positivenews.FragmentTab.ProfileBioTab;
import com.example.thymen.positivenews.FragmentTab.ProfilePreferencesTab;
import com.example.thymen.positivenews.FragmentTab.ProfileSavedArticlesTab;
import com.example.thymen.positivenews.Adapter.ProfileTabAdapter;
import com.example.thymen.positivenews.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        TabLayout tabLayout = view.findViewById(R.id.profileTabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        ProfileTabAdapter profileTabAdapter = new ProfileTabAdapter(getChildFragmentManager());
        profileTabAdapter.addFragment(new ProfileBioTab(), "Bio");
        profileTabAdapter.addFragment(new ProfileSavedArticlesTab(), "Saved Articles");
        profileTabAdapter.addFragment(new ProfilePreferencesTab(), "Preferences");

        viewPager.setAdapter(profileTabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
