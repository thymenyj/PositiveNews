/*
    ProfileTabAdapter sets the positions of ProfileBioTab, ProfileSavedArticlesTab and ProfilePreferencesTab.
 */

package com.example.thymen.positivenews.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.thymen.positivenews.FragmentTab.ProfileBioTab;
import com.example.thymen.positivenews.FragmentTab.ProfilePreferencesTab;
import com.example.thymen.positivenews.FragmentTab.ProfileSavedArticlesTab;

import java.util.ArrayList;
import java.util.List;

public class ProfileTabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList();
    private final List<String> fragmentListTitles = new ArrayList();

    public ProfileTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ProfileBioTab();
            case 1:
                return new ProfileSavedArticlesTab();
            case 2:
                return new ProfilePreferencesTab();
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
