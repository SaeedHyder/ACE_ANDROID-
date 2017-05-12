package com.app.ace.fragments;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.TwitterUser;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.global.SignupFormConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.DateHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.IGetLocation;
import com.app.ace.ui.dialogs.DialogFactory;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.ExpandableGridView;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static android.R.attr.path;
import static android.app.Activity.RESULT_OK;

/**
 * Created by khan_muhammad on 3/13/2017.
 */

public class TrainerSignUpForm2Fragment extends BaseFragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,MainActivity.ImageSetter,IGetLocation {


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

    @InjectView(R.id.txt_pref_training_gym)
    private AnyTextView txt_pref_training_gym;

    @InjectView(R.id.txt_browse_cv)
    private AnyTextView txt_browse_cv;


    //Education
    @InjectView(R.id.cb_degree)
    private CheckBox cb_degree;

    @InjectView(R.id.cb_nasm)
    private CheckBox cb_nasm;

    @InjectView(R.id.cb_ncsa)
    private CheckBox cb_ncsa;

    @InjectView(R.id.cb_acsm)
    private CheckBox cb_acsm;

    @InjectView(R.id.cb_check)
    private CheckBox cb_check;

    @InjectView(R.id.cb_ace)
    private CheckBox cb_ace;

    @InjectView(R.id.cb_aother_cirtifications)
    private CheckBox cb_aother_cirtifications;

    @InjectView(R.id.cb_cpr)
    private CheckBox cb_cpr;


    //Speciality
    @InjectView(R.id.cb_select_all)
    private CheckBox cb_select_all;

    @InjectView(R.id.cb_flexibility)
    private CheckBox cb_flexibility;

    @InjectView(R.id.cb_dunamic_strength)
    private CheckBox cb_dunamic_strength;

    @InjectView(R.id.cb_static_strength)
    private CheckBox cb_static_strength;

    @InjectView(R.id.cb_circuit)
    private CheckBox cb_circuit;

    @InjectView(R.id.cb_aerobic)
    private CheckBox cb_aerobic;

    @InjectView(R.id.cb_body_building)
    private CheckBox cb_body_building;

    @InjectView(R.id.cb_loose_weight)
    private CheckBox cb_loose_weight;

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
    String gymLocation;
    String lat;
    String log;


    public String Education,Speciality,Years_of_Exp,Gym_days,gym_time_from,gym_time_to;

    ArrayList<String> EducationArray = new ArrayList<>();
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
        if(jsonString != null)
            signupFormConstants = new Gson().fromJson(jsonString, SignupFormConstants.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // TODO Auto-generated method stub

        return inflater.inflate( R.layout.fragment_signup_form2, container, false );

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        // TODO Auto-generated method stub
        super.onViewCreated( view, savedInstanceState );

        btnSignUp.setText(getString(R.string.signup));

        setListeners();

        EducationArray = new ArrayList<>();
    }

    private void setListeners() {
        btnSignUp.setOnClickListener(this);
        txt_from.setOnClickListener(this);
        txt_to.setOnClickListener(this);
        txt_pref_training_gym.setOnClickListener(this);
        txt_browse_cv.setOnClickListener(this);

        cb_degree.setOnCheckedChangeListener(this);
        cb_nasm.setOnCheckedChangeListener(this);
        cb_ncsa.setOnCheckedChangeListener(this);
        cb_acsm.setOnCheckedChangeListener(this);
        cb_check.setOnCheckedChangeListener(this);
        cb_ace.setOnCheckedChangeListener(this);
        cb_aother_cirtifications.setOnCheckedChangeListener(this);
        cb_cpr.setOnCheckedChangeListener(this);

        cb_select_all.setOnCheckedChangeListener(this);
        cb_flexibility.setOnCheckedChangeListener(this);
        cb_dunamic_strength.setOnCheckedChangeListener(this);
        cb_static_strength.setOnCheckedChangeListener(this);
        cb_circuit.setOnCheckedChangeListener(this);
        cb_aerobic.setOnCheckedChangeListener(this);
        cb_body_building.setOnCheckedChangeListener(this);
        cb_loose_weight.setOnCheckedChangeListener(this);

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

    }

    @Override
    public void setTitleBar( TitleBar titleBar ) {
        // TODO Auto-generated method stub
        super.setTitleBar( titleBar);

        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeadingWithDiscription(getDockActivity().getResources().getString(R.string.education_and_cirtification),getDockActivity().getResources().getString(R.string.please_select),getDockActivity());

    }

    private void openFromTimePickerDialog(final AnyTextView txtview) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime  = "";
                txtview.setText(selectedHour + ":" + selectedMinute);

