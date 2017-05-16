package com.app.ace.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.entities.TwitterUser;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.TokenUpdater;
import com.app.ace.helpers.TwitterLoginHelper;
import com.app.ace.ui.views.TitleBar;
import com.google.common.util.concurrent.ExecutionError;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/13/2017.
 */

public class SignUpFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.btnSignup_Trainee)
    private Button btnSignup_Trainee;

    @InjectView(R.id.btnSignup_Trainer)
    private Button btnSignup_Trainer;

    @InjectView(R.id.btnSignup_Twitter)
    private Button btnSignup_Twitter;

    @InjectView(R.id.twitterLogin)
    private TwitterLoginButton twitterLogin;

    private boolean isTrainee = false;

    public static SignUpFragment newInstance() {

        return new SignUpFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.setSubHeading(getActivity().getResources().getString(R.string.imgdesc_signup));

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setListeners();

    }

    private void setListeners() {
        btnSignup_Trainee.setOnClickListener(this);
        btnSignup_Trainer.setOnClickListener(this);
        btnSignup_Twitter.setOnClickListener(this);


        setTwitterLogin();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    private void setTwitterLogin() {
        twitterLogin.setCallback(new TwitterLoginHelper() {
            @Override
            public void onSuccess(final TwitterUser user) {

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(Twitter.getSessionManager().getActiveSession(), new com.twitter.sdk.android.core.Callback<String>() {
                    @Override
                    public void success(Result<String> result) {

                        TwitterUser twitterUser = user;
                        twitterUser.setUserEmail(result.data.toString());

                        startFragment(twitterUser);

                    }

                    @Override
                    public void failure(TwitterException exception) {

                      try{
                            final TwitterUser twitterUser = user;
                            twitterUser.setUserEmail("");

                            startFragment(twitterUser);

                      }catch (ExecutionError e){
                            e.printStackTrace();
                      }
                    }
                });
            }

            @Override
            public void onFailure(TwitterException exception) {

            }
        });
    }

    private void startFragment(TwitterUser twitterUser) {
        if(isTrainee){
            getDockActivity().addDockableFragment(TrianeeSignUpFragment.newInstance(twitterUser),"TrianeeSignUpFragment");
        }else{
            getDockActivity().addDockableFragment(TrainerSignUpForm1Fragment.newInstance(twitterUser),"TrainerSignUpForm1Fragment");
        }

    }

    private void showTwitterSignUpDialog() {

        final DialogFragment successPopUp = DialogFragment.newInstance();
        successPopUp.setPopupData(getString(R.string.sign_via_twitter), "", "",  "",true,true);
        TokenUpdater.getInstance().UpdateToken(getDockActivity(),prefHelper.getUserId(),"Android",prefHelper.getFirebase_TOKEN());
        successPopUp.setbtndialog_1_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                twitterLogin.performClick();

                if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    prefHelper.setIsTwitterLogin(true);
                    isTrainee = true;
                }

                successPopUp.dismissDialog();

                //getDockActivity().addDockableFragment(TrianeeSignUpFragment.newInstance(),"TrianeeSignUpFragment");

            }
        });

        successPopUp.setbtndialog_2_Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                twitterLogin.performClick();

                if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    prefHelper.setIsTwitterLogin(true);
                    isTrainee = false;
                }

                successPopUp.dismissDialog();

                //getDockActivity().addDockableFragment(TrainerSignUpForm1Fragment.newInstance(),"TrainerSignUpForm1Fragment");

            }
        });

        successPopUp.show(getDockActivity().getSupportFragmentManager(), "twitterSignUpPopUp");

    }

    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.btnSignup_Trainee:

                prefHelper.setIsTwitterLogin(false);
                getDockActivity().addDockableFragment(TrianeeSignUpFragment.newInstance(),"TrianeeSignUpFragment");
                break;

            case R.id.btnSignup_Trainer:

                prefHelper.setIsTwitterLogin(false);
                getDockActivity().addDockableFragment(TrainerSignUpForm1Fragment.newInstance(),"TrainerSignUpForm1Fragment");
                break;


            case R.id.btnSignup_Twitter:

                if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    showTwitterSignUpDialog();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            twitterLogin.onActivityResult(requestCode, resultCode, data);
        }catch (Exception e){

        }

    }

}
