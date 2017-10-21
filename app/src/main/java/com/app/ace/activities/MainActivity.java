package com.app.ace.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.ace.R;
import com.app.ace.fragments.HomeFragment;
import com.app.ace.fragments.LanguageFragment;
import com.app.ace.fragments.SideMenuFragment;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.ScreenHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.OnSettingActivateListener;
import com.app.ace.ui.dialogs.DialogFactory;
import com.app.ace.ui.views.TitleBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenFile;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ChosenVideo;
import com.kbeanie.imagechooser.api.ChosenVideos;
import com.kbeanie.imagechooser.api.FileChooserListener;
import com.kbeanie.imagechooser.api.FileChooserManager;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.api.IntentUtils;
import com.kbeanie.imagechooser.api.VideoChooserListener;
import com.kbeanie.imagechooser.api.VideoChooserManager;

import java.util.Locale;

import roboguice.inject.InjectView;

//import com.kbeanie.imagechooser.api.ChosenImages;

public class MainActivity extends DockActivity implements OnClickListener, ImageChooserListener, VideoChooserListener, FileChooserListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static String TAG = "ICA";
    private final int videoDuration = 30;
    @InjectView(R.id.header_main)
    public TitleBar titleBar;
    public final int LocationResultCode = 1;
    public final int WifiResultCode = 2;
    @InjectView(R.id.content_frame)
    public RelativeLayout content_frame;
    private AlertDialog alert;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    @InjectView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    ImageSetter imageSetter;
    private MainActivity mContext;
    private boolean loading;
    private float lastTranslate = 0.0f;
    private ImageChooserManager imageChooserManager;
    private VideoChooserManager videoChooserManager;
    private VideoView videoView;
    private String filePath;
    private FileChooserManager fm;
    private int chooserType;
    private boolean isActivityResultOver = false;
    private String originalFilePath;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    public boolean isNotificationTap = false;
    private Uri fileUri;
    private OnSettingActivateListener settingActivateListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);

        setCurrentLocale();
        //   setLayoutDirection();

        if (getIntent() != null) {
            if (getIntent().getExtras() != null)
                isNotificationTap = getIntent().getExtras().getBoolean("tapped");
        }


        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        settingSideMenu();

        titleBar.setMenuButtonListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        titleBar.setBackButtonListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {
                   // popBackStackTillEntry(1);
                     popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });
        //  onNotificationReceived();
        if (savedInstanceState == null)
            initFragment();


    }

    public String selectedLanguage() {
        if (prefHelper.isLanguageArabic())
            return "ar";
        else
            return "en";
    }

    private void setLayoutDirection() {
        if (prefHelper.isLanguageArabic())
            content_frame.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else {
            content_frame.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    public void setOnSettingActivateListener(OnSettingActivateListener ActivateListener) {
        this.settingActivateListener = ActivateListener;
    }

    public boolean statusCheck() {
        if (isConnected(getApplicationContext())) {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                turnLocationOn(null);
                // buildAlertMessageNoGps(R.string.gps_question, Settings.ACTION_LOCATION_SOURCE_SETTINGS, LocationResultCode);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfoMob = cm.getNetworkInfo(cm.TYPE_MOBILE);
        NetworkInfo netInfoWifi = cm.getNetworkInfo(cm.TYPE_WIFI);
        if (netInfoMob != null && netInfoMob.isConnectedOrConnecting()) {
            Log.v("TAG", "Mobile Internet connected");
            return true;
        }
        if (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting()) {
            Log.v("TAG", "Wifi Internet connected");
            return true;
        }
        buildAlertMessageNoGps(R.string.wifi_question, Settings.ACTION_WIFI_SETTINGS, WifiResultCode);
        return false;

    }

    private void buildAlertMessageNoGps(final int StringResourceID, final String IntentType, final int requestCode) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getDockActivity());
        builder
                .setMessage(getString(StringResourceID))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.gps_yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (StringResourceID == R.string.gps_question) {
                            dialog.cancel();
                            turnLocationOn(null);
                            dialog.dismiss();
                        } else {
                            dialog.cancel();
                            startImpIntent(dialog, IntentType, requestCode);
                            dialog.dismiss();
                        }

                        // startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),LocationResultCode);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.gps_no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.dismiss();
                        dialog.cancel();

                    }
                });
        if (alert == null) {
            alert = builder.create();

        }

        if (!alert.isShowing())
            alert.show();

    }


    private void startImpIntent(DialogInterface dialog, String IntentType, int requestCode) {
        dialog.dismiss();
        Intent i = new Intent(IntentType);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(i, requestCode);
    }

    public void turnLocationOn(GoogleApiClient apiClient) {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            apiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            //**************************

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(apiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            settingActivateListener.onLocationActivateListener();
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        getDockActivity(), 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }

    /*

    protected BroadcastReceiver broadcastReceiver;
    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications

                    System.out.println("registration complete");
                    // FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    isNotificationTap = true;
                    Toast.makeText(getApplicationContext(), "REciecsdf", Toast.LENGTH_SHORT).show();
                    System.out.println(prefHelper.getFirebase_TOKEN());
                    // String message = intent.getStringExtra("message");
                    //message.trim();
                    // if ( Patterns.WEB_URL.matcher(message).matches())

                    //  Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));
    }
*/

    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    private void settingSideMenu() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        sideMenuFragment = SideMenuFragment.newInstance();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();

        drawerLayout.closeDrawers();
    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }

    public void initFragment() {
        if (prefHelper.isLogin()) {
            addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        } else {
            addDockableFragment(LanguageFragment.newInstance(), "LanguageFragment");
            //addDockableFragment(LoginFragment.newInstance(),"LoginFragment");
        }
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void setCurrentLocale() {
        if (prefHelper.isLanguageArabic()) {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = new Locale("ar");
            resources.updateConfiguration(conf, dm);
        } else {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = new Locale("en");
            resources.updateConfiguration(conf, dm);
        }
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        if (progressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onClick(View view) {

    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

//   @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//     /*  LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("LoginFragment");
//        if (fragment != null) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }else{
//
//            SignUpFragment signUpFragment = (SignUpFragment) getSupportFragmentManager().findFragmentByTag("SignUpFragment");
//
//            if (signUpFragment != null) {
//                signUpFragment.onActivityResult(requestCode, resultCode, data);
//            }
//
//
//        }*/
//       BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(getDockFrameLayoutId());
//
//       if(fragment != null) {
//
//           try {
//
//               fragment.onActivityResult(requestCode, resultCode, data);
//           }
//           catch (Exception e){
//               e.printStackTrace();
//           }
//       }
//
//
//    }


    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    public void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pickFile() {
        fm = new FileChooserManager(this);
        fm.setFileChooserListener(this);
        try {
            // progressBar.setVisibility(View.VISIBLE);
            fm.choose();
        } catch (Exception e) {
            // progressBar.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }
    }


    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "Chosen Image: O - " + image.getFilePathOriginal());
                Log.i(TAG, "Chosen Image: T - " + image.getFileThumbnail());
                Log.i(TAG, "Chosen Image: Ts - " + image.getFileThumbnailSmall());
                isActivityResultOver = true;
                originalFilePath = image.getFilePathOriginal();
                thumbnailFilePath = image.getFileThumbnail();
                thumbnailSmallFilePath = image.getFileThumbnailSmall();
                //pbar.setVisibility(View.GONE);
                if (image != null) {
                    Log.i(TAG, "Chosen Image: Is not null");

                    // Toast.makeText(getApplication(),thumbnailFilePath,Toast.LENGTH_LONG).show();
                    imageSetter.setImage(thumbnailFilePath);

                    //loadImage(imageViewThumbnail, image.getFileThumbnail());
                } else {
                    Log.i(TAG, "Chosen Image: Is null");
                }

            }
        });

    }


    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "OnError: " + reason);
                // pbar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, reason,
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + images.size());
                onImageChosen(images.getImage(0));
            }
        });
    }

    public void captureVideo() {
        chooserType = ChooserType.REQUEST_CAPTURE_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_CAPTURE_VIDEO);

        Bundle bundle = new Bundle();
        bundle.putInt(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        bundle.putInt(MediaStore.EXTRA_DURATION_LIMIT, videoDuration);
        videoChooserManager.setExtras(bundle);

        videoChooserManager.setVideoChooserListener(this);

        try {
            // progressBar.setVisibility(View.VISIBLE);
            filePath = videoChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void pickVideo() {
        chooserType = ChooserType.REQUEST_PICK_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_PICK_VIDEO);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        videoChooserManager.setExtras(bundle);
        videoChooserManager.setVideoChooserListener(this);
        try {
            videoChooserManager.choose();
            //    progressBar.setVisibility(View.VISIBLE);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onVideoChosen(final ChosenVideo video) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (video != null) {
                    try {
                        if (video.getVideoFilePath() != null) {

                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                            retriever.setDataSource(video.getVideoFilePath());
                            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                            long timeInmillisec = Long.parseLong(time);
                            long duration = timeInmillisec / 1000;
                            long hours = duration / 3600;
                            long minutes = (duration - hours * 3600) / 60;
                            long seconds = duration - (hours * 3600 + minutes * 60);

                            if ((duration) > videoDuration) {
                                // Show Your Messages
                                Dialog dialog = DialogFactory.createMessageDialog(MainActivity.this, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "Video duration exceed its limit", "UnSupportable Video");
                                dialog.show();
                                // UIHelper.showAlertDialog("Video duration exceed its limit","UnSupportable Video",getApplicationContext());
                            } else {
                                originalFilePath = video.getVideoFilePath().toString();
                                thumbnailSmallFilePath = video.getVideoPreviewImage().toString();
                                //    Toast.makeText(getApplicationContext(), originalFilePath, Toast.LENGTH_LONG).show();
                                // Toast.makeText(getApplicationContext(), thumbnailSmallFilePath, Toast.LENGTH_LONG).show();

                                imageSetter.setVideo(originalFilePath, thumbnailSmallFilePath);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //      videoView.setVideoURI(Uri.parse(new File(video.getVideoFilePath()).toString()));
                    //    videoView.start();

                    // imageViewThumb.setImageURI(Uri.parse(new File(video.getThumbnailPath()).toString()));
                    // imageViewThumbSmall.setImageURI(Uri.parse(new File(video.getThumbnailSmallPath()).toString()));
                }
            }
        });
    }


    @Override
    public void onVideosChosen(final ChosenVideos videos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(getClass().getName(), "run: Videos Chosen: " + videos.size());
                onVideoChosen(videos.getImage(0));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "OnActivityResult");
        Log.i(TAG, "File Path : " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        setCurrentLocale();
        if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }
            Log.i(TAG, "Probable file size: " + fm.queryProbableFileSize(data.getData(), this));
            fm.submit(requestCode, data);
        }
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_CAPTURE_VIDEO || requestCode == ChooserType.REQUEST_PICK_VIDEO)) {
            if (videoChooserManager == null) {
                reinitializeVideoChooser();
            }
            videoChooserManager.submit(requestCode, data);
        } else {
            //progressBar.setVisibility(View.GONE);
        }
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else {
            // progressBar.setVisibility(View.GONE);
        }


        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(getDockFrameLayoutId());

        if (fragment != null) {
            try {
                fragment.onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFileChosen(final ChosenFile file) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // progressBar.setVisibility(View.INVISIBLE);
                imageSetter.setFilePath(file.getFilePath());
                populateFileDetails(file);
            }
        });
    }

    private void populateFileDetails(ChosenFile file) {
        StringBuffer text = new StringBuffer();
        text.append("File name: " + file.getFileName() + "\n\n");
        text.append("File path: " + file.getFilePath() + "\n\n");
        text.append("Mime type: " + file.getMimeType() + "\n\n");
        text.append("File extn: " + file.getExtension() + "\n\n");
        text.append("File size: " + file.getFileSize() / 1024 + "KB");

        //Toast.makeText(this, text.toString(), Toast.LENGTH_LONG).show();
    }


    private void reinitializeVideoChooser() {
        videoChooserManager = new VideoChooserManager(this, chooserType, true);
        videoChooserManager.setVideoChooserListener(this);

        videoChooserManager.reinitialize(filePath);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "Saving Stuff");
        Log.i(TAG, "File Path: " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        outState.putBoolean("activity_result_over", isActivityResultOver);
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        outState.putString("orig", originalFilePath);
        outState.putString("thumb", thumbnailFilePath);
        outState.putString("thumbs", thumbnailSmallFilePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }
            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
            if (savedInstanceState.containsKey("activity_result_over")) {
                isActivityResultOver = savedInstanceState.getBoolean("activity_result_over");
                originalFilePath = savedInstanceState.getString("orig");
                thumbnailFilePath = savedInstanceState.getString("thumb");
                thumbnailSmallFilePath = savedInstanceState.getString("thumbs");
            }

        }
        Log.i(TAG, "Restoring Stuff");
        Log.i(TAG, "File Path: " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        Log.i(TAG, "Activity Result Over: " + isActivityResultOver);
        if (isActivityResultOver) {
            //populateData();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    private void checkForSharedVideo(Intent intent) {
        if (intent != null) {
            if (intent.getAction() != null && intent.getType() != null && intent.getExtras() != null) {
                VideoChooserManager m = new VideoChooserManager(this, ChooserType.REQUEST_PICK_VIDEO);
                m.setVideoChooserListener(this);

                m.submit(ChooserType.REQUEST_PICK_VIDEO, IntentUtils.getIntentForMultipleSelection(intent));

            }
        }
    }

    public void setImageSetter(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface ImageSetter {

        public void setImage(String imagePath);

        public void setFilePath(String filePath);

        public void setVideo(String videoPath, String VideoThumbail);

    }
}