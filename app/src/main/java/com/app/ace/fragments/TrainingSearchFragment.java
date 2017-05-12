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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 4/3/2017.
 */

public class TrainingSearchFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    @InjectView(R.id.cb_SelectAlll)
    CheckBox cb_SelectAlll;

    @InjectView(R.id.cb_Flexiblity_tra)
    CheckBox cb_Flexiblity_tra;

    @InjectView(R.id.cb_StaticStren)
    CheckBox cb_StaticStren;

    @InjectView(R.id.cb_Dynamic_Streng)
    CheckBox cb_Dynamic_Streng;

    @InjectView(R.id.cb_Circuit_Training)
    CheckBox cb_Circuit_Training;

    @InjectView(R.id.cb_AerobicTraining)
    CheckBox cb_AerobicTraining;

    @InjectView(R.id.cb_Body_Building)
    CheckBox cb_Body_Building;

    @InjectView(R.id.cb_Lose_Weight)
    CheckBox cb_Lose_Weight;

    ArrayList<String> checkList;

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
        checkList = new ArrayList<>();
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

        switch (view.getId()) {
            case R.id.btn_training_Search_Submit:
                String list_types = StringUtils.join(checkList,",");
                getDockActivity().addDockableFragment(TrainerAvailListFragment.newInstance(list_types), "TrainerAvaliableFragment");
                break;

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.cb_SelectAlll:
                cb_Flexiblity_tra.setChecked(isChecked);
                cb_StaticStren.setChecked(isChecked);
                cb_Dynamic_Streng.setChecked(isChecked);
                cb_Circuit_Training.setChecked(isChecked);
                cb_AerobicTraining.setChecked(isChecked);
                cb_Body_Building.setChecked(isChecked);
                cb_Lose_Weight.setChecked(isChecked);
                addAllCheck();
                break;
            case R.id.cb_Flexiblity_tra:
                if (!isChecked) {
                    removeOnUncheck(cb_Flexiblity_tra.getText().toString());
                } else
                    addCheck(cb_Flexiblity_tra.getText().toString());
                break;
            case R.id.cb_StaticStren:
                if (!isChecked) {
                    removeOnUncheck(cb_StaticStren.getText().toString());
                } else
                    addCheck(cb_StaticStren.getText().toString());
                break;
            case R.id.cb_Dynamic_Streng:
                if (!isChecked) {
                    removeOnUncheck(cb_Dynamic_Streng.getText().toString());
                } else
                    addCheck(cb_Dynamic_Streng.getText().toString());
                break;
            case R.id.cb_Circuit_Training:
                if (!isChecked) {
                    removeOnUncheck(cb_Circuit_Training.getText().toString());
                } else
                    addCheck(cb_Circuit_Training.getText().toString());
                break;
            case R.id.cb_AerobicTraining:
                if (!isChecked) {
                    removeOnUncheck(cb_AerobicTraining.getText().toString());
                } else
                    addCheck(cb_AerobicTraining.getText().toString());
                break;
            case R.id.cb_Body_Building:
                if (!isChecked) {
                    removeOnUncheck(cb_Body_Building.getText().toString());
                } else
                    addCheck(cb_Body_Building.getText().toString());
                break;
            case R.id.cb_Lose_Weight:
                if (!isChecked) {
                    removeOnUncheck(cb_Lose_Weight.getText().toString());
                } else
                    addCheck(cb_Lose_Weight.getText().toString());
                break;


        }
    }
    private void removeOnUncheck(String text){
        if (checkList.contains(text))
            checkList.remove(checkList.indexOf(text));
    }
    private void addAllCheck() {
        addCheck(cb_Flexiblity_tra.getText().toString());
        addCheck(cb_StaticStren.getText().toString());
        addCheck(cb_Dynamic_Streng.getText().toString());
        addCheck(cb_Circuit_Training.getText().toString());
        addCheck(cb_AerobicTraining.getText().toString());
        addCheck(cb_Body_Building.getText().toString());
        addCheck(cb_Lose_Weight.getText().toString());
    }

    private void addCheck(String Text) {
        if (!checkList.contains(Text))
            checkList.add(Text);
    }

}
