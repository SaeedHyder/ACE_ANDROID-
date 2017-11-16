package com.app.ace.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.app.ace.R.id.txt1;

/**
 * Created on 4/27/2017.
 */

public class DialogHelper {
    Dialog dialog;
    int days = 1;
    CalendarPickerView calendarView;
    Date StartDate;
    private ImageLoader imageLoader;

    public DialogHelper(Context context) {
        if (context != null) {
            this.dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
       /* */
    }

    public DialogHelper(Context context, boolean title) {
        this.dialog = new Dialog(context);

        if (title) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

       /* */
    }

    public Dialog initLogoutDialog(int layoutID, View.OnClickListener onclicklistenerYes, String message) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        Button btnDeleteOk = (Button) dialog.findViewById(R.id.btnDeleteOk);
        btnDeleteOk.setOnClickListener(onclicklistenerYes);
        AnyTextView textView = (AnyTextView) dialog.findViewById(txt1);
        textView.setText(message);
        return this.dialog;
    }

    public Dialog initDialog(int layoutID, Date startDate) {

        this.dialog.setContentView(layoutID);
        calendarView = (CalendarPickerView) this.dialog.findViewById(R.id.calendarView);
        StartDate = startDate;
        return this.dialog;
    }

    public Dialog initDialog(int layoutID) {

        this.dialog.setContentView(layoutID);

        return this.dialog;
    }

    public Dialog initlogout(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initLanguage(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initDeleteChat(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }


    public Dialog postImage(int layoutID, Context context, String picpath) {
        this.dialog.setContentView(layoutID);
        imageLoader = ImageLoader.getInstance();
        PhotoView photoView = (PhotoView) dialog.findViewById(R.id.photo_view);
        imageLoader.displayImage(picpath, photoView);

        return this.dialog;
    }

    public Dialog feedback(int layoutID, Context context, View.OnClickListener submit) {
        this.dialog.setContentView(layoutID);
        Button Submit = (Button) dialog.findViewById(R.id.btn_Submit);
        Submit.setOnClickListener(submit);
        final AnyEditTextView hours = getHours(R.id.edt_hours_day);
        final AnyEditTextView days = getDays(R.id.edt_total_days);
        final AnyTextView totalHours = getTotalHours(R.id.txt_total_hours);
        hours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equals("")) {
                    int hoursnumber = Integer.parseInt(s.toString());
                    if (hoursnumber <= 0 || hoursnumber > 24) {
                        hours.setError(dialog.getContext().getResources().getString(R.string.valid_hours));
                    } else if (!days.getText().toString().trim().equals("")) {
                        int daysnumber = Integer.parseInt(days.getText().toString());
                        totalHours.setText(hoursnumber * daysnumber + "");
                    }
                }
                if (s.toString().trim().equals("")) {
                    totalHours.setText("");
                }
            }
        });
        days.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hours.getText().toString().trim().equals("") && !s.toString().trim().equals("")) {
                    int hoursnumber = Integer.parseInt(hours.getText().toString());
                    int daysnumber = Integer.parseInt(s.toString());
                    totalHours.setText(hoursnumber * daysnumber + "");
                }
                if (s.toString().trim().equals("")) {
                    totalHours.setText("");
                }
            }
        });
        return this.dialog;
    }

    public AnyEditTextView getHours(int editTextID) {
        AnyEditTextView hours = (AnyEditTextView) dialog.findViewById(editTextID);
        return hours;
    }

    public AnyEditTextView getDays(int editTextID) {
        AnyEditTextView days = (AnyEditTextView) dialog.findViewById(editTextID);
        return days;
    }

    public AnyTextView getTotalHours(int editTextID) {
        AnyTextView totalHours = (AnyTextView) dialog.findViewById(editTextID);
        return totalHours;
    }

    public void setDuration(Context context) {
        final Spinner sp_weeks = (Spinner) this.dialog.findViewById(R.id.sp_weeks);
        List<String> timeDuration = new ArrayList<String>();
        timeDuration.add("Select time duration");
        timeDuration.add("2 Week");
        timeDuration.add("1 Month");
        timeDuration.add("3 Months");
        timeDuration.add("6 Months");
        setCalendarView(StartDate, days);
        ArrayAdapter<String> TimeDurationAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, timeDuration);
        TimeDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weeks.setAdapter(TimeDurationAdapter);
        sp_weeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String duration = sp_weeks.getSelectedItem().toString();
                if (duration == "2 Week") {
                    days = 14;
                    setCalendarView(StartDate, days);

                }
                if (duration == "1 Month") {
                    days = 30;
                    setCalendarView(StartDate, days);
                }
                if (duration == "3 Months") {
                    days = 90;
                    setCalendarView(StartDate, days);
                }
                if (duration == "6 Months") {
                    days = 180;
                    setCalendarView(StartDate, days);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setSummitButton(View.OnClickListener onClickListener) {
        Button submit = (Button) this.dialog.findViewById(R.id.btn_training_Search_Submit);
        submit.setOnClickListener(onClickListener);

    }

    private void setCalendarView(Date StartDate, int days) {
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
        nextYear.add(Calendar.MONTH, 1);
        nextYear.add(Calendar.DAY_OF_MONTH, 1);

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

    public void hideDialog() {
        this.dialog.dismiss();
    }

    public Date dismissDialog() {
        this.dialog.dismiss();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarView.getSelectedDate());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public void dismisspoptuDialog() {
        this.dialog.dismiss();
    }


}
