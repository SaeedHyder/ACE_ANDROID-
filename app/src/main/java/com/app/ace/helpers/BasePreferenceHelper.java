package com.app.ace.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.ace.entities.RegistrationResult;
import com.app.ace.retrofit.GsonFactory;
import com.google.inject.Inject;

public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";

    private static final String FILENAME = "preferences";

    protected static final String _TOKEN = "token";

    protected static final String KEY_TWITTER_LOGN = "isTwitterLogin";

    protected static final String USERNAME = "userName";


    protected static final String USERID = "userId";

    protected static final String KEY_USER = "key_user";


    @Inject
    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public void setIsTwitterLogin( boolean isTwitterLogin ) {
        putBooleanPreference( context, FILENAME, KEY_TWITTER_LOGN, isTwitterLogin );
    }

    public boolean isTwitterLogin() {
        return getBooleanPreference(context, FILENAME, KEY_TWITTER_LOGN);
    }

    public void setToken( String _token ) {
        putStringPreference( context, FILENAME, _TOKEN, _token );
    }


    public String getToken( ) {
        return getStringPreference(context, FILENAME, _TOKEN);
    }

    public void setUsrName( String _token ) {
        putStringPreference( context, FILENAME, USERNAME, _token );
    }


    public String getUserName( ) {
        return getStringPreference(context, FILENAME, USERNAME);
    }

    public void setUsrId( String userId ) {
        putStringPreference( context, FILENAME, USERID, userId );
    }


    public String getUserId( ) {
        return getStringPreference(context, FILENAME, USERID);
    }

    public static String getUSERID() {
        return USERID;
    }


    public RegistrationResult getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), RegistrationResult.class);
    }

    public void putUser(RegistrationResult user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }












}
