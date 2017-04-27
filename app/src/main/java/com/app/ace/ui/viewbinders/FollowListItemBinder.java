package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;

import com.app.ace.entities.FollowDataItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.ExpandableGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class FollowListItemBinder extends ViewBinder<FollowDataItem> {
    private ImageLoader imageLoader;
    ArrayList<FollowDataItem> Array = new ArrayList<>();
    public FollowListItemBinder() {
        super(R.layout.follow_listitem);

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(View view) {
        return new FollowListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(FollowDataItem entity, int position, int grpPosition, View view, Activity activity) {

        FollowListItemBinder.ViewHolder viewHolder = (FollowListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getUserImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());
        viewHolder.txtUserDetail.setText(entity.getUserMessage());


        CustomeGridViewAdapter adapterCustomeGridView;
        adapterCustomeGridView = new CustomeGridViewAdapter(activity, R.layout.follow_listitem, entity.getImgChildGrid() );
        viewHolder.gridview.setAdapter(adapterCustomeGridView);
        viewHolder.gridview.setExpanded(true);
    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txtUserDetail;

        ExpandableGridView gridview;

        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserName);
            txtUserDetail = (AnyTextView) view.findViewById(R.id.txtUserDetail);

            gridview = (ExpandableGridView) view.findViewById(R.id.gridId);

        }
    }
}
