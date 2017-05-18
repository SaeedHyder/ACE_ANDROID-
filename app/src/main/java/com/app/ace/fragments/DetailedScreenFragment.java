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
import com.app.ace.entities.Slot;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.DetailedScreenListItemBinder;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;

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
    private Slot currentSlot;
    private static String SLOT = "SLOT";
    private ArrayListAdapter<DetailedScreenItem> adapter;

    private ArrayList<DetailedScreenItem> userCollection = new ArrayList<>();

    public static DetailedScreenFragment newInstance() {

        return new DetailedScreenFragment();
    }
    public static DetailedScreenFragment newInstance(String slotJson) {
        Bundle args = new Bundle();
        args.putString(SLOT, slotJson);
        DetailedScreenFragment fragment = new DetailedScreenFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            SLOT = getArguments().getString(SLOT);
            // Toast.makeText(getDockActivity(), ConversationId, Toast.LENGTH_LONG).show();
        }
        if (!SLOT.isEmpty()){
            currentSlot =  GsonFactory.getConfiguredGson().fromJson(SLOT,Slot.class);
        }
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
        if (currentSlot!=null) {
            userCollection.add(new DetailedScreenItem("Dates Schedule", currentSlot.getDate()));
            userCollection.add(new DetailedScreenItem("Time Slot", currentSlot.getStartTime()+"-"+currentSlot.getEndTime()));
            userCollection.add(new DetailedScreenItem("Training", currentSlot.getBookings().getTrainingType()));

        }
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
