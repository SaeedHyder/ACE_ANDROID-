package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.TrainerAvailDataItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class TraineravaialListItemBinder extends ViewBinder<TrainerAvailDataItem> {
    private ImageLoader imageLoader;
    public TraineravaialListItemBinder() {
        super(R.layout.trainer_avail_listitem);

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(View view) {
        return new TraineravaialListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(TrainerAvailDataItem entity, int position, int grpPosition, View view, Activity activity) {

        TraineravaialListItemBinder.ViewHolder viewHolder = (TraineravaialListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getUserImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());
        viewHolder.txtUserDetail.setText(entity.getUserMessage());

    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txtUserDetail;


        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserName);
            txtUserDetail = (AnyTextView) view.findViewById(R.id.txtUserDetail);


        }
    }
}
