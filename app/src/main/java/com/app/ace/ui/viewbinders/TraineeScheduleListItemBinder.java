package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.TraineeScheduleItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class TraineeScheduleListItemBinder extends ViewBinder<TraineeScheduleItem> {

    public TraineeScheduleListItemBinder() {
        super(R.layout.trainee_schedule_item);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TraineeScheduleListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(TraineeScheduleItem entity, int position, int grpPosition, View view, Activity activity) {

        TraineeScheduleListItemBinder.ViewHolder viewHolder = (TraineeScheduleListItemBinder.ViewHolder) view.getTag();

        viewHolder.txtBookingCol1.setText(entity.getBookingDetailCol1());
        viewHolder.txtBookingCol2.setText(entity.getBookingDetailCol2());

    }



    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtBookingCol1;
        private AnyTextView txtBookingCol2;


        public ViewHolder(View view) {

            txtBookingCol1 = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_1stColumn);
            txtBookingCol2 = (AnyTextView) view.findViewById(R.id.txt_traineeSchedule_2stColumn);

        }
    }
}