                finaltime = selectedHour + ":" + selectedMinute;
                if(finaltime != null) {
                    gym_time_from = finaltime;

                    if(gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

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
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void openToTimePickerDialog(final AnyTextView txtview) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String finaltime  = "";
                txtview.setText(selectedHour + ":" + selectedMinute);

                finaltime = selectedHour + ":" + selectedMinute;

                if(finaltime != null) {
                    gym_time_to = finaltime;

                    if(gym_time_to != null && gym_time_from != null && gym_time_to.length() > 0 && gym_time_from.length() > 0) {

                        try{
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
                        }catch (Exception e){
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

        if(Education.length() == 0){
            UIHelper.showLongToastInCenter(getDockActivity(),  getString(R.string.toast_education_empty));
            return false;
        }
       else if(Speciality.length() == 0){
            UIHelper.showLongToastInCenter(getDockActivity(),  getString(R.string.toast_speciality_empty));
            return false;
        }
        else if(Years_of_Exp.length() == 0){
            UIHelper.showLongToastInCenter(getDockActivity(),  getString(R.string.toast_experience_empty));
            return false;
        }
        else if(Gym_days.length() == 0){
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_gym_days_empty));
            return false;
        }
        else if(txt_from.length() == 0){
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_time_from_empty));
            return false;
        }
        else if(txt_to.length() == 0){
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.toast_time_to_empty));
            return false;
        }else{
            return true;
        }

   }

    public boolean validateEditText(){
        return edtUniversity.testValidity() && edtPrimaryReason.testValidity();
    }


    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.btnSignUp:

                Education = StringUtils.join(EducationArray,",");
                Speciality = StringUtils.join(SpecialityArray,",");
                Gym_days = StringUtils.join(GymDaysArray,",");

                if(validateOtherData() && validateEditText()){
                    if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                        signupTrainer();
                    //getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(), "VarificationCodeFragment");
                }



                break;

            case R.id.txt_from:

                openFromTimePickerDialog(txt_from);

                break;

            case R.id.txt_to:

                openToTimePickerDialog(txt_to);

                break;

            case R.id.txt_pref_training_gym:

                MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
                mapControllerFragment.setDelegate(this);

                DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);

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

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_picture",  signupFormConstants.getProfilePic().getName(), RequestBody.create(MediaType.parse("image/*"),  signupFormConstants.getProfilePic()));

          MultipartBody.Part  cvFile = MultipartBody.Part.createFormData("resume",resumePath, RequestBody.create(MediaType.parse("*/*"), resume));




        String SocialMediaName = "";
        if(!signupFormConstants.getSocial_user_id().equalsIgnoreCase("")){
            SocialMediaName = AppConstants.twitter;
        }else{
            SocialMediaName = "";
        }

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.resgiterTrainer(
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getSocial_user_id()),
                RequestBody.create(MediaType.parse("text/plain"),SocialMediaName),
                RequestBody.create(MediaType.parse("text/plain"),signupFormConstants.getfName()),
                RequestBody.create(MediaType.parse("text/plain"),signupFormConstants.getlName()),
                RequestBody.create(MediaType.parse("text/plain"),signupFormConstants.getMobileNumber()),
                RequestBody.create(MediaType.parse("text/plain"),signupFormConstants.getEmail()),
                RequestBody.create(MediaType.parse("text/plain"),signupFormConstants.getPassword()),
                filePart,
                cvFile,
                RequestBody.create(MediaType.parse("text/plain"),AppConstants.trainer),
                RequestBody.create(MediaType.parse("text/plain"),Education),
                RequestBody.create(MediaType.parse("text/plain"),edtUniversity.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),""),
                RequestBody.create(MediaType.parse("text/plain"),gymLocation),
                RequestBody.create(MediaType.parse("text/plain"),lat),
                RequestBody.create(MediaType.parse("text/plain"),log),
                RequestBody.create(MediaType.parse("text/plain"),Speciality),
                RequestBody.create(MediaType.parse("text/plain"),Years_of_Exp),
                RequestBody.create(MediaType.parse("text/plain"),edtPrimaryReason.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),Gym_days),
                RequestBody.create(MediaType.parse("text/plain"),""),
                RequestBody.create(MediaType.parse("text/plain"),""),
                RequestBody.create(MediaType.parse("text/plain"), "android"));

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        AppConstants.user_id = response.body().getResult().getId();
                        AppConstants._token = response.body().getResult().get_token();
                        prefHelper.setToken( AppConstants._token);
                        prefHelper.setUsrName(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                        prefHelper.setUsrId(response.body().getResult().getId());
                        prefHelper.putUser(response.body().getResult());

                        if(response.body().getResult().getUser_type().equals(AppConstants.trainee)){

                            //AppConstants.is_show_trainer = false;

                        }else{
                            //AppConstants.is_show_trainer = true;
                        }

                        getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(signupFormConstants.getUserName(),signupFormConstants.getEmail()), "VarificationCodeFragment");
                        /*showSuccessDialog();
                        getDockActivity().showHome();*/
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){

            //Education
            case R.id.cb_degree:

                if(isChecked){

                    EducationArray.add(getString(R.string.cb_degree));

                }else{
                    EducationArray.remove(getString(R.string.cb_degree));
                }

                break;

            case R.id.cb_nasm:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_nasm));
                }else{
                    EducationArray.remove(getString(R.string.cb_nasm));
                }

                break;

            case R.id.cb_ncsa:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_ncsa));
                }else{
                    EducationArray.remove(getString(R.string.cb_ncsa));
                }

                break;

            case R.id.cb_acsm:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_acsm));
                }else{
                    EducationArray.remove(getString(R.string.cb_acsm));
                }

                break;

            case R.id.cb_check:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_check));
                }else{
                    EducationArray.remove(getString(R.string.cb_check));
                }

                break;


            case R.id.cb_ace:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_ace));
                }else{
                    EducationArray.remove(getString(R.string.cb_ace));
                }

                break;

            case R.id.cb_aother_cirtifications:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_aother_cirtifications));
                }else{
                    EducationArray.remove(getString(R.string.cb_aother_cirtifications));
                }

                break;

            case R.id.cb_cpr:

                if(isChecked){
                    EducationArray.add(getString(R.string.cb_cpr));
                }else{
                    EducationArray.remove(getString(R.string.cb_cpr));
                }

                break;


            //Speciality

            case R.id.cb_select_all:

                cb_flexibility.setChecked(isChecked);
                cb_dunamic_strength.setChecked(isChecked);
                cb_static_strength.setChecked(isChecked);
                cb_circuit.setChecked(isChecked);
                cb_aerobic.setChecked(isChecked);
                cb_body_building.setChecked(isChecked);
                cb_loose_weight.setChecked(isChecked);

                break;

            case R.id.cb_flexibility:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_flexibility));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_flexibility));
                }

                break;

            case R.id.cb_dunamic_strength:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_dunamic_strength));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_dunamic_strength));
                }

                break;

            case R.id.cb_static_strength:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_static_strength));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_static_strength));
                }

                break;

            case R.id.cb_circuit:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_circuit));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_circuit));
                }

                break;

            case R.id.cb_aerobic:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_aerobic));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_aerobic));
                }

                break;

            case R.id.cb_body_building:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_body_building));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_body_building));
                }

                break;

            case R.id.cb_loose_weight:

                if(isChecked){
                    SpecialityArray.add(getString(R.string.cb_loose_weight));
                }else{
                    SpecialityArray.remove(getString(R.string.cb_loose_weight));
                }

                break;

            //Experience

            case R.id.cb_less_then_1:

                if(isChecked){
                    Years_of_Exp = getString(R.string.cb_less_then_1);
                    cb_1_to_5_years.setChecked(false);
                    cb_5_years_or_more.setChecked(false);
                }

                break;

            case R.id.cb_1_to_5_years:

                if(isChecked){
                    Years_of_Exp = getString(R.string.cb_1_to_5_years);
                    cb_less_then_1.setChecked(false);
                    cb_5_years_or_more.setChecked(false);
                }

                break;

            case R.id.cb_5_years_or_more:

                if(isChecked){
                    Years_of_Exp = getString(R.string.cb_5_years_or_more);
                    cb_1_to_5_years.setChecked(false);
                    cb_less_then_1.setChecked(false);
                }

                break;

            //Gym Days

            case R.id.cb_sun:

                if(isChecked){
                    GymDaysArray.add("sun");
                }else{
                    GymDaysArray.remove("sun");
                }

                break;

            case R.id.cb_mon:

                if(isChecked){
                    GymDaysArray.add("mon");
                }else{
                    GymDaysArray.remove("mon");
                }

                break;

            case R.id.cb_tue:

                if(isChecked){
                    GymDaysArray.add("tue");
                }else{
                    GymDaysArray.remove("tue");
                }

                break;

            case R.id.cb_wed:

                if(isChecked){
                    GymDaysArray.add("wed");
                }else{
                    GymDaysArray.remove("wed");
                }

                break;

            case R.id.cb_thur:

                if(isChecked){
                    GymDaysArray.add("thur");
                }else{
                    GymDaysArray.remove("thur");
                }

                break;

            case R.id.cb_fri:

                if(isChecked){
                    GymDaysArray.add("fri");
                }else{
                    GymDaysArray.remove("fri");
                }

                break;

            case R.id.cb_sat:

                if(isChecked){
                    GymDaysArray.add("Sat");
                }else{
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
        if(filePath != null){
            resume = new File(filePath);
            resumePath=filePath;
            //Toast.makeText(getDockActivity(),cvFile.toString(),Toast.LENGTH_LONG).show();
           // txt_browse_cv.setText(cvFile.toString());

        }

    }

    @Override
    public void setVideo(String videoPath) {

    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {

        txt_pref_training_gym.setText(formattedAddress);
        gymLocation=formattedAddress;
        lat=String.valueOf(location.latitude);
        log=String.valueOf(location.longitude);
       // Toast.makeText(getDockActivity(),location.toString(),Toast.LENGTH_LONG).show();
       // Toast.makeText(getDockActivity(),formattedAddress,Toast.LENGTH_LONG).show();




    }
}
