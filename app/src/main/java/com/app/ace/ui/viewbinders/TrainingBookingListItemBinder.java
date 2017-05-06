package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TimePicker;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.TrainingBookingCalenderItem;
import com.app.ace.fragments.TrainingBookingCalenderFragment;
import com.app.ace.helpers.DateHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.TrainingBooking;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import static com.app.ace.R.id.time;
import static com.app.ace.R.id.txt;
import static com.app.ace.R.id.txtTo;
import static com.app.ace.R.id.txtback;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class TrainingBookingListItemBinder extends ViewBinder<TrainingBookingCalenderItem>  {

    Context context;
    TrainingBooking trainingbooking;
    public String txtTo,txtFrom,txt,txtSecTo,txtSecFrom,txtThirdFrom,txtThirdTo,selectedDay;
    String prevDay=null;

    List<String> timeArray=new ArrayList();

    HashMap<String,List<String>> ScheduleHashMap=new HashMap<>();


    public String gym_time_to,gym_time_from;



    public TrainingBookingListItemBinder(Context context,TrainingBooking trainingbooking) {
        super(R.layout.training_booking_calender_items);

        this.context= context;
        this.trainingbooking=trainingbooking;
       // this.trainerBookingChangeText=trainerBookingChangeText;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TrainingBookingListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final TrainingBookingCalenderItem entity, int position, int grpPosition, View view, Activity activity) {

        //final ViewHolder viewHolder = (ViewHolder) view.getTag();

        final TrainingBookingListItemBinder.ViewHolder viewHolder = (TrainingBookingListItemBinder.ViewHolder) view.getTag();

       String date= parseDate(entity.getTxtDayDate());

        if(entity.getTxtDayDate().contains("Sat")) {
            viewHolder.txtDayDate.setTextColor(R.color.red);
            viewHolder.txtDayDate.setText(date);
        }
        else if(entity.getTxtDayDate().contains("Sun"))
        {
            viewHolder.txtDayDate.setTextColor(R.color.red);
            viewHolder.txtDayDate.setText(date);
        }
        else
        {
            viewHolder.txtDayDate.setTextColor(R.color.black);
            viewHolder.txtDayDate.setText(date);
        }
      //  viewHolder.txtTo.setText(entity.getTxtTo());
        //viewHolder.txtFrom.setText(entity.getTxtFrom());

        //viewHolder.txtTo.addTextChangedListener(new GenericTextWatcher(entity, position));


        viewHolder.txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // openFromTimePickerDialog(viewHolder.txtTo);
                openToTimePickerDialog(viewHolder.txtTo);

                viewHolder.txtTo.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = entity.getTxtDayDate();

                        if(!selectedDay.equals(prevDay)) {
                            timeArray = new ArrayList<String>();
                            txtTo = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }
                        else{

                            txtTo = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }

                        prevDay = selectedDay;

                    }
                });

            }
        });


        viewHolder.txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openToTimePickerDialog(viewHolder.txtFrom);
                //openFromTimePickerDialog(viewHolder.txtFrom);

                viewHolder.txtFrom.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = entity.getTxtDayDate();

                        if(!selectedDay.equals(prevDay)) {
                            timeArray = new ArrayList<String>();
                            txtFrom = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }
                        else{

                            txtFrom = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }

                        prevDay = selectedDay;
                    }
                });
            }
        });

        viewHolder.txtSecTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openToTimePickerDialog(viewHolder.txtSecTo);
                //openFromTimePickerDialog(viewHolder.txtSecTo);

                viewHolder.txtSecTo.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = entity.getTxtDayDate();

                        if(!selectedDay.equals(prevDay)) {
                            timeArray = new ArrayList<String>();
                            txtSecTo = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }
                        else{

                            txtSecTo = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }

                        prevDay = selectedDay;

                    }
                });
            }
        });


        viewHolder.txtSecFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openFromTimePickerDialog(viewHolder.txtSecFrom);
                openToTimePickerDialog(viewHolder.txtSecFrom);

                viewHolder.txtSecFrom.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        selectedDay = entity.getTxtDayDate();

                        if(!selectedDay.equals(prevDay)) {
                            timeArray = new ArrayList<String>();
                            txtSecFrom = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }
                        else{

                            txtSecFrom = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }

                        prevDay = selectedDay;
                    }
                });
            }
        });

        viewHolder.txtThirdTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openToTimePickerDialog(viewHolder.txtThirdTo);
                //openFromTimePickerDialog(viewHolder.txtThirdTo);

                viewHolder.txtThirdTo.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        selectedDay = entity.getTxtDayDate();

                        if(!selectedDay.equals(prevDay)) {
                            timeArray = new ArrayList<String>();
                            txtThirdTo = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }
                        else{

                            txtThirdTo = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }

                        prevDay = selectedDay;
                    }
                });
            }
        });


        viewHolder.txtThirdFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openFromTimePickerDialog(viewHolder.txtThirdFrom);
                openToTimePickerDialog(viewHolder.txtThirdFrom);


                viewHolder.txtThirdFrom.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        selectedDay = entity.getTxtDayDate();

                        if(!selectedDay.equals(prevDay)) {
                            timeArray = new ArrayList<String>();
                            txtThirdFrom = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }
                        else{

                            txtThirdFrom = s.toString();
                            timeArray.add(s.toString());
                            ScheduleHashMap.put(selectedDay, timeArray);
                        }

                        prevDay = selectedDay;

                    }
                });

            }
        });


        viewHolder.txtIncre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               trainingbooking.textList();

                viewHolder.txtback.setVisibility(View.VISIBLE);

                viewHolder.txtSecTo.setVisibility(View.VISIBLE);
                viewHolder.txtSecFrom.setVisibility(View.VISIBLE);

                viewHolder.txtIncre.setVisibility(View.GONE);
                viewHolder.txtIncre1.setVisibility(View.VISIBLE);

                viewHolder.txtTo.setVisibility(View.GONE);
                viewHolder.txtFrom.setVisibility(View.GONE);


            }
        });

        viewHolder.txtIncre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.txtThirdTo.setVisibility(View.VISIBLE);
                viewHolder.txtThirdFrom.setVisibility(View.VISIBLE);

                viewHolder.txtSecTo.setVisibility(View.GONE);
                viewHolder.txtSecFrom.setVisibility(View.GONE);

                viewHolder.txtTo.setVisibility(View.GONE);
                viewHolder.txtFrom.setVisibility(View.GONE);

                viewHolder.txtback1.setVisibility(View.VISIBLE);
                viewHolder.txtback.setVisibility(View.GONE);

                trainingbooking.LastAvailtxt();

            }
        });

        viewHolder.txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.txtback.setVisibility(View.GONE);
                trainingbooking.backList();


                viewHolder.txtTo.setVisibility(View.VISIBLE);
                viewHolder.txtFrom.setVisibility(View.VISIBLE);


                viewHolder.txtSecTo.setVisibility(View.GONE);
                viewHolder.txtSecFrom.setVisibility(View.GONE);

                viewHolder.txtThirdTo.setVisibility(View.GONE);
                viewHolder.txtThirdFrom.setVisibility(View.GONE);

            }
        });

        viewHolder.txtback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.txtSecTo.setVisibility(View.VISIBLE);
                viewHolder.txtSecFrom.setVisibility(View.VISIBLE);

                viewHolder.txtback.setVisibility(View.VISIBLE);
                viewHolder.txtback1.setVisibility(View.GONE);
                viewHolder.txtIncre.setVisibility(View.VISIBLE);
                viewHolder.txtIncre1.setVisibility(View.GONE);

                viewHolder.txtThirdTo.setVisibility(View.GONE);
                viewHolder.txtThirdFrom.setVisibility(View.GONE);

                viewHolder.txtTo.setVisibility(View.GONE);
                viewHolder.txtFrom.setVisibility(View.GONE);

                trainingbooking.textList();




            }
        });


    }

    private String parseDate(String txtDayDate) {

        String[] SplitDate=txtDayDate.split(",");
        String day=SplitDate[0];
        String dayDate=SplitDate[1];
        String Date=day+","+dayDate;

        return Date;
    }

    public HashMap<String, List<String>> getTimeArray() {

        //ScheduleHashMap.put(day,timeArray);

       return ScheduleHashMap;
    }

    private void openToTimePickerDialog(final AnyTextView txtTo) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime  = "";
                txtTo.setText(selectedHour + ":" + selectedMinute);

                finaltime = selectedHour + ":" + selectedMinute;

                if(finaltime != null) {
                    gym_time_to = finaltime;

                    if(gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

                        try{
                            String[] time_from = gym_time_from.split(":");
                            String[] time_to = gym_time_to.split(":");

                            int StartHour = Integer.parseInt(time_from[0]);
                            int StartMin = Integer.parseInt(time_from[1]);
                            int EndHour = Integer.parseInt(time_to[0]);
                            int EndMin = Integer.parseInt(time_to[1]);

                            if (!DateHelper.isTimeAfter(StartHour, StartMin, EndHour, EndMin)) {
                                txtTo.setText("");
                                UIHelper.showShortToastInCenter( context, "End time should be less then start time");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                }
            }
        }, hour, minute, true);//Yes 24 hour time


        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void openFromTimePickerDialog(final AnyTextView txtview) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime  = "";
                txtview.setText(selectedHour + ":" + selectedMinute);

                finaltime = selectedHour + ":" + selectedMinute;
                if(finaltime != null) {
                    gym_time_from = finaltime;

                    if(gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

                        try {
                            String[] time_from = gym_time_from.split(":");
                            String[] time_to = gym_time_to.split(":");

                            int StartHour = Integer.parseInt(time_from[0]);
                            int StartMin = Integer.parseInt(time_from[1]);
                            int EndHour = Integer.parseInt(time_to[0]);
                            int EndMin = Integer.parseInt(time_to[1]);

                            if (!DateHelper.isTimeAfter(StartHour, StartMin, EndHour, EndMin)) {
                                txtview.setText("");
                                UIHelper.showShortToastInCenter(context,"End time should be less then start time");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }




    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtDayDate;
        private AnyTextView txtTo;
        private AnyTextView txtFrom;
        private AnyTextView txtSecTo;
        private AnyTextView txtSecFrom;
        private AnyTextView txtThirdTo;
        private AnyTextView txtThirdFrom;
        private AnyTextView txtback;
        private AnyTextView txtIncre;
        private AnyTextView txtback1;
        private AnyTextView txtIncre1;


        public ViewHolder(View view) {

            txtDayDate = (AnyTextView) view.findViewById(R.id.txtDayDate);
            txtTo = (AnyTextView) view.findViewById(R.id.txtTo);
            txtFrom = (AnyTextView) view.findViewById(R.id.txtFrom);

            txtSecTo = (AnyTextView) view.findViewById(R.id.txtSecTo);
            txtSecFrom = (AnyTextView) view.findViewById(R.id.txtSecFrom);

            txtThirdTo = (AnyTextView) view.findViewById(R.id.txtThirdTo);
            txtThirdFrom = (AnyTextView) view.findViewById(R.id.txtThirdFrom);

            txtback= (AnyTextView) view.findViewById(R.id.txtback);
            txtIncre = (AnyTextView) view.findViewById(R.id.txtIncre);

            txtback1= (AnyTextView) view.findViewById(R.id.txtback1);
            txtIncre1 = (AnyTextView) view.findViewById(R.id.txtIncre1);

        }
    }




}
