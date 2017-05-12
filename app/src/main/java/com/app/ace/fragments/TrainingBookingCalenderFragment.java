package com.app.ace.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.ScheduleTime;
import com.app.ace.entities.TrainerBookingCalendarJson;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.Datedialoghelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.SetTimeDataOnTextView;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;


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


import static com.app.ace.R.id.SecondtxtFrom;
import static com.app.ace.R.id.SecondtxtTo;
import static com.app.ace.R.id.ThirdtxtFrom;
import static com.app.ace.R.id.ThirdtxtTo;
import static com.app.ace.R.id.textView;
import static com.app.ace.R.id.txt;
import static com.app.ace.R.id.txtDayDate;
import static com.app.ace.R.id.txtFrom;
import static java.lang.Integer.parseInt;


/**
 * Created by saeedHyder on 4/7/2017.
 */

public class TrainingBookingCalenderFragment extends BaseFragment implements View.OnClickListener,SetTimeDataOnTextView {



    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;

    @InjectView(R.id.SecondAvail)
    LinearLayout SecondAvail;

    @InjectView(R.id.ThirdAvail)
    LinearLayout ThirdAvail;

    @InjectView(R.id.iv_Calander)
    private ImageView iv_Calander;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    @InjectView(R.id.SelectDatecalendar)
    ImageView SelectDatecalendar;

    @InjectView(R.id.txt_Date)
    AnyTextView txt_Date;

    @InjectView(R.id.sp_weeks)
    Spinner sp_weeks;

    @InjectView(R.id.txtTo)
    AnyTextView txtTo;

    @InjectView(R.id.txtFrom)
    AnyTextView txtFrom;

    @InjectView(R.id.SecondtxtTo)
    AnyTextView SecondtxtTo;

    @InjectView(R.id.SecondtxtFrom)
    AnyTextView SecondtxtFrom;

    @InjectView(R.id.ThirdtxtTo)
    AnyTextView ThirdtxtTo;

    @InjectView(R.id.ThirdtxtFrom)
    AnyTextView ThirdtxtFrom;

    @InjectView(R.id.txtIncre)
    ImageView txtIncre;

    @InjectView(R.id.SecondtxtIncre)
    ImageView SecondtxtIncre;

    @InjectView(R.id.ThirdtxtIncre)
    ImageView ThirdtxtIncre;


    String month;
    int monthValue;
    int monthDateValue;
    ArrayList<String> days;
    int oldValue;
    Object textView ;

    String TimeSelected;

    Calendar calendar ;

    int Year, Month, Day ;


    TrainerBookingCalendarJson trainerBookingCalendarJson;
    ArrayList<TrainerBookingCalendarJson> trainerBookingCalendarJsonCollection;

    HashMap<String,List<String>> ScheduleHashMap=new HashMap<>();
    List<String> timeArray=new ArrayList();

    public String selectedDay;
    public Date dateSpecified;
    public Date EndDate;
    public String JsonDate;


    String trainerScheduleJson;


    public static TrainingBookingCalenderFragment newInstance() {

        return new TrainingBookingCalenderFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_booking_calender, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        days = DaysOfMonth(monthValue);
        setDatePickerVariables();

        setEndTimeSpinner();

        setListener();
    }



    private void ShowRadioListDialog() {

        final DialogBox successPopUp  = DialogBox.newInstance();
        successPopUp.setDocActivityContext(getDockActivity());
        successPopUp.SetTimeDataOnTextViewContext(this);
        successPopUp.setPopupData("select",true);
        successPopUp.show(getDockActivity().getSupportFragmentManager(), "listDilog");

    }

