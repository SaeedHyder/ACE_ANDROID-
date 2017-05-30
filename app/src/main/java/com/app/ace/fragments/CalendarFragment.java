package com.app.ace.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.ace.R;
import com.app.ace.entities.AvailableSlot;
import com.app.ace.entities.BookingSchedule;
import com.app.ace.entities.CalenderEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.entities.TrainerBooking;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DateHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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

/**
 * Created by saeedhyder on 4/4/2017.
 */

public class CalendarFragment extends BaseFragment implements View.OnClickListener {
    public static String USER_ID = "USER_ID";
    public static String ADDRESS = "gymaddress";
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    @InjectView(R.id.btn_avaliablity)
    Button btn_avaliablity;
    int TotalArray = -1;

    /* @InjectView(R.id.calendarView)
     CalendarPickerView calendarView;*/
    @InjectView(R.id.sp_trainerTime)
    Spinner sp_timer;
    @InjectView(R.id.sp_weeks)
    Spinner sp_weeks;
    @InjectView(R.id.sp_category)
    Spinner sp_category;
    @InjectView(R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;
    @InjectView(R.id.txt_headerText)
    AnyTextView txt_headerText;
    String buildingTypes ="";// getString(R.string.Flexiblity_training);
    Map<Date, Drawable> dateDrawableMap = new TreeMap<>();
    Map<Date, Integer> dateTextDrawableMap = new TreeMap<>();
    BookingSchedule bookingSchedule;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private String duration = "";//getString(R.string.two_Week);
    private int days = 14;
    private List<String> timings = new ArrayList<String>();
    private Date EndDate;
    private ArrayList<Slot> slots = new ArrayList<>();
    private ArrayList<BookingSchedule> bookingScheduleArrayList = new ArrayList<>();
    private ArrayList<CalenderEnt> calenderids = new ArrayList<>();
    private ArrayList<Date> totaldate = new ArrayList<>();
    private Date startDate;
    private String GymAddress;

    public static CalendarFragment newInstance(String startDate,String trainerGymAddress) {
        Bundle args = new Bundle();
        args.putString(USER_ID, startDate);
        args.putString(ADDRESS, trainerGymAddress);
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            USER_ID = getArguments().getString(USER_ID);
            GymAddress=getArguments().getString(ADDRESS);
            // Toast.makeText(getDockActivity(), ConversationId, Toast.LENGTH_LONG).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buildingTypes = getString(R.string.Flexiblity_training);
        duration = getString(R.string.two_Week);
        View parentView = inflater.inflate(R.layout.fragment_calendar, container, false);

        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        caldroidFragment = new CaldroidFragment();
        InitCalenderFragment(savedInstanceState);
        btn_training_Search_Submit.setVisibility(View.GONE);

        // Attach to the activity
        txt_headerText.setText(GymAddress);
        setCalenderListener();
        initCategorySpinner();
        initDurationSpinner();


        setListener();
    }

    private void setCalenderListener() {
        final CaldroidListener caldroidListener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                caldroidFragment.clearSelectedDates();
                caldroidFragment.moveToDate(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

               EndDate = getEndDate(calendar.getTime(),duration);
               /* caldroidFragment.setMinDate(date);
                caldroidFragment.setMaxDate(EndDate);*/
               caldroidFragment.setSelectedDates(date,EndDate);
                // Attach to the activity
                startDate = date;
                resetDate();
                caldroidFragment.refreshView();
           /*     FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction t = fragmentManager.beginTransaction();

                if (caldroidFragment.isAdded()) {
                    fragmentManager.beginTransaction().remove(caldroidFragment).commitNow();
                }

                t.replace(R.id.calendar1, caldroidFragment);
                t.commit();*/

            }
        };
        caldroidFragment.setCaldroidListener(caldroidListener);
    }

    private void InitCalenderFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            //  Uncomment this line to use dark theme
//         args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style..CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();

