package com.app.ace.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.TrainingBookingCalenderItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.DateHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.TrainingBooking;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TrainingBookingListItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;

import roboguice.inject.InjectView;



/**
 * Created by muniyemiftikhar on 4/7/2017.
 */

public class TrainingBookingCalenderFragment extends BaseFragment implements DatePickerListener,TrainingBooking, View.OnClickListener {

    @InjectView(R.id.avail)
    AnyTextView avail;


    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;

    @InjectView(R.id.iv_Calander)
    private ImageView iv_Calander;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    @InjectView(R.id.datePicker)
    private HorizontalPicker datePicker;

    @InjectView(R.id.lv_trainingBokingCalender)
    private ListView lv_trainingBokingCalender;


    private ArrayListAdapter<TrainingBookingCalenderItem> adapter;

    private ArrayList<TrainingBookingCalenderItem> userCollection = new ArrayList<>();

    public static TrainingBookingCalenderFragment newInstance() {

        return new TrainingBookingCalenderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<TrainingBookingCalenderItem>(getDockActivity(), new TrainingBookingListItemBinder(getDockActivity(),this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_booking_calender, container, false);
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
        getSearchUserData();
       // addList();
        setListener();

    }

    private void setListener() {

        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        avail.setOnClickListener(this);

    }


    private void getSearchUserData() {
        userCollection= new ArrayList<>();
        userCollection.add(new TrainingBookingCalenderItem("Fri 7","12:00","1:00","4:00","8:00"));
        userCollection.add(new TrainingBookingCalenderItem("Fri 7","12:00","1:00","4:00","8:00"));
        //userCollection.add(new DetailedScreenItem("Training","BodyBuilding"));
        //addList();
      /*  userCollection= new ArrayList<>();
        userCollection.add(new TrainingBookingCalenderItem("6:00","8:00"));
        userCollection.add(new TrainingBookingCalenderItem("6:00","8:00"));*/

        bindData(userCollection);
    }

    private void bindData(ArrayList<TrainingBookingCalenderItem> userCollection) {
        adapter.clearList();
        lv_trainingBokingCalender.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showRepeatButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getDockActivity(),"Repeat",Toast.LENGTH_LONG).show();
            }
        });

        titleBar.showTickButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getDockActivity(),"Saved",Toast.LENGTH_LONG).show();
            }
        });
        titleBar.setSubHeading("Trainer Calendar");

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
    }

    @Override
    public void addList() {

    }

    @Override
    public void backList() {
        avail.setText("Availabilty");
//getSearchUserData();
    }

    @Override
    public void textList() {
avail.setText("Second Availabilty");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_profile:

                getDockActivity().addDockableFragment(TrainerClientScheduleFragment.newInstance(), "TrainerClientScheduleFragment");

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


