package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.ScheduleTime;
import com.app.ace.entities.TrainerBookingCalendarJson;
import com.app.ace.entities.TrainingBookingCalenderItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.TrainingBooking;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.adapters.CalendarArrayListAdapter;
import com.app.ace.ui.viewbinders.TrainingBookingListItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static java.lang.Integer.parseInt;


/**
 * Created by saeedHyder on 4/7/2017.
 */

public class TrainingBookingCalenderFragment extends BaseFragment implements DatePickerListener, TrainingBooking, View.OnClickListener, com.app.ace.interfaces.NumberPicker {

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

    @InjectView (R.id.numberPicker1)
    NumberPicker numberPicker1;

    @InjectView(R.id.btn_ok)
    Button btn_ok;

    ArrayList<String> days;

    @InjectView(R.id.sp_month)
    Spinner sp_month;
    String month;
    int monthValue;
    int monthDateValue;
    int oldValue;


    TrainerBookingCalendarJson trainerBookingCalendarJson;
    ArrayList<TrainerBookingCalendarJson> trainerBookingCalendarJsonCollection;


    TrainingBookingListItemBinder trainingBookingListItemBinder;
    ScheduleTime scheduleTime;
    String trainerScheduleJson;





    private CalendarArrayListAdapter<TrainingBookingCalenderItem> adapter;

    private ArrayList<TrainingBookingCalenderItem> userCollection = new ArrayList<>();

