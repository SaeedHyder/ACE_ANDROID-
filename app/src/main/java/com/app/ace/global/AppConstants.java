package com.app.ace.global;


public class AppConstants {

    public static String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    //Code For success and failure
    public static String CODE_SUCCESS = "Success";
    public static String CODE_FAILURE = "Error";

    //Activity result code
    public static final int CAMERA_REQUEST = 1001;
    public static final int GALLERY_REQUEST = 1002;
    public static final int VIDEO_REQUEST = 1003;
    public static final int CV_REQUEST = 1004;

    public static boolean is_show_trainer = false;
    public static String user_id;
    public static String _token;

    public static String trainee = "trainee";
    public static String trainer = "trainer";

    public static String twitter = "twitter";

    public static String fixPassword = "123456";

    public static float zoomIn =500;

    //Firebase Constants
    // broadcast receiver intent filters
    public static final String TOPIC_GLOBAL = "global";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static  int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;



}
