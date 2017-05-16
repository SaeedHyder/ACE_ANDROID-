package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.ace.R;
import com.app.ace.entities.FollowingCountListEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.TrainerTimingSlots;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.TitleBar;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.global.AppConstants.trainer;

/**
 * Created by saeedhyder on 4/4/2017.
 */

public class CalendarFragment extends BaseFragment implements View.OnClickListener {

    @InjectView (R.id.calendarView)
    CalendarPickerView calendarView;

    @InjectView (R.id.btn_avaliablity)
    Button btn_avaliablity;

    @InjectView(R.id.sp_trainerTime)
    Spinner sp_timer;

    @InjectView(R.id.sp_weeks)
    Spinner sp_weeks;

    @InjectView(R.id.sp_category)
    Spinner sp_category;

    @InjectView(R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    int days;
    List<String> timings=new ArrayList<String>();


    public static CalendarFragment newInstance() {

        return new CalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_calendar, container, false);

        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trainerTimeSlots();


        //Spinner Weeks
        List<String> timeDuration = new ArrayList<String>();
        timeDuration.add("Select time duration");
        timeDuration.add("2 Week");
        timeDuration.add("1 Month");
        timeDuration.add("3 Months");
        timeDuration.add("6 Months");

        ArrayAdapter<String> TimeDurationAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, timeDuration);
        TimeDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weeks.setAdapter(TimeDurationAdapter);

        //Spinner Category
        List<String> category = new ArrayList<String>();
        category.add("Types of Training");
        category.add("Flexiblity training");
        category.add("Dynamic Strength training");
        category.add("Static strength training");
        category.add("Circuit training");
        category.add("Aerobic training");
        category.add("Body Building");
        category.add("Lose Weight");
        
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(categoryAdapter);
       /* String calendarDate = calendarView.getDate()+"";
        Toast.makeText(getDockActivity(),calendarDate,Toast.LENGTH_LONG).show();*/



        setCalendarView(days);

        sp_weeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String duration = sp_weeks.getSelectedItem().toString();
                if(duration=="2 Week")
                {
                    days=14;
                    setCalendarView(days);

                }
                if(duration=="1 Month")
                {
                    days=30;
                    setCalendarView(days);
                }
                if(duration=="3 Months")
                {
                    days=90;
                    setCalendarView(days);
                }
                if(duration=="6 Months")
                {
                    days=180;
                    setCalendarView(days);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        setListener();
    }

    private void trainerTimeSlots() {

        Call<ResponseWrapper<ArrayList<TrainerTimingSlots>>> callBack = webService.TrainerTimingSlots(prefHelper.getUserId());
        loadingStarted();

        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<TrainerTimingSlots>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<TrainerTimingSlots>>> call, Response<ResponseWrapper<ArrayList<TrainerTimingSlots>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS))
                {
                    getTrainerTimingSlots(response.body().getResult());
                }
                else
                {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<TrainerTimingSlots>>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });

    }

    private void getTrainerTimingSlots(ArrayList<TrainerTimingSlots> result) {

        timings.add("Slots Avaliable");

        for(TrainerTimingSlots item: result)
        {
            timings.add(item.getStart_time()+" to "+item.getEnd_time());

        }

        ArrayAdapter<String> timerAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, timings);

        timerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_timer.setAdapter(timerAdapter);

    }

    private void setCalendarView(int days) {
       /* calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1990, 1, 1))
                .setMaximumDate(CalendarDay.from(2060, 12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.selectRange(CalendarDay.from(2017, 4, 12), CalendarDay.from(2017, 4, 15));*/


        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendarView.setCustomDayView(new DefaultDayViewAdapter());
        // calendarView.setCustomDayView(new CustomDayViewAdapter(myDockActivity));


        Calendar today = Calendar.getInstance();
        ArrayList<Date> dates = new ArrayList<Date>();
        today.add(Calendar.DATE, 1);
        dates.add(today.getTime());

        today.add(Calendar.DATE, days);
        dates.add(today.getTime());


        //dates.add(today.getTime());
        calendarView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
       // calendarView.setDecorators(Arrays.<CalendarCellDecorator>asList(new SampleDecorator()));
        calendarView.init(new Date(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withSelectedDates(dates);


    }

    private void setListener() {

        btn_training_Search_Submit.setOnClickListener(this);

    }

    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_training_Search_Submit:

                getDockActivity().addDockableFragment(CalenderPopupDialogFragment.newInstance(),"CalenderPopupDialogFragment");
                break;
        }}


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("May");

    }

}