    private void setEndTimeSpinner() {
        List<String> timeDuration = new ArrayList<String>();
        timeDuration.add("2 Week");
        timeDuration.add("1 Month");
        timeDuration.add("3 Months");
        timeDuration.add("6 Months");

        ArrayAdapter<String> TimeDurationAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, timeDuration);
        TimeDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weeks.setAdapter(TimeDurationAdapter);
    }

    private void setDatePickerVariables() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }


   void ShowDateDialog(final AnyTextView txtView)
   {
       final Datedialoghelper datedialoghelper =  new Datedialoghelper();
       datedialoghelper.initDateDialog(getDockActivity(), Year, Month, Day, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               Date todayDate = null;
               try {
                   todayDate = sdf.parse(sdf.format(new Date()));
               } catch (ParseException e) {
                   e.printStackTrace();
               }

               calendar.set(Calendar.YEAR, year);
               calendar.set(Calendar.MONTH, month);
               calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
               dateSpecified = calendar.getTime();

                   if(dateSpecified.before(todayDate))
                   {
                       Toast.makeText(getDockActivity(),"Date Should Not Be Less Than Current Date",Toast.LENGTH_LONG).show();
                   }
                   else
                   {
                       JsonDate=year+"-"+month+"-"+dayOfMonth;
                       txtView.setText(dayOfMonth+"/"+month+"/"+year);
                   }

           }
       },"TextTo");
       datedialoghelper.showDate();
   }

   public String parseDate(String dateSpecified)
   {
       String[] SplitDate=dateSpecified.split(" ");
       String day=SplitDate[0];

       return day;
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
        txtTo.setOnClickListener(this);
        txtFrom.setOnClickListener(this);
        SecondtxtTo.setOnClickListener(this);
        SecondtxtFrom.setOnClickListener(this);
        ThirdtxtTo.setOnClickListener(this);
        ThirdtxtFrom.setOnClickListener(this);
        SelectDatecalendar.setOnClickListener(this);
        txtIncre.setOnClickListener(this);
        SecondtxtIncre.setOnClickListener(this);
        ThirdtxtIncre.setOnClickListener(this);

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

                     setDataInJson(ScheduleHashMap);
                //createTrainerSchedule(trainerBookingCalendarJsonCollection);
            }
        });
        titleBar.setSubHeading("Trainer Calendar");

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

            case R.id.txtTo:
                ShowRadioListDialog();
                textView = txtTo;

                txtTo.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = parseDate(dateSpecified.toString());


                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);

                    }
                });


                break;

            case R.id.txtFrom:
                ShowRadioListDialog();
                textView = txtFrom;

                txtFrom.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = parseDate(dateSpecified.toString());


                        timeArray.add(s.toString());
                        ScheduleHashMap.put(selectedDay, timeArray);

                    }
                });
                break;

            case R.id.SecondtxtTo:
                ShowRadioListDialog();
                textView = SecondtxtTo;

                SecondtxtTo.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = parseDate(dateSpecified.toString());

                        timeArray.add(s.toString());
                        ScheduleHashMap.put(selectedDay, timeArray);
                    }
                });


                break;

            case R.id.SecondtxtFrom:
                ShowRadioListDialog();
                textView = SecondtxtFrom;

                SecondtxtFrom.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = parseDate(dateSpecified.toString());

                        timeArray.add(s.toString());
                        ScheduleHashMap.put(selectedDay, timeArray);

                    }
                });

                break;


            case R.id.ThirdtxtTo:
                ShowRadioListDialog();
                textView = ThirdtxtTo;

                ThirdtxtTo.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = parseDate(dateSpecified.toString());

                        timeArray.add(s.toString());
                        ScheduleHashMap.put(selectedDay, timeArray);

                    }
                });
                break;

            case R.id.ThirdtxtFrom:
                ShowRadioListDialog();
                textView = ThirdtxtFrom;

                ThirdtxtFrom.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = parseDate(dateSpecified.toString());


                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);

                    }

                });

                break;

            case R.id.SelectDatecalendar:
                ShowDateDialog(txt_Date);
                break;

            case R.id.txtIncre:
                SecondAvail.setVisibility(View.VISIBLE);
                break;

            case R.id.SecondtxtIncre:
                SecondAvail.setVisibility(View.VISIBLE);
                ThirdAvail.setVisibility(View.VISIBLE);
                ThirdtxtIncre.setVisibility(View.INVISIBLE);
                break;



        }

    }

    public Date EndDate(String days)
    {
        if(days.contains("2 weeks")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateSpecified);
            cal.add(Calendar.DATE, 14);
             EndDate = cal.getTime();
        }

        if(days.contains("1 Month")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateSpecified);
            cal.add(Calendar.DATE, 30);
            EndDate = cal.getTime();
        }
        if(days.contains("3 Months")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateSpecified);
            cal.add(Calendar.DATE, 90);
            EndDate = cal.getTime();
        }
        if(days.contains("6 Months")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateSpecified);
            cal.add(Calendar.DATE, 180);
            EndDate = cal.getTime();
        }

        return EndDate;

    }



    public void setDataInJson(HashMap<String, List<String>> timeArray)
{

    ArrayList<ScheduleTime> scheduleTimeCollection = new ArrayList();
    ScheduleTime scheduleTime = null;

    trainerBookingCalendarJsonCollection = new ArrayList<>();

    try {



     for( HashMap.Entry<String, List<String>> entry : timeArray.entrySet())
     {


         String Selecteddate=dateSpecified.toString();
         String[] days=Selecteddate.split(" ");
         String monthName=days[1];


         Date EndDate1=EndDate("1 Month");

         trainerBookingCalendarJson = new TrainerBookingCalendarJson();
         trainerBookingCalendarJson.setTrainer_id(parseInt(prefHelper.getUserId()));
         trainerBookingCalendarJson.setMonth(monthName);


         String data=entry.getValue().toString().replace("[","").replace("]","");

         String[] timings=data.split(",");


         trainerBookingCalendarJson.setDate(JsonDate);

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
    public void setData(String itemPosition)
    {
        AnyTextView textView= (AnyTextView) this.textView;
        textView.setText(itemPosition);

    }




}


