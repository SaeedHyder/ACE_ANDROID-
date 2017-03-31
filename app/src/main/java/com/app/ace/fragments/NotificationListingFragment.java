package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.NotificationDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.NotificationListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class NotificationListingFragment  extends BaseFragment implements View.OnClickListener{


    @InjectView(R.id.listView)
    private ListView listView;

    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;

    @InjectView(R.id.iv_Calander)
    private ImageView iv_Calander;

    @InjectView(R.id.iv_Camera)
    private ImageView iv_Camera;

    @InjectView(R.id.iv_Fav)
    private ImageView iv_Fav;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    private ArrayListAdapter<NotificationDataItem> adapter;
    private ArrayList<NotificationDataItem> dataCollection = new ArrayList<>();

    public static NotificationListingFragment newInstance() {

        return new NotificationListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<NotificationDataItem>(getDockActivity(), new NotificationListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_listing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
        getNotificationData();
    }

    private void setListener() {

        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_Camera.setOnClickListener(this);
        iv_Fav.setOnClickListener(this);
        iv_profile.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.showLongToastInCenter(getDockActivity(), "Will be implemented in beta");
            }
        });
    }

    private void getNotificationData() {

        dataCollection = new ArrayList<>();

        dataCollection.add(new NotificationDataItem("Lorel ispum dolor set", "Wed at 08:00 am"));
        dataCollection.add(new NotificationDataItem("Etiam in ante odio pulvinar ", "Wed at 07:00 pm"));
        dataCollection.add(new NotificationDataItem("Steve Camb", "Wed at 04:45 am"));

        bindData(dataCollection);

    }

    private void bindData(ArrayList<NotificationDataItem> dataCollection) {
        adapter.clearList();
        listView.setAdapter(adapter);
        adapter.addAll(dataCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.notifications));

        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");

            }
        });
    }

    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.iv_list:

            case R.id.iv_profile:

                AppConstants.is_show_trainer = false;
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(),"TrainerProfileFragment");

                break;

            case R.id.iv_Fav:

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

            case R.id.iv_Camera:


                break;

            case R.id.iv_Calander:

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

            case R.id.iv_Home:

                getDockActivity().addDockableFragment(HomeFragment.newInstance(),"HomeFragment");

                break;

        }
    }
}
