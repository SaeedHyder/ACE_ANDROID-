package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/14/2017.
 */

public class VarificationCodeFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.edtCode)
    private AnyEditTextView edtCode;

    @InjectView(R.id.txtResendCode)
    private AnyTextView txtResendCode;

    @InjectView(R.id.btnConfirm)
    private Button btnConfirm;

    @InjectView(R.id.btnCancel)
    private Button btnCancel;

    private String userName = "";
    private String email = "";

    public static String USER_NAME = "user_name";
    public static String EMAIL = "email";

    public static VarificationCodeFragment newInstance(String userName, String email) {

        if (userName == null) {
            userName = "";
        }

        Bundle args = new Bundle();
        args.putString(USER_NAME, userName);
        args.putString(EMAIL, email);
        VarificationCodeFragment fragment = new VarificationCodeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            email = getArguments().getString(EMAIL);
            userName = getArguments().getString(USER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_varification_code, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
    }

    private void setListener() {
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        txtResendCode.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.verification_code));
    }

    private void showSignUpDialog() {

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


    private boolean validateField() {
        return edtCode.testValidity();
    }

    private void verifyUser() {

        loadingStarted();

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.verifyUser(
                edtCode.getText().toString(),
                prefHelper.getUserId(),
                getMainActivity().selectedLanguage());

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {

                loadingFinished();

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    if (response.body().getUserDeleted() == 0) {
                        prefHelper.putUser(response.body().getResult());
                        showSignUpDialog();
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

            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResult>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });

    }

    private void resendCode() {

        loadingStarted();

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.resencCode(
                email,
                getMainActivity().selectedLanguage());

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        if (response.body().getUserDeleted() == 0) {
                            UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnConfirm:

                if (validateField()) {

                    verifyUser();
                    //showSignUpDialog();
                }

                break;

            case R.id.btnCancel:
                getDockActivity().addDockableFragment(VerifyPhoneFragment.newInstance(), "VerifyPhoneFragment");
                break;

            case R.id.txtResendCode:
                /*if(validateField()) {
                    UIHelper.showLongToastInCenter(getDockActivity(), "Will be implemented in beta version");
                }*/
                //getDockActivity().addDockableFragment(Verf.newInstance(), "WelcomeTutorialFragment");
                resendCode();

                break;

        }

    }

}