package com.example.thymen.positivenews.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.thymen.positivenews.FragmentTab.HomeCategoriesTab;
import com.example.thymen.positivenews.FragmentTab.HomePersonalTab;

import java.util.ArrayList;
import java.util.List;

public class HomeTabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList();
    private final List<String> fragmentListTitles = new ArrayList();

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("getItem", "fragmentpager");
        switch(position){
            case 0:
                return new HomePersonalTab();
            case 1:
                return new HomeCategoriesTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentListTitles.add(title);

    }
}
