package com.app.ace.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created on 4/27/2017.
 */

public class DialogHelper  {
    Dialog dialog;

    private ImageLoader imageLoader;
int days = 1;
    CalendarPickerView calendarView;
    Date StartDate;
    public DialogHelper(Context context) {
        this.dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       /* */
    }
    public DialogHelper(Context context,boolean title) {
        this.dialog = new Dialog(context);

        if (title){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

       /* */
    }


    public Dialog initDialog(int layoutID,Date startDate) {

        this.dialog.setContentView(layoutID);
        calendarView = (CalendarPickerView) this.dialog.findViewById(R.id.calendarView);
        StartDate = startDate;
        return this.dialog;
    }
    public Dialog initDialog(int layoutID) {

        this.dialog.setContentView(layoutID);

        return this.dialog;
    }



    public Dialog postImage(int layoutID, Context context, String picpath)
    {
        this.dialog.setContentView(layoutID);
        imageLoader = ImageLoader.getInstance();
        PhotoView photoView = (PhotoView) dialog.findViewById(R.id.photo_view);
        imageLoader.displayImage(picpath, photoView);

        return this.dialog;
    }

    public void setDuration(Context context){
        final Spinner sp_weeks = (Spinner) this.dialog.findViewById(R.id.sp_weeks);
        List<String> timeDuration = new ArrayList<String>();
        timeDuration.add("Select time duration");
        timeDuration.add("2 Week");
        timeDuration.add("1 Month");
        timeDuration.add("3 Months");
        timeDuration.add("6 Months");
        setCalendarView(StartDate,days);
        ArrayAdapter<String> TimeDurationAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, timeDuration);
        TimeDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weeks.setAdapter(TimeDurationAdapter);
        sp_weeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String duration = sp_weeks.getSelectedItem().toString();
                if(duration=="2 Week")
                {
                    days=14;
                    setCalendarView(StartDate,days);

                }
                if(duration=="1 Month")
                {
                    days=30;
                    setCalendarView(StartDate,days);
                }
                if(duration=="3 Months")
                {
                    days=90;
                    setCalendarView(StartDate,days);
                }
                if(duration=="6 Months")
                {
                    days=180;
                    setCalendarView(StartDate,days);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void setSummitButton(View.OnClickListener onClickListener){
        Button submit = (Button)this.dialog.findViewById(R.id.btn_training_Search_Submit);
        submit.setOnClickListener(onClickListener);

    }
    private void setCalendarView(Date StartDate,int days) {
       /* calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1990, 1, 1))
                .setMaximumDate(CalendarDay.from(2060, 12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.selectRange(CalendarDay.from(2017, 4, 12), CalendarDay.from(2017, 4, 15));*/


        Calendar nextYear = Calendar.getInstance();
        nextYear.setTime(StartDate);
        nextYear.add(Calendar.YEAR, 1);
        nextYear.add(Calendar.MONTH,1);
        nextYear.add(Calendar.DAY_OF_MONTH,1);

        calendarView.setCustomDayView(new DefaultDayViewAdapter());
        // calendarView.setCustomDayView(new CustomDayViewAdapter(myDockActivity));


        Calendar today = Calendar.getInstance();
        today.setTime(StartDate);
        ArrayList<Date> dates = new ArrayList<Date>();
        today.add(Calendar.DATE, 1);
        dates.add(today.getTime());

        today.add(Calendar.DATE, days);
        dates.add(today.getTime());


        //dates.add(today.getTime());
        calendarView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        //calendarView.setDecorators(Arrays.<CalendarCellDecorator>asList(new SampleDecorator()));

        calendarView.init(StartDate, nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withSelectedDates(dates);

    }

    public void SetTitle(String title) {
       this.dialog.setTitle(title);
    }

    public void setMessage(int txtmessageID, String message) {
        TextView txtmessage = (TextView) this.dialog.findViewById(txtmessageID);
        txtmessage.setText(message);
    }

    public void initPositiveBtn(int btnPositiveID, String btnText, View.OnClickListener onClickListener) {
        Button btnpositive = (Button) this.dialog.findViewById(btnPositiveID);
        btnpositive.setText(btnText);
        btnpositive.setOnClickListener(onClickListener);
    }

    public void initNegativeBtn(int btnNegativeID, String btnText, View.OnClickListener onClickListener) {
        Button btnnegative = (Button) this.dialog.findViewById(btnNegativeID);
        btnnegative.setText(btnText);
        btnnegative.setOnClickListener(onClickListener);
    }

    public void showDialog() {
        this.dialog.show();
    }

    public Date dismissDialog() {
        this.dialog.dismiss();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarView.getSelectedDate());
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return calendar.getTime();
    }
    public void dismisspoptuDialog(){
        this.dialog.dismiss();
    }


}
