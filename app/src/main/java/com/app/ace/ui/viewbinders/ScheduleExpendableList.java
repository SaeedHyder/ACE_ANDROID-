package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.LoginFragment;
import com.app.ace.global.WebServiceConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.interfaces.OndeleteListener;
import com.app.ace.retrofit.WebService;
import com.app.ace.retrofit.WebServiceFactory;
import com.app.ace.ui.viewbinders.abstracts.ExpandableListViewBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/4/2017.
 */

public class ScheduleExpendableList<E> extends ExpandableListViewBinder<String, E> implements View.OnClickListener {
    OndeleteListener deleteListener;
    private WebService service;
    Context context;
    DockActivity dockActivity;
    private BasePreferenceHelper preferenceHelper;

    public ScheduleExpendableList(Context context, DockActivity dockActivity, OndeleteListener ondeleteListener) {

        super(R.layout.trainer_schedule_header_item, R.layout.trainer_schedule_child_item);
        deleteListener = ondeleteListener;
        context = context;
        this.dockActivity = dockActivity;
        service = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,
                WebServiceConstants.SERVICE_BASE_URL);
        preferenceHelper = new BasePreferenceHelper(context);
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {

        return new NewExpListGroupItemViewHolder(view);
    }

    @Override
    public BaseChildViewHolder createChildViewHolder(View view) {
        return new NewExpListChildItemViewHolder(view);
    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, View view, Activity activity, boolean isExpended) {
        try {
            if (!entity.equals("null,null")) {
                NewExpListGroupItemViewHolder groupItemViewHolder = (NewExpListGroupItemViewHolder) view.getTag();
                if (isExpended) {
                    groupItemViewHolder.indicator.setImageResource(R.drawable.dropdownup);
                } else
                    groupItemViewHolder.indicator.setImageResource(R.drawable.dropdowndown);

                groupItemViewHolder.deleteSchedule.setOnClickListener(this);
                String head = entity;
                String[] date = head.split(",");
                groupItemViewHolder.deleteSchedule.setTag(entity);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(date[0]);
                Date endDate = dateFormat.parse(date[1]);
                System.out.println(startDate.getDate());
                groupItemViewHolder.txtHeader.setText(startDate.getDate() + " " +
                        getShortMonthName(startDate.getYear(), startDate.getMonth(), startDate.getDate()) + "  -  " +
                        endDate.getDate() + " " + getShortMonthName(endDate.getYear(), endDate.getMonth(), endDate.getDate()));
        /*
        String[] startDatearray = date[0].split("-");
        String startDate = startDatearray[2]+" "+Datedialoghelper.getShortMonthName(Integer.parseInt(startDatearray[0])
                ,Integer.parseInt(startDatearray[1])
                ,Integer.parseInt(startDatearray[2]));
        String EndDate = startDatearray[2]+" "+Datedialoghelper.getShortMonthName(Integer.parseInt(startDatearray[0])
                ,Integer.parseInt(startDatearray[1])
                ,Integer.parseInt(startDatearray[2]));*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getShortMonthName(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        return new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
    }

    @Override
    public void bindChildView(E entity, int position, int grpPosition, View view, Activity activity) {
        try {
            NewExpListChildItemViewHolder childItemViewHolder = (NewExpListChildItemViewHolder) view.getTag();
            String child = (String) entity;
            String[] time = child.split(",");
            childItemViewHolder.txtStart.setText(time[0]);
            childItemViewHolder.txtEnd.setText(time[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_deleteSchedule:
                String entity = (String) v.getTag();
                String head = entity;
                final String[] date = head.split(",");
                System.out.println(preferenceHelper.getUserId());
                Call<ResponseWrapper> callback = service.deleteSchedule(preferenceHelper.getUserId(), date[0], date[1]);
                callback.enqueue(new Callback<ResponseWrapper>() {
                    @Override
                    public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                        if (response.body().getUserDeleted() == 0) {
                            Log.e("BINDEREXPANDABLE", response.message());
                            deleteListener.Ondelete();
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
                        Log.e("BINDEREXPANDABLE", t.toString());
                    }
                });
                break;
        }
    }


    public static class NewExpListGroupItemViewHolder extends BaseGroupViewHolder {
        TextView txtHeader;
        ImageView indicator;
        ImageView deleteSchedule;

        public NewExpListGroupItemViewHolder(View view) {
            txtHeader = (TextView) view.findViewById(R.id.txt_date_group);
            indicator = (ImageView) view.findViewById(R.id.groupindicator);
            deleteSchedule = (ImageView) view.findViewById(R.id.img_deleteSchedule);
        }
    }

    public static class NewExpListChildItemViewHolder extends BaseChildViewHolder {
        TextView txtStart;
        TextView txtEnd;

        public NewExpListChildItemViewHolder(View view) {
            txtStart = (TextView) view.findViewById(R.id.txtStart);
            txtEnd = (TextView) view.findViewById(R.id.txtEnd);
        }
    }
}
