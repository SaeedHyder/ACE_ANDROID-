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

        adapter = new FollowingPageAdapter( getChildFragmentManager() );
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

        setListener();
    }

    private void setListener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setCurrentItem(1);
    }


    private void getUserData() {

       if( adapter.getCount()>0)
       {adapter.clearList();}

        adapter.addFragment(new FollowListFragment(),getString(R.string.Follow));
        adapter.addFragment(new YouListFragment(),getString(R.string.You));
        pager.setAdapter(adapter);
        pager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(pager);

    }



    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Follow));

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }
}
