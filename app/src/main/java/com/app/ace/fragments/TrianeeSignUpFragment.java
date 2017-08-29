package com.app.ace.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.TwitterUser;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.global.PasswordEditTextChangeListener;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.TokenUpdater;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by khan_muhammad on 3/13/2017.
 */

public class TrianeeSignUpFragment extends BaseFragment implements View.OnClickListener, MainActivity.ImageSetter {


    @InjectView(R.id.btnSignUp)
    private Button btnSignUp;

    @InjectView(R.id.btnNext)
    private Button btnNext;

    @InjectView(R.id.edtFName)
    private AnyEditTextView edtFName;

    @InjectView(R.id.edtLName)
    private AnyEditTextView edtLName;

    @InjectView(R.id.edtEmail)
    private AnyEditTextView edtEmail;

    @InjectView(R.id.edtMobileNumber)
    private AnyEditTextView edtMobileNumber;

    @InjectView(R.id.edtPassword)
    private AnyEditTextView edtPassword;

    @InjectView(R.id.txtPassword)
    private AnyTextView txtPassword;

    @InjectView(R.id.view_Password)
    private View view_Password;


    @InjectView(R.id.multiImageLayout)
    private RelativeLayout multiImageLayout;

    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;

    public static String USER_MODEL = "user";
    public TwitterUser twitterUser;

    public File profilePic;

    public String UserName = "";

    public static TrianeeSignUpFragment newInstance() {

        return new TrianeeSignUpFragment();
    }

    public static TrianeeSignUpFragment newInstance(TwitterUser user) {


        try {

            Bundle args = new Bundle();
            args.putString(USER_MODEL, new Gson().toJson(user));
            TrianeeSignUpFragment fragment = new TrianeeSignUpFragment();
            fragment.setArguments(args);

            return fragment;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String jsonString = getArguments().getString(USER_MODEL);
            if (jsonString != null)
                twitterUser = new Gson().fromJson(jsonString, TwitterUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_signup_form1, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isTwitterLogin()) {

            edtPassword.setVisibility(View.GONE);
            txtPassword.setVisibility(View.GONE);
            view_Password.setVisibility(View.GONE);
            edtPassword.setText(AppConstants.fixPassword);
        }

        btnSignUp.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

        //AppConstants.is_show_trainer = true;

        setListeners();

        if (twitterUser != null) {
            edtFName.setText(twitterUser.getUserName());
            edtEmail.setText(twitterUser.getUserEmail());
        }

    }

    private void setListeners() {
        btnSignUp.setOnClickListener(this);
        multiImageLayout.setOnClickListener(this);
        getMainActivity().setImageSetter(this);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getActivity().getResources().getString(R.string.sign_up_as_trainee));
    }

    private boolean validateFields() {
        return edtFName.testValidity() && edtLName.testValidity() && edtEmail.testValidity() && edtPassword.testValidity() && edtMobileNumber.testValidity();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.multiImageLayout:


                CameraHelper.uploadPhotoDialog(getMainActivity());

                break;

            case R.id.btnSignUp:

                if (validateFields() ) {

                    if(profilePic == null){
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.profile_pic_error));
                    }else {

                        if (edtPassword.getText().toString().length() < 6) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
                        }
                        else if(edtMobileNumber.getText().toString().length() < 11){
                            UIHelper.showShortToastInCenter(getDockActivity(), "Mobile Number should be 11 or more characters long");
                        }else {
                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                                TokenUpdater.getInstance().UpdateToken(getDockActivity(),prefHelper.getUserId(),"android",prefHelper.getFirebase_TOKEN());
                                signupTrainee();
                        }
                    }
                }

                break;

        }
    }


    private void signupTrainee() {

        loadingStarted();

        String UserId = "";

        if (twitterUser != null) {
            UserId = twitterUser.getUserId();
            UserName = twitterUser.getUserName();
        } else {
            UserName = edtFName.getText().toString() + " " + edtLName.getText().toString();
        }

        /*if (profilePic == null) {
            profilePic = new File("");
        }*/

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_picture",
                profilePic.getName(), RequestBody.create(MediaType.parse("image/*"), profilePic));

        String SocialMediaName = "";
        if(!UserId.equalsIgnoreCase("")){
            SocialMediaName = AppConstants.twitter;
        }else{
            SocialMediaName = "";
        }

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.resgiterTrainee(
                RequestBody.create(MediaType.parse("text/plain"), UserId),
                RequestBody.create(MediaType.parse("text/plain"), SocialMediaName),
                RequestBody.create(MediaType.parse("text/plain"), edtFName.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtLName.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtMobileNumber.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtEmail.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtPassword.getText().toString()),
                filePart,
                RequestBody.create(MediaType.parse("text/plain"), AppConstants.trainee),
                RequestBody.create(MediaType.parse("text/plain"), "android"));

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        if (twitterUser != null) {
                            if (twitterUser.getUserId() != null)
                                prefHelper.setIsTwitterLogin(true);
                            else
                                prefHelper.setIsTwitterLogin(false);
                        } else {
                            prefHelper.setIsTwitterLogin(false);
                        }

                        AppConstants.user_id = response.body().getResult().getId();
                        AppConstants._token = response.body().getResult().get_token();
                        prefHelper.setToken(AppConstants._token);
                        prefHelper.setUsrName(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                        prefHelper.setUsrId(response.body().getResult().getId());
                        prefHelper.putUser(response.body().getResult());

                        if (response.body().getResult().getUser_type().equals(AppConstants.trainee)) {

                            // AppConstants.is_show_trainer = false;

                        } else {
                            // AppConstants.is_show_trainer = true;
                        }

                        getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(UserName, edtEmail.getText().toString()), "VarificationCodeFragment");
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == AppConstants.CAMERA_REQUEST) {
//
//            profilePic = CameraHelper.retrieveAndDisplayPicture(requestCode, resultCode, data, getDockActivity(), iv_profile);
//
//        } else if (requestCode == AppConstants.GALLERY_REQUEST) {
//
//            profilePic = CameraHelper.retrieveAndDisplayPicture(requestCode, resultCode, data, getDockActivity(), iv_profile);
//        }
//    }

    @Override
    public void setImage(String imagePath) {

        if (imagePath != null) {
            profilePic = new File(imagePath);
            ImageLoader.getInstance().displayImage(
                    "file:///" +imagePath, iv_profile);
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath,String videothumb) {

    }
}
