package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.YouDataItem;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.interfaces.FollowService;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.ace.R.id.btn;
import static com.app.ace.R.id.btn_Unfollow;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouListItemBinder extends ViewBinder<YouDataItem> {
    private ImageLoader imageLoader;

    FollowService followService;
    DockActivity context;

    public YouListItemBinder(FollowService followService,DockActivity context) {
        super(R.layout.you_listitem);
        this.followService=followService;
        this.context=context;

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(View view) {
        return new YouListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final YouDataItem entity, final int position, int grpPosition, View view, Activity activity) {

        final YouListItemBinder.ViewHolder viewHolder = (YouListItemBinder.ViewHolder) view.getTag();

        String[] SplitTime=entity.getTime().split(" ");
        String time=SplitTime[1];

        imageLoader.displayImage(entity.getUserImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());
        viewHolder.txtUserDetail.setText(entity.getUserMessage());
        viewHolder.txtTime.setText(time);
        if(entity.getYouImage()==null)
        {
            if(entity.getIsfollowing().contains("0"))
            {
                viewHolder.btn_follow.setVisibility(View.VISIBLE);
                viewHolder.btn_Unfollow.setVisibility(View.GONE);
                viewHolder.imgYou.setVisibility(View.GONE);
            }
            else
            {
                viewHolder.btn_follow.setVisibility(View.GONE);
                viewHolder.btn_Unfollow.setVisibility(View.VISIBLE);
                viewHolder.imgYou.setVisibility(View.GONE);
            }

          //  viewHolder.btn_follow.setVisibility(View.VISIBLE);


        }
        else {
            viewHolder.btn_follow.setVisibility(View.GONE);
            viewHolder.btn_Unfollow.setVisibility(View.GONE);
            viewHolder.imgYou.setVisibility(View.VISIBLE);
            imageLoader.displayImage(entity.getYouImage(), viewHolder.imgYou);
        }

        viewHolder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.btn_follow.setVisibility(View.GONE);
                viewHolder.btn_Unfollow.setVisibility(View.VISIBLE);

                followService.followUser(entity.getSenderId(),position,1);
            }
        });

        viewHolder.btn_Unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.btn_Unfollow.setVisibility(View.GONE);
                viewHolder.btn_follow.setVisibility(View.VISIBLE);

                followService.UnFollowUser(entity.getSenderId(),position,0);
            }
        });

        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(TrainerProfileFragment.newInstance(Integer.parseInt(entity.getSenderId())), "TrainerProfileFragment");
            }
        });




        //imageLoader.displayImage(entity.getYouImage(),viewHolder.imgYou);

    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txtUserDetail;
        private AnyTextView txtTime;
        private ImageView imgYou;
        private Button btn_follow;
        private Button btn_Unfollow;



        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserName);
            txtUserDetail = (AnyTextView) view.findViewById(R.id.txtUserDetail);
            imgYou=(ImageView) view.findViewById(R.id.imgYou);
            txtTime=(AnyTextView) view.findViewById(R.id.txtTime);
            btn_follow=(Button)view.findViewById(R.id.btn_follow);
            btn_Unfollow=(Button)view.findViewById(R.id.btn_Unfollow);


        }
    }
}
