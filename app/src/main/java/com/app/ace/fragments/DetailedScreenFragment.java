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
import com.app.ace.entities.DetailedScreenItem;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.DetailedScreenListItemBinder;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

import static com.app.ace.R.id.iv_Camera;
import static com.app.ace.R.id.iv_Fav;
import static com.app.ace.R.id.iv_Home;

/**
 * Created by saeedhyder on 4/5/2017.
 */

public class DetailedScreenFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.lv_detailedScreen)
    private ListView lv_detailedScreen;

    @InjectView(R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    @InjectView(R.id.iv_CalanderDetailedScreen)
    private ImageView iv_CalanderDetailedScreen;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;

    private ArrayListAdapter<DetailedScreenItem> adapter;

    private ArrayList<DetailedScreenItem> userCollection = new ArrayList<>();

    public static DetailedScreenFragment newInstance() {

        return new DetailedScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<DetailedScreenItem>(getDockActivity(), new DetailedScreenListItemBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSearchUserData();
        setListener();
    }

    private void setListener() {

        btn_training_Search_Submit.setOnClickListener(this);
        iv_Home.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        iv_CalanderDetailedScreen.setOnClickListener(this);

    }

    private void getSearchUserData() {
        userCollection= new ArrayList<>();
        userCollection.add(new DetailedScreenItem("Dates Schedule","10 May - 23 May"));
        userCollection.add(new DetailedScreenItem("Time Slot","16:00 - 17:00"));
        userCollection.add(new DetailedScreenItem("Training","BodyBuilding"));

        bindData(userCollection);
    }

    private void bindData(ArrayList<DetailedScreenItem> userCollection) {
        adapter.clearList();
        lv_detailedScreen.setAdapter(adapter);
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

            case R.id.iv_profile:

                getDockActivity().addDockableFragment(DetailedScreenFragment.newInstance(),"DetailedScreenFragment");

                break;

            case R.id.iv_CalanderDetailedScreen:

                getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(),"TrainerBookingCalendarFragment");

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
}
