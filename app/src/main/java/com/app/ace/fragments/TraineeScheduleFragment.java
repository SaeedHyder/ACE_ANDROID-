package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.TraineeScheduleItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TraineeScheduleListItemBinder;
import com.app.ace.ui.views.TitleBar;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class TraineeScheduleFragment extends BaseFragment implements View.OnClickListener,DatePickerListener {

    @InjectView(R.id.datePicker)
    private HorizontalPicker datePicker;
    @InjectView(R.id.lv_trauneeScheduleScreen)
    private ListView lv_trauneeScheduleScreen;

    @InjectView(R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    @InjectView(R.id.iv_Home)
    ImageView iv_Home;

    @InjectView(R.id.iv_Calander)
    ImageView iv_Calander;


    private ArrayListAdapter<TraineeScheduleItem> adapter;

    private ArrayList<TraineeScheduleItem> userCollection = new ArrayList<>();


    public static TraineeScheduleFragment newInstance()
    {
        return new TraineeScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<TraineeScheduleItem>(getDockActivity(), new TraineeScheduleListItemBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainee_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker
                .setListener(this)
                .setDays(120)
                .setOffset(7)
                .init();
        datePicker.setDate(new DateTime());
        getTraineeScheduleData();
        setListener();
    }

    private void setListener() {

        btn_training_Search_Submit.setOnClickListener(this);
        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
    }


    private void getTraineeScheduleData() {
        userCollection= new ArrayList<>();
        userCollection.add(new TraineeScheduleItem("Dates Schedule","10 May - 23 May"));
        userCollection.add(new TraineeScheduleItem("Time Slot","16:00 - 17:00"));
        userCollection.add(new TraineeScheduleItem("Training","BodyBuilding"));

        bindData(userCollection);
    }

    private void bindData(ArrayList<TraineeScheduleItem> userCollection) {
        adapter.clearList();
        lv_trauneeScheduleScreen.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("My Schedule");

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_training_Search_Submit:

                getDockActivity().addDockableFragment(NotificationListingFragment.newInstance(),"NotificationListingFragment");
                break;

            case R.id.iv_Home:
                getDockActivity().addDockableFragment(HomeFragment.newInstance(),"HomeFragment");
                break;

            case R.id.iv_Calander:

                getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

              /*  if(AppConstants.is_show_trainer){

                    getDockActivity().addDockableFragment(TrainerBookingCalendarFragment.newInstance(),"TrainerBookingCalendarFragment");
                }
                else
                {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

                }*/

                break;

        }
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

    }
}
