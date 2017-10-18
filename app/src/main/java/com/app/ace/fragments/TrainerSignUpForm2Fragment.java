package com.app.ace.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.app.ace.R;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.global.SignupFormConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.DateHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.TokenUpdater;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.IGetLocation;
import com.app.ace.ui.dialogs.DialogFactory;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/13/2017.
 */

public class TrainerSignUpForm2Fragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, MainActivity.ImageSetter, IGetLocation {


    @InjectView(R.id.btnSignUp)
    private Button btnSignUp;


    @InjectView(R.id.edtPrimaryReason)
    private AnyEditTextView edtPrimaryReason;

    @InjectView(R.id.edtUniversity)
    private AnyEditTextView edtUniversity;

    @InjectView(R.id.txt_from)
    private AnyTextView txt_from;

    @InjectView(R.id.txt_to)
    private AnyTextView txt_to;

    @InjectView(R.id.txt_Location)
    private AnyTextView txt_Location;

    @InjectView(R.id.txt_browse_cv)
    private AnyTextView txt_browse_cv;


    //Education
    @InjectView(R.id.cb_Mathematics)
    private CheckBox cb_Mathematics;

    @InjectView(R.id.cb_Fitness_health)
    private CheckBox cb_Fitness_health;

    @InjectView(R.id.cb_Islamic_studies)
    private CheckBox cb_Islamic_studies;

    @InjectView(R.id.cb_english)
    private CheckBox cb_english;

    @InjectView(R.id.cb_chemistry)
    private CheckBox cb_chemistry;

    @InjectView(R.id.cb_physics)
    private CheckBox cb_physics;

    @InjectView(R.id.cb_human_resources)
    private CheckBox cb_human_resources;

    @InjectView(R.id.cb_project_managment)
    private CheckBox cb_project_managment;

    @InjectView(R.id.cb_biology)
    private CheckBox cb_biology;

    @InjectView(R.id.cb_java)
    private CheckBox cb_java;

    @InjectView(R.id.cb_graduation_project)
    private CheckBox cb_graduation_project;

    @InjectView(R.id.sp_Gender)
    private Spinner sp_Gender;

    @InjectView(R.id.cb_sun)
    private CheckBox cb_sun;

    @InjectView(R.id.cb_mon)
    private CheckBox cb_mon;

    @InjectView(R.id.cb_tue)
    private CheckBox cb_tue;

    @InjectView(R.id.cb_wed)
    private CheckBox cb_wed;

    @InjectView(R.id.cb_thur)
    private CheckBox cb_thur;

    @InjectView(R.id.cb_fri)
    private CheckBox cb_fri;

    @InjectView(R.id.cb_sat)
    private CheckBox cb_sat;

    //Years of Exp
    @InjectView(R.id.cb_less_then_1)
    private CheckBox cb_less_then_1;

    @InjectView(R.id.cb_1_to_5_years)
    private CheckBox cb_1_to_5_years;


    @InjectView(R.id.cb_5_years_or_more)
    private CheckBox cb_5_years_or_more;

    public File resume;
    public String resumePath;
    String gymLocation = "";
    String lat;
    String log;


    public String Education = "", Speciality = "", Years_of_Exp = "", Gym_days = "", gym_time_from = "", gym_time_to = "";

    ArrayList<String> TrainerSpeciality = new ArrayList<>();
    ArrayList<String> SpecialityArray = new ArrayList<>();
    ArrayList<String> GymDaysArray = new ArrayList<>();

    public static String SIGNUP_MODEL = "signup_model";
    public SignupFormConstants signupFormConstants;

    public static TrainerSignUpForm2Fragment newInstance(SignupFormConstants signupFormConstants) {

        Bundle args = new Bundle();
        args.putString(SIGNUP_MODEL, new Gson().toJson(signupFormConstants));
        TrainerSignUpForm2Fragment fragment = new TrainerSignUpForm2Fragment();
        fragment.setArguments(args);

        return fragment;
    }

