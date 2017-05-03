package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.CreatePostEnt;
import com.app.ace.entities.FollowUser;
import com.app.ace.entities.HomeListDataEnt;
import com.app.ace.entities.PostsEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.CommentSectionFragment;
import com.app.ace.fragments.HomeFragment;
import com.app.ace.fragments.SharePopUpfragment;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.IOnLike;
import com.app.ace.retrofit.WebService;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.ace.R.id.gridView;
import static com.app.ace.R.id.txt_no_data;
import static com.app.ace.global.AppConstants.user_id;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class HomeFragmentItemBinder extends ViewBinder<HomeListDataEnt>  {


    private ImageLoader imageLoader;
    private DockActivity context;
    Display display;


    IOnLike IOnLike;


    public HomeFragmentItemBinder(DockActivity context,IOnLike IOnLike) {
        super(R.layout.fragment_home_item);

        this.context = context;

        this.IOnLike = IOnLike;

        imageLoader = ImageLoader.getInstance();
    }



    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new HomeFragmentItemBinder.ViewHolder(view);
    }

     int postId;



    @Override
    public void bindView(final HomeListDataEnt homeListDataEnt, int position, int grpPosition,
                         View view, Activity activity) {

            postId=homeListDataEnt.getId();

        final HomeFragmentItemBinder.ViewHolder viewHolder = (HomeFragmentItemBinder.ViewHolder) view.getTag();

    /*    if(homeListDataEnt.getVideoUrl()!=null)
        {
            viewHolder.vv_post_video.setVisibility(View.VISIBLE);
            viewHolder.iv_post_pic.setVisibility(View.GONE);

            Uri uri=Uri.parse(homeListDataEnt.getVideoUrl());
            viewHolder.vv_post_video.setVideoURI(uri);
            viewHolder.vv_post_video.requestFocus();
            viewHolder.vv_post_video.start();
        }
*/
    if (homeListDataEnt.getProfile_post_pic_path().contains(".mp4"))
    {

        /*RelativeLayout.LayoutParams lv= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lv.addRule(RelativeLayout.CENTER_HORIZONTAL);*/

       // Toast.makeText(context,homeListDataEnt.getProfile_post_pic_path(),Toast.LENGTH_LONG).show();
        viewHolder.vv_post_video.setVisibility(View.VISIBLE);
        viewHolder.iv_post_pic.setVisibility(View.GONE);

        MediaController mediaController= new MediaController(context);
        mediaController.setAnchorView(viewHolder.vv_post_video);


        viewHolder.vv_post_video.setKeepScreenOn(true);
        Uri uri=Uri.parse(homeListDataEnt.getProfile_post_pic_path());
        viewHolder.vv_post_video.setVideoURI(uri);
        viewHolder.vv_post_video.setMediaController(mediaController);
        viewHolder.vv_post_video.requestFocus();
        viewHolder.vv_post_video.start();
        mediaController.hide();


    /*   if (viewHolder.vv_post_video.isScrollContainer())
        {
            viewHolder.vv_post_video.pause();
            mediaController.hide();
        }
*/
    }
    else
    {

        viewHolder.vv_post_video.setVisibility(View.GONE);
        viewHolder.iv_post_pic.setVisibility(View.VISIBLE);
        imageLoader.displayImage(homeListDataEnt.getProfile_post_pic_path(), viewHolder.iv_post_pic, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
        });
    }

        imageLoader.displayImage(homeListDataEnt.getProfile_pic_path(), viewHolder.civ_profile_pic);
        //imageLoader.displayImage(homeListDataEnt.getProfile_post_pic_path(), viewHolder.iv_post_pic);

        viewHolder.txt_profileName.setText(homeListDataEnt.getProfile_name());
        viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes()+" likes");
        viewHolder. txt_commenter_Name.setText(homeListDataEnt.getFriend_name());
        viewHolder.txt_comment.setText(homeListDataEnt.getFriend_comment());
        viewHolder.txt_view_all_comments.setText("View all "+ homeListDataEnt.getTotal_comments()+" comments");

        viewHolder.txt_profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstants.is_show_trainer = true;
                context.addDockableFragment(TrainerProfileFragment.newInstance(homeListDataEnt.getUser_id()), "TrainerProfileFragment");
            }
        });

        viewHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IOnLike.setLikeHit(homeListDataEnt.getId());

            }
        });

        viewHolder.iv_do_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UIHelper.showShortToastInCenter(context,context.getString(R.string.will_be_implemented));
                context.addDockableFragment(CommentSectionFragment.newInstance(homeListDataEnt.getId()), "CommentSectionFragment");
            }
        });


        viewHolder.iv_sendto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UIHelper.showShortToastInCenter(context,context.getString(R.string.will_be_implemented));
                context.addDockableFragment(SharePopUpfragment.newInstance(), "SharePopUpfragment");
            }
        });


        viewHolder.txt_view_all_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.addDockableFragment(CommentSectionFragment.newInstance(homeListDataEnt.getId()), "CommentSectionFragment");

            }
        });

    }



    public static class ViewHolder extends BaseViewHolder {

        private ProgressBar progressBar;

        private CircleImageView civ_profile_pic;
        private ImageView iv_post_pic,iv_like,iv_do_comment,iv_sendto;
        private VideoView vv_post_video;
        AnyTextView txt_profileName,txt_likes_count,txt_commenter_Name,txt_comment,txt_view_all_comments;

        public ViewHolder(View view) {

            progressBar = (ProgressBar) view.findViewById(R.id.progressBar) ;

            civ_profile_pic = (CircleImageView) view.findViewById(R.id.civ_profile_pic);
            iv_post_pic = (ImageView) view.findViewById(R.id.iv_post_pic);
            iv_like = (ImageView) view.findViewById(R.id.iv_like);
            iv_do_comment = (ImageView) view.findViewById(R.id.iv_do_comment);
            iv_sendto = (ImageView) view.findViewById(R.id.iv_sendto);

            txt_profileName = (AnyTextView) view.findViewById(R.id.txt_profileName);
            txt_likes_count = (AnyTextView) view.findViewById(R.id.txt_likes_count);
            txt_commenter_Name = (AnyTextView) view.findViewById(R.id.txt_commenter_Name);
            txt_comment = (AnyTextView) view.findViewById(R.id.txt_comment);
            txt_view_all_comments = (AnyTextView) view.findViewById(R.id.txt_view_all_comments);
            vv_post_video=(VideoView)view.findViewById(R.id.vv_post_video);


        }
    }


}