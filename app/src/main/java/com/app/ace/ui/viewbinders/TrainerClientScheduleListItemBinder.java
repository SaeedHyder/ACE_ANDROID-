package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.TrainerClientScheduleItem;
import com.app.ace.fragments.DetailedScreenFragment;
import com.app.ace.fragments.TrainerClientScheduleFragment;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class TrainerClientScheduleListItemBinder extends ViewBinder<TrainerClientScheduleItem> {

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
    public void bindView(TrainerClientScheduleItem entity, int position, int grpPosition, View view, Activity activity) {

        TrainerClientScheduleListItemBinder.ViewHolder viewHolder = (TrainerClientScheduleListItemBinder.ViewHolder) view.getTag();

        viewHolder.txtTime.setText(entity.gettxtTime());
        viewHolder.btnName.setText(entity.gebtnName());
       /* viewHolder.showdate.setText(entity.getshowdate());*/

        viewHolder.btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(DetailedScreenFragment.newInstance(), "DetailedScreenFragment");

            }
        });

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
