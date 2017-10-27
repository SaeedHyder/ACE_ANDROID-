package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.ToggleButton;

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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener {
    private static String NOTIFICATION_ON = "NOTIFICATION_ON";
    private static String PRIVATE_ACCOUNT = "PRIVATE_ACCOUNT";
    @InjectView(R.id.cb_english)
    private RadioButton cb_english;
    @InjectView(R.id.cb_arabic)
    private RadioButton cb_arabic;

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
    private boolean notification_on = true;
    private boolean pubicAccount = true;
    public String account;


    public static SettingsFragment newInstance() {

        return new SettingsFragment();
    }

    public static SettingsFragment newInstance(boolean isNotificationOn, boolean isPrivateaccount) {
        Bundle args = new Bundle();
        args.putBoolean(NOTIFICATION_ON, isNotificationOn);
        args.putBoolean(PRIVATE_ACCOUNT, isPrivateaccount);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            notification_on = getArguments().getBoolean(NOTIFICATION_ON);
            pubicAccount = getArguments().getBoolean(PRIVATE_ACCOUNT);
        }
        return inflater.inflate(R.layout.fragments_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toggle_notifications.setChecked(notification_on);
        toggle_private_or_public.setChecked(pubicAccount);
        cb_english.setChecked(true);

        if (prefHelper.isLanguageArabic()) {
            cb_arabic.setChecked(true);
        } else {
            cb_english.setChecked(true);
        }
        if (prefHelper.isLanguageArabic()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        setListeners();

    }

    private void setListeners() {

        txt_logout.setOnClickListener(this);
        toggle_private_or_public.setOnClickListener(this);
        toggle_notifications.setOnClickListener(this);

        toggle_notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotificationStatus();
            }
        });

        toggle_private_or_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotificationStatus();
            }
        });

        cb_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogHelper dialog = new DialogHelper(getDockActivity());
                dialog.initLanguage(R.layout.language_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefHelper.putLang(getDockActivity(), "en");
                        dialog.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (prefHelper.isLanguageArabic()) {
                            cb_arabic.setChecked(true);
                        } else {
                            cb_english.setChecked(true);
                        }

                        dialog.hideDialog();
                    }
                });
                dialog.showDialog();


            }
        });
        cb_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogHelper dialog = new DialogHelper(getDockActivity());
                dialog.initLanguage(R.layout.language_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefHelper.putLang(getDockActivity(), "ar");
                        dialog.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (prefHelper.isLanguageArabic()) {
                            cb_arabic.setChecked(true);
                        } else {
                            cb_english.setChecked(true);
                        }
                        dialog.hideDialog();
                    }
                });
                dialog.showDialog();

            }
        });

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

                if (!prefHelper.isTwitterLogin()) {
                    if (!edit_newPassword.getText().toString().equals("") || !txt_CurrentPassword.getText().toString().equals("") || !edit_conNewPassword.getText().toString().equals("")) {
                        updateSetting();
                    } else {
                        contactUs();
                        getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                    }
                } else {
                    if (!edit_newPassword.getText().toString().equals("") || !txt_CurrentPassword.getText().toString().equals("") || !edit_conNewPassword.getText().toString().equals("")) {
                      UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.twitter_password_error));
                    }
                    else{
                        contactUs();
                        getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                    }



                }


            }
        });

    }

    private void updateSetting() {

        UpdatePassword();
        contactUs();


    }

    void UpdatePassword() {

        if (!edit_newPassword.getText().toString().equals("")) {
            if (txt_CurrentPassword.getText().toString().equals("")) {
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.enter_current_password));
            }
            if (!edit_newPassword.getText().toString().equals(edit_conNewPassword.getText().toString())) {
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.password_not_matched));
            } else {

                Call<ResponseWrapper> callBack = webService.ChangePassword(prefHelper.getUserId(), edit_conNewPassword.getText().toString(), txt_CurrentPassword.getText().toString());

                callBack.enqueue(new Callback<ResponseWrapper>() {
                    @Override
                    public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                        loadingStarted();
                        if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                            if (response.body().getUserDeleted() == 0) {
                                UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                            } else {
                                final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                                dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialogHelper.hideDialog();
                                        getDockActivity().popBackStackTillEntry(0);
                                        getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                                    }
                                }, response.body().getMessage());
                                dialogHelper.showDialog();
                            }
                            loadingFinished();

                        } else {
                            loadingFinished();
                            UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                        loadingFinished();
                        UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                    }
                });
            }

        }
    }

    void contactUs() {
        if (!txt_contact_us_disc.getText().toString().equals("")) {
            Call<ResponseWrapper> callBack = webService.ContactUs(prefHelper.getUserId(), txt_contact_us_disc.getText().toString(), getMainActivity().selectedLanguage());

            callBack.enqueue(new Callback<ResponseWrapper>() {
                @Override
                public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                        if (response.body().getUserDeleted() == 0) {
                            loadingFinished();
                            txt_contact_us_disc.setText("");
                            // UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                        } else {
                            final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                            dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogHelper.hideDialog();
                                    getDockActivity().popBackStackTillEntry(0);
                                    getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                                }
                            }, response.body().getMessage());
                            dialogHelper.showDialog();
                        }
                    } else {
                        loadingFinished();
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    void NotificationStatus() {
        String notification;

        if (toggle_notifications.isChecked()) notification = "1";
        else notification = "0";

        if (toggle_private_or_public.isChecked()) account = "0";
        else account = "1";

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.NotificationStatus(
                RequestBody.create(MediaType.parse("text/plain"), prefHelper.getUserId()),
                RequestBody.create(MediaType.parse("text/plain"), notification),
                RequestBody.create(MediaType.parse("text/plain"), account),
                RequestBody.create(MediaType.parse("text/plain"), getMainActivity().selectedLanguage()));
        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                    loadingFinished();

                    if (response.body().getUserDeleted() == 0) {

                    } else {
                        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                        dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogHelper.hideDialog();
                                getDockActivity().popBackStackTillEntry(0);
                                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                            }
                        }, response.body().getMessage());
                        dialogHelper.showDialog();
                    }
                    // UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                } else {
                    loadingFinished();
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


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.txt_logout:

                final DialogHelper dialog = new DialogHelper(getDockActivity());
                dialog.initlogout(R.layout.logout_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefHelper.setLoginStatus(false);
                        prefHelper.setIsTwitterLogin(false);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        dialog.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hideDialog();
                    }
                });
                dialog.showDialog();


                break;
        }
    }


}
