package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 4/24/2017.
 */

public class FollowingFragment extends BaseFragment{

    @InjectView(R.id.tab_layout)
    private TabLayout tabLayout;

    @InjectView(R.id.pager)
    private ViewPager pager;

    private FollowingPageAdapter adapter;

    public static FollowingFragment newInstance() {

        return new FollowingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FollowingPageAdapter( getFragmentManager() );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.following_pager_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListener();
        getUserData();
    }

    private void getUserData() {

adapter.addFragment(new FollowListFragment(),"Follow");
        adapter.addFragment(new YouListFragment(),"You");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);


    }



    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Follow");

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }
}
