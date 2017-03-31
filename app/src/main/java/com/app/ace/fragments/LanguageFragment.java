package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;
import com.daimajia.slider.library.Indicators.PagerIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

import roboguice.inject.InjectView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by khan_muhammad on 3/10/2017.
 */

public class LanguageFragment extends BaseFragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener{

    String[] LANGUAGELIST = {"English", "Arabic"};

    @InjectView(R.id.btnDone)
    private Button btnDone;

    @InjectView(R.id.android_material_design_spinner)
    private Spinner materialDesignSpinner;

    public static LanguageFragment newInstance() {

        return new LanguageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add(getString(R.string.english));
        categories.add(getString(R.string.arabic));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        materialDesignSpinner.setAdapter(dataAdapter);
        materialDesignSpinner.setSelection(0);

        setListener();
    }



    private void setListener() {
        btnDone.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnDone:
                getDockActivity().addDockableFragment(WelcomeTutorialFragment.newInstance(), "WelcomeTutorialFragment");
                break;

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}