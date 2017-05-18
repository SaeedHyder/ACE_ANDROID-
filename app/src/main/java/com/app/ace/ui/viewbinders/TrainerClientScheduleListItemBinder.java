package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.Booking;
import com.app.ace.entities.Slot;
import com.app.ace.entities.TrainerClientScheduleItem;
import com.app.ace.fragments.DetailedScreenFragment;
import com.app.ace.fragments.TrainerClientScheduleFragment;
import com.app.ace.retrofit.GsonFactory;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class TrainerClientScheduleListItemBinder extends ViewBinder<Slot> {

    DockActivity context;

    public TrainerClientScheduleListItemBinder(DockActivity context) {

        super(R.layout.trainer_client_schedule_items);
        this.context = context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TrainerClientScheduleListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(Slot entity, int position, int grpPosition, View view, Activity activity) {

        TrainerClientScheduleListItemBinder.ViewHolder viewHolder = (TrainerClientScheduleListItemBinder.ViewHolder) view.getTag();
        if (entity.getBookings()!=null) {
            viewHolder.txtTime.setText(entity.getStartTime() + "-" + entity.getEndTime());

            viewHolder.btnName.setText(entity.getBookings().getUser().getFirst_name()
                    +" "+entity.getBookings().getUser().getLast_name());
            final String slotjson =  GsonFactory.getConfiguredGson().toJson(entity);

       /* viewHolder.showdate.setText(entity.getshowdate());*/

            viewHolder.btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.addDockableFragment(DetailedScreenFragment.newInstance(slotjson), "DetailedScreenFragment");

                }
            });
        }

    }


    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtTime;
        private Button btnName;
       /* private AnyTextView showdate;*/


        public ViewHolder(View view) {

            txtTime = (AnyTextView) view.findViewById(R.id.txtTime);
            btnName = (Button) view.findViewById(R.id.btnName);
           /* showdate= (AnyTextView) view.findViewById(R.id.showdate);*/

        }
    }

}