   /* public static TrainerSignUpForm2Fragment newInstance() {

        return new TrainerSignUpForm2Fragment();
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsonString = getArguments().getString(SIGNUP_MODEL);
        if (jsonString != null)
            signupFormConstants = new Gson().fromJson(jsonString, SignupFormConstants.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.fragment_signup_form2, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        btnSignUp.setText(getString(R.string.signup));

        setListeners();

        spGender();

        TrainerSpeciality = new ArrayList<>();
    }

    private void spGender() {
        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add(getString(R.string.male));
        categories.add(getString(R.string.femaile));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_Gender.setAdapter(dataAdapter);
        sp_Gender.setSelection(0);
    }

    private void setListeners() {
        btnSignUp.setOnClickListener(this);
        txt_from.setOnClickListener(this);
        txt_to.setOnClickListener(this);
        txt_Location.setOnClickListener(this);
        txt_browse_cv.setOnClickListener(this);

        cb_Mathematics.setOnCheckedChangeListener(this);
        cb_Fitness_health.setOnCheckedChangeListener(this);
        cb_Islamic_studies.setOnCheckedChangeListener(this);
        cb_english.setOnCheckedChangeListener(this);
        cb_chemistry.setOnCheckedChangeListener(this);
        cb_physics.setOnCheckedChangeListener(this);
        cb_human_resources.setOnCheckedChangeListener(this);
        cb_project_managment.setOnCheckedChangeListener(this);
        cb_biology.setOnCheckedChangeListener(this);
        cb_java.setOnCheckedChangeListener(this);
        cb_graduation_project.setOnCheckedChangeListener(this);


        cb_less_then_1.setOnCheckedChangeListener(this);
        cb_1_to_5_years.setOnCheckedChangeListener(this);
        cb_5_years_or_more.setOnCheckedChangeListener(this);

        cb_sun.setOnCheckedChangeListener(this);
        cb_mon.setOnCheckedChangeListener(this);
        cb_tue.setOnCheckedChangeListener(this);
        cb_wed.setOnCheckedChangeListener(this);
        cb_thur.setOnCheckedChangeListener(this);
        cb_fri.setOnCheckedChangeListener(this);
        cb_sat.setOnCheckedChangeListener(this);

        getMainActivity().setImageSetter(this);

        edtPrimaryReason.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);

        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeadingWithDiscription(getDockActivity().getResources().getString(R.string.education_and_cirtification), getDockActivity().getResources().getString(R.string.please_select), getDockActivity());

    }

    private void openFromTimePickerDialog(final AnyTextView txtview, final AnyTextView startTime) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime = "";
                finaltime = selectedHour + ":" + selectedMinute;

                String[] startHoursarray = startTime.getText().toString().split(":");
                String startHour = startHoursarray[0];
                String startMinutes = startHoursarray[1];


                if (!(selectedHour <= Integer.parseInt(startHour) && selectedMinute <= Integer.parseInt(startMinutes))) {
                    txtview.setText(selectedHour + ":" + selectedMinute);

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.end_time_should));
                }


                if (finaltime != null) {
                    gym_time_to = finaltime;

                    if (gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

                        try {
                            String[] time_from = gym_time_from.split(":");
                            String[] time_to = gym_time_to.split(":");

                            int StartHour = Integer.parseInt(time_from[0]);
                            int StartMin = Integer.parseInt(time_from[1]);
                            int EndHour = Integer.parseInt(time_to[0]);
                            int EndMin = Integer.parseInt(time_to[1]);

                            if (!DateHelper.isTimeAfter(StartHour, StartMin, EndHour, EndMin)) {
                                txtview.setText("");
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.end_time_should));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }, hour, minute, true);//Yes 24 hour time


        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

  /*  private void openFromTimePickerDialog(final AnyTextView txtview) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime = "";
                txtview.setText(selectedHour + ":" + selectedMinute);

                finaltime = selectedHour + ":" + selectedMinute;
                if (finaltime != null) {
                    gym_time_from = finaltime;

                    if (gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

                        try {
                            String[] time_from = gym_time_from.split(":");
                            String[] time_to = gym_time_to.split(":");

                            int StartHour = Integer.parseInt(time_from[0]);
                            int StartMin = Integer.parseInt(time_from[1]);
                            int EndHour = Integer.parseInt(time_to[0]);
                            int EndMin = Integer.parseInt(time_to[1]);

                            if (!DateHelper.isTimeAfter(StartHour, StartMin, EndHour, EndMin)) {
                                txtview.setText("");
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.end_time_should));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }*/

