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
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.entities.TraineeScheduleEnt;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.interfaces.OndeleteListener;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TraineeScheduleListItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class TraineeScheduleFragment extends BaseFragment implements View.OnClickListener, DatePickerListener, OndeleteListener {

    @InjectView(R.id.img_DetailedProfile)
    ImageView img_DetailedProfile;
    @InjectView(R.id.iv_Home)
    ImageView iv_Home;
    @InjectView(R.id.iv_Calander)
    ImageView iv_Calander;
    @InjectView(R.id.datePicker)
    private HorizontalPicker datePicker;
    @InjectView(R.id.lv_trauneeScheduleScreen)
    private ListView lv_trauneeScheduleScreen;
    @InjectView(R.id.txt_day)
    private AnyTextView txt_day;
    @InjectView(R.id.txt_time)
    private AnyTextView txt_time;
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;

    @InjectView(R.id.txt_detailedS_ProfileName)
    private AnyTextView txt_detailedS_ProfileName;
    private ArrayListAdapter<Slot> adapter;

    private ArrayList<Slot> userCollection = new ArrayList<>();


    public static TraineeScheduleFragment newInstance() {
        return new TraineeScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<Slot>(getDockActivity(), new TraineeScheduleListItemBinder(getDockActivity(), getDockActivity(), this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainee_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker
                .setListener(this)
                .setDays(120)
                .setOffset(7)
                .init();
        datePicker.setDate(new DateTime());
        getTraineeScheduleData(new DateTime());
        setListener();

    }

    private void setListener() {


        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
    }


    private void getTraineeScheduleData(DateTime dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(dateTime.toDate());

        Call<ResponseWrapper<TraineeScheduleEnt>> callback = webService.getTraineeSchedule(prefHelper.getUserId(), date);
        callback.enqueue(new Callback<ResponseWrapper<TraineeScheduleEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<TraineeScheduleEnt>> call, Response<ResponseWrapper<TraineeScheduleEnt>> response) {

                if (response.body().getUserDeleted() == 0) {
                    if (response.body() != null) {
                        setTraineeScheduleData(response.body().getResult());
                    }
                } else {
                    final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                    dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogHelper.hideDialog();
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        }
                    });
                    dialogHelper.showDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<TraineeScheduleEnt>> call, Throwable t) {
                Log.e("TraineeScheduleFragment", t.toString());
            }
        });

    }

    private void setTraineeScheduleData(TraineeScheduleEnt result) {

        if (result.getSlots().size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            lv_trauneeScheduleScreen.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            lv_trauneeScheduleScreen.setVisibility(View.VISIBLE);
        }
        ImageLoader.getInstance().displayImage(result.getUser().getProfile_image(), img_DetailedProfile);
        txt_detailedS_ProfileName.setText(result.getUser().getFirst_name()
                + " " + result.getUser().getLast_name());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        txt_day.setText("");
        txt_time.setText("");
        /*try {
           *//* Date date = format.parse(result.getDate());
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);*//*

        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        userCollection = result.getSlots();

        bindData(userCollection);
    }

    private void bindData(ArrayList<Slot> userCollection) {
        adapter.clearList();
        lv_trauneeScheduleScreen.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.My_Schedule));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_training_Search_Submit:

                getDockActivity().addDockableFragment(NotificationListingFragment.newInstance(), "NotificationListingFragment");
                break;

            case R.id.iv_Home:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                break;

            case R.id.iv_Calander:

                getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(), "TraineeScheduleFragment");

              /*  if(AppConstants.is_show_trainer){

                    getDockActivity().addDockableFragment(TrainerBookingCalendarFragment.newInstance(),"TrainerBookingCalendarFragment");
                }
                else
                {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

                }*/

                break;

        }
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        String week = dateSelected.toString("EEE", Locale.getDefault()).toUpperCase();
        String month = dateSelected.toString("MMMM", Locale.getDefault());
        String date = String.valueOf(dateSelected.getDayOfMonth());

        //showdate.setText(week + " , " + month + "  " + date);
        getTraineeScheduleData(dateSelected);
    }

    @Override
    public void Ondelete() {

        // getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void OndeleteTrainee(int position) {
        loadingStarted();
        if (userCollection.size() > position) {
            userCollection.remove(position);
            adapter.clearList();
            lv_trauneeScheduleScreen.setAdapter(adapter);
            adapter.addAll(userCollection);
            adapter.notifyDataSetChanged();
            loadingFinished();
        }
    }
}
