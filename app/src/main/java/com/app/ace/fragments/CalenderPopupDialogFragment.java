package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;

import roboguice.inject.InjectView;

import static android.R.attr.id;
import static com.app.ace.R.id.iv_Calander;
import static com.app.ace.R.id.iv_Camera;
import static com.app.ace.R.id.iv_Fav;
import static com.app.ace.R.id.iv_Home;
import static com.app.ace.R.id.iv_profile;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class CalenderPopupDialogFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.btndialogCalendar_2)
    Button btndialogCalendar_2;

    @InjectView(R.id.btndialogCalendar_1)
    Button btndialogCalendar_1;

    public static CalenderPopupDialogFragment newInstance() {

        return new CalenderPopupDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_popup, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {

        btndialogCalendar_2.setOnClickListener(this);
        btndialogCalendar_1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btndialogCalendar_2:

                    AppConstants.is_show_trainer = false;
                    getDockActivity().addDockableFragment(CalendarFragment.newInstance(),"CalendarFragment");

                    break;

            case R.id.btndialogCalendar_1:

                AppConstants.is_show_trainer = false;
                getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

                break;


        }
    }
}
