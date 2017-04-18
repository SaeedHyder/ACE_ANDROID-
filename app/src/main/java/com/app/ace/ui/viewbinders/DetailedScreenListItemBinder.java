package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.DetailedScreenItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class DetailedScreenListItemBinder extends ViewBinder<DetailedScreenItem> {

    public DetailedScreenListItemBinder() {
        super(R.layout.detailed_screen_items);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new DetailedScreenListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(DetailedScreenItem entity, int position, int grpPosition, View view, Activity activity) {

        DetailedScreenListItemBinder.ViewHolder viewHolder = (DetailedScreenListItemBinder.ViewHolder) view.getTag();

        viewHolder.txtBookingCol1.setText(entity.getBookingDetailCol1());
        viewHolder.txtBookingCol2.setText(entity.getBookingDetailCol2());

    }


    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txtBookingCol1;
        private AnyTextView txtBookingCol2;


        public ViewHolder(View view) {

            txtBookingCol1 = (AnyTextView) view.findViewById(R.id.txt_1stColumn);
            txtBookingCol2 = (AnyTextView) view.findViewById(R.id.txt_2stColumn);

        }
    }

}
