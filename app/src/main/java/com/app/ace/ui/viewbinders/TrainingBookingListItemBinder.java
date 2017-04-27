package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.util.Calendar;

import static com.app.ace.R.id.txt;
import static com.app.ace.R.id.txtTo;

/**
 * Created by muniyemiftikhar on 4/7/2017.
 */

public class TrainingBookingListItemBinder extends ViewBinder<TrainingBookingCalenderItem> {

    Context context;
    TrainingBooking trainingbooking;


    public String gym_time_to,gym_time_from;


    public TrainingBookingListItemBinder(Context context,TrainingBooking trainingbooking) {
        super(R.layout.training_booking_calender_items);

        this.context= context;
        this.trainingbooking=trainingbooking;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TrainingBookingListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final TrainingBookingCalenderItem entity, int position, int grpPosition, View view, Activity activity) {

        //final ViewHolder viewHolder = (ViewHolder) view.getTag();

        final TrainingBookingListItemBinder.ViewHolder viewHolder = (TrainingBookingListItemBinder.ViewHolder) view.getTag();

        if(entity.getTxtDayDate().contains("Sat")) {
            viewHolder.txtDayDate.setTextColor(R.color.red);
            viewHolder.txtDayDate.setText(entity.getTxtDayDate());
        }
        else if(entity.getTxtDayDate().contains("Sun"))
        {
            viewHolder.txtDayDate.setTextColor(R.color.red);
            viewHolder.txtDayDate.setText(entity.getTxtDayDate());
        }
        else
        {
            viewHolder.txtDayDate.setTextColor(R.color.black);
            viewHolder.txtDayDate.setText(entity.getTxtDayDate());
        }
      //  viewHolder.txtTo.setText(entity.getTxtTo());
        //viewHolder.txtFrom.setText(entity.getTxtFrom());

        viewHolder.txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openToTimePickerDialog(viewHolder.txtTo);
            }
        });

        viewHolder.txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFromTimePickerDialog(viewHolder.txtFrom);
            }
        });






        viewHolder.txtIncre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               trainingbooking.textList();

                viewHolder.txtback.setVisibility(View.VISIBLE);

                viewHolder.txtSecTo.setVisibility(View.VISIBLE);
                viewHolder.txtSecTo.setText(entity.getTxtSecTo());
                viewHolder.txtSecFrom.setVisibility(View.VISIBLE);
                viewHolder.txtSecFrom.setText(entity.getTxtSecFrom());

                viewHolder.txtTo.setVisibility(View.GONE);
                viewHolder.txtTo.setText(entity.getTxtTo());
                viewHolder.txtFrom.setVisibility(View.GONE);
                viewHolder.txtFrom.setText(entity.getTxtFrom());

            }
        });

        viewHolder.txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.txtback.setVisibility(View.GONE);
                trainingbooking.backList();


                viewHolder.txtTo.setVisibility(View.VISIBLE);
                viewHolder.txtTo.setText(entity.getTxtTo());
                viewHolder.txtFrom.setVisibility(View.VISIBLE);
                viewHolder.txtFrom.setText(entity.getTxtFrom());

                viewHolder.txtSecTo.setVisibility(View.GONE);
                viewHolder.txtSecTo.setText(entity.getTxtSecTo());
                viewHolder.txtSecFrom.setVisibility(View.GONE);
                viewHolder.txtSecFrom.setText(entity.getTxtSecFrom());
            }
        });

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
        private AnyTextView txtIncre;
        private AnyTextView txtSecTo;
        private AnyTextView txtSecFrom;

        private AnyTextView txtback;


        public ViewHolder(View view) {

            txtDayDate = (AnyTextView) view.findViewById(R.id.txtDayDate);
            txtTo = (AnyTextView) view.findViewById(R.id.txtTo);
            txtFrom = (AnyTextView) view.findViewById(R.id.txtFrom);
            txtIncre = (AnyTextView) view.findViewById(R.id.txtIncre);

            txtSecTo = (AnyTextView) view.findViewById(R.id.txtSecTo);
            txtSecFrom = (AnyTextView) view.findViewById(R.id.txtSecFrom);

            txtback= (AnyTextView) view.findViewById(R.id.txtback);

        }
    }

}
