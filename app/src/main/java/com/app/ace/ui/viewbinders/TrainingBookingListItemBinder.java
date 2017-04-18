package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.TrainingBookingCalenderItem;
import com.app.ace.interfaces.TrainingBooking;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;

/**
 * Created by muniyemiftikhar on 4/7/2017.
 */

public class TrainingBookingListItemBinder extends ViewBinder<TrainingBookingCalenderItem> {
    TrainingBooking trainingBooking;




    public TrainingBookingListItemBinder(TrainingBooking trainingBooking) {
        super(R.layout.training_booking_calender_items);

        this.trainingBooking= trainingBooking;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TrainingBookingListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final TrainingBookingCalenderItem entity, int position, int grpPosition, View view, Activity activity) {

        //final ViewHolder viewHolder = (ViewHolder) view.getTag();

        final TrainingBookingListItemBinder.ViewHolder viewHolder = (TrainingBookingListItemBinder.ViewHolder) view.getTag();

        viewHolder.txtDayDate.setText(entity.getTxtDayDate());
        viewHolder.txtTo.setText(entity.getTxtTo());
        viewHolder.txtFrom.setText(entity.getTxtFrom());

        viewHolder.txtIncre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                trainingBooking.textList();

                viewHolder.txtback.setVisibility(View.VISIBLE);

                viewHolder.txtSecTo.setVisibility(View.VISIBLE);
                viewHolder.txtSecTo.setText(entity.getTxtSecTo());
                viewHolder.txtSecFrom.setVisibility(View.VISIBLE);
                viewHolder.txtSecFrom.setText(entity.getTxtSecFrom());

                viewHolder.txtTo.setVisibility(View.GONE);
                viewHolder.txtTo.setText(entity.getTxtTo());
                viewHolder.txtFrom.setVisibility(View.GONE);
                viewHolder.txtFrom.setText(entity.getTxtFrom());

            }
        });

        viewHolder.txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.txtback.setVisibility(View.GONE);
                trainingBooking.backList();

                viewHolder.txtTo.setVisibility(View.VISIBLE);
                viewHolder.txtTo.setText(entity.getTxtTo());
                viewHolder.txtFrom.setVisibility(View.VISIBLE);
                viewHolder.txtFrom.setText(entity.getTxtFrom());

                viewHolder.txtSecTo.setVisibility(View.GONE);
                viewHolder.txtSecTo.setText(entity.getTxtSecTo());
                viewHolder.txtSecFrom.setVisibility(View.GONE);
                viewHolder.txtSecFrom.setText(entity.getTxtSecFrom());
            }
        });

    }


    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtDayDate;
        private AnyTextView txtTo;
        private AnyTextView txtFrom;
        private AnyTextView txtIncre;
        private AnyTextView txtSecTo;
        private AnyTextView txtSecFrom;

        private AnyTextView txtback;


        public ViewHolder(View view) {

            txtDayDate = (AnyTextView) view.findViewById(R.id.txtDayDate);
            txtTo = (AnyTextView) view.findViewById(R.id.txtTo);
            txtFrom = (AnyTextView) view.findViewById(R.id.txtFrom);
            txtIncre = (AnyTextView) view.findViewById(R.id.txtIncre);

            txtSecTo = (AnyTextView) view.findViewById(R.id.txtSecTo);
            txtSecFrom = (AnyTextView) view.findViewById(R.id.txtSecFrom);

            txtback= (AnyTextView) view.findViewById(R.id.txtback);

        }
    }

}
