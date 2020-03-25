package com.senoritasaudi.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class IntroPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;

    public IntroPagerAdapter(@NonNull FragmentManager fm) {
        super(fm,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
