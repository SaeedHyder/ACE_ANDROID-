package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.Slot;
import com.app.ace.fragments.LoginFragment;
import com.app.ace.global.WebServiceConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.interfaces.OndeleteListener;
import com.app.ace.retrofit.WebService;
import com.app.ace.retrofit.WebServiceFactory;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class TraineeScheduleListItemBinder extends ViewBinder<Slot> {
    OndeleteListener deleteListener;
    private WebService service;
    Context context;
    DockActivity dockActivity;
    private BasePreferenceHelper preferenceHelper;
    MainActivity getMainActivity;

    public TraineeScheduleListItemBinder(Context context, DockActivity dockActivity, OndeleteListener ondeleteListener) {
        super(R.layout.trainee_schedule_item);
        deleteListener = ondeleteListener;
        this.dockActivity = dockActivity;
        context = context;
        service = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,
                WebServiceConstants.SERVICE_BASE_URL);
        preferenceHelper = new BasePreferenceHelper(context);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TraineeScheduleListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final Slot entity, final int position, int grpPosition, View view, Activity activity) {

        TraineeScheduleListItemBinder.ViewHolder viewHolder = (TraineeScheduleListItemBinder.ViewHolder) view.getTag();
        final Context context = viewHolder.txt_traineeSchedule_1stColumn.getContext();
        viewHolder.txt_traineeSchedule_1stColumn.setText(context.getString(R.string.date_schedule));
        viewHolder.txt_traineeSchedule_timeslot.setText(context.getString(R.string.time_slot));
        viewHolder.txt_traineeSchedule_training.setText(context.getString(R.string.training));

        viewHolder.txt_traineeSchedule_2stColumn.setText(entity.getDate());
        viewHolder.txt_traineeSchedule_timeslot2stColumn.setText(entity.getStartTime() + "-" + entity.getEndTime());
        viewHolder.txt_traineeSchedule_training2stColumn.setText(entity.getBookings().getTraining_type());
        if (entity.getBookings().getTrainer_accepted() == 1) {
            viewHolder.cancleBooking.setVisibility(View.VISIBLE);
            viewHolder.pendingbtn.setVisibility(View.GONE);
        } else {
            viewHolder.pendingbtn.setVisibility(View.VISIBLE);
            viewHolder.cancleBooking.setVisibility(View.GONE);
        }
        viewHolder.cancleBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseWrapper> callback = service.deleteBooking(String.valueOf(entity.getBookings().getId()),
                        preferenceHelper.getUserId());
                callback.enqueue(new Callback<ResponseWrapper>() {
                    @Override
                    public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                        if (response.body().getUserDeleted() == 0) {
                            deleteListener.OndeleteTrainee(position);
                        } else {
                            final DialogHelper dialogHelper = new DialogHelper(dockActivity);
                            dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogHelper.hideDialog();
                                    dockActivity.popBackStackTillEntry(0);
                                    dockActivity.addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                                }
                            },response.body().getMessage());
                            dialogHelper.showDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                        Log.e("DetailedScreenFragment", t.toString());
                    }
                });

            }
        });

    }


    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txt_traineeSchedule_1stColumn;
        private AnyTextView txt_traineeSchedule_2stColumn;
        private AnyTextView txt_traineeSchedule_timeslot;
        private AnyTextView txt_traineeSchedule_timeslot2stColumn;
        private AnyTextView txt_traineeSchedule_training;
        private AnyTextView txt_traineeSchedule_training2stColumn;
        private Button cancleBooking;
        private Button pendingbtn;

        public ViewHolder(View view) {

            txt_traineeSchedule_1stColumn = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_1stColumn);
            txt_traineeSchedule_2stColumn = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_2stColumn);
            txt_traineeSchedule_timeslot = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_timeslot);
            txt_traineeSchedule_timeslot2stColumn = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_timeslot2stColumn);
            txt_traineeSchedule_training = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_training);
            txt_traineeSchedule_training2stColumn = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_training2stColumn);
            cancleBooking = (Button) view.findViewById(R.id.btn_training_Cancle_Submit);
            pendingbtn = (Button) view.findViewById(R.id.btn_pending);

        }
    }
}

