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
import com.app.ace.entities.TwitterUser;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.global.SignupFormConstants;
import com.app.ace.global.SignupFormEditTextChangeListener;
import com.app.ace.helpers.CameraHelper;
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

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/13/2017.
 */

public class TrainerSignUpForm1Fragment extends BaseFragment implements View.OnClickListener, MainActivity.ImageSetter {


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

    public static TrainerSignUpForm1Fragment newInstance() {

        return new TrainerSignUpForm1Fragment();
    }

    public static TrainerSignUpForm1Fragment newInstance(TwitterUser user) {


        Bundle args = new Bundle();
        args.putString(USER_MODEL, new Gson().toJson(user));
        TrainerSignUpForm1Fragment fragment = new TrainerSignUpForm1Fragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.fragment_signup_form1, container, false);

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


        if (twitterUser != null) {
            edtFName.setText(twitterUser.getUserName());
            edtEmail.setText(twitterUser.getUserEmail());
            SetTwitterImage(twitterUser.getUserPic());

        }

        btnSignUp.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.VISIBLE);

        AppConstants.is_show_trainer = false;

        setListeners();

        edtFName.addTextChangedListener(new SignupFormEditTextChangeListener(getDockActivity(), edtFName));
        edtLName.addTextChangedListener(new SignupFormEditTextChangeListener(getDockActivity(), edtLName));
        edtEmail.addTextChangedListener(new SignupFormEditTextChangeListener(getDockActivity(), edtEmail));
        edtMobileNumber.addTextChangedListener(new SignupFormEditTextChangeListener(getDockActivity(), edtMobileNumber));
        edtPassword.addTextChangedListener(new SignupFormEditTextChangeListener(getDockActivity(), edtPassword));


    }

    private void setListeners() {
        btnNext.setOnClickListener(this);
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
        titleBar.setSubHeading(getActivity().getResources().getString(R.string.sign_up_as_trainer));
    }

    private boolean validateFields() {
        if (edtFName.equals("")) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.enter_fName));
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
        } else {
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

            case R.id.btnNext:

                if (validateFields()) {

                    if (profilePic == null) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.profile_pic_error));
                    } else {

                        if (edtPassword.getText().toString().length() < 6) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
                        } else if (edtMobileNumber.getText().toString().length() < 11) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.phone_should_be_11));
                        } else {

                            SignupFormConstants signupFormConstants = new SignupFormConstants();
                            signupFormConstants.setfName(edtFName.getText().toString());
                            signupFormConstants.setlName(edtLName.getText().toString());
                            signupFormConstants.setEmail(edtEmail.getText().toString());
                            signupFormConstants.setMobileNumber(edtMobileNumber.getText().toString());
                            signupFormConstants.setPassword(edtPassword.getText().toString());
                            signupFormConstants.setProfilePic(profilePic);

                            String UserId = "";
                            if (twitterUser != null) {
                                UserId = twitterUser.getUserId();
                                signupFormConstants.setUserName(twitterUser.getUserName());
                            } else {
                                signupFormConstants.setUserName(edtFName.getText().toString() + " " + edtLName.getText().toString());
                            }

                            signupFormConstants.setSocial_user_id(UserId);

                            getDockActivity().addDockableFragment(TrainerSignUpForm2Fragment.newInstance(signupFormConstants), "TrainerSignUpForm2Fragment");


                        }
                    }

                }

                break;

        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.CAMERA_REQUEST) {

            profilePic = CameraHelper.retrieveAndDisplayPicture(requestCode,resultCode,data,getDockActivity(),iv_profile);
            if(profilePic== null){
                profilePic = new File("");
            }

        } else if (requestCode == AppConstants.GALLERY_REQUEST) {

            profilePic = CameraHelper.retrieveAndDisplayPicture(requestCode,resultCode,data,getDockActivity(),iv_profile);
            if(profilePic== null){
                profilePic = new File("");
            }
        }
    }*/

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
    public void setVideo(String videoPath, String videoTHumb) {

    }

}
