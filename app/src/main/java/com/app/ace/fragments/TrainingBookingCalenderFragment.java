package com.app.ace.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import roboguice.inject.InjectView;

import static com.app.ace.R.id.datePicker;
import static com.app.ace.R.id.sp_weeks;


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



    @InjectView(R.id.lv_trainingBokingCalender)
    private ListView lv_trainingBokingCalender;

    ArrayList<String> days;

    @InjectView(R.id.sp_month)
    Spinner sp_month;
    String month;
    int monthValue;


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

        SpinnerData();

        days=DaysOfMonth(monthValue);

        getSearchUserData();
       // addList();
        setListener();

    }

    private void SpinnerData() {

        List<String> months = new ArrayList<String>();
        months.add("Select month");
        months.add("JANUARY");
        months.add("FEBRUARY");
        months.add("MARCH");
        months.add("APRIL");
        months.add("MAY");
        months.add("JUNE");
        months.add("JULY");
        months.add("AUGUST");
        months.add("SEPTEMBER");
        months.add("OCTOBER");
        months.add("NOVEMBER");
        months.add("DECEMBER");

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_month.setAdapter(monthAdapter);

        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                month = sp_month.getSelectedItem().toString();

                if(month.contains("JANUARY"))
                {
                    monthValue=0;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("FEBRUARY"))
                {
                    monthValue=1;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("MARCH"))
                {
                    monthValue=2;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("APRIL"))
                {
                    monthValue=3;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("MAY"))
                {
                    monthValue=4;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("JUNE"))
                {
                    monthValue=5;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("JULY"))
                {
                    monthValue=6;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("AUGUST"))
                {
                    monthValue=7;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("SEPTEMBER"))
                {
                    monthValue=8;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("OCTOBER"))
                {
                    monthValue=9;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("NOVEMBER"))
                {
                    monthValue=10;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if(month.contains("DECEMBER"))
                {
                    monthValue=11;
                    days=DaysOfMonth(monthValue);
                    getSearchUserData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<String> DaysOfMonth(int monthValue) {

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(2017,monthValue,1);
        // Calculate remaining days in month
        int remainingDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - mCalendar.get(Calendar.DAY_OF_MONTH) + 1;
        ArrayList<String> allDays = new ArrayList<String>();
        SimpleDateFormat mFormat = new SimpleDateFormat("EEE, dd ", Locale.US);
        for(int i = 0; i < remainingDay; i++){
            // Add day to list
            allDays.add(mFormat.format(mCalendar.getTime()));
            // Move next day
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return allDays;


    }

    private void setListener() {

        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        avail.setOnClickListener(this);

    }


    private void getSearchUserData() {
        userCollection= new ArrayList<>();
        for(String item: days) {
            userCollection.add(new TrainingBookingCalenderItem(item, "12:00", "1:00", "4:00", "8:00"));
        }
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


