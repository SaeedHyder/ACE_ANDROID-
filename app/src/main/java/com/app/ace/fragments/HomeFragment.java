package com.app.ace.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.app.ace.BaseApplication;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.CreatePostEnt;
import com.app.ace.entities.HomeListDataEnt;
import com.app.ace.entities.HomeResultEnt;
import com.app.ace.entities.PostsEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.IOnLike;
import com.app.ace.interfaces.SetHomeUpdatedData;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.HomeFragmentItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import berlin.volders.badger.CountBadge;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

public class HomeFragment extends BaseFragment implements View.OnClickListener,
        MainActivity.ImageSetter, IOnLike, SetHomeUpdatedData {

    public File postImage;
    public boolean isNotificationTap = false;
    protected BroadcastReceiver broadcastReceiver;
    AnyTextView txt_Trainer;
    AnyTextView txt_Training;
    AnyTextView txt_Location;
    HomeFragmentItemBinder homeFragmentItemBinder;
    String Lastcomment;
    String NameCommentor;
    @InjectView(R.id.gridView)
    private GridView gridView;
    @InjectView(R.id.iv_Home)
    private ImageView iv_Home;
    @InjectView(R.id.iv_Calander)
    private ImageView iv_Calander;
    @InjectView(R.id.iv_Camera)
    private ImageView iv_Camera;
    @InjectView(R.id.iv_Fav)
    private ImageView iv_Fav;
    @InjectView(R.id.iv_profile)
    private ImageView iv_profile;
    @InjectView(R.id.txt_no_data)
    private AnyTextView txt_no_data;
    private ArrayListAdapter<HomeListDataEnt> adapter;
    private List<HomeListDataEnt> dataCollection;
    private DockActivity activity;
    private TitleBar titleBar;
    private CountBadge badge;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<HomeListDataEnt>(getDockActivity(),
                new HomeFragmentItemBinder(getDockActivity(), this, this));


        BaseApplication.getBus().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println(prefHelper.getBadgeCount());
        getAllHomePosts();
        setListener();
        if (getMainActivity().isNotificationTap) {
            getMainActivity().isNotificationTap = false;
            showNotification();
        }
        onNotificationReceived();
    }

    private void setListener() {

        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_Camera.setOnClickListener(this);
        iv_Fav.setOnClickListener(this);
        iv_profile.setOnClickListener(this);


        getMainActivity().setImageSetter(this);

    }

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
                    showCountOnNotificationReceived();
                    System.out.println(prefHelper.getFirebase_TOKEN());


                }
            }
        };
    }

    private void showCountOnNotificationReceived() {

        titleBar.showBadge();
        titleBar.addtoBadge(prefHelper.getBadgeCount());
        titleBar.getImgNotificationCounter().invalidate();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (titleBar.isBadgeVisible()) {
            titleBar.addtoBadge(prefHelper.getBadgeCount());
        }
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));
    }

    private void setHomePostsData(ArrayList<PostsEnt> postsEntArrayList) {

        if (postsEntArrayList.size() == 0) {

            gridView.setVisibility(View.INVISIBLE);
            txt_no_data.setVisibility(View.VISIBLE);

        } else {

            dataCollection = new ArrayList<HomeListDataEnt>();

            for (PostsEnt postsEnt : postsEntArrayList) {

                dataCollection.add(new HomeListDataEnt(Integer.parseInt(postsEnt.getLike_count()),
                        Integer.parseInt(postsEnt.getComment_count()), postsEnt.getCreator().getProfile_image(),
                        postsEnt.getCreator().getFirst_name() + " " + postsEnt.getCreator().getLast_name(), postsEnt.getPost_image(), "Time Joe", "Hi nice", postsEnt.getUser_id(), postsEnt.getId(), postsEnt.getComment(), postsEnt.getIs_liked()));

            }

            bindData(dataCollection);
        }

    }

    private void bindData(List<HomeListDataEnt> dataCollection) {
        adapter.clearList();
        gridView.setAdapter(adapter);
        adapter.addAll(dataCollection);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        this.titleBar = titleBar;

        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.app_name));
        titleBar.hideSearchBar();
        badge = titleBar.initBadge(getDockActivity());
        titleBar.hideBadge();
        if (prefHelper.getBadgeCount() > 0) {
            titleBar.showBadge();
            titleBar.addtoBadge(prefHelper.getBadgeCount());
        }
        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (prefHelper.getUser().getUser_type().equals(AppConstants.trainer))
                    getDockActivity().addDockableFragment(SearchTraineeFragment.newInstance(), "SearchTraineeFragment");
                else
                    popUpDropdown(v);
            }
        });


        titleBar.showCommentButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                getDockActivity().addDockableFragment(InboxListFragment.newInstance(), "InboxListFragment");

            }
        });

        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                getDockActivity().addDockableFragment(NotificationListingFragment.newInstance(), "NotificationListingFragment");

            }
        });

    }

    private void createPost(boolean isImage) {

        loadingStarted();

        String UserName = "";
        String UserId = "";
        UserId = prefHelper.getUserId();

        if (prefHelper.getUserName() != null) {
            UserName = prefHelper.getUserName();

        } else {
            UserName = "";
        }

        MultipartBody.Part filePart;

        if (isImage) {
            filePart = MultipartBody.Part.createFormData("image", postImage.getName(), RequestBody.create(MediaType.parse("image/*"), postImage));

        } else {
            filePart = MultipartBody.Part.createFormData("image", postImage.getName(), RequestBody.create(MediaType.parse("video/*"), postImage));
        }


        Call<ResponseWrapper<CreatePostEnt>> callBack = webService.createPost(
                RequestBody.create(MediaType.parse("text/plain"), UserName),
                filePart,
                RequestBody.create(MediaType.parse("text/plain"), UserId));

        callBack.enqueue(new Callback<ResponseWrapper<CreatePostEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<CreatePostEnt>> call, Response<ResponseWrapper<CreatePostEnt>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {


                        getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<CreatePostEnt>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), "Connection Problem");
            }
        });

    }

    private void getAllHomePosts() {

        loadingStarted();

        Call<ResponseWrapper<HomeResultEnt>> callBack = webService.getAllHomePosts(prefHelper.getUserId());

        callBack.enqueue(new Callback<ResponseWrapper<HomeResultEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<HomeResultEnt>> call, Response<ResponseWrapper<HomeResultEnt>> response) {
                try {
                    loadingFinished();

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        setHomePostsData(response.body().getResult().getPosts());

                    } else {
                        gridView.setVisibility(View.INVISIBLE);
                        txt_no_data.setVisibility(View.VISIBLE);
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<HomeResultEnt>> call, Throwable t) {
                loadingFinished();
                gridView.setVisibility(View.INVISIBLE);
                txt_no_data.setVisibility(View.VISIBLE);
                txt_no_data.setText(getString(R.string.no_internet));
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_list:

            case R.id.iv_profile:

                AppConstants.is_show_trainer = false;
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(Integer.parseInt(prefHelper.getUserId())), "TrainerProfileFragment");

                break;

            case R.id.iv_Fav:

                getDockActivity().addDockableFragment(FollowingFragment.newInstance(), "FollowingFragment");

                break;

            case R.id.iv_Camera:
                CameraHelper.uploadMedia(getMainActivity());

                //CameraHelper.uploadPhotoAndVideoDialog(HomeFragment.newInstance(),getDockActivity());

                //  UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                //CameraHelper.uploadPhotoDialog(this, getDockActivity());

               /* dialog = new CameraGalleryDialogFragment();

                dialog.setData(getDockActivity());

                dialog.setListener(new CameraGalleryDialogFragment.onCameraDialogListener() {
                    @Override
                    public void onResultReceived(String imageUri, File file) {

                    }

                    @Override
                    public void onError(String reason) {

                    }
                });

                dialog.show(getDockActivity().getSupportFragmentManager(),"My_Dialog");*/

                break;

            case R.id.iv_Calander:



                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                if (prefHelper.getUser().getUser_type().equalsIgnoreCase("trainer")) {
                    getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(), "TrainingBookingCalenderFragment");
                } else {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(), "TraineeScheduleFragment");
                }
              /*  if(AppConstants.is_show_trainer){

                    getDockActivity().addDockableFragment(TrainerBookingCalendarFragment.newInstance(),"TrainerBookingCalendarFragment");
                }
                else
                {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

                }*/

                break;

            case R.id.iv_Home:

                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                break;

            /*case R.id.txt_Trainer:

                getDockActivity().addDockableFragment(SearchTraineeFragment.newInstance(),"HomeFragment");

                break;

            case R.id.txt_Training:

                getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"HomeFragment");

                break;

            case R.id.txt_Location:

                getDockActivity().addDockableFragment(MapScreenFragment.newInstance(),"HomeFragment");

                break;*/


        }
    }


   /* CameraGalleryDialogFragment dialog;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.CAMERA_REQUEST) {

            postImage =  CameraHelper.retrievePictureFromCameraAndGallery(requestCode,resultCode,data,getDockActivity());
            if(postImage !=null)
                createPost();

        } else if (requestCode == AppConstants.GALLERY_REQUEST) {

            postImage = CameraHelper.retrievePictureFromCameraAndGallery(requestCode,resultCode,data,getDockActivity());
            if(postImage !=null)
                createPost();
        }
    }*/


    void popUpDropdown(View v) {
        LayoutInflater layoutInflater = (LayoutInflater) getDockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.home_search_items, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                (int) getResources().getDimension(R.dimen.x100),
                (int) getResources().getDimension(R.dimen.x100));

        txt_Trainer = (AnyTextView) popupView.findViewById(R.id.txt_Trainer);
        txt_Training = (AnyTextView) popupView.findViewById(R.id.txt_Training);
        txt_Location = (AnyTextView) popupView.findViewById(R.id.txt_Location);

               /* txt_Trainer.setOnClickListener(this);
                txt_Training.setOnClickListener(this);
                txt_Location.setOnClickListener(this);*/


        txt_Trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(SearchTrainerFragment.newInstance(), "SearchTraineeFragment");
                popupWindow.dismiss();
            }
        });

        txt_Training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(TrainingSearchFragment.newInstance(), "TrainingSearchFragment");
                popupWindow.dismiss();
            }
        });

        txt_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(MapScreenFragment.newInstance(), "MapScreenFragment");
                popupWindow.dismiss();
            }
        });

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });

        popupWindow.showAsDropDown(v);

    }


    @Override
    public void setImage(String imagePath) {

        if (imagePath != null) {
            postImage = new File(imagePath);
            createPost(true);
        }

    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath) {

        if (videoPath != null) {
            postImage = new File(videoPath);
            createPost(false);
        }

    }

    @Override
    public void setLikeHit(final int postId) {

        Call<ResponseWrapper<PostsEnt>> callBack = webService.likePost(
                prefHelper.getUserId(),
                postId);

        callBack.enqueue(new Callback<ResponseWrapper<PostsEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<PostsEnt>> call, Response<ResponseWrapper<PostsEnt>> response) {

                int i = 1;

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    //UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                } else {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<PostsEnt>> call, Throwable t) {

                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });


    }

    @Override
    public void setUpdatedData(int position, String data, int likes, int comments) {


        HomeListDataEnt updatedItem = (HomeListDataEnt) adapter.getItem(position);
        updatedItem.setIs_liked(data);
        updatedItem.setTotoal_likes(likes);
        updatedItem.setTotal_comments(comments);

        adapter.add(updatedItem);

        adapter.notifyDataSetChanged();


    }


    public void showNotification() {
        getDockActivity().addDockableFragment(NotificationListingFragment.newInstance(), "NotificationListingFragment");
    }
}
