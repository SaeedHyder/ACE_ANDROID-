package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.ace.R;
import com.app.ace.entities.YouDataItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouListItemBinder extends ViewBinder<YouDataItem> {
    private ImageLoader imageLoader;

    public YouListItemBinder() {
        super(R.layout.you_listitem);

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(View view) {
        return new YouListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(YouDataItem entity, int position, int grpPosition, View view, Activity activity) {

        YouListItemBinder.ViewHolder viewHolder = (YouListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getUserImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());
        viewHolder.txtUserDetail.setText(entity.getUserMessage());
        viewHolder.imgYou.setImageResource(entity.getYouImage());
        //imageLoader.displayImage(entity.getYouImage(),viewHolder.imgYou);

    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txtUserDetail;
        private ImageView imgYou;


        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserName);
            txtUserDetail = (AnyTextView) view.findViewById(R.id.txtUserDetail);
            imgYou=(ImageView) view.findViewById(R.id.imgYou);


        }
    }
}
