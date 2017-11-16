package com.app.ace.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.TwitterUser;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.TokenUpdater;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

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
    private String LANGUAGE = "";

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
        } catch (Exception e) {
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

        if (prefHelper.isLanguageArabic()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (prefHelper.isTwitterLogin()) {

            edtPassword.setVisibility(View.GONE);
            txtPassword.setVisibility(View.GONE);
            view_Password.setVisibility(View.GONE);
            edtPassword.setText(AppConstants.fixPassword);
        }
        InternetHelper.CheckInternetConectivityandShowToast(getDockActivity());

        btnSignUp.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

        //AppConstants.is_show_trainer = true;

        setListeners();

        if (twitterUser != null) {
            edtFName.setText(twitterUser.getUserName());
            edtEmail.setText(twitterUser.getUserEmail());
            SetTwitterImage(twitterUser.getUserPic());
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
        if (edtFName.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(),getString(R.string.enter_fName));
            return false;
        } else if (edtLName.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.Enter_Lname));
            return false;
        } else if (edtEmail.equals("") || edtEmail.getText().toString().isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.enter_email));
            return false;
        } else if (edtPassword.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.Enter_password));
            return false;
        } else if (edtMobileNumber.getText().toString().equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.enter_mobile_no));
            return false;
        }  else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.multiImageLayout:


                CameraHelper.uploadPhotoDialog(getMainActivity());

                break;

            case R.id.btnSignUp:

                if (validateFields()) {

                    if (profilePic == null) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.profile_pic_error));
                    } else {

                        if (edtPassword.getText().toString().length() < 6) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
                        } else if (edtMobileNumber.getText().toString().length() < 11) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.phone_should_be_11));
                        } else {
                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                                TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());
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
        if (!UserId.equalsIgnoreCase("")) {
            SocialMediaName = AppConstants.twitter;
        } else {
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
                RequestBody.create(MediaType.parse("text/plain"), "android"),
                RequestBody.create(MediaType.parse("text/plain"), getMainActivity().selectedLanguage()));

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        if (response.body().getUserDeleted() == 0) {

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

                           // getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(UserName, edtEmail.getText().toString()), "VarificationCodeFragment");
                            showSignUpDialog(UserName);
                            /*showSuccessDialog();
                        getDockActivity().showHome();*/
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
                        }
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

    private void showSignUpDialog(String userName) {

        final DialogFragment successPopUp = DialogFragment.newInstance();
        successPopUp.setPopupData(getString(R.string.imgdesc_signup), getString(R.string.you_all_sign), userName, getString(R.string.we_glad_you_here), true, false);

        successPopUp.setbtndialog_1_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPopUp.dismissDialog();
                getDockActivity().popBackStackTillEntry(0);
                prefHelper.setLoginStatus(true);
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
            }
        });

        successPopUp.show(getDockActivity().getSupportFragmentManager(), "signUpPopUp");

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
                    "file:///" + imagePath, iv_profile);
        }
    }
    private void SetTwitterImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
           /* profilePic = new File(imagePath);
            profilePath = imagePath;
            Glide.with(getDockActivity())
                    .load(imagePath)
                    .into(CircularImageSharePop);*/
            Picasso.with(getDockActivity()).load(imagePath).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        iv_profile.setImageBitmap(bitmap);
                        String root = Environment.getExternalStorageDirectory().toString();
                        profilePic = new File(root + "/ace");

                        if (!profilePic.exists()) {
                            profilePic.mkdirs();
                        }

                        String name = new Date().toString() + ".jpg";
                        profilePic = new File(profilePic, name);
                        FileOutputStream out = new FileOutputStream(profilePic);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        // some action
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            //  ImageLoader.getInstance().displayImage(
            //     "file:///" +imagePath, CircularImageSharePop);
        }
    }
    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath, String videothumb) {

    }
}
