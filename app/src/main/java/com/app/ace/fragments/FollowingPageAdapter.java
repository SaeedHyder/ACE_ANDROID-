package com.app.ace.fragments;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muniyemiftikhar on 4/24/2017.
 */


public class FollowingPageAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public FollowingPageAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {

        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void clearList() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return mFragmentList.get(position);
    }
}