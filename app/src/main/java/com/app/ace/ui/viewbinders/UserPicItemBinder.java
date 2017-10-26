package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.profilePostEnt;
import com.app.ace.fragments.HomeFragment;
import com.app.ace.fragments.PostImageFragment;
import com.app.ace.fragments.VideoViewFragment;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.interfaces.ImageClickListener;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 3/17/2017.
 */

public class UserPicItemBinder extends ViewBinder<profilePostEnt> {
    ImageClickListener clickListener;
    DockActivity dockActivity;
    private ImageLoader imageLoader;

    public UserPicItemBinder(DockActivity dockActivity,ImageClickListener clickListener) {
        super(R.layout.user_pic_list_item);
        this.dockActivity=dockActivity;
        this.clickListener = clickListener;

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final profilePostEnt picpath, int position, int grpPosition,
                         View view, Activity activity) {


        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (picpath.getPost_image().contains(".mp4"))
        {
            viewHolder.rl_video.setVisibility(View.VISIBLE);
            viewHolder.iv_pic.setVisibility(View.GONE);
            imageLoader.displayImage(picpath.getPost_thumb_image(), viewHolder.iv_video);

            viewHolder.iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  DialogHelper videoDialog=new DialogHelper(dockActivity);
                    videoDialog.playVideo(R.layout.videoplayer_fragment,dockActivity,picpath);
                    videoDialog.showDialog();*/
                    dockActivity.addDockableFragment(VideoViewFragment.newInstance(picpath.getPost_image(),picpath.getPost_thumb_image()), "VideoViewFragment");

                }
            });
        }
        else
        {
            viewHolder.rl_video.setVisibility(View.GONE);
            viewHolder.iv_pic.setVisibility(View.VISIBLE);
            imageLoader.displayImage(picpath.getPost_image(), viewHolder.iv_pic);

            viewHolder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  dockActivity.addDockableFragment(PostImageFragment.newInstance(picpath.getPost_image()), "PostImageFragment");

                    DialogHelper postImage=new DialogHelper(dockActivity);
                    postImage.postImage(R.layout.postimage_dialog,dockActivity,picpath.getPost_image());
                    postImage.showDialog();

                }
            });


        }








    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView iv_pic;
        RelativeLayout rl_video;
        ImageView iv_video;

        public ViewHolder(View view) {
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            rl_video=(RelativeLayout)view.findViewById(R.id.rl_video);
            iv_video=(ImageView)view.findViewById(R.id.iv_video);

        }
    }


}
