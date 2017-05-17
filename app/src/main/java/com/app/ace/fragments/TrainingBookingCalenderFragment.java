package com.app.ace.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.ScheduleEnt;
import com.app.ace.entities.ScheduleTime;
import com.app.ace.entities.TrainerBookingCalendarJson;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.Datedialoghelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.SetTimeDataOnTextView;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.ArrayListExpandableAdapter;
import com.app.ace.ui.viewbinders.ScheduleExpendableList;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static java.lang.Integer.parseInt;


/**
 * Created by saeedHyder on 4/7/2017.
 */

public class TrainingBookingCalenderFragment extends BaseFragment implements View.OnClickListener, SetTimeDataOnTextView {


    public String StxtTo, StxtFrom, txtSecTo, txtSecFrom, txtThirdFrom, txtThirdTo, selectedDay;
    public String prevDay = null;
    public Date dateSpecified;
    public Date EndDate;

    ExpandableListView expandablelistview;
    @InjectView(R.id.SecondAvail)
    LinearLayout SecondAvail;
    @InjectView(R.id.ThirdAvail)
    LinearLayout ThirdAvail;
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
    Object textView;
    String TimeSelected;
    Calendar calendar;
    int Year, Month, Day;
    int mMonth = 0, mDay = 0, mYear = 0;
    TrainerBookingCalendarJson trainerBookingCalendarJson = new TrainerBookingCalendarJson();
    ArrayList<TrainerBookingCalendarJson> trainerBookingCalendarJsonCollection;
    HashMap<String, HashMap<String, List<String>>> datemap = new HashMap<>();
    HashMap<Integer, String> ScheduleHashMap = new HashMap<>();
    ArrayList<String> alreadySelectedTime = new ArrayList<>();
    List<String> timeArray = new ArrayList();
    public String arrayLastItem="";
    String trainerScheduleJson;
    int LastSelectHour = 0;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    ArrayListExpandableAdapter<String, HashMap<String, ArrayList<String>>> adapter;
    ScheduleExpendableList listItemBinder;
    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;
    @InjectView(R.id.iv_Calander)
    private ImageView iv_Calander;
    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;
    private String spinerValue = "2 Week";

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
        expandablelistview = (ExpandableListView) view.findViewById(R.id.expandablelistview);
        loadingStarted();
        getSavedSchedule(prefHelper.getUserId());
        days = DaysOfMonth(monthValue);
        setDatePickerVariables();

        setEndTimeSpinner();

