package com.app.ace.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.app.ace.R;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/10/2017.
 */

public class WelcomeTutorialFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    @InjectView(R.id.pagerIndicator)
    private PagerIndicator pagerIndicator;

    @InjectView(R.id.imageSlider)
    private SliderLayout imageSlider;

    @InjectView(R.id.btnStart)
    private Button btnStart;

    @InjectView(R.id.iv_text)
    private ImageView iv_text;



    public static WelcomeTutorialFragment newInstance() {

        return new WelcomeTutorialFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welocme_tutorial, container, false);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setImageGallery();
        setListener();
    }

    private void setListener() {
        btnStart.setOnClickListener(this);
    }

    private void setImageGallery() {

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(0, R.drawable.tutorial_bg_1);
        arrayList.add(1, R.drawable.tutorial_bg_2);
        arrayList.add(2, R.drawable.tutorial_bg_3);
        arrayList.add(3, R.drawable.tutorial_bg_4);
        arrayList.add(4, R.drawable.tutorial_bg_5);

        for (Integer image : arrayList) {
            DefaultSliderView textSliderView = new DefaultSliderView(getDockActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(image)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", image+"");

            imageSlider.addSlider(textSliderView);
        }

        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.addOnPageChangeListener(this);
        imageSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        imageSlider.stopAutoCycle();

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){

            case 0:
                iv_text.setImageResource(R.drawable.welcome1);
                break;

            case 1:
                iv_text.setImageResource(R.drawable.welcome2);
                break;

            case 2:
                iv_text.setImageResource(R.drawable.welcome3);
                break;

            case 3:
                iv_text.setImageResource(R.drawable.welcome4);
                break;

            case 4:
                iv_text.setImageResource(R.drawable.welcome5);
                break;

        }

        if(position == 4){
            btnStart.setVisibility(View.VISIBLE);
        }else{
            btnStart.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnStart:
                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;
        }

    }
}