    public static TrainingBookingCalenderFragment newInstance() {

        //this.trainingBookingListItemBinder=trainingBookingListItemBinder;

        return new TrainingBookingCalenderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trainingBookingListItemBinder = new TrainingBookingListItemBinder(getDockActivity(), this,this);
        adapter = new CalendarArrayListAdapter<TrainingBookingCalenderItem>(getDockActivity(), trainingBookingListItemBinder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_booking_calender, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SpinnerData();

        days = DaysOfMonth(monthValue);

        getSearchUserData();
        // addList();
        setListener();



    }

    private void numberPicker(final AnyTextView txtTo) {

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(24);
        numberPicker1.setWrapSelectorWheel(true);

        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                String Old = "Old Value : ";
                String New = "New Value : ";
                //tv1.setText(oldVal +":00");
                txtTo.setText(newVal+":00");
                //Toast.makeText(getDockActivity(),String.valueOf(newVal),Toast.LENGTH_LONG).show();


            }
        });

    }

    private void SpinnerData() {

        List<String> months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

       // ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner, months);
      //  monthAdapter.setDropDownViewResource(R.layout.spinner);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_dropdown_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_month.setAdapter(monthAdapter);


        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                month = sp_month.getSelectedItem().toString();

                if (month.contains("January")) {
                    monthValue = 0;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("February")) {
                    monthValue = 1;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("March")) {
                    monthValue = 2;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("April")) {
                    monthValue = 3;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("May")) {
                    monthValue = 4;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("June")) {
                    monthValue = 5;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("July")) {
                    monthValue = 6;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("August")) {
                    monthValue = 7;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("September")) {
                    monthValue = 8;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("October")) {
                    monthValue = 9;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("November")) {
                    monthValue = 10;
                    days = DaysOfMonth(monthValue);
                    getSearchUserData();
                }
                if (month.contains("December")) {
                    monthValue = 11;
                    days = DaysOfMonth(monthValue);
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
        monthDateValue=monthValue+1;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        mCalendar.set(year, monthValue, 1);
        // Calculate remaining days in month
        int remainingDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - mCalendar.get(Calendar.DAY_OF_MONTH) + 1;
        ArrayList<String> allDays = new ArrayList<String>();
        SimpleDateFormat mFormat = new SimpleDateFormat("EEE, dd ,MM,MMMM", Locale.US);
        for (int i = 0; i < remainingDay; i++) {
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
        btn_ok.setOnClickListener(this);

    }


    private void getSearchUserData() {
        userCollection = new ArrayList<>();
        for (String item : days) {
            userCollection.add(new TrainingBookingCalenderItem(item, "00:00", "00:00", "00:00", "00:00","00:00", "00:00"));
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
        adapter.addAll(userCollection);
        lv_trainingBokingCalender.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void createTrainerSchedule(ArrayList<TrainerBookingCalendarJson> jsonObject)
    {
        Call<ResponseWrapper> callBack = webService.createSchedule(
                jsonObject);

        callBack.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    //UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    showBookingDialog();

                }
                else
                {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });


    }

    private void showBookingDialog() {

        final DialogFragment successPopUp = DialogFragment.newInstance();
        successPopUp.setPopupData("Booking Slots","", prefHelper.getUserName().toUpperCase(), "Your Booking Slot are Updated",true,false);

        successPopUp.setbtndialog_1_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPopUp.dismissDialog();
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(), "TrainingBookingCalenderFragment");
            }
        });

        successPopUp.show(getDockActivity().getSupportFragmentManager(), "signUpPopUp");
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showRepeatButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getDockActivity(), "Repeat", Toast.LENGTH_LONG).show();
            }
        });

        titleBar.showTickButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trainingBookingListItemBinder.getTimeArray();
                setDataInJson(trainingBookingListItemBinder.getTimeArray());
                createTrainerSchedule(trainerBookingCalendarJsonCollection);
            }
        });
        titleBar.setSubHeading("Trainer Calendar");

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
    }

    @Override
    public void LastAvailtxt() {
        avail.setText("3rd Availabilty");

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

    public void setDataInJson(HashMap<String, List<String>> timeArray)
{

    ArrayList<ScheduleTime> scheduleTimeCollection = new ArrayList();
    ScheduleTime scheduleTime = null;

    trainerBookingCalendarJsonCollection = new ArrayList<>();

    try {



     for( HashMap.Entry<String, List<String>> entry : timeArray.entrySet())
     {

         String[] days=entry.getKey().replace(" ","").split(",");
         String day=days[1];
         String month=days[2];
         String monthName=days[3];
         String date="2017-"+month+"-"+day;

         trainerBookingCalendarJson = new TrainerBookingCalendarJson();
         trainerBookingCalendarJson.setTrainer_id(parseInt(prefHelper.getUserId()));
         trainerBookingCalendarJson.setMonth(monthName);


         String data=entry.getValue().toString().replace("[","").replace("]","");

         String[] timings=data.split(",");


         trainerBookingCalendarJson.setDate(date.toString());

         int i=1;
         int hours;
         String Starttime="";
         String EndTime="";

         for (String item : timings)
         {

             if(i==1 && item!=null) {

                 Starttime=item;
                // scheduleTime = new ScheduleTime();
                // scheduleTime.setStart_time(item);
             }
             if(i==2 && item!=null) {

                 EndTime=item;
                 hours= TimeDifference(Starttime,EndTime);

                 String[] startTime=Starttime.split(":");
                 String StartTimeSplit=startTime[0];
                 for(int x=0;x<hours;x++)
                 {
                     int IntStartTime= parseInt(StartTimeSplit.trim())+x;
                     int IntEndTime=IntStartTime+1;
                     scheduleTime = new ScheduleTime();
                     scheduleTime.setStart_time(String.valueOf(IntStartTime)+":00");

                     scheduleTime.setEnd_time(String.valueOf(IntEndTime)+":00");
                     scheduleTimeCollection.add(scheduleTime);

                 }

             }
             if(i==3 && item!=null) {

                 Starttime=item;
               //  scheduleTime = new ScheduleTime();
                // scheduleTime.setStart_time(item);
             }
             if(i==4 && item!=null) {

                 EndTime=item;
                 hours= TimeDifference(Starttime,EndTime);

                 String[] startTime=Starttime.split(":");
                 String StartTimeSplit=startTime[0];
                 for(int x=0;x<hours;x++)
                 {

                     int IntStartTime= Integer.parseInt(StartTimeSplit.trim())+x;
                     int IntEndTime=IntStartTime+1;
                     scheduleTime = new ScheduleTime();
                     scheduleTime.setStart_time(String.valueOf(IntStartTime)+":00");

                     scheduleTime.setEnd_time(String.valueOf(IntEndTime)+":00");
                     scheduleTimeCollection.add(scheduleTime);

                 }


                 //scheduleTime.setEnd_time(item);
                // scheduleTimeCollection.add(scheduleTime);
             }

             if(i==5 && item!=null) {

                 Starttime=item;
                 //scheduleTime = new ScheduleTime();
                // scheduleTime.setStart_time(item);
             }
             if(i==6 && item!=null) {

                 EndTime=item;
                 hours= TimeDifference(Starttime,EndTime);

                 String[] startTime=Starttime.split(":");
                 String StartTimeSplit=startTime[0];
                 for(int x=0;x<hours;x++)
                 {
                     int IntStartTime= (parseInt(StartTimeSplit.trim()))+x;
                     int IntEndTime=IntStartTime+1;
                     scheduleTime = new ScheduleTime();
                     scheduleTime.setStart_time(String.valueOf(IntStartTime)+":00");

                     scheduleTime.setEnd_time(String.valueOf(IntEndTime)+":00");
                     scheduleTimeCollection.add(scheduleTime);

                 }

                 //scheduleTime.setEnd_time(item);
                // scheduleTimeCollection.add(scheduleTime);
             }

             i++;

           //  Toast.makeText(getDockActivity(),item,Toast.LENGTH_LONG).show();
         }
         trainerBookingCalendarJson.setTime(scheduleTimeCollection);
         trainerBookingCalendarJsonCollection.add(trainerBookingCalendarJson);
         scheduleTimeCollection = new ArrayList();


     }



     trainerScheduleJson = GsonFactory.getConfiguredGson().toJson(trainerBookingCalendarJsonCollection);

    } catch (Exception e) {
        e.printStackTrace();
    }



    }



    int TimeDifference(String startTime,String endTime)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(startTime);
            date2 = simpleDateFormat.parse(endTime);
        }  catch (ParseException e) {
            e.printStackTrace();
        }


        long difference = date2.getTime() - date1.getTime();
        int dayss = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*dayss)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*dayss) - (1000*60*60*hours)) / (1000*60);
        hours = (hours < 0 ? -hours : hours);

        //Toast.makeText(getDockActivity(), String.valueOf(hours),Toast.LENGTH_LONG).show();

        return hours;
    }

    @Override
    public void UpdateTime(AnyTextView txtTo) {

        lv_trainingBokingCalender.setVisibility(View.GONE);
        numberPicker1.setVisibility(View.VISIBLE);
        btn_ok.setVisibility(View.VISIBLE);
        numberPicker(txtTo);


    }

    @Override
    public void PressBtn() {

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberPicker1.setVisibility(View.GONE);
                btn_ok.setVisibility(View.GONE);
                lv_trainingBokingCalender.setVisibility(View.VISIBLE);


            }
        });
    }
}


