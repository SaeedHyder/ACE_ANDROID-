package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.app.ace.R;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener{

    @InjectView(R.id.txt_logout)
    private AnyTextView txt_logout;

    @InjectView(R.id.txt_CurrentPassword)
    private AnyEditTextView txt_CurrentPassword;

    @InjectView(R.id.edit_newPassword)
    private AnyEditTextView edit_newPassword;

    @InjectView(R.id.edit_conNewPassword)
    private AnyEditTextView edit_conNewPassword;

    @InjectView(R.id.txt_contact_us_disc)
    private AnyEditTextView txt_contact_us_disc;

    @InjectView(R.id.toggle_notifications)
    private ToggleButton toggle_notifications;

    @InjectView(R.id.toggle_private_or_public)
    private ToggleButton toggle_private_or_public;



    public static SettingsFragment newInstance() {

        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_settings, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();

    }

    private void setListeners() {

        txt_logout.setOnClickListener(this);
        toggle_private_or_public.setOnClickListener(this);
        toggle_notifications.setOnClickListener(this);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getActivity().getResources().getString(R.string.settings));

        titleBar.showSaveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               updateSetting();

            }
        });

    }

    private void updateSetting() {

        UpdatePassword();
        contactUs();
        NotificationStatus();



    }

    void UpdatePassword(){

        if(!edit_newPassword.getText().toString().equals("")){
            if(txt_CurrentPassword.getText().toString().equals(""))
            {
                UIHelper.showLongToastInCenter(getDockActivity(), "Enter Current Password");
            }
        if(!edit_newPassword.getText().toString().equals(edit_conNewPassword.getText().toString()))
        {
            UIHelper.showLongToastInCenter(getDockActivity(), "Password Not Matched");
        }
        else {

            Call<ResponseWrapper> callBack = webService.ChangePassword(prefHelper.getUserId(), edit_conNewPassword.getText().toString(), txt_CurrentPassword.getText().toString());

            callBack.enqueue(new Callback<ResponseWrapper>() {
                @Override
                public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }

    }}

    void contactUs()
    {
        if(!txt_contact_us_disc.getText().toString().equals("")) {
            Call<ResponseWrapper> callBack = webService.ContactUs(prefHelper.getUserId(), txt_contact_us_disc.getText().toString());

            callBack.enqueue(new Callback<ResponseWrapper>() {
                @Override
                public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
}

    void NotificationStatus()
    {
        String notification;
        String account;

        if(toggle_notifications.isChecked()) notification="1";
        else notification="0";

        if(toggle_private_or_public.isChecked()) account="1";
        else account="0";

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.NotificationStatus(
                RequestBody.create(MediaType.parse("text/plain"),prefHelper.getUserId()),
                RequestBody.create(MediaType.parse("text/plain"),notification),
                RequestBody.create(MediaType.parse("text/plain"),account));
        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResult>> call, Throwable t) {

                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });


    }


    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){

            case R.id.txt_logout:
                prefHelper.setLoginStatus(false);
                prefHelper.setIsTwitterLogin(false);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");

                break;
        }
    }
}
