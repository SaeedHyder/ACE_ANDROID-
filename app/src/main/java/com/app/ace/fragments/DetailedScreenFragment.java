package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.DetailedScreenItem;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.entities.Slot;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DateHelper;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.DetailedScreenListItemBinder;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.iv_Camera;
import static com.app.ace.R.id.iv_Fav;
import static com.app.ace.R.id.iv_Home;
import static com.app.ace.R.id.ll_profile;
import static com.app.ace.R.id.riv_profile_pic;

/**
 * Created by saeedhyder on 4/5/2017.
 */

public class DetailedScreenFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.lv_detailedScreen)
    private ListView lv_detailedScreen;

    @InjectView(R.id.ll_mainFrameDetailed)
    LinearLayout ll_mainFrameDetailed;

    @InjectView(R.id.ll_profile)
    LinearLayout ll_profile;

    @InjectView(R.id.btn_cancel_booking)
    Button btn_cancel_booking;

    @InjectView(R.id.iv_CalanderDetailedScreen)
    private ImageView iv_CalanderDetailedScreen;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;

    @InjectView(R.id.txt_day)
    private AnyTextView txt_day;
    @InjectView(R.id.txt_time)
    private AnyTextView txt_time;
    private ImageLoader imageLoader;

    @InjectView(R.id.txt_detailedS_ProfileName)
    private AnyTextView txt_detailedS_ProfileName;

    @InjectView (R.id.img_DetailedProfile)
    CircleImageView img_DetailedProfile;

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
        imageLoader = ImageLoader.getInstance();
        if (currentSlot!=null){
            txt_detailedS_ProfileName.setText(currentSlot.getBookings().getUser().getFirst_name()
                    +" "+currentSlot.getBookings().getUser().getLast_name());
            imageLoader.displayImage(currentSlot.getBookings().getUser().getProfile_image(), img_DetailedProfile);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            try {
                Date date = format.parse(currentSlot.getDate());
                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
                txt_day.setText(dayOfWeek);
                txt_time.setText(currentSlot.getStartTime()+"-"+currentSlot.getEndTime()+" "+getString(R.string.one_hour));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        getSearchUserData();
        setListener();
    }

    private void setListener() {

        btn_cancel_booking.setOnClickListener(this);
        iv_Home.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        iv_CalanderDetailedScreen.setOnClickListener(this);

    }

    private void getSearchUserData() {
        userCollection= new ArrayList<>();
        if (currentSlot!=null) {
            userCollection.add(new DetailedScreenItem(getString(R.string.date_schedule), currentSlot.getDate()));
            userCollection.add(new DetailedScreenItem(getString(R.string.time_slot), currentSlot.getStartTime()+"-"+currentSlot.getEndTime()));
            userCollection.add(new DetailedScreenItem(getString(R.string.training), currentSlot.getBookings().getTraining_type()));

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
        titleBar.setSubHeading(getString(R.string.My_Schedule));

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.btn_cancel_booking:
                Call<ResponseWrapper>callback = webService.deleteBooking(String.valueOf(currentSlot.getBookings().getId()),
                        prefHelper.getUserId());
                callback.enqueue(new Callback<ResponseWrapper>() {
                    @Override
                    public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                            getDockActivity().addDockableFragment(NotificationListingFragment.newInstance()
                                    ,"NotificationListingFragment");

                    }

                    @Override
                    public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                        Log.e("DetailedScreenFragment",t.toString());
                    }
                });


                break;

            case R.id.iv_Home:

                getDockActivity().addDockableFragment(HomeFragment.newInstance(),"HomeFragment");

                break;

            case R.id.iv_profile:

                getDockActivity().addDockableFragment(DetailedScreenFragment.newInstance(),"DetailedScreenFragment");

                break;

            case R.id.iv_CalanderDetailedScreen:

                getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(),
                        "TrainerBookingCalendarFragment");

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
