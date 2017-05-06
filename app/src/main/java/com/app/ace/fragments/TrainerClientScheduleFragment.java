package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.GetTraineeBookings;
import com.app.ace.entities.InboxDataItem;
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.ShowComments;
import com.app.ace.entities.TrainerClientScheduleItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TrainerClientScheduleListItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.global.AppConstants.trainee;


public class TrainerClientScheduleFragment extends BaseFragment implements DatePickerListener, View.OnClickListener {

    @InjectView(R.id.showdate)
    private AnyTextView showdate;

    @InjectView(R.id.datePicker)
    private HorizontalPicker datePicker;

    @InjectView(R.id.lv_trainer_srceen)
    private ListView lv_trainer_srceen;

    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;

    @InjectView(R.id.iv_Calander)
    private ImageView iv_Calander;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    private ArrayListAdapter<TrainerClientScheduleItem> adapter;

    private ArrayList<TrainerClientScheduleItem> userCollection = new ArrayList<>();

    public TrainerClientScheduleFragment() {
    }

    public static TrainerClientScheduleFragment newInstance() {

        return new TrainerClientScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<TrainerClientScheduleItem>(getDockActivity(), new TrainerClientScheduleListItemBinder(getDockActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_trainer_client_schedule, container, false);
    }
@Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        datePicker
                .setListener(this)
                .setDays(120)
                .setOffset(7)
                .init();
        datePicker.setDate(new DateTime());
      //  getSearchUserData();
        setTraineeBookings();
        setListener();
    }

    private void setTraineeBookings() {

        Call<ResponseWrapper<ArrayList<GetTraineeBookings>>> callBack = webService.ShowTraineeBookings(prefHelper.getUserId());

        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<GetTraineeBookings>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<GetTraineeBookings>>> call, Response<ResponseWrapper<ArrayList<GetTraineeBookings>>> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    setTraineeData(response.body().getResult());

                }
                else
                {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<GetTraineeBookings>>> call, Throwable t) {

                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });

    }

    private void setTraineeData(ArrayList<GetTraineeBookings> result) {

        userCollection = new ArrayList<>();

        for(GetTraineeBookings item : result){
            userCollection.add(new TrainerClientScheduleItem(item.getStart_time()+"-"+item.getEnd_time(),item.getTrainer().getFirst_name()+" "+item.getTrainer().getLast_name()));
        }
        bindData(userCollection);
    }

    private void setListener() {

        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        datePicker.setOnClickListener(this);

    }


   /* private void getSearchUserData() {
        userCollection= new ArrayList<>();
        userCollection.add(new TrainerClientScheduleItem("11:00-12:00","James"));
        userCollection.add(new TrainerClientScheduleItem("1:00-2:00","Eoin"));
       // userCollection.add(new TrainerClientScheduleItem("Training","BodyBuilding"));

        bindData(userCollection);
    }*/

    private void bindData(ArrayList<TrainerClientScheduleItem> userCollection) {
        adapter.clearList();
        lv_trainer_srceen.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("My Schdedule");

    }
/*    @InjectView(R.id.showdate)
    private AnyTextView showdate;*/
    @Override
    public void onDateSelected(DateTime dateSelected) {
//String week= dateSelected.dayOfWeek();
        //Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());

        String week = dateSelected.toString("EEE", Locale.getDefault()).toUpperCase();
        String month = dateSelected.toString("MMMM", Locale.getDefault());
        String date = String.valueOf(dateSelected.getDayOfMonth());

        showdate.setText(week +" , "+month+"  "+date);
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

        }

    }

}
