package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.FollowersCountDataItem;

import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by muniyemiftikhar on 5/2/2017.
 */

public class FollowersCountListBinder extends ViewBinder<FollowersCountDataItem> {
    private ImageLoader imageLoader;

    DockActivity context;

    public FollowersCountListBinder(DockActivity context) {
        super(R.layout.followers_count_listitem);

        this.context=context;

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(View view) {
        return new FollowersCountListBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final FollowersCountDataItem entity, int position, int grpPosition, View view, Activity activity) {

        FollowersCountListBinder.ViewHolder viewHolder = (FollowersCountListBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getUserImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());

        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(TrainerProfileFragment.newInstance(entity.getId()), "TrainerProfileFragment");
            }
        });

        viewHolder.txtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(TrainerProfileFragment.newInstance(entity.getId()), "TrainerProfileFragment");
            }
        });


    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;


        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImageFollowers);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserNameFollowers);



        }
    }
}
