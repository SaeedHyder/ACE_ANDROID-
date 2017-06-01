package com.app.ace.fragments;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

import static com.app.ace.global.AppConstants.user_id;

/**
 * Created by saeedhyder on 5/30/2017.
 */

public class VideoViewFragment extends BaseFragment {

    @InjectView (R.id.vv_post_video)
    VideoView vv_post_video;
    public static String PICPATH = "pic_path";
    String picPath;
    public static String VIDEOTHUMBPATH = "video_thumb";
    String videoThumb;

    public static VideoViewFragment newInstance() {

        return new VideoViewFragment();
    }

    public static VideoViewFragment newInstance(String picpath, String post_thumb_image) {

        Bundle args = new Bundle();
        args.putString(PICPATH, String.valueOf(picpath));
        args.putString(VIDEOTHUMBPATH,post_thumb_image);
        VideoViewFragment fragment = new VideoViewFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            picPath = getArguments().getString(PICPATH);
            videoThumb=getArguments().getString(VIDEOTHUMBPATH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.videoplayer_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showVideo();
    }

    private void showVideo() {

        loadingStarted();
        final MediaController mediaController= new MediaController(getDockActivity());
        mediaController.setAnchorView(vv_post_video);
        final Uri uri=Uri.parse(picPath);
        vv_post_video.setKeepScreenOn(true);
        vv_post_video.setVideoURI(uri);
        vv_post_video.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
        vv_post_video.start();

        vv_post_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        vv_post_video.setBackgroundColor(Color.TRANSPARENT);
                         loadingFinished();
                       vv_post_video.setMediaController(mediaController);
                        mp.start();
                       // mediaController.show();

                    }
                });
            }
        });
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Video");

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }
}
