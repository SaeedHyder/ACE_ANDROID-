package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.ace.R;
import com.app.ace.entities.BookingRequestEnt;
import com.app.ace.entities.DetailedScreenItem;
import com.app.ace.entities.NotificationEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 4/5/2017.
 */

public class DetailedScreenFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.ll_mainFrameDetailed)
    LinearLayout ll_mainFrameDetailed;

    @InjectView(R.id.ll_profile)
    LinearLayout ll_profile;

    private ImageLoader imageLoader;

    @InjectView(R.id.txt_detailedS_ProfileName)
    private AnyTextView txt_detailedS_ProfileName;

    @InjectView(R.id.txt_hours)
    private AnyTextView txt_hours;

    @InjectView(R.id.txt_days)
    private AnyTextView txt_days;

    @InjectView(R.id.txt_total_hours)
    private AnyTextView txt_total_hours;

    @InjectView(R.id.btn_send_msg)
    private Button btn_send_msg;

    @InjectView(R.id.img_DetailedProfile)
    CircleImageView img_DetailedProfile;

    private BookingRequestEnt bookingData = new BookingRequestEnt();

    private Slot currentSlot;
    private static String SLOT = "SLOT";
    private static String ACTIONID = "actionid";
    private static String ENTITY = "entity";
    private int actionID;
    private NotificationEnt slotIdEntity;
    private String slotId;
    private ArrayListAdapter<DetailedScreenItem> adapter;

    private ArrayList<DetailedScreenItem> userCollection = new ArrayList<>();

    public static DetailedScreenFragment newInstance() {

        return new DetailedScreenFragment();
    }

    public static DetailedScreenFragment newInstance(int actionId) {
        Bundle args = new Bundle();
        args.putInt(ACTIONID, actionId);
        DetailedScreenFragment fragment = new DetailedScreenFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static DetailedScreenFragment newInstance(String slotJson, NotificationEnt entity) {
        Bundle args = new Bundle();
        args.putString(SLOT, slotJson);
        args.putString(ENTITY, new Gson().toJson(entity));
        DetailedScreenFragment fragment = new DetailedScreenFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actionID = getArguments().getInt(ACTIONID);
            // Toast.makeText(getDockActivity(), ConversationId, Toast.LENGTH_LONG).show();
        }
       /* if (getArguments() != null) {
            SLOT = getArguments().getString(SLOT);
            ENTITY = getArguments().getString(ENTITY);
            // Toast.makeText(getDockActivity(), ConversationId, Toast.LENGTH_LONG).show();
        }
        if (!SLOT.isEmpty()) {
            currentSlot = GsonFactory.getConfiguredGson().fromJson(SLOT, Slot.class);
        }
        if (ENTITY != null) {
            slotIdEntity = GsonFactory.getConfiguredGson().fromJson(ENTITY, NotificationEnt.class);
        }
*/
        // adapter = new ArrayListAdapter<DetailedScreenItem>(getDockActivity(), new DetailedScreenListItemBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_screen, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        view.setVisibility(View.INVISIBLE);
        super.onViewCreated(view, savedInstanceState);
        imageLoader = ImageLoader.getInstance();

        bookingRequetData(view);
        setListener();


    }

    private void bookingRequetData(final View view) {
        Call<ResponseWrapper<BookingRequestEnt>> callback = webService.bookingRequest(prefHelper.getUserId(), actionID);

        callback.enqueue(new Callback<ResponseWrapper<BookingRequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<BookingRequestEnt>> call, Response<ResponseWrapper<BookingRequestEnt>> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    if (response.body().getUserDeleted() == 0) {
                        setRequestData(response.body().getResult());
                        bookingData = response.body().getResult();
                        view.setVisibility(View.VISIBLE);
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

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<BookingRequestEnt>> call, Throwable t) {
                Log.e(getClass().getName(), t.toString());
            }
        });

    }

    private void setRequestData(BookingRequestEnt result) {
        if (result != null) {
            imageLoader.displayImage(result.getUser().getProfile_image(), img_DetailedProfile);
            txt_detailedS_ProfileName.setText(result.getUser().getFirst_name() + " " + result.getUser().getLast_name());
            txt_hours.setText(result.getNoOfHours() + "");
            txt_days.setText(result.getNoOfDays() + "");
            txt_total_hours.setText(result.getTotalHoursRequested() + "");
        }

    }

    private void setListener() {
        btn_send_msg.setOnClickListener(this);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.booking_requesrt));

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_send_msg:
                getDockActivity().popBackStackTillEntry(1);
                getDockActivity().addDockableFragment(NewMsgChat_Screen_Fragment.newInstance(bookingData.getUser().getId(), bookingData.getUser().getFirst_name() + " " + bookingData.getUser().getLast_name()), "NewMsgChat_Screen_Fragment");
                break;


        }
    }

}
