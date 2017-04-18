package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 4/3/2017.
 */

public class TrainingSearchFragment extends BaseFragment implements View.OnClickListener{

    @InjectView (R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    public static TrainingSearchFragment newInstance() {

        return new TrainingSearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_training_search, container, false);

        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
    }

    private void setListener() {

        btn_training_Search_Submit.setOnClickListener(this);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.search));

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_training_Search_Submit:

                getDockActivity().addDockableFragment(TrainerAvailListFragment.newInstance(),"TrainerAvaliableFragment");
                break;

        }
    }
}