        setListener();
    }

    private void getSavedSchedule(String userId) {
        //  UIHelper.showShortToastInCenter(getDockActivity(), "asda" + prefHelper.getUserId());
        Call<ResponseWrapper<ArrayList<ScheduleEnt>>> callback = webService.getSchedule(userId);
        callback.enqueue(new Callback<ResponseWrapper<ArrayList<ScheduleEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ScheduleEnt>>> call, Response<ResponseWrapper<ArrayList<ScheduleEnt>>> response) {

                bindData(response.body().getResult());
                loadingFinished();
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ScheduleEnt>>> call, Throwable t) {
                Log.e("BoookingCalender", t.toString());
                loadingFinished();
            }
        });
    }

    private void bindData(ArrayList<ScheduleEnt> result) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<String>>();
        ArrayList<String> childData = new ArrayList<>();
        if (result.size() <= 0) {
            expandablelistview.setVisibility(View.GONE);
        } else {
            expandablelistview.setVisibility(View.VISIBLE);
        }
        int j = -1;
        for (ScheduleEnt entity : result
                ) {
            j++;
            if (entity.getStartDate() != null && entity.getEndDate() != null) {
                String header = entity.getStartDate() + "," + entity.getEndDate();
                listDataHeader.add(header);
                if (!entity.getTimeStart1().equals("00:00:00") && !entity.getTimeEnd1().equals("00:00:00")) {
                    childData.add(entity.getTimeStart1() + "," + entity.getTimeEnd1());
                }
                if (!entity.getTimeStart2().equals("00:00:00") && !entity.getTimeEnd2().equals("00:00:00")) {
                    childData.add(entity.getTimeStart2() + "," + entity.getTimeEnd2());
                }
                if (!entity.getTimeStart3().equals("00:00:00") && !entity.getTimeEnd3().equals("00:00:00")) {
                    childData.add(entity.getTimeStart3() + "," + entity.getTimeEnd3());
                }

                listDataChild.put(header, childData);
                childData = new ArrayList<>();
            }

        }
        bindView();
    }

    private void bindView() {
        listItemBinder = new ScheduleExpendableList();
        adapter = new ArrayListExpandableAdapter<>(getDockActivity(),
                listDataHeader,
                listDataChild,
                listItemBinder);
      /*  int width = getResources().getDisplayMetrics().widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandablelistview.setIndicatorBounds(width - getPixelValue(Math.round(getResources().getDimension(R.dimen.x10)))
                    , width - getPixelValue(Math.round(getResources().getDimension(R.dimen.x5))));
        } else {
            expandablelistview.setIndicatorBoundsRelative(width - getPixelValue(Math.round(getResources().getDimension(R.dimen.x10))
            ), width - getPixelValue(Math.round(getResources().getDimension(R.dimen.x5))));
        }*/
        expandablelistview.setChildDivider(getResources().getDrawable((R.drawable.line)));
        expandablelistview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public int getPixelValue(int dp) {

        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void ShowRadioListDialog() {

        final DialogBox successPopUp = DialogBox.newInstance();
        successPopUp.setDocActivityContext(getDockActivity());
        successPopUp.SetTimeDataOnTextViewContext(this);
        successPopUp.setPopupData("select", true);
        successPopUp.show(getDockActivity().getSupportFragmentManager(), "listDilog");

    }

    private void setEndTimeSpinner() {
        final List<String> timeDuration = new ArrayList<String>();
        timeDuration.add("2 Week");
        timeDuration.add("1 Month");
        timeDuration.add("3 Months");
        timeDuration.add("6 Months");
        EndDate = getEndDate(timeDuration.get(0));
        ArrayAdapter<String> TimeDurationAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, timeDuration);
        TimeDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weeks.setAdapter(TimeDurationAdapter);
        sp_weeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinerValue = timeDuration.get(position);
                EndDate = getEndDate(timeDuration.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinerValue = timeDuration.get(0);
                EndDate = getEndDate(timeDuration.get(0));
            }
        });

    }

    private void setDatePickerVariables() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }


    void ShowDateDialog(final AnyTextView txtView) {


        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date todayDate = null;
                        try {
                            todayDate = sdf.parse(sdf.format(new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateSpecified = calendar.getTime();
                        mMonth = monthOfYear;

                        mDay = dayOfMonth;
                        mYear = year;
                        if (dateSpecified.before(todayDate)) {
                            Toast.makeText(getDockActivity(), "Date Should Not Be Less Than Current Date", Toast.LENGTH_LONG).show();
                        } else {
                            if (txtView.getText().toString().equals(dayOfMonth + "/" + month + "/" + year)) {

                            } else {
                                txtView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }

                        }
                    }
                }, Year, Month, Day
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");


    }

    public String parseDate(String dateSpecified) {
        String[] SplitDate = dateSpecified.split(" ");
        String day = SplitDate[0];

        return day;
    }


    private ArrayList<String> DaysOfMonth(int monthValue) {

        Calendar mCalendar = Calendar.getInstance();
        monthDateValue = monthValue + 1;
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


    private void createTrainerSchedule(ArrayList<TrainerBookingCalendarJson> jsonObject) {
        Call<ResponseWrapper> callBack = webService.createSchedule(
                jsonObject);
        callBack.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    loadingFinished();
                    //UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    showBookingDialog();

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                System.out.println(t.toString());
                Log.e("Calender", t.toString());
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });


    }

    private void showBookingDialog() {

        final DialogFragment successPopUp = DialogFragment.newInstance();
        successPopUp.setPopupData("Booking Slots", "", prefHelper.getUserName().toUpperCase(), "Your Booking Slot are Updated", true, false);

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


        titleBar.showTickButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingStarted();
                //  trainingBookingListItemBinder.getTimeArray();
                // setDataInJson(trainingBookingListItemBinder.getTimeArray());

                Map<Integer, String> map = new TreeMap<Integer, String>(ScheduleHashMap);
                if (!map.isEmpty())
                    setDataInJson(map);
                //createTrainerSchedule(trainerBookingCalendarJsonCollection);
            }
        });
        titleBar.setSubHeading("Trainer Calendar");

    }

    private void ShowRepeatButton() {

        getTitleBar().showRepeatButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setupCalenderDialog();
                v.invalidate();
            }
        });
        getTitleBar().invalidate();


    }

    private void setupCalenderDialog() {
        final DialogHelper dialog = new DialogHelper(getDockActivity());

        EndDate = getEndDate(spinerValue);


        dialog.initDialog(R.layout.dialog_repeat, EndDate);
        dialog.setDuration(getDockActivity());


        dialog.setSummitButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDate = dialog.dismissDialog();
                System.out.println(EndDate.toString());
            }
        });
        dialog.showDialog();
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
                if (dateSpecified != null) {
                    ShowRadioListDialog();
                    textView = txtTo;

                    txtTo.addTextChangedListener(new TextWatcher() {


                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            alreadySelectedTime.remove(txtTo.getText().toString());
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            System.out.println("asdasdasd");
                            addtoArray(s, 1);
                        }


                    });
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Select Start Date First");
                }

                break;

            case R.id.txtFrom:
                if (checkorder(null, txtTo, 2)) {
                    ShowRadioListDialog();
                    textView = txtFrom;

                    txtFrom.addTextChangedListener(new TextWatcher() {


                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            alreadySelectedTime.remove(txtFrom.getText().toString());

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {


                            checkorder(s, txtTo, 2);
                            ShowRepeatButton();

                        }

                    });
                }
                break;

            case R.id.SecondtxtTo:
                if (checkprevious(txtTo, txtFrom)) {

                    ShowRadioListDialog();
                    textView = SecondtxtTo;

                    SecondtxtTo.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            alreadySelectedTime.remove(SecondtxtTo.getText().toString());
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (checkprevious(txtTo, txtFrom)) {


                                addtoArray(s, 3);

                            }
                        }
                    });
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Please Fill in Order");
                }

                break;

            case R.id.SecondtxtFrom:
                if (checkprevious(txtTo, txtFrom)) {
                    if (checkorder(null, SecondtxtTo, 4)) {
                        ShowRadioListDialog();
                        textView = SecondtxtFrom;

                        SecondtxtFrom.addTextChangedListener(new TextWatcher() {


                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                alreadySelectedTime.remove(SecondtxtFrom.getText().toString());
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                                if (checkprevious(txtTo, txtFrom)) {


                                    checkorder(s, SecondtxtTo, 4);

                                }
                            }
                        });
                    }
                }


                break;


            case R.id.ThirdtxtTo:
                if (checkprevious(SecondtxtTo, SecondtxtFrom)) {
                    ShowRadioListDialog();
                    textView = ThirdtxtTo;

                    ThirdtxtTo.addTextChangedListener(new TextWatcher() {


                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            alreadySelectedTime.remove(ThirdtxtTo.getText().toString());
                        }


                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (checkprevious(SecondtxtTo, SecondtxtFrom)) {


                                addtoArray(s, 5);

                            }
                        }
                    });
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Please Fill in Order");
                }
                break;

            case R.id.ThirdtxtFrom:
                if (checkprevious(SecondtxtTo, SecondtxtFrom)) {
                    if (checkorder(null, ThirdtxtTo, 6)) {
                        ShowRadioListDialog();
                        textView = ThirdtxtFrom;

                        ThirdtxtFrom.addTextChangedListener(new TextWatcher() {


                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                alreadySelectedTime.remove(ThirdtxtFrom.getText().toString());
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                                if (checkprevious(SecondtxtTo, SecondtxtFrom)) {
                                    checkorder(s, ThirdtxtTo, 6);

                                }
                            }

                        });
                    }
                }


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

    private boolean checkprevious(TextView textto, TextView textfrom) {
        return !textto.getText().toString().isEmpty() && !textfrom.getText().toString().isEmpty();
    }

    private Boolean checkorder(Editable s, TextView txtview, Integer key) {

        if (txtview.getText().toString().isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Please Fill in Start First");
            return false;
        } else {
            if (s != null) {
                String[] time = txtview.getText().toString().split(":00");
                int hour = Integer.parseInt(time[0]);
                String[] time1 = s.toString().split(":00");
                int hour1 = Integer.parseInt(time1[0]);
                if (hour > hour1) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "End Time is Lesser then Start Time");
                } else {
                    addtoArray(s, key);
                }
            }

        }
        return true;
    }

    private void addtoArray(Editable s, Integer key) {
        if (dateSpecified == null) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Please Select Start Date First");
            s.clear();
        } else {
            /*if (ScheduleHashMap.containsKey(key)) {
                ScheduleHashMap.remove(key);
                selectedDay = parseDate(dateSpecified.toString());
                timeArray.add(s.toString());
                ScheduleHashMap.put(key, s.toString());

            } else {*/
            selectedDay = parseDate(dateSpecified.toString());
            timeArray.add(s.toString());
            ScheduleHashMap.put(key, s.toString());

            //}
        }

    }

    public Date getEndDate(String days) {
        if (dateSpecified != null) {
            if (days.contains("2 Week")) {
                Calendar enddate = Calendar.getInstance();
                enddate.setTime(dateSpecified);
                enddate.add(Calendar.DAY_OF_MONTH, 14);
                EndDate = enddate.getTime();
            }

            if (days.contains("1 Month")) {
                Calendar enddate = Calendar.getInstance();
                enddate.setTime(dateSpecified);
                enddate.add(Calendar.MONTH, 1);
                EndDate = enddate.getTime();
            }
            if (days.contains("3 Months")) {
                Calendar enddate = Calendar.getInstance();
                enddate.setTime(dateSpecified);
                enddate.add(Calendar.MONTH, 3);
                EndDate = enddate.getTime();
            }
            if (days.contains("6 Months")) {
                Calendar enddate = Calendar.getInstance();
                enddate.setTime(dateSpecified);
                enddate.add(Calendar.MONTH, 6);
                EndDate = enddate.getTime();
            }
        }
        return EndDate;

    }


    public void setDataInJson(Map<Integer, String> timeArray) {

        ArrayList<ScheduleTime> scheduleTimeCollection = new ArrayList();
        ScheduleTime scheduleTime = null;
        trainerBookingCalendarJsonCollection = new ArrayList<>();

        try {
            String Starttime = "";
            String EndTime = "";
            int i = 0;
            int hours;
            int TotalHours = 0;
            for (HashMap.Entry<Integer, String> entry : timeArray.entrySet()) {

                i++;
                String Selecteddate = dateSpecified.toString();
                if (i == 1 && entry.getValue() != null) {
                    Starttime = entry.getValue();
                }
                if (i == 2 && entry.getValue() != null) {
                    i = 0;
                    EndTime = entry.getValue();

                    hours = TimeDifference(Starttime, EndTime);
                    TotalHours = TotalHours + hours;
                    String[] startTime = Starttime.split(":");
                    String StartTimeSplit = startTime[0];
                    for (int x = 0; x < hours; x++) {
                        int IntStartTime = parseInt(StartTimeSplit.trim()) + x;
                        int IntEndTime = IntStartTime + 1;
                        scheduleTime = new ScheduleTime();
                        scheduleTime.setStart_time(String.valueOf(IntStartTime) + ":00");

                        scheduleTime.setEnd_time(String.valueOf(IntEndTime) + ":00");
                        scheduleTimeCollection.add(scheduleTime);

                    }

                }


            }
            if (TotalHours > 24) {
                UIHelper.showShortToastInCenter(getDockActivity(), "Total Slots exceeds 24 Hours");
            } else {
                System.out.println(prefHelper.getUserId());
                trainerBookingCalendarJson.setTrainer_id(Integer.parseInt(prefHelper.getUserId()));
                trainerBookingCalendarJson.setMonth(Datedialoghelper.getFullMonthName(mYear, mMonth, mDay));
                trainerBookingCalendarJson.setStart_date(new SimpleDateFormat(
                        "yyyy-MM-dd", Locale.ENGLISH).format(dateSpecified.getTime()));
                if (EndDate == null)
                    EndDate = getEndDate(spinerValue);

                trainerBookingCalendarJson.setEnd_date(new SimpleDateFormat(
                        "yyyy-MM-dd", Locale.ENGLISH).format(EndDate.getTime()));


                if (timeArray.containsKey(1) && timeArray.containsKey(2)) {
                    trainerBookingCalendarJson.setTime_start_1(timeArray.get(1));
                    trainerBookingCalendarJson.setTime_end_1(timeArray.get(2));
                } else {
                    trainerBookingCalendarJson.setTime_start_1("");
                    trainerBookingCalendarJson.setTime_end_1("");
                }
                if (timeArray.containsKey(3) && timeArray.containsKey(4)) {
                    trainerBookingCalendarJson.setTime_start_2(timeArray.get(3));
                    trainerBookingCalendarJson.setTime_end_2(timeArray.get(4));
                } else {
                    trainerBookingCalendarJson.setTime_start_2("");
                    trainerBookingCalendarJson.setTime_end_2("");
                }
                if (timeArray.containsKey(5) && timeArray.containsKey(6)) {
                    trainerBookingCalendarJson.setTime_start_3(timeArray.get(5));
                    trainerBookingCalendarJson.setTime_end_3(timeArray.get(6));
                } else {
                    trainerBookingCalendarJson.setTime_start_3("");
                    trainerBookingCalendarJson.setTime_end_3("");
                }

                trainerBookingCalendarJson.setTime(scheduleTimeCollection);
                trainerBookingCalendarJsonCollection.add(trainerBookingCalendarJson);
                scheduleTimeCollection = new ArrayList();
                trainerScheduleJson = GsonFactory.getConfiguredGson().toJson(trainerBookingCalendarJsonCollection);
                System.out.println(trainerScheduleJson);
                createTrainerSchedule(trainerBookingCalendarJsonCollection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    int TimeDifference(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(startTime);
            date2 = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long difference = date2.getTime() - date1.getTime();
        int dayss = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * dayss)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * dayss) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);

        //Toast.makeText(getDockActivity(), String.valueOf(hours),Toast.LENGTH_LONG).show();

        return hours;
    }


    @Override
    public void setData(String itemPosition) {

        if(alreadySelectedTime.size()>0) {
            arrayLastItem = alreadySelectedTime.get(alreadySelectedTime.size() - 1);
        }

        if (!itemPosition.isEmpty()) {
            if(!arrayLastItem.equals("") && arrayLastItem.contains(itemPosition))
            {
                if(alreadySelectedTime.contains(itemPosition))
                {
                    String[] time = itemPosition.split(":00");
                    int hour = Integer.parseInt(time[0]);
                    if (hour < LastSelectHour) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Selected Time is Lesser then Previous Selected Time");
                    } else {
                        AnyTextView textView = (AnyTextView) this.textView;
                        textView.setText(itemPosition);
                        alreadySelectedTime.add(itemPosition);
                        String[] thistime = itemPosition.split(":00");
                        LastSelectHour = Integer.parseInt(time[0]);

                    }
                }
            }
            else if (!arrayLastItem.contains(itemPosition) && alreadySelectedTime.contains(itemPosition)) {
                UIHelper.showShortToastInCenter(getDockActivity(), "Time Already Selected Please select a Differnet time");
            }
            else {
                String[] time = itemPosition.split(":00");
                int hour = Integer.parseInt(time[0]);
                if (hour < LastSelectHour) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Selected Time is Lesser then Previous Selected Time");
                } else {
                    AnyTextView textView = (AnyTextView) this.textView;
                    textView.setText(itemPosition);
                    alreadySelectedTime.add(itemPosition);
                    String[] thistime = itemPosition.split(":00");
                    LastSelectHour = Integer.parseInt(time[0]);

                }
            }
        }
    }


}




              /*  String[] days = Selecteddate.split(" ");
                String monthName = days[1];
                int day = Integer.parseInt(days[2]);

                //int day=dateSpecified.getDay();
                int month = dateSpecified.getMonth();

                String date = "2017-" + month + "-" + day;

                Date EndDate1 = getEnd_date("1 Month");

                trainerBookingCalendarJson = new TrainerBookingCalendarJson();
                trainerBookingCalendarJson.setTrainer_id(parseInt(prefHelper.getUserId()));
                trainerBookingCalendarJson.setMonth(monthName);


                String data = entry.getValue().toString().replace("[", "").replace("]", "");

                String[] timings = data.split(",");


                trainerBookingCalendarJson.setDate(date.toString());

                int i = 1;
                int hours;
                String Starttime = "";
                String EndTime = "";

                for (String item : timings) {

                    if (i == 1 && item != null) {

                        Starttime = item;
                        // scheduleTime = new ScheduleTime();
                        // scheduleTime.setStart_time(item);
                    }
                    if (i == 2 && item != null) {

                        EndTime = item;
                        hours = TimeDifference(Starttime, EndTime);

                        String[] startTime = Starttime.split(":");
                        String StartTimeSplit = startTime[0];
                        for (int x = 0; x < hours; x++) {
                            int IntStartTime = parseInt(StartTimeSplit.trim()) + x;
                            int IntEndTime = IntStartTime + 1;
                            scheduleTime = new ScheduleTime();
                            scheduleTime.setStart_time(String.valueOf(IntStartTime) + ":00");

                            scheduleTime.setEnd_time(String.valueOf(IntEndTime) + ":00");
                            scheduleTimeCollection.add(scheduleTime);

                        }

                    }
                    if (i == 3 && item != null) {

                        Starttime = item;
                        //  scheduleTime = new ScheduleTime();
                        // scheduleTime.setStart_time(item);
                    }
                    if (i == 4 && item != null) {

                        EndTime = item;
                        hours = TimeDifference(Starttime, EndTime);

                        String[] startTime = Starttime.split(":");
                        String StartTimeSplit = startTime[0];
                        for (int x = 0; x < hours; x++) {

                            int IntStartTime = Integer.parseInt(StartTimeSplit.trim()) + x;
                            int IntEndTime = IntStartTime + 1;
                            scheduleTime = new ScheduleTime();
                            scheduleTime.setStart_time(String.valueOf(IntStartTime) + ":00");

                            scheduleTime.setEnd_time(String.valueOf(IntEndTime) + ":00");
                            scheduleTimeCollection.add(scheduleTime);

                        }


                        //scheduleTime.setEnd_time(item);
                        // scheduleTimeCollection.add(scheduleTime);
                    }

                    if (i == 5 && item != null) {

                        Starttime = item;
                        //scheduleTime = new ScheduleTime();
                        // scheduleTime.setStart_time(item);
                    }
                    if (i == 6 && item != null) {

                        EndTime = item;
                        hours = TimeDifference(Starttime, EndTime);

                        String[] startTime = Starttime.split(":");
                        String StartTimeSplit = startTime[0];
                        for (int x = 0; x < hours; x++) {
                            int IntStartTime = (parseInt(StartTimeSplit.trim())) + x;
                            int IntEndTime = IntStartTime + 1;
                            scheduleTime = new ScheduleTime();
                            scheduleTime.setStart_time(String.valueOf(IntStartTime) + ":00");

                            scheduleTime.setEnd_time(String.valueOf(IntEndTime) + ":00");
                            scheduleTimeCollection.add(scheduleTime);

                        }

                        //scheduleTime.setEnd_time(item);
                        // scheduleTimeCollection.add(scheduleTime);
                    }

                    i++;*/

//  Toast.makeText(getDockActivity(),item,Toast.LENGTH_LONG).show();