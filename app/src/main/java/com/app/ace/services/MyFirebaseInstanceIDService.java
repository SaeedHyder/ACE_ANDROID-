package com.app.ace.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.TokenUpdater;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.app.ace.global.AppConstants;
import com.google.inject.Inject;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    protected BasePreferenceHelper preferenceHelper ;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(AppConstants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        TokenUpdater.getInstance().UpdateToken(getApplicationContext(),preferenceHelper.getUserId(),"android",token);
    }

    private void storeRegIdInPref(String token) {

        preferenceHelper.setFirebase_TOKEN(token);
    }
}