    private void openToTimePickerDialog(final AnyTextView txtview) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime = "";
                finaltime = selectedHour + ":" + selectedMinute;

                txtview.setText(selectedHour + ":" + selectedMinute);


                if (finaltime != null) {
                    gym_time_to = finaltime;

                    if (gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

                        try {
                            String[] time_from = gym_time_from.split(":");
                            String[] time_to = gym_time_to.split(":");

                            int StartHour = Integer.parseInt(time_from[0]);
                            int StartMin = Integer.parseInt(time_from[1]);
                            int EndHour = Integer.parseInt(time_to[0]);
                            int EndMin = Integer.parseInt(time_to[1]);

                            if (!DateHelper.isTimeAfter(StartHour, StartMin, EndHour, EndMin)) {
                                txtview.setText("");
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.end_time_should));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }, hour, minute, true);//Yes 24 hour time


        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private boolean validateOtherData() {

        if (Education.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_education_empty));
            return false;
        } else if (Speciality.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_speciality_empty));
            return false;
        } else if (Years_of_Exp.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_experience_empty));
            return false;
        } else if (Gym_days.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_gym_days_empty));
            return false;
        } else if (txt_from.getText().toString().equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_time_from_empty));
            return false;
        } else if (txt_to.getText().toString().equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_time_to_empty));
            return false;
        } else if (gymLocation.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_location_to_empty));
            return false;
        } else {
            return true;
        }


    }

    public boolean validateEditText() {
        return edtUniversity.testValidity() && edtPrimaryReason.testValidity();

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnSignUp:

                Education = StringUtils.join(TrainerSpeciality, ",");
                //Speciality = StringUtils.join(SpecialityArray, ",");
                Speciality = "dummy";
                Gym_days = StringUtils.join(GymDaysArray, ",");

                if (validateOtherData() && validateEditText()) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                        TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());
                    signupTrainer();
                    //getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(), "VarificationCodeFragment");
                }


                break;

            case R.id.txt_from:

                if (!txt_to.getText().toString().equals("")) {
                    openFromTimePickerDialog(txt_from, txt_to);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Select Start Time First");
                }

                break;

            case R.id.txt_to:

                openToTimePickerDialog(txt_to);

                break;

            case R.id.txt_Location:

                if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())){
                MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
                mapControllerFragment.setDelegate(this);

                DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);}

                break;

            case R.id.txt_browse_cv:

                CameraHelper.uploadFile(getMainActivity());
                //  Toast.makeText(getDockActivity(),cvFile.toString(),Toast.LENGTH_LONG).show();

              /*  //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                Intent intent = new Intent();
                intent.setType("text/plain");
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,AppConstants.CV_REQUEST);

                break;*/

        }
    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            Uri currFileURI = data.getData();
            String path = currFileURI.getPath();
            txt_browse_cv.setText(path);
            Toast.makeText(getDockActivity(),path,Toast.LENGTH_LONG).show();
        }

    }*/

    private void signupTrainer() {

        loadingStarted();
        MultipartBody.Part cvFile;
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_picture", signupFormConstants.getProfilePic().getName(), RequestBody.create(MediaType.parse("image/*"), signupFormConstants.getProfilePic()));
        if (resumePath != null && resume != null) {
            cvFile = MultipartBody.Part.createFormData("resume", resumePath,
                    RequestBody.create(MediaType.parse("*/*"), resume));

        } else {
            cvFile = MultipartBody.Part.createFormData("resume", "",
                    RequestBody.create(MediaType.parse("*/*"), ""));
        }


        String SocialMediaName = "";
        if (!signupFormConstants.getSocial_user_id().equalsIgnoreCase("")) {
            SocialMediaName = AppConstants.twitter;
        } else {
            SocialMediaName = "";
        }

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.resgiterTrainer(
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getSocial_user_id()),
                RequestBody.create(MediaType.parse("text/plain"), SocialMediaName),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getfName()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getlName()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getMobileNumber()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getEmail()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getPassword()),
                RequestBody.create(MediaType.parse("text/plain"), sp_Gender.getSelectedItem().toString()),
                filePart,
                cvFile,
                RequestBody.create(MediaType.parse("text/plain"), AppConstants.trainer),
                RequestBody.create(MediaType.parse("text/plain"), Education),
                RequestBody.create(MediaType.parse("text/plain"), edtUniversity.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), ""),
                RequestBody.create(MediaType.parse("text/plain"), gymLocation),
                RequestBody.create(MediaType.parse("text/plain"), lat),
                RequestBody.create(MediaType.parse("text/plain"), log),
                RequestBody.create(MediaType.parse("text/plain"), ""),
                RequestBody.create(MediaType.parse("text/plain"), Years_of_Exp),
                RequestBody.create(MediaType.parse("text/plain"), edtPrimaryReason.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), Gym_days),
                RequestBody.create(MediaType.parse("text/plain"), txt_from.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), txt_to.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), "android"),
                RequestBody.create(MediaType.parse("text/plain"), getMainActivity().selectedLanguage()));

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                        if (response.body().getUserDeleted() == 0) {

                        AppConstants.user_id = response.body().getResult().getId();
                        AppConstants._token = response.body().getResult().get_token();
                        prefHelper.setToken(AppConstants._token);
                        prefHelper.setUsrName(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                        prefHelper.setUsrId(response.body().getResult().getId());
                        prefHelper.putUser(response.body().getResult());

                        if (response.body().getResult().getUser_type().equals(AppConstants.trainee)) {

                            //AppConstants.is_show_trainer = false;

                        } else {
                            //AppConstants.is_show_trainer = true;
                        }

                        getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(signupFormConstants.getUserName(), signupFormConstants.getEmail()), "VarificationCodeFragment");
                        /*showSuccessDialog();
                        getDockActivity().showHome();*/

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }  } else {

                        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                        dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogHelper.hideDialog();
                                getDockActivity().popBackStackTillEntry(0);
                                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                            }
                        });
                        dialogHelper.showDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResult>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });

    }

    private void unselectSelectAll() {
       /* cb_select_all.setOnCheckedChangeListener(null);
        cb_select_all.setChecked(false);
        cb_select_all.setOnCheckedChangeListener(this);*/
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            //Education
            case R.id.cb_Mathematics:

                if (isChecked) {

                    TrainerSpeciality.add(cb_Mathematics.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_Mathematics.getText().toString());
                }

                break;

            case R.id.cb_Fitness_health:

                if (isChecked) {

                    TrainerSpeciality.add(cb_Fitness_health.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_Fitness_health.getText().toString());
                }

                break;

            case R.id.cb_Islamic_studies:
                if (isChecked) {

                    TrainerSpeciality.add(cb_Islamic_studies.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_Islamic_studies.getText().toString());
                }


                break;

            case R.id.cb_english:

                if (isChecked) {

                    TrainerSpeciality.add(cb_english.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_english.getText().toString());
                }


                break;

            case R.id.cb_chemistry:

                if (isChecked) {

                    TrainerSpeciality.add(cb_chemistry.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_chemistry.getText().toString());
                }


                break;


            case R.id.cb_physics:

                if (isChecked) {

                    TrainerSpeciality.add(cb_physics.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_physics.getText().toString());
                }


                break;

            case R.id.cb_human_resources:

                if (isChecked) {

                    TrainerSpeciality.add(cb_human_resources.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_human_resources.getText().toString());
                }


                break;

            case R.id.cb_project_managment:

                if (isChecked) {

                    TrainerSpeciality.add(cb_project_managment.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_project_managment.getText().toString());
                }


                break;


            case R.id.cb_biology:

                if (isChecked) {

                    TrainerSpeciality.add(cb_biology.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_biology.getText().toString());
                }


                break;

            case R.id.cb_java:

                if (isChecked) {

                    TrainerSpeciality.add(cb_java.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_java.getText().toString());
                }


                break;

            case R.id.cb_graduation_project:

                if (isChecked) {

                    TrainerSpeciality.add(cb_graduation_project.getText().toString());

                } else {
                    TrainerSpeciality.remove(cb_graduation_project.getText().toString());
                }


                break;


            //Speciality

            /*case R.id.cb_select_all:

                *//*    cb_flexibility.setChecked(isChecked);
                    cb_dunamic_strength.setChecked(isChecked);
                    cb_static_strength.setChecked(isChecked);
                    cb_circuit.setChecked(isChecked);
                    cb_aerobic.setChecked(isChecked);
                    cb_body_building.setChecked(isChecked);
                    cb_loose_weight.setChecked(isChecked);*//*


                break;

            case R.id.cb_flexibility:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_flexibility));


                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_flexibility));
                }

                break;

            case R.id.cb_dunamic_strength:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_dunamic_strength));
                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_dunamic_strength));

                }

                break;

            case R.id.cb_static_strength:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_static_strength));
                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_static_strength));

                }

                break;

            case R.id.cb_circuit:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_circuit));
                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_circuit));

                }

                break;

            case R.id.cb_aerobic:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_aerobic));
                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_aerobic));

                }

                break;

            case R.id.cb_body_building:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_body_building));
                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_body_building));

                }

                break;

            case R.id.cb_loose_weight:

                if (isChecked) {
                    SpecialityArray.add(getString(R.string.cb_loose_weight));
                } else {
                    unselectSelectAll();
                    SpecialityArray.remove(getString(R.string.cb_loose_weight));

                }

                break;*/

            //Experience

            case R.id.cb_less_then_1:

                if (isChecked) {
                    Years_of_Exp = getString(R.string.cb_less_then_1);
                    cb_1_to_5_years.setChecked(false);
                    cb_5_years_or_more.setChecked(false);
                }

                break;

            case R.id.cb_1_to_5_years:

                if (isChecked) {
                    Years_of_Exp = getString(R.string.cb_1_to_5_years);
                    cb_less_then_1.setChecked(false);
                    cb_5_years_or_more.setChecked(false);
                }

                break;

            case R.id.cb_5_years_or_more:

                if (isChecked) {
                    Years_of_Exp = getString(R.string.cb_5_years_or_more);
                    cb_1_to_5_years.setChecked(false);
                    cb_less_then_1.setChecked(false);
                }

                break;

            //Gym Days

            case R.id.cb_sun:

                if (isChecked) {
                    GymDaysArray.add("sun");
                } else {
                    GymDaysArray.remove("sun");
                }

                break;

            case R.id.cb_mon:

                if (isChecked) {
                    GymDaysArray.add("mon");
                } else {
                    GymDaysArray.remove("mon");
                }

                break;

            case R.id.cb_tue:

                if (isChecked) {
                    GymDaysArray.add("tue");
                } else {
                    GymDaysArray.remove("tue");
                }

                break;

            case R.id.cb_wed:

                if (isChecked) {
                    GymDaysArray.add("wed");
                } else {
                    GymDaysArray.remove("wed");
                }

                break;

            case R.id.cb_thur:

                if (isChecked) {
                    GymDaysArray.add("thur");
                } else {
                    GymDaysArray.remove("thur");
                }

                break;

            case R.id.cb_fri:

                if (isChecked) {
                    GymDaysArray.add("fri");
                } else {
                    GymDaysArray.remove("fri");
                }

                break;

            case R.id.cb_sat:

                if (isChecked) {
                    GymDaysArray.add("Sat");
                } else {
                    GymDaysArray.remove("Sat");
                }

                break;

        }

    }

    @Override
    public void setImage(String imagePath) {

    }

    @Override
    public void setFilePath(String filePath) {
        if (filePath != null) {
            resume = new File(filePath);
            resumePath = filePath;
            txt_browse_cv.setText(filePath);
            //Toast.makeText(getDockActivity(),cvFile.toString(),Toast.LENGTH_LONG).show();
            // txt_browse_cv.setText(cvFile.toString());

        }

    }

    @Override
    public void setVideo(String videoPath, String videoThumb) {

    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {

        txt_Location.setText(formattedAddress);
        if (formattedAddress != null)
            gymLocation = formattedAddress;
        else
            gymLocation = "";
        lat = String.valueOf(location.latitude);
        log = String.valueOf(location.longitude);
        // Toast.makeText(getDockActivity(),location.toString(),Toast.LENGTH_LONG).show();
        // Toast.makeText(getDockActivity(),formattedAddress,Toast.LENGTH_LONG).show();


    }
}
