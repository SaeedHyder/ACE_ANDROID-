package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/14/2017.
 */

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener{

    @InjectView(R.id.edtEmail)
    private AnyEditTextView edtEmail;

    @InjectView(R.id.btnSubmit)
    private Button btnSubmit;


    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
    }

    private void setListener() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.forgot_password));
    }

    private void showForgotPasswordDialog() {

        final DialogFragment successPopUp = DialogFragment.newInstance();
        successPopUp.setPopupData(getString(R.string.reset), getString(R.string.an_email_has_been), "", "", true, false);

        successPopUp.setbtndialog_1_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successPopUp.dismissDialog();
                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
            }
        });

        successPopUp.show(getMainActivity().getSupportFragmentManager(), "forgotPasswordPopUp");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                if(validate()) {
                    loadingStarted();
                    Call<ResponseWrapper> call = webService.forgetPassword(edtEmail.getText().toString(), getMainActivity().selectedLanguage());
                    call.enqueue(new Callback<ResponseWrapper>() {
                        @Override
                        public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                            if(response.body().getMessage().contains("new password"))
                            {
                                if (response.body().getUserDeleted()==0) {
                                    loadingFinished();
                                    showForgotPasswordDialog();
                                } else {
                                    final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                                    dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialogHelper.hideDialog();
                                            getDockActivity().popBackStackTillEntry(0);
                                            getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                                        }
                                    });
                                    dialogHelper.showDialog();
                                }
                            }
                            else
                            {
                                loadingFinished();
                                UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                            loadingFinished();
                            Log.e("ForgotPasswordFragment",t.toString());
                        }
                    });

                }
                break;
        }
    }

    private boolean validate() {
        return edtEmail.testValidity();
    }
}
