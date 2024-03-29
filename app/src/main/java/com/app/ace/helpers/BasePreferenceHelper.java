package com.app.ace.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.app.ace.activities.MainActivity;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.retrofit.GsonFactory;
import com.google.inject.Inject;

import java.util.Locale;

public class BasePreferenceHelper extends PreferenceHelper {

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String _TOKEN = "token";
    protected static final String Firebase_TOKEN = "Firebasetoken";
    protected static final String KEY_TWITTER_LOGN = "isTwitterLogin";
    protected static final String USERNAME = "userName";
    protected static final String USERID = "userId";
    protected static final String KEY_USER = "key_user";
    protected static final String BADGE_COUNT = "BADGE_COUNT";
    private static final String FILENAME = "preferences";
    protected static final String KEY_DEFAULT_LANG = "keyLanguage";

    private Context context;


    @Inject
    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public static String getUSERID() {
        return USERID;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus(boolean isLogin) {
        putBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS, isLogin);
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public void setIsTwitterLogin(boolean isTwitterLogin) {
        putBooleanPreference(context, FILENAME, KEY_TWITTER_LOGN, isTwitterLogin);
    }

    public boolean isTwitterLogin() {
        return getBooleanPreference(context, FILENAME, KEY_TWITTER_LOGN);
    }
    public Integer getBadgeCount() {
        return getIntegerPreference(context, FILENAME, BADGE_COUNT);
    }

    public void setBadgeCount(Integer _BADGE_COUNT) {
        putIntegerPreference(context, FILENAME, BADGE_COUNT, _BADGE_COUNT);
    }
    public String getToken() {
        return getStringPreference(context, FILENAME, _TOKEN);
    }

    public void setToken(String _token) {
        putStringPreference(context, FILENAME, _TOKEN, _token);
    }

    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }

    public void setUsrName(String _token) {
        putStringPreference(context, FILENAME, USERNAME, _token);
    }

    public String getUserName() {
        return getStringPreference(context, FILENAME, USERNAME);
    }

    public void setUsrId(String userId) {
        putStringPreference(context, FILENAME, USERID, userId);
    }

    public String getUserId() {
        return getStringPreference(context, FILENAME, USERID);
    }

    public RegistrationResult getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), RegistrationResult.class);
    }

    public void putUser(RegistrationResult user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public void putLang(Activity activity, String lang) {
        Log.v("lang", "|" + lang);
        Resources resources = context.getResources();

        if (lang.equals("ar")){
            lang = "ar";}
        else{
            lang = "en";}

        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);
        //Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        android.content.res.Configuration conf = resources.getConfiguration();
        conf.locale = new Locale(lang);
        resources.updateConfiguration(conf, dm);
        ((MainActivity) activity).restartActivity();
    }

    public void PutLang(MainActivity activity,String lang){
        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);

    }


    public String getLang() {
        return getStringPreference(context, FILENAME, KEY_DEFAULT_LANG);
    }

    public boolean isLanguageArabic() {
        return getLang().equalsIgnoreCase("ar");
    }




}
