package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.SpecialityEnt;
import com.app.ace.entities.SpecialityResultEnt;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.specialityItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.ExpandableGridView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;


/**
 * Created by saeedhyder on 4/3/2017.
 */

public class TrainingSearchFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.btn_training_Search_Submit)
    Button btn_training_Search_Submit;

    @InjectView(R.id.mainframe)
    LinearLayout mainframe;

    @InjectView(R.id.gv_speciality)
    private ExpandableGridView gv_speciality;

    @InjectView(R.id.txt_no_data)
    AnyTextView txt_no_data;

    ArrayList<String> checkList;
    String specialityIDS = "";

    private ArrayListAdapter<SpecialityEnt> adapter;
    private List<SpecialityEnt> userCollection = new ArrayList<>();

    public static TrainingSearchFragment newInstance() {

        return new TrainingSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<SpecialityEnt>(getDockActivity(), new specialityItemBinder(getDockActivity()));
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
        mainframe.setVisibility(View.GONE);
        if (prefHelper.isLanguageArabic()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        getSpecialityData();
        setListener();
    }

    private void getSpecialityData() {
        loadingStarted();

        Call<ResponseWrapper<SpecialityResultEnt>> call = webService.specialityData(getMainActivity().selectedLanguage());

        call.enqueue(new Callback<ResponseWrapper<SpecialityResultEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SpecialityResultEnt>> call, Response<ResponseWrapper<SpecialityResultEnt>> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    loadingFinished();

                if (response.body().getUserDeleted() == 0) {
                    loadingFinished();
                    setSpecialityData(response.body().getResult().getSpecialities());

                } else {
                    final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                    dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogHelper.hideDialog();
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        }
                    },response.body().getMessage());
                    dialogHelper.showDialog();
                }}
                else
                {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SpecialityResultEnt>> call, Throwable t) {
                loadingFinished();
                Log.e("TrainingSeaarchFragment", t.toString());
            }
        });

    }

    private void setSpecialityData(List<SpecialityEnt> result) {

        mainframe.setVisibility(View.VISIBLE);

        userCollection = new ArrayList<>();
        userCollection.addAll(result);

        if (userCollection.size() <= 0) {
            txt_no_data.setVisibility(View.VISIBLE);
            gv_speciality.setVisibility(View.GONE);
        } else {
            txt_no_data.setVisibility(View.GONE);
            gv_speciality.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        adapter.addAll(userCollection);
        gv_speciality.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

        switch (view.getId()) {
            case R.id.btn_training_Search_Submit:

                StringBuilder stringBuilder = new StringBuilder();
                for (SpecialityEnt ent : userCollection) {
                    if (ent.isIschecked()) {
                        stringBuilder.append(ent.getId());
                        stringBuilder.append(",");
                    }
                }
                specialityIDS = stringBuilder.toString();
                specialityIDS = specialityIDS.substring(0, specialityIDS.length() - 1);


                if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    getDockActivity().addDockableFragment(TrainerAvailListFragment.newInstance(specialityIDS), "TrainerAvaliableFragment");
                }
                break;

        }

    }

  /*  @Override
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
    }*/

  /*  private void addAllCheck() {
       *//* addCheck(cb_Flexiblity_tra.getText().toString());
        addCheck(cb_StaticStren.getText().toString());
        addCheck(cb_Dynamic_Streng.getText().toString());
        addCheck(cb_Circuit_Training.getText().toString());
        addCheck(cb_AerobicTraining.getText().toString());
        addCheck(cb_Body_Building.getText().toString());
        addCheck(cb_Lose_Weight.getText().toString());*//*
    }*/

/*
    private void addCheck(String Text) {
        if (!checkList.contains(Text))
            checkList.add(Text);
    }
*/

}
