package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

import static com.app.ace.R.id.cb_AerobicTraining;
import static com.app.ace.R.id.cb_Body_Building;
import static com.app.ace.R.id.cb_Circuit_Training;
import static com.app.ace.R.id.cb_Dynamic_Streng;
import static com.app.ace.R.id.cb_Flexiblity_tra;
import static com.app.ace.R.id.cb_Lose_Weight;
import static com.app.ace.R.id.cb_StaticStren;

/**
 * Created by saeedhyder on 4/3/2017.
 */

public class TrainingSearchFragment extends BaseFragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    @InjectView (R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    @InjectView (R.id.cb_SelectAlll)
    CheckBox cb_SelectAlll;

    @InjectView (R.id.cb_Flexiblity_tra)
    CheckBox cb_Flexiblity_tra;

    @InjectView (R.id.cb_StaticStren)
    CheckBox cb_StaticStren;

    @InjectView (R.id.cb_Dynamic_Streng)
    CheckBox cb_Dynamic_Streng;

    @InjectView (R.id.cb_Circuit_Training)
    CheckBox cb_Circuit_Training;

    @InjectView (R.id.cb_AerobicTraining)
    CheckBox cb_AerobicTraining;

    @InjectView (R.id.cb_Body_Building)
    CheckBox cb_Body_Building;

    @InjectView (R.id.cb_Lose_Weight)
    CheckBox cb_Lose_Weight;

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

        cb_SelectAlll.setOnCheckedChangeListener(this);
        cb_Flexiblity_tra.setOnCheckedChangeListener(this);
        cb_StaticStren.setOnCheckedChangeListener(this);
        cb_Dynamic_Streng.setOnCheckedChangeListener(this);
        cb_Circuit_Training.setOnCheckedChangeListener(this);
        cb_AerobicTraining.setOnCheckedChangeListener(this);
        cb_Body_Building.setOnCheckedChangeListener(this);
        cb_Lose_Weight.setOnCheckedChangeListener(this);

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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){

        case R.id.cb_SelectAlll:


        cb_Flexiblity_tra.setChecked(isChecked);
        cb_StaticStren.setChecked(isChecked);
        cb_Dynamic_Streng.setChecked(isChecked);
        cb_Circuit_Training.setChecked(isChecked);
        cb_AerobicTraining.setChecked(isChecked);
        cb_Body_Building.setChecked(isChecked);
        cb_Lose_Weight.setChecked(isChecked);

        break;

    }
    }
}
