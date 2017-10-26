package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.entities.TrainerBooking;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TrainerClientScheduleListItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;


public class TrainerClientScheduleFragment extends BaseFragment implements DatePickerListener, View.OnClickListener {

    ArrayList<Slot> slots = new ArrayList<>();
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
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;
    private ArrayListAdapter<Slot> adapter;
    private ArrayList<Slot> userCollection = new ArrayList<>();

    public TrainerClientScheduleFragment() {
    }

    public static TrainerClientScheduleFragment newInstance() {

        return new TrainerClientScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<Slot>(getDockActivity(), new TrainerClientScheduleListItemBinder(getDockActivity()));
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
        setTraineeBookings(new DateTime());
        setListener();
    }

    private void setTraineeBookings(DateTime dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(dateTime.toDate());

        Call<ResponseWrapper<TrainerBooking>> callBack = webService.getScheduleTrainee(prefHelper.getUserId(), date, date);
        loadingStarted();

        callBack.enqueue(new Callback<ResponseWrapper<TrainerBooking>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<TrainerBooking>> call, Response<ResponseWrapper<TrainerBooking>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    if (response.body().getUserDeleted() == 0) {
                        setTraineeData(response.body().getResult());
                    } else {
                        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                        dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogHelper.hideDialog();
                                getDockActivity().popBackStackTillEntry(0);
                                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                            }
                        },response.body().getMessage());
                        dialogHelper.showDialog();
                    }
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
       /* Call<ResponseWrapper<ArrayList<GetTraineeBookings>>> callBack = webService.ShowTraineeBookings(prefHelper.getUserId());

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
        });*/

    }

    private void setTraineeData(TrainerBooking result) {

        userCollection = new ArrayList<>();

        if (result.getSlots().size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            lv_trainer_srceen.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            lv_trainer_srceen.setVisibility(View.VISIBLE);
        }

        ;

        slots = result.getSlots();
        for (Slot item : slots) {
            if (item.getBookings() != null) {
                userCollection.add(item);
            }
            //userCollection.add(new TrainerClientScheduleItem(item.getStart_time() + "-" + item.getEnd_time(), item.getTrainer().getFirst_name() + " " + item.getTrainer().getLast_name()));
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

    private void bindData(ArrayList<Slot> userCollection) {
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
        titleBar.setSubHeading(getString(R.string.My_Schedule));

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

        showdate.setText(week + " , " + month + "  " + date);
        setTraineeBookings(dateSelected);
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
