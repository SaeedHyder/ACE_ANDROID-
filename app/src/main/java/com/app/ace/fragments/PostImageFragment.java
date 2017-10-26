package com.app.ace.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 10/23/2017.
 */

public class PostImageFragment extends BaseFragment {

    @InjectView(R.id.photo_view)
    private PhotoView photo_view;

    public static String PICPATH = "pic_path";
    String picPath;
    ImageLoader imageLoader;

    public static PostImageFragment newInstance() {
        return new PostImageFragment();
    }

    public static PostImageFragment newInstance(String picture) {
        Bundle args = new Bundle();
        args.putString(PICPATH, picture);
        PostImageFragment fragment = new PostImageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            picPath = getArguments().getString(PICPATH);

        }
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.fragment_post_image, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        if (prefHelper.isLanguageArabic())
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (picPath != null)
            imageLoader.displayImage(picPath, photo_view);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.image));
    }
}