        if (caldroidFragment.isAdded()) {
            fragmentManager.beginTransaction().remove(caldroidFragment).commitNow();
        }

        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();
    }

    private void resetDate() {
        calenderids.clear();
        dateDrawableMap.clear();
        dateTextDrawableMap.clear();
        totaldate.clear();
        TotalArray = -1;
    }

    private void initDurationSpinner() {
        //Spinner Weeks
        List<String> timeDuration = new ArrayList<String>();
        timeDuration.add(getString(R.string.Select_time_duration));
        timeDuration.add(getString(R.string.two_Week));
        timeDuration.add(getString(R.string.one_Month));
        timeDuration.add(getString(R.string.three_Months));
        timeDuration.add(getString(R.string.Six_Months));

        ArrayAdapter<String> TimeDurationAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, timeDuration);
        TimeDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weeks.setAdapter(TimeDurationAdapter);



       /* String calendarDate = calendarView.getDate()+"";
        Toast.makeText(getDockActivity(),calendarDate,Toast.LENGTH_LONG).show();*/
        //  EndDate = getEndDate(duration);

        //setCalendarView(days);

        sp_weeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                duration = sp_weeks.getSelectedItem().toString();
                EndDate = getEndDate(duration);

                /*if (duration == "2 Week") {
                    days = 14;
                    setCalendarView(days);

                }
                if (duration == "1 Month") {
                    days = 30;
                    setCalendarView(days);
                }
                if (duration == "3 Months") {
                    days = 90;
                    setCalendarView(days);
                }
                if (duration == "6 Months") {
                    days = 180;
                    setCalendarView(days);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initCategorySpinner() {
        //Spinner Category

        final List<String> category = new ArrayList<String>();
        category.add(getString(R.string.Flexiblity_training));
        category.add(getString(R.string.dynamic_strenght_taining));
        category.add(getString(R.string.Static_strength_training));
        category.add(getString(R.string.Circuit_training));
        category.add(getString(R.string.Aerobic_training));
        category.add(getString(R.string.Body_Building));
        category.add(getString(R.string.Lose_Weight));

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(categoryAdapter);
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buildingTypes = category.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Date getEndDate(Date date,String days) {


        if (days.contains(getString(R.string.two_Week))) {
            Calendar enddate = Calendar.getInstance();
            enddate.setTime(date);
            enddate.add(Calendar.DAY_OF_MONTH, 13);


            EndDate = enddate.getTime();
        }

        if (days.contains(getString(R.string.one_Month))) {
            Calendar enddate = Calendar.getInstance();
            enddate.setTime(date);
            enddate.add(Calendar.MONTH, 1);

            EndDate = enddate.getTime();
        }
        if (days.contains(getString(R.string.three_Months))) {
            Calendar enddate = Calendar.getInstance();
            enddate.setTime(date);
            enddate.add(Calendar.MONTH, 3);

            EndDate = enddate.getTime();
        }
        if (days.contains(getString(R.string.Six_Months))) {
            Calendar enddate = Calendar.getInstance();
            enddate.setTime(date);
            enddate.add(Calendar.MONTH, 6);

            EndDate = enddate.getTime();
        }

        return EndDate;

    }
    public Date getEndDate(String days) {


        if (days.contains(getString(R.string.two_Week))) {
            Calendar enddate = Calendar.getInstance();
            enddate.add(Calendar.DAY_OF_MONTH, 13);
            EndDate = enddate.getTime();
        }

        if (days.contains(getString(R.string.one_Month))) {
            Calendar enddate = Calendar.getInstance();
            enddate.add(Calendar.MONTH, 1);
            EndDate = enddate.getTime();
        }
        if (days.contains(getString(R.string.three_Months))) {
            Calendar enddate = Calendar.getInstance();
            enddate.add(Calendar.MONTH, 3);
            EndDate = enddate.getTime();
        }
        if (days.contains(getString(R.string.Six_Months))) {
            Calendar enddate = Calendar.getInstance();
            enddate.add(Calendar.MONTH, 6);
            EndDate = enddate.getTime();
        }

        return EndDate;

    }

    private void trainerTimeSlots() {
        if (EndDate == null)
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Duration First");
        else {
            if (startDate == null){
                startDate =new Date();
            }
            String StartDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(startDate.getTime());

            //EndDate = getEndDate(duration);

            String endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(EndDate.getTime());
            Call<ResponseWrapper<TrainerBooking>> callBack = webService.getScheduleTrainee(USER_ID, StartDate, endDate);
            loadingStarted();

            callBack.enqueue(new Callback<ResponseWrapper<TrainerBooking>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<TrainerBooking>> call, Response<ResponseWrapper<TrainerBooking>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                        getTrainerTimingSlots(response.body().getResult());
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
    }

    private void getTrainerTimingSlots(TrainerBooking result) {
        //UIHelper.showShortToastInCenter(getDockActivity(), "asd" + USER_ID);
        //timings.add("Slots Avaliable");
        slots.clear();
        slots = result.getSlots();
        if (slots.isEmpty()){
            UIHelper.showShortToastInCenter(getDockActivity(),"Trainer is not Available during this Period");
            calenderids.clear();
            caldroidFragment.clearSelectedDates();
            caldroidFragment.clearDisableDates();
            caldroidFragment.refreshView();

        }
        ArrayList<AvailableSlot> availableSlots = result.getAvailable_slots();
     /*   ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.red));
        ColorDrawable green = new ColorDrawable(Color.GREEN);
        ColorDrawable white = new ColorDrawable(Color.WHITE);*/
        timings.clear();

        for (AvailableSlot item : availableSlots) {
            timings.add(item.getStartTime() + " to " + item.getEndTime());

        }

        initTimerSpinner();

    }

    private void initTimerSpinner() {
        final ArrayAdapter<String> timerAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, timings);

        timerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_timer.setAdapter(timerAdapter);
        sp_timer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetDate();
                showDateinCalender(timerAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDateinCalender(String timeSlot) {

        Drawable green = getResources().getDrawable(R.drawable.booked);
        Drawable white = getResources().getDrawable(R.drawable.available);
        Drawable red = getResources().getDrawable(R.drawable.not_available);
        Date date = null;
        int index = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Slot item : slots
                ) {
            String slottime = item.getStartTime() + " to " + item.getEndTime();
            try {
                date = dateFormat.parse(item.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            totaldate.add(date);
            if (date != null) {
                index++;

                if (slottime.equals(timeSlot)) {
                    TotalArray++;
                    // totaldate.remove(index);
                    if (item.getBookings() != null) {
                        dateDrawableMap.put(date, green);
                        dateTextDrawableMap.put(date, R.color.white);
                    } else {

                        if (dateDrawableMap.containsKey(date)){

                        }else{
                            calenderids.add(new CalenderEnt(item.getId()));
                            dateDrawableMap.put(date, white);
                            dateTextDrawableMap.put(date, R.color.black);
                        }


                    }
                }
            }
          /*  else{
                dateDrawableMap.put(date, red);
                dateTextDrawableMap.put(date, R.color.white);
            }*/
        }
        Collections.sort(totaldate);

      /*  Collections.sort(totaldate, new Comparator<MyObject>() {
            public int compare(MyObject o1, MyObject o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });*/




        Calendar calender = Calendar.getInstance();
        if (!totaldate.isEmpty()) {

            final List<Map.Entry<Date, Drawable>> entries =
                    new ArrayList<Map.Entry<Date, Drawable>>(dateDrawableMap.entrySet());
            Map.Entry<Date,Drawable> entry=dateDrawableMap.entrySet().iterator().next();
       if (entry.getKey() !=null){
           Date date1 =entry.getKey();
           Date currentDate = startDate;
           int numadd =0;
           SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
           String currentDatestring = f.format(currentDate);
           String startdateString = f.format(date1);
           while (!currentDatestring.equals(startdateString)) {

               numadd = 1;

               dateDrawableMap.put(calender.getTime(), red);
               dateTextDrawableMap.put(calender.getTime(), R.color.white);
               calender.add(Calendar.DAY_OF_MONTH, 1);
               currentDate = calender.getTime();
               currentDatestring = f.format(currentDate);
               //calender.add(Calendar.DAY_OF_MONTH, 1);
           }
       }
            Map.Entry<Date,Drawable> lastentry = entries.get(entries.size()-1);
           if (lastentry.getKey()!=null){
               Date date2 = lastentry.getKey();//totaldate.get(totaldate.size() - 1);
               calender.setTime(date2);
               calender.add(Calendar.DAY_OF_MONTH, 1);
               int numadd =0;
               SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
               String enddatestring = f.format(EndDate);
               String startdateString = f.format(date2);
               while (!startdateString.equals(enddatestring)) {

                   date2 = calender.getTime();
                   startdateString = f.format(date2);


                   dateDrawableMap.put(calender.getTime(), red);
                   dateTextDrawableMap.put(calender.getTime(), R.color.white);
                   calender.add(Calendar.DAY_OF_MONTH, numadd);
                   numadd = 1;
                   //  calender.add(Calendar.DAY_OF_MONTH, 1);
               }
           }
        }
        setCustomResourceForDates();
        caldroidFragment.refreshView();

      /*  // Attach to the activity
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();

        if (caldroidFragment.isAdded()) {
            fragmentManager.beginTransaction().remove(caldroidFragment).commitNow();
        }

        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();*/
    }

    private void setBooking(ArrayList<BookingSchedule> result) {
        Call<ResponseWrapper> callback = webService.bookTrainer(result);
        callback.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    loadingFinished();
                    //UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(), "TraineeScheduleFragment");

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

    private void bookTrainer() {

            bookingSchedule = new BookingSchedule();
            bookingSchedule.setUser_id(Integer.parseInt(prefHelper.getUserId()));
            bookingSchedule.setTraining_type(buildingTypes);
            bookingSchedule.setAll_ids(calenderids);
            bookingScheduleArrayList.add(bookingSchedule);
            setBooking(bookingScheduleArrayList);
            String sad = GsonFactory.getConfiguredGson().toJson(bookingScheduleArrayList);
            System.out.println(sad);

    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        // cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.setTime(EndDate);
      /*      cal.add(Calendar.DATE, 7);*/
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            if (dateDrawableMap.size() > 1) {
                caldroidFragment.setBackgroundDrawableForDates(dateDrawableMap);
                caldroidFragment.setTextColorForDates(dateTextDrawableMap);
            }
         /*   caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);*/
        }
    }

    private void setListener() {
        btn_avaliablity.setOnClickListener(this);
        btn_training_Search_Submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_training_Search_Submit:
                if (dateDrawableMap.size() == (calenderids.size())) {
                    if (buildingTypes!=null && !buildingTypes.isEmpty()) {
                        if (!calenderids.isEmpty())
                        setupDialog();
                        else {
                            UIHelper.showShortToastInCenter(getDockActivity(),"Trainer is not available ");
                        }
                    }else{
                        UIHelper.showShortToastInCenter(getDockActivity(),"Select Training Type First");
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Trainer is not available for whole Duration");
                }

                //getDockActivity().addDockableFragment(CalenderPopupDialogFragment.newInstance(), "CalenderPopupDialogFragment");
                break;
            case R.id.btn_avaliablity:
                btn_training_Search_Submit.setVisibility(View.VISIBLE);
                trainerTimeSlots();
                break;
        }
    }

    private void setupDialog() {
        final DialogHelper dialog = new DialogHelper(getDockActivity(), true);
        dialog.initDialog(R.layout.calendar_popup);
        dialog.initPositiveBtn(R.id.btndialogCalendar_1, getString(R.string.CONFIRM), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingStarted();
                bookTrainer();
                dialog.dismisspoptuDialog();
            }
        });
        dialog.initNegativeBtn(R.id.btndialogCalendar_2, getString(R.string.CANCEL), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismisspoptuDialog();
            }
        });
        dialog.showDialog();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("May");

    }

}
 /* private void setCalendarView(int days) {
       *//* calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1990, 1, 1))
                .setMaximumDate(CalendarDay.from(2060, 12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.selectRange(CalendarDay.from(2017, 4, 12), CalendarDay.from(2017, 4, 15));*//*


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

    }*/
