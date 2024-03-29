package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.HomeListDataEnt;
import com.app.ace.fragments.CommentSectionFragment;
import com.app.ace.fragments.SharePopUpfragment;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.IOnLike;
import com.app.ace.interfaces.SetHomeUpdatedData;
import com.app.ace.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class HomeFragmentItemBinder extends RecyclerViewBinder<HomeListDataEnt> {


    Display display;
    boolean is_liked = true;
    boolean is_liked1 = true;
    int count = 0;
    int x, y = 0;
    int postId;
    SetHomeUpdatedData setHomeUpdatedData;
    BasePreferenceHelper prefHelper;
    IOnLike IOnLike;
    private ImageLoader imageLoader;
    private DockActivity context;
    private boolean isvideoPlaying = false;
    private ViewHolder currentVideoView;

    public HomeFragmentItemBinder(DockActivity context, IOnLike IOnLike, SetHomeUpdatedData setHomeUpdatedData, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_home_item);

        this.context = context;
        this.IOnLike = IOnLike;
        imageLoader = ImageLoader.getInstance();
        this.setHomeUpdatedData = setHomeUpdatedData;
        this.prefHelper = prefHelper;
    }


    @Override
    public BaseViewHolder createViewHolder(
            View view) {
        return new HomeFragmentItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final HomeListDataEnt homeListDataEnt, int position, Object viewsHolder, final Context mcontext) {
        final HomeFragmentItemBinder.ViewHolder viewHolder = (HomeFragmentItemBinder.ViewHolder) viewsHolder;
        postId = homeListDataEnt.getId();
        if (homeListDataEnt.getFollower_post()==1){
            viewHolder.FollowerPost.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.FollowerPost.setVisibility(View.GONE);
        }
        if (homeListDataEnt.getProfile_post_pic_path().contains(".mp4")) {
            imageLoader.displayImage(homeListDataEnt.getVideoThumbnail(), viewHolder.iv_videoThumb);
            viewHolder.vv_post_video.setVisibility(View.GONE);
            viewHolder.iv_post_pic.setVisibility(View.GONE);
            viewHolder.iv_playBtn.setVisibility(View.VISIBLE);
            viewHolder.rl_videoThumb.setVisibility(View.VISIBLE);
            viewHolder.iv_videoThumb.setVisibility(View.VISIBLE);
            viewHolder.vv_post_video.stopPlayback();
            final MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(viewHolder.vv_post_video);
            final Uri uri = Uri.parse(homeListDataEnt.getProfile_post_pic_path());
            viewHolder.vv_post_video.setKeepScreenOn(true);


            viewHolder.iv_playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // viewHolder.vv_post_video.setMediaController(mediaController);
                    //viewHolder.vv_post_video.start();
                    if (currentVideoView != null) {
                        currentVideoView.vv_post_video.stopPlayback();
                        currentVideoView.vv_post_video.setVisibility(View.GONE);
                        currentVideoView.iv_post_pic.setVisibility(View.GONE);
                        currentVideoView.iv_playBtn.setVisibility(View.VISIBLE);
                        currentVideoView.rl_videoThumb.setVisibility(View.VISIBLE);
                        currentVideoView.iv_videoThumb.setVisibility(View.VISIBLE);
                        currentVideoView = null;
                    }
                    if (currentVideoView == null) {
                        currentVideoView = viewHolder;
                        isvideoPlaying = true;
                        viewHolder.iv_playBtn.setVisibility(View.GONE);
                        viewHolder.iv_videoThumb.setVisibility(View.GONE);
                        viewHolder.vv_post_video.setVisibility(View.VISIBLE);
                        viewHolder.vv_post_video.setVideoURI(uri);
                        viewHolder.vv_post_video.start();


                        viewHolder.vv_post_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                isvideoPlaying = false;
                                viewHolder.vv_post_video.setVisibility(View.GONE);
                                viewHolder.iv_post_pic.setVisibility(View.GONE);
                                viewHolder.iv_playBtn.setVisibility(View.VISIBLE);
                                viewHolder.rl_videoThumb.setVisibility(View.VISIBLE);
                                viewHolder.iv_videoThumb.setVisibility(View.VISIBLE);
                                currentVideoView = null;


//                        mp.reset();
                            }
                        });
                    }
                }
            });


        } else {

            viewHolder.vv_post_video.setVisibility(View.GONE);
            viewHolder.iv_post_pic.setVisibility(View.VISIBLE);
            viewHolder.iv_playBtn.setVisibility(View.GONE);
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
        viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes() + " " + context.getResources().getString(R.string.likes));
        viewHolder.txt_view_all_comments.setText(viewHolder.txt_view_all_comments.getContext().getString(R.string.view_all) + " " + homeListDataEnt.getTotal_comments()
                + " " + viewHolder.txt_view_all_comments.getContext().getString(R.string.comments));

        if (homeListDataEnt.getIs_liked().contains("1")) {
            viewHolder.iv_like.setImageResource(R.drawable.heart_icon3);
        } else {
            viewHolder.iv_like.setImageResource(R.drawable.heart_icon2);
        }

        // viewHolder. txt_commenter_Name.setText(homeListDataEnt.getFriend_name());
        if (homeListDataEnt.getCommentsArray().size() > 0) {

            viewHolder.txt_commenter_Name.setVisibility(View.VISIBLE);
            viewHolder.txt_commenter_Name.setPadding(0, 0, 0, 15);
            viewHolder.txt_commenter_Name.setText(homeListDataEnt.getCommentsArray().get(0).getFirst_name() + " " + homeListDataEnt.getCommentsArray().get(0).getLast_name());

            viewHolder.txt_comment.setVisibility(View.VISIBLE);
            viewHolder.txt_comment.setPadding(0, 0, 0, 15);
            viewHolder.txt_comment.setText(homeListDataEnt.getCommentsArray().get(0).getComment_text());
        } else

        {
            viewHolder.txt_commenter_Name.setVisibility(View.GONE);
            viewHolder.txt_commenter_Name.setPadding(0, 0, 0, 0);
            viewHolder.txt_commenter_Name.setText("");

            viewHolder.txt_comment.setPadding(0, 0, 0, 0);
            viewHolder.txt_comment.setVisibility(View.GONE);
            viewHolder.txt_comment.setText("");


        }
        //lastPostComment.setLastCommentOnPost(viewHolder.txt_commenter_Name,viewHolder.txt_comment,homeListDataEnt.getId());

        viewHolder.txt_profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstants.is_show_trainer = true;
                context.addDockableFragment(TrainerProfileFragment.newInstance(homeListDataEnt.getUser_id()), "TrainerProfileFragment");
            }
        });

        viewHolder.civ_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstants.is_show_trainer = true;
                context.addDockableFragment(TrainerProfileFragment.newInstance(homeListDataEnt.getUser_id()), "TrainerProfileFragment");
            }
        });


        viewHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeListDataEnt.getIs_liked().contains("0")) {
                    IOnLike.setLikeHit(homeListDataEnt.getId());
                    homeListDataEnt.setIs_liked("1");
                    homeListDataEnt.setTotoal_likes(homeListDataEnt.getTotoal_likes() + 1);
                    viewHolder.iv_like.setImageResource(R.drawable.heart_icon3);
                    viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes() + " " +
                            context.getResources().getString(R.string.likes));
                  /*  setHomeUpdatedData.setUpdatedData(position, "1", homeListDataEnt.getTotoal_likes() + 1
                            , homeListDataEnt.getTotal_comments());*/

                } else {

                    IOnLike.setLikeHit(homeListDataEnt.getId());
                    homeListDataEnt.setIs_liked("0");
                    homeListDataEnt.setTotoal_likes(homeListDataEnt.getTotoal_likes() - 1);
                    viewHolder.iv_like.setImageResource(R.drawable.heart_icon2);
                    viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes() + " " +
                            context.getResources().getString(R.string.likes));
                    //setHomeUpdatedData.setUpdatedData(position, "0", homeListDataEnt.getTotoal_likes() - 1, homeListDataEnt.getTotal_comments());

                }


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
                if (homeListDataEnt.getProfile_post_pic_path().contains(".mp4")) {
                    UIHelper.showShortToastInCenter(context, "Video Cant be Shared");
                } else {
                    context.addDockableFragment(SharePopUpfragment.newInstance(homeListDataEnt.getProfile_post_pic_path()), "SharePopUpfragment");
                }

            }
        });


        viewHolder.txt_view_all_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.addDockableFragment(CommentSectionFragment.newInstance(homeListDataEnt.getId()), "CommentSectionFragment");

            }
        });

    }


 /*   @Override
    public void bindView(final HomeListDataEnt homeListDataEnt, final int position, int grpPosition,
                         View view, final Activity activity) {

        postId = homeListDataEnt.getId();

        final HomeFragmentItemBinder.ViewHolder viewHolder = (HomeFragmentItemBinder.ViewHolder) view.getTag();


        if (homeListDataEnt.getProfile_post_pic_path().contains(".mp4")) {
            imageLoader.displayImage(homeListDataEnt.getVideoThumbnail(), viewHolder.iv_videoThumb);
            viewHolder.vv_post_video.setVisibility(View.GONE);
            viewHolder.iv_post_pic.setVisibility(View.GONE);
            viewHolder.iv_playBtn.setVisibility(View.VISIBLE);
            viewHolder.rl_videoThumb.setVisibility(View.VISIBLE);
            viewHolder.iv_videoThumb.setVisibility(View.VISIBLE);
            viewHolder.vv_post_video.stopPlayback();
            final MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(viewHolder.vv_post_video);
            final Uri uri = Uri.parse(homeListDataEnt.getProfile_post_pic_path());
            viewHolder.vv_post_video.setKeepScreenOn(true);


            viewHolder.iv_playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // viewHolder.vv_post_video.setMediaController(mediaController);
                    //viewHolder.vv_post_video.start();
                    if (currentVideoView != null) {
                        currentVideoView.vv_post_video.stopPlayback();
                        currentVideoView.vv_post_video.setVisibility(View.GONE);
                        currentVideoView.iv_post_pic.setVisibility(View.GONE);
                        currentVideoView.iv_playBtn.setVisibility(View.VISIBLE);
                        currentVideoView.rl_videoThumb.setVisibility(View.VISIBLE);
                        currentVideoView.iv_videoThumb.setVisibility(View.VISIBLE);
                        currentVideoView = null;
                    }
                    if (currentVideoView == null) {
                        currentVideoView = viewHolder;
                        isvideoPlaying = true;
                        viewHolder.iv_playBtn.setVisibility(View.GONE);
                        viewHolder.iv_videoThumb.setVisibility(View.GONE);
                        viewHolder.vv_post_video.setVisibility(View.VISIBLE);
                        viewHolder.vv_post_video.setVideoURI(uri);
                        viewHolder.vv_post_video.start();


                        viewHolder.vv_post_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                isvideoPlaying = false;
                                viewHolder.vv_post_video.setVisibility(View.GONE);
                                viewHolder.iv_post_pic.setVisibility(View.GONE);
                                viewHolder.iv_playBtn.setVisibility(View.VISIBLE);
                                viewHolder.rl_videoThumb.setVisibility(View.VISIBLE);
                                viewHolder.iv_videoThumb.setVisibility(View.VISIBLE);
                                currentVideoView = null;


//                        mp.reset();
                            }
                        });
                    }
                }
            });


        } else {

            viewHolder.vv_post_video.setVisibility(View.GONE);
            viewHolder.iv_post_pic.setVisibility(View.VISIBLE);
            viewHolder.iv_playBtn.setVisibility(View.GONE);
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
        viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes() + " " + activity.getString(R.string.likes));
        viewHolder.txt_view_all_comments.setText(viewHolder.txt_view_all_comments.getContext().getString(R.string.view_all) + " " + homeListDataEnt.getTotal_comments()
                + " " + viewHolder.txt_view_all_comments.getContext().getString(R.string.comments));

        if (homeListDataEnt.getIs_liked().contains("1")) {
            viewHolder.iv_like.setImageResource(R.drawable.heart_icon3);
        } else {
            viewHolder.iv_like.setImageResource(R.drawable.heart_icon2);
        }

        // viewHolder. txt_commenter_Name.setText(homeListDataEnt.getFriend_name());
        if (homeListDataEnt.getCommentsArray().size() > 0) {

            viewHolder.txt_commenter_Name.setVisibility(View.VISIBLE);
            viewHolder.txt_commenter_Name.setPadding(0, 0, 0, 15);
            viewHolder.txt_commenter_Name.setText(homeListDataEnt.getCommentsArray().get(0).getFirst_name() + " " + homeListDataEnt.getCommentsArray().get(0).getLast_name());

            viewHolder.txt_comment.setVisibility(View.VISIBLE);
            viewHolder.txt_comment.setPadding(0, 0, 0, 15);
            viewHolder.txt_comment.setText(homeListDataEnt.getCommentsArray().get(0).getComment_text());
        } else

        {
            viewHolder.txt_commenter_Name.setVisibility(View.GONE);
            viewHolder.txt_commenter_Name.setPadding(0, 0, 0, 0);
            viewHolder.txt_commenter_Name.setText("");

            viewHolder.txt_comment.setPadding(0, 0, 0, 0);
            viewHolder.txt_comment.setVisibility(View.GONE);
            viewHolder.txt_comment.setText("");


        }
        //lastPostComment.setLastCommentOnPost(viewHolder.txt_commenter_Name,viewHolder.txt_comment,homeListDataEnt.getId());

        viewHolder.txt_profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstants.is_show_trainer = true;
                context.addDockableFragment(TrainerProfileFragment.newInstance(homeListDataEnt.getUser_id()), "TrainerProfileFragment");
            }
        });

        viewHolder.civ_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstants.is_show_trainer = true;
                context.addDockableFragment(TrainerProfileFragment.newInstance(homeListDataEnt.getUser_id()), "TrainerProfileFragment");
            }
        });


        viewHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeListDataEnt.getIs_liked().contains("0")) {
                    IOnLike.setLikeHit(homeListDataEnt.getId());
                    homeListDataEnt.setIs_liked("1");
                    homeListDataEnt.setTotoal_likes(homeListDataEnt.getTotoal_likes() + 1);
                    viewHolder.iv_like.setImageResource(R.drawable.heart_icon3);
                    viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes() + " " +
                            activity.getString(R.string.likes));
                  *//*  setHomeUpdatedData.setUpdatedData(position, "1", homeListDataEnt.getTotoal_likes() + 1
                            , homeListDataEnt.getTotal_comments());*//*

                } else {

                    IOnLike.setLikeHit(homeListDataEnt.getId());
                    homeListDataEnt.setIs_liked("0");
                    homeListDataEnt.setTotoal_likes(homeListDataEnt.getTotoal_likes() - 1);
                    viewHolder.iv_like.setImageResource(R.drawable.heart_icon2);
                    viewHolder.txt_likes_count.setText(homeListDataEnt.getTotoal_likes() + " " +
                            activity.getString(R.string.likes));
                    //setHomeUpdatedData.setUpdatedData(position, "0", homeListDataEnt.getTotoal_likes() - 1, homeListDataEnt.getTotal_comments());

                }


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
                if (homeListDataEnt.getProfile_post_pic_path().contains(".mp4")) {
                    UIHelper.showShortToastInCenter(context, "Video Cant be Shared");
                } else {
                    context.addDockableFragment(SharePopUpfragment.newInstance(homeListDataEnt.getProfile_post_pic_path()), "SharePopUpfragment");
                }

            }
        });


        viewHolder.txt_view_all_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.addDockableFragment(CommentSectionFragment.newInstance(homeListDataEnt.getId()), "CommentSectionFragment");

            }
        });


    }*/


    public static class ViewHolder extends BaseViewHolder {

        AnyTextView txt_profileName, txt_likes_count, txt_commenter_Name, txt_comment, txt_view_all_comments;
        RelativeLayout rl_videoThumb;
        ImageView iv_videoThumb;
        private ProgressBar progressBar;
        private CircleImageView civ_profile_pic;
        private ImageView iv_post_pic, iv_like, iv_do_comment, iv_sendto;
        private VideoView vv_post_video;
        private ImageView iv_playBtn;
        private ImageView FollowerPost;

        public ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            civ_profile_pic = (CircleImageView) view.findViewById(R.id.civ_profile_pic);
            iv_post_pic = (ImageView) view.findViewById(R.id.iv_post_pic);
            iv_like = (ImageView) view.findViewById(R.id.iv_like);
            iv_do_comment = (ImageView) view.findViewById(R.id.iv_do_comment);
            iv_sendto = (ImageView) view.findViewById(R.id.iv_sendto);

            txt_profileName = (AnyTextView) view.findViewById(R.id.txt_profileName);
            txt_likes_count = (AnyTextView) view.findViewById(R.id.txt_likes_count);
            txt_view_all_comments = (AnyTextView) view.findViewById(R.id.txt_view_all_comments);
            vv_post_video = (VideoView) view.findViewById(R.id.vv_post_video);

            txt_commenter_Name = (AnyTextView) view.findViewById(R.id.txt_commenter_Name);
            txt_comment = (AnyTextView) view.findViewById(R.id.txt_comment);

            iv_playBtn = (ImageView) view.findViewById(R.id.iv_playBtn);
            rl_videoThumb = (RelativeLayout) view.findViewById(R.id.rl_videoThumb);
            iv_videoThumb = (ImageView) view.findViewById(R.id.iv_videoThumb);
            FollowerPost = (ImageView)view.findViewById(R.id.img_follower_post);

        }
    }


}