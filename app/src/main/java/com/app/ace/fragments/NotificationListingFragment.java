package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.NotificationEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.entities.TrainerBooking;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.RejectBooking;
import com.app.ace.interfaces.TraineeSchedule;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.NotificationListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.lv_trainer_srceen;


/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class NotificationListingFragment  extends BaseFragment implements View.OnClickListener,TraineeSchedule{

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

    private ArrayList<Slot> userCollection = new ArrayList<>();

    ArrayList<Slot> slots = new ArrayList<>();
    private String slotjson;

    private ArrayListAdapter<NotificationEnt> adapter;
    private ArrayList<NotificationEnt> dataCollection = new ArrayList<>();

    public static NotificationListingFragment newInstance() {

        return new NotificationListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<NotificationEnt>(getDockActivity(), new NotificationListItemBinder(getDockActivity(),this));
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

        Call<ResponseWrapper<ArrayList<NotificationEnt>>>callback = webService.getAppNotification(prefHelper.getUserId());
        System.out.println(prefHelper.getUserId());
        callback.enqueue(new Callback<ResponseWrapper<ArrayList<NotificationEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<NotificationEnt>>> call, Response<ResponseWrapper<ArrayList<NotificationEnt>>> response) {
                getNotificationData(response.body().getResult());
                prefHelper.setBadgeCount(0);
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<NotificationEnt>>> call, Throwable t) {
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

    private void getNotificationData(ArrayList<NotificationEnt> notificationList) {

        dataCollection = new ArrayList<>();

        if (notificationList.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        dataCollection.addAll(notificationList);
        bindData(dataCollection);

    }

    private void bindData(ArrayList<NotificationEnt> dataCollection) {
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
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.iv_list:

            case R.id.iv_profile:

                AppConstants.is_show_trainer = false;
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(Integer.parseInt(prefHelper.getUserId())),"TrainerProfileFragment");

                break;

            case R.id.iv_Fav:
                getDockActivity().addDockableFragment(FollowingFragment.newInstance(),"F0llowinf");
              //  UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

            case R.id.iv_Camera:
                CameraHelper.uploadMedia(getMainActivity());

                break;

            case R.id.iv_Calander:

                getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(),"TrainingBookingCalenderFragment");

               /* if(AppConstants.is_show_trainer){

                    getDockActivity().addDockableFragment(TrainerBookingCalendarFragment.newInstance(),"TrainerBookingCalendarFragment");
                }
                else
                {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

                }*/

                break;

            case R.id.iv_Home:

                getDockActivity().addDockableFragment(HomeFragment.newInstance(),"HomeFragment");

                break;

        }
    }

   /* @Override
    public void deleteBooking(final int Position, Object entity) {

        final NotificationEnt ent=(NotificationEnt) entity;
        Call<ResponseWrapper<NotificationEnt>>callback = webService.rejectBooking(prefHelper.getUserId(),ent.getId_range());

        callback.enqueue(new Callback<ResponseWrapper<NotificationEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<NotificationEnt>> call, Response<ResponseWrapper<NotificationEnt>> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());

                    int notificationId=dataCollection.get(Position).getId();
                    deleteNotification(notificationId,Position);

                   *//* dataCollection.remove(Position);
                    adapter.clearList();
                    adapter.addAll(dataCollection);
                    adapter.notifyDataSetChanged();*//*

                } else {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<NotificationEnt>> call, Throwable t) {
                Log.e(getClass().getName(),t.toString());
            }
        });



    }

    @Override
    public void acceptBooking(int Position, Object entity) {

        int notificationId=dataCollection.get(Position).getId();
        deleteNotification(notificationId,Position);

       *//* dataCollection.remove(Position);
        adapter.clearList();
        adapter.addAll(dataCollection);
        adapter.notifyDataSetChanged();*//*

    }

    private void deleteNotification(int notificationId, final int position) {

        Call<ResponseWrapper<NotificationEnt>>callback = webService.deleteNotification(prefHelper.getUserId(),notificationId);

        callback.enqueue(new Callback<ResponseWrapper<NotificationEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<NotificationEnt>> call, Response<ResponseWrapper<NotificationEnt>> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    dataCollection.remove(position);
                    adapter.clearList();
                    adapter.addAll(dataCollection);
                    adapter.notifyDataSetChanged();

                } else {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<NotificationEnt>> call, Throwable t) {
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });


    }*/

    @Override
    public void getTraineeSchedule(final NotificationEnt entity) {

        Call<ResponseWrapper<TrainerBooking>> callBack = webService.getScheduleTrainee(prefHelper.getUserId(), entity.getBooking_start(), entity.getBooking_start());
        loadingStarted();

        callBack.enqueue(new Callback<ResponseWrapper<TrainerBooking>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<TrainerBooking>> call, Response<ResponseWrapper<TrainerBooking>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    setTraineeData(response.body().getResult(),entity);
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<TrainerBooking>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });

    }

    private void setTraineeData(TrainerBooking result, NotificationEnt entity) {
        Slot trainerSlots = new Slot();
        if(result!=null) {
            slots = result.getSlots();
            for (Slot item : slots) {
                if (item.getBookings() != null) {
                    if (item.getBookings().getUser().getId() == entity.getSender_id() && item.getId().equals(entity.getSlot_id())) {
                        trainerSlots = item;
                    }
                }
            }
            System.out.print(trainerSlots.toString());
            slotjson = GsonFactory.getConfiguredGson().toJson(trainerSlots);
        }
        if(trainerSlots.getBookings()!=null){
        getDockActivity().addDockableFragment(DetailedScreenFragment.newInstance(slotjson,entity), "DetailedScreenFragment");}
        else{
            getDockActivity().addDockableFragment(TrainerClientScheduleFragment.newInstance(), "TrainerClientScheduleFragment");
           // UIHelper.showShortToastInCenter(getDockActivity(),"creashed");
            }
        }
    }

