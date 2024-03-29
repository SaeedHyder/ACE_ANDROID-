package com.app.ace.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.TwitterUser;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.TokenUpdater;
import com.app.ace.helpers.TwitterLoginHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.common.util.concurrent.ExecutionError;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

public class LoginFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.btnLogin)
    private Button btnLogin;

    @InjectView(R.id.btnSignin_Twitter)
    private Button btnSignin_Twitter;

    @InjectView(R.id.twitterLogin)
    private TwitterLoginButton twitterLogin;

    @InjectView(R.id.txtForgotPass)
    private AnyTextView txtForgotPass;

    @InjectView(R.id.txtCreateAccount)
    private AnyTextView txtCreateAccount;

    @InjectView(R.id.edtEmail)
    private AnyEditTextView edtEmail;


    @InjectView(R.id.edtPassword)
    private AnyEditTextView edtPassword;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        if (prefHelper.isLanguageArabic())
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        setListeners();

    }

    private void setListeners() {
        btnSignin_Twitter.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        txtCreateAccount.setOnClickListener(this);

        setTwitterLogin();

		/*TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false, new com.twitter.sdk.android.core.Callback<User>() {
            @Override
			public void success(Result<User> result) {


				String userName = result.data.screenName;
				//int userId = result.data.id;
				String pictureUrl = result.data.profileImageUrl;
				String coverUrl = result.data.profileBannerUrl;
			}

			@Override
			public void failure(TwitterException e) {

			}
		});*/


    }

    private void setTwitterLogin() {
        TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());
        twitterLogin.setCallback(new TwitterLoginHelper() {
            @Override
            public void onSuccess(final TwitterUser user) {

                Call<User> userCall = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, false);
                userCall.enqueue(new com.twitter.sdk.android.core.Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        TwitterUser twitterUser = user;
                        twitterUser.setUserPic(result.data.profileImageUrl.replace("_normal", "_bigger"));
                        sociallogin(twitterUser);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        try {
                            TwitterUser twitterUser = user;
                            twitterUser.setUserEmail("");
                            sociallogin(twitterUser);
                        } catch (ExecutionError e) {

                        }
                    }
                });

            }

            @Override
            public void onFailure(TwitterException exception) {

            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.setSubHeading(getActivity().getResources().getString(R.string.login));
    }

    private void sociallogin(final TwitterUser user) {

        loadingStarted();

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.socialLogin(
                user.getUserId(),
                AppConstants.twitter,
                user.getUserPic(),
                prefHelper.getFirebase_TOKEN(),
                "android",
                getMainActivity().selectedLanguage());

        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {

                loadingFinished();
                TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    if (response.body().getUserDeleted() == 0) {
                        getDockActivity().popBackStackTillEntry(0);
                        prefHelper.setLoginStatus(true);
                        AppConstants.user_id = response.body().getResult().getId();
                        AppConstants._token = response.body().getResult().get_token();
                        prefHelper.setUsrName(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                        prefHelper.setUsrId(response.body().getResult().getId());
                        prefHelper.setToken(response.body().getResult().get_token());
                        prefHelper.putUser(response.body().getResult());
                        prefHelper.setIsTwitterLogin(true);

                        //prefHelper.setMobileNo(response.body().getResult().getPhone_no());


                        if (response.body().getResult().getUser_type().equals(AppConstants.trainee)) {

                            //AppConstants.is_show_trainer = false;

                        } else {
                            //AppConstants.is_show_trainer = true;
                        }
                        getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                    } else {
                        //UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                        showTwitterSignUpDialog(user);

                    }
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
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResult>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });

    }

    private void showTwitterSignUpDialog(final TwitterUser user) {

        final DialogFragment successPopUp = DialogFragment.newInstance();
        successPopUp.setPopupData(getString(R.string.sign_via_twitter), "", "", "", true, true);

        successPopUp.setbtndialog_1_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefHelper.setIsTwitterLogin(true);
                successPopUp.dismissDialog();
                getDockActivity().addDockableFragment(TrianeeSignUpFragment.newInstance(user), "TrianeeSignUpFragment");

            }
        });

        successPopUp.setbtndialog_2_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefHelper.setIsTwitterLogin(true);
                successPopUp.dismissDialog();
                getDockActivity().addDockableFragment(TrainerSignUpForm1Fragment.newInstance(user), "TrainerSignUpForm1Fragment");

            }
        });

        successPopUp.show(getDockActivity().getSupportFragmentManager(), "twitterSignUpPopUp");

    }

    private void loginUser() {

        loadingStarted();

        Call<ResponseWrapper<RegistrationResult>> callBack = webService.loginUser(
                edtEmail.getText().toString(),
                edtPassword.getText().toString(),
                prefHelper.getFirebase_TOKEN(),
                "android",
                getMainActivity().selectedLanguage());


        callBack.enqueue(new Callback<ResponseWrapper<RegistrationResult>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResult>> call, Response<ResponseWrapper<RegistrationResult>> response) {

                TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());
                loadingFinished();

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    if (response.body().getUserDeleted() == 0) {


                        getDockActivity().popBackStackTillEntry(0);

                        prefHelper.setLoginStatus(true);


                        //AppConstants.user_id = response.body().getResult().getId();
                        //AppConstants._token = response.body().getResult().get_token();


                        prefHelper.setUsrName(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                        prefHelper.setUsrId(response.body().getResult().getId());
                        prefHelper.setToken(response.body().getResult().get_token());
                        prefHelper.putUser(response.body().getResult());

                        if (response.body().getResult().getUser_type().equals(AppConstants.trainee)) {

                            //AppConstants.is_show_trainer = false;

                        } else {
                            //AppConstants.is_show_trainer = true;
                        }

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
                } else {

                    if (response.body().getMessage().equalsIgnoreCase("Account not verified Please check email for verification code")) {
                        prefHelper.setUsrId(response.body().getResult().getId());
                        AppConstants.user_id = prefHelper.getUserId();
                        prefHelper.setUsrName(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                        prefHelper.setUsrId(response.body().getResult().getId());
                        prefHelper.setToken(response.body().getResult().get_token());
                        prefHelper.putUser(response.body().getResult());
                        getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name(), response.body().getResult().getEmail()), "VarificationCodeFragment");

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }


                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResult>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });

    }

    private boolean validateFields() {
        return edtEmail.testValidity() && edtPassword.testValidity();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnLogin:
                if (validateFields()) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                        loginUser();
                }
                break;

            case R.id.txtForgotPass:

                getDockActivity().addDockableFragment(ForgotPasswordFragment.newInstance(), "ForgotPasswordFragment");
                break;


            case R.id.txtCreateAccount:

                getDockActivity().addDockableFragment(SignUpFragment.newInstance(), "SignUpFragment");
                break;

            case R.id.btnSignin_Twitter:

                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))


                    twitterLogin.performClick();

                //getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == 1) {
                UIHelper.showShortToastInCenter(getDockActivity(), "Twitter App not found");
            }

            twitterLogin.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

		/*try {
            if(resultCode != 1) {
				twitterLogin.onActivityResult(requestCode, resultCode, data);
			}
			else{

				UIHelper.showShortToastInCenter(getDockActivity(), "Twitter App not found");
			}
		}catch (Exception e){

		}*/


    }
}
