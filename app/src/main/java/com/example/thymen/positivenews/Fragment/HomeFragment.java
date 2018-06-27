/*
    HomeFragment contains the personalFeedFragment and categoryFeedFragment
*/

package com.example.thymen.positivenews.Fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thymen.positivenews.FragmentTab.HomeCategoriesTab;
import com.example.thymen.positivenews.FragmentTab.HomePersonalTab;
import com.example.thymen.positivenews.Adapter.HomeTabAdapter;
import com.example.thymen.positivenews.R;


public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        TabLayout tabLayout = view.findViewById(R.id.homeTabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        HomeTabAdapter homeTabAdapter = new HomeTabAdapter(getChildFragmentManager());
        homeTabAdapter.addFragment(new HomePersonalTab(), "Personal");
        homeTabAdapter.addFragment(new HomeCategoriesTab(), "Categories");

        viewPager.setAdapter(homeTabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
