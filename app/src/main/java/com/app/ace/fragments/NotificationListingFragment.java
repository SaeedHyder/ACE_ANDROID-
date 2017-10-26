package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.NewNotificationEnt;
import com.app.ace.entities.Notification;
import com.app.ace.entities.NotificationEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.entities.TrainerBooking;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.TraineeSchedule;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.NotificationListItemBinder;

import com.app.ace.ui.views.TitleBar;
import com.paginate.Paginate;
import com.paginate.abslistview.LoadingListItemCreator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;


/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class NotificationListingFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;
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

    int offset = 0;
    int limit = 10;


    private ArrayListAdapter<Notification> adapter;
    private ArrayList<Notification> dataCollection = new ArrayList<>();

    public static NotificationListingFragment newInstance() {

        return new NotificationListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<Notification>(getDockActivity(), new NotificationListItemBinder(getDockActivity(), prefHelper));
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

        if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            notificationService(0, 1000);
        }

    }

    private void notificationService(int offset, int limit) {

        Call<ResponseWrapper<NewNotificationEnt>> callback = webService.getAppNotification(prefHelper.getUserId(), offset, limit);
        System.out.println(prefHelper.getUserId());
        callback.enqueue(new Callback<ResponseWrapper<NewNotificationEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<NewNotificationEnt>> call, Response<ResponseWrapper<NewNotificationEnt>> response) {
                if (response.body().getUserDeleted() == 0) {
                    getNotificationData(response.body().getResult().getNotification());
                    prefHelper.setBadgeCount(0);
                } else {
                    final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                    dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogHelper.hideDialog();
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        }
                    },response.body().getMessage());
                    dialogHelper.showDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<NewNotificationEnt>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


    private void setListener() {

        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_Camera.setOnClickListener(this);
        iv_Fav.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
    }

    private void getNotificationData(ArrayList<Notification> notificationList) {


        dataCollection = new ArrayList<>();

        dataCollection.addAll(notificationList);
        bindData(dataCollection);

    }

    private void bindData(ArrayList<Notification> dataCollection) {

        if (dataCollection.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

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
        titleBar.showBackButton();
        titleBar.hideBadge();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_list:

            case R.id.iv_profile:

                AppConstants.is_show_trainer = false;
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(Integer.parseInt(prefHelper.getUserId())), "TrainerProfileFragment");

                break;

            case R.id.iv_Fav:
                getDockActivity().addDockableFragment(FollowingFragment.newInstance(), "F0llowinf");

                break;

            case R.id.iv_Camera:
                CameraHelper.uploadMedia(getMainActivity());

                break;

            case R.id.iv_Calander:

                getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(), "TrainingBookingCalenderFragment");


                break;

            case R.id.iv_Home:

                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                break;

        }
    }


}