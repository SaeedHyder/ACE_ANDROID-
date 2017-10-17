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

    @InjectView(R.id.cb_Mathematics)
    CheckBox cb_Mathematics;

    @InjectView(R.id.cb_Fitness_health)
    CheckBox cb_Fitness_health;

    @InjectView(R.id.cb_Islamic_studies)
    CheckBox cb_Islamic_studies;

    @InjectView(R.id.cb_English)
    CheckBox cb_English;

    @InjectView(R.id.cb_chemistry)
    CheckBox cb_chemistry;

    @InjectView(R.id.cb_physics)
    CheckBox cb_physics;

    @InjectView(R.id.cb_human_resources)
    CheckBox cb_human_resources;

    @InjectView(R.id.cb_project_managment)
    CheckBox cb_project_managment;

    @InjectView(R.id.cb_biology)
    CheckBox cb_biology;

    @InjectView(R.id.cb_java)
    CheckBox cb_java;

    @InjectView(R.id.cb_graduation_project)
    CheckBox cb_graduation_project;

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
        if (prefHelper.isLanguageArabic()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        setListener();
    }

    private void setListener() {

        btn_training_Search_Submit.setOnClickListener(this);

        cb_Mathematics.setOnCheckedChangeListener(this);
        cb_Fitness_health.setOnCheckedChangeListener(this);
        cb_Islamic_studies.setOnCheckedChangeListener(this);
        cb_English.setOnCheckedChangeListener(this);
        cb_chemistry.setOnCheckedChangeListener(this);
        cb_physics.setOnCheckedChangeListener(this);
        cb_human_resources.setOnCheckedChangeListener(this);
        cb_project_managment.setOnCheckedChangeListener(this);
        cb_biology.setOnCheckedChangeListener(this);
        cb_java.setOnCheckedChangeListener(this);
        cb_graduation_project.setOnCheckedChangeListener(this);

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
                String list_types = StringUtils.join(checkList, ",");
                getDockActivity().addDockableFragment(TrainerAvailListFragment.newInstance(list_types), "TrainerAvaliableFragment");
                break;

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.cb_Mathematics:
                if (!isChecked) {
                    removeOnUncheck(cb_Mathematics.getText().toString());
                } else
                    addCheck(cb_Mathematics.getText().toString());
                break;
            case R.id.cb_Fitness_health:
                if (!isChecked) {
                    removeOnUncheck(cb_Fitness_health.getText().toString());
                } else
                    addCheck(cb_Fitness_health.getText().toString());
                break;
            case R.id.cb_Islamic_studies:
                if (!isChecked) {
                    removeOnUncheck(cb_Islamic_studies.getText().toString());
                } else
                    addCheck(cb_Islamic_studies.getText().toString());
                break;
            case R.id.cb_English:
                if (!isChecked) {
                    removeOnUncheck(cb_English.getText().toString());
                } else
                    addCheck(cb_English.getText().toString());
                break;
            case R.id.cb_chemistry:
                if (!isChecked) {
                    removeOnUncheck(cb_chemistry.getText().toString());
                } else
                    addCheck(cb_chemistry.getText().toString());
                break;
            case R.id.cb_physics:
                if (!isChecked) {
                    removeOnUncheck(cb_physics.getText().toString());
                } else
                    addCheck(cb_physics.getText().toString());
                break;
            case R.id.cb_human_resources:
                if (!isChecked) {
                    removeOnUncheck(cb_human_resources.getText().toString());
                } else
                    addCheck(cb_human_resources.getText().toString());
                break;
            case R.id.cb_project_managment:
                if (!isChecked) {
                    removeOnUncheck(cb_project_managment.getText().toString());
                } else
                    addCheck(cb_project_managment.getText().toString());
                break;
            case R.id.cb_biology:
                if (!isChecked) {
                    removeOnUncheck(cb_biology.getText().toString());
                } else
                    addCheck(cb_biology.getText().toString());
                break;
            case R.id.cb_java:
                if (!isChecked) {
                    removeOnUncheck(cb_java.getText().toString());
                } else
                    addCheck(cb_java.getText().toString());
                break;
            case R.id.cb_graduation_project:
                if (!isChecked) {
                    removeOnUncheck(cb_graduation_project.getText().toString());
                } else
                    addCheck(cb_graduation_project.getText().toString());
                break;


        }
    }

    private void removeOnUncheck(String text) {
        if (checkList.contains(text))
            checkList.remove(checkList.indexOf(text));
    }

    private void addAllCheck() {
       /* addCheck(cb_Flexiblity_tra.getText().toString());
        addCheck(cb_StaticStren.getText().toString());
        addCheck(cb_Dynamic_Streng.getText().toString());
        addCheck(cb_Circuit_Training.getText().toString());
        addCheck(cb_AerobicTraining.getText().toString());
        addCheck(cb_Body_Building.getText().toString());
        addCheck(cb_Lose_Weight.getText().toString());*/
    }

    private void addCheck(String Text) {
        if (!checkList.contains(Text))
            checkList.add(Text);
    }

}
