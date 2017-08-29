package com.app.ace.fragments;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.app.ace.BaseApplication;
import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.FollowUser;
import com.app.ace.entities.RegistrationResult;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.User;
import com.app.ace.entities.UserProfile;
import com.app.ace.entities.post;
import com.app.ace.entities.profilePostEnt;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.CameraHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.ImageClickListener;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.UserPicItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.CustomRatingBar;
import com.app.ace.ui.views.ExpandableGridView;
import com.app.ace.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/17/2017.
 */

public class TrainerProfileFragment extends BaseFragment implements View.OnClickListener, ImageClickListener {

    public static String USER_ID = "User_Id";
    @InjectView(R.id.txt_tagline)
    AnyTextView txt_tagline;
    @InjectView(R.id.btn_followTrainee)
    Button btn_followTrainee;
    @InjectView(R.id.btn_unfollowTrainee)
    Button btn_unfollowTrainee;
    @InjectView(R.id.btn_edit)
    Button btn_edit;
    @InjectView(R.id.txt_postCount)
    AnyTextView txt_postCount;
    @InjectView(R.id.txt_FollowersCount)
    AnyTextView txt_FollowersCount;
    @InjectView(R.id.txt_FollowingsCount)
    AnyTextView txt_FollowingsCount;
    @InjectView(R.id.txt_no_data)
    AnyTextView txt_no_data;
    AnyTextView txt_TrainerProfileFrag;
    AnyTextView txt_TrainingProfileFrag;
    AnyTextView txt_LocationProfileFrag;
    String Trainer, Trainee;
    boolean isNotificationOn = false;
    boolean isPublicAccount = false;
    String user_id;
    int rating = 5;
    @InjectView(R.id.scrollView)
    private ScrollView scrollView;
    @InjectView(R.id.gv_pics)
    private ExpandableGridView gv_pics;
    @InjectView(R.id.iv_grid)
    private ImageView iv_grid;
    @InjectView(R.id.iv_list)
    private ImageView iv_list;
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
    @InjectView(R.id.btn_follow)
    private Button btn_follow;
    @InjectView(R.id.btn_Unfollow)
    private Button btn_Unfollow;
    @InjectView(R.id.btn_request)
    private Button btn_request;
    @InjectView(R.id.btn_edit_or_follow)
    private Button btn_edit_or_follow;
    @InjectView(R.id.ll_one_button)
    private LinearLayout ll_one_button;
    @InjectView(R.id.ll_two_buttons)
    private LinearLayout ll_two_buttons;
    @InjectView(R.id.ll_trainer)
    private LinearLayout ll_trainer;
    @InjectView(R.id.ll_trainee)
    private LinearLayout ll_trainee;
    @InjectView(R.id.ll_grid)
    private LinearLayout ll_grid;
    @InjectView(R.id.ll_list)
    private LinearLayout ll_list;
    @InjectView(R.id.ll_separator)
    private LinearLayout ll_separator;
    @InjectView(R.id.txt_profileName)
    private AnyTextView txt_profileName;
    @InjectView(R.id.txt_Trainer)
    private AnyTextView txt_Trainer;
    @InjectView(R.id.rbAddRating)
    private CustomRatingBar rbAddRating;
    @InjectView(R.id.riv_profile_pic)
    private CircleImageView riv_profile_pic;
    @InjectView(R.id.txt_education_cirtification_dis)
    private AnyTextView txt_education_cirtification_dis;
    @InjectView(R.id.txt_preffered_training_loc_dis)
    private AnyTextView txt_preffered_training_loc_dis;
    @InjectView(R.id.txt_avaliability_dis)
    private AnyTextView txt_avaliability_dis;
    private ArrayListAdapter<profilePostEnt> adapter;
    private List<profilePostEnt> dataCollection;

    private List<String> ImageCollection;
    private DockActivity activity;
    private ImageLoader imageLoader;
    private boolean isTrainer = false;
    String TrainerGymAddress;
    String Speciality;

    public static TrainerProfileFragment newInstance() {
        return new TrainerProfileFragment();
    }


    public static TrainerProfileFragment newInstance(int user_id) {
        Bundle args = new Bundle();
        args.putString(USER_ID, String.valueOf(user_id));
        TrainerProfileFragment fragment = new TrainerProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingStarted();
        if (getArguments() != null) {
            user_id = getArguments().getString(USER_ID);
        } else {
            user_id = prefHelper.getUserId();
        }


        imageLoader = ImageLoader.getInstance();
        adapter = new ArrayListAdapter<profilePostEnt>(getDockActivity(), new UserPicItemBinder(getDockActivity(), this));

        BaseApplication.getBus().register(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollView.setVisibility(View.INVISIBLE);
        loadingFinished();
        setListener();
        showProfiles();


    }

    private void showProfiles() {
        RegistrationResult result;
        Call<ResponseWrapper<UserProfile>> callBack = webService.UserProfile(user_id, prefHelper.getUserId());

        callBack.enqueue(new Callback<ResponseWrapper<UserProfile>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserProfile>> call, Response<ResponseWrapper<UserProfile>> response) {

                loadingFinished();
                if (response.body() != null) {
                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                        try {
                            if (response.body().getResult().getNotification_status().equals("1")) {
                                isNotificationOn = true;
                            } else {
                                isNotificationOn = false;
                            }
                            if (response.body().getResult().getPrivate_account().equals("0")) {
                                isPublicAccount = true;
                            } else {
                                isPublicAccount = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        RegistrationResult result = prefHelper.getUser();
                        result.setProfile_image(response.body().getResult().getProfile_image());
                        prefHelper.putUser(result);

                        scrollView.setVisibility(View.VISIBLE);

                        if (response.body().getResult().getUser_type().equals(AppConstants.trainer)) {

                            isTrainer = true;
                            Trainer = AppConstants.trainer;
                            result.setEducation(response.body().getResult().getEducation());
                            result.setSpeciality(response.body().getResult().getSpeciality());
                            prefHelper.putUser(result);

                            if (response.body().getResult().getId() == Integer.parseInt(prefHelper.getUserId())) {
                                btn_edit.setVisibility(View.VISIBLE);
                                btn_follow.setVisibility(View.GONE);
                                btn_Unfollow.setVisibility(View.GONE);
                                btn_request.setVisibility(View.GONE);
                                rbAddRating.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        rbAddRating.setFocusable(false);
                                        return true;
                                    }
                                });


                            }
                            if (response.body().getResult().getUser_type().equalsIgnoreCase(prefHelper.getUser().getUser_type())) {
                                if (response.body().getResult().getId() != Integer.parseInt(prefHelper.getUserId())) {
                                    btn_edit.setVisibility(View.GONE);
                                    btn_follow.setVisibility(View.VISIBLE);
                                    btn_Unfollow.setVisibility(View.GONE);
                                    btn_request.setVisibility(View.GONE);

                                }
                            } else {
                                btn_edit.setVisibility(View.GONE);
                                btn_follow.setVisibility(View.VISIBLE);
                                btn_Unfollow.setVisibility(View.GONE);
                                btn_request.setVisibility(View.VISIBLE);
                            }

                            if (response.body().getResult().getIs_following() == 0) {
                                btn_follow.setVisibility(View.VISIBLE);
                                btn_Unfollow.setVisibility(View.GONE);
                            } else {
                                btn_follow.setVisibility(View.GONE);
                                btn_Unfollow.setVisibility(View.VISIBLE);

                            }
                      /*  if(response.body().getResult().getUser_type().equalsIgnoreCase(AppConstants.trainer) && response.body().getResult().getId() != Integer.parseInt(prefHelper.getUserId())){
                            btn_edit.setVisibility(View.GONE);
                            btn_follow.setVisibility(View.VISIBLE);
                            btn_Unfollow.setVisibility(View.GONE);
                            btn_request.setVisibility(View.GONE);

                            if (response.body().getResult().getIs_following() == 0) {
                                btn_follow.setVisibility(View.VISIBLE);
                                btn_Unfollow.setVisibility(View.GONE);
                            } else {
                                btn_follow.setVisibility(View.GONE);
                                btn_Unfollow.setVisibility(View.VISIBLE);

                            }
                        }
*/
                            ll_one_button.setVisibility(View.INVISIBLE);
                            ll_two_buttons.setVisibility(View.VISIBLE);


                            ll_trainer.setVisibility(View.VISIBLE);
                            ll_trainee.setVisibility(View.GONE);

                            txt_Trainer.setVisibility(View.VISIBLE);
                            rbAddRating.setVisibility(View.VISIBLE);


                            ll_separator.setVisibility(View.VISIBLE);

                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                Speciality = response.body().getResult().getSpeciality();
                                TrainerGymAddress = response.body().getResult().getGym_address();
                                txt_profileName.setText(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                                imageLoader.displayImage(response.body().getResult().getProfile_image(), riv_profile_pic);
                                txt_education_cirtification_dis.setText(response.body().getResult().getEducation() + " " + response.body().getResult().getUniversity());
                                txt_preffered_training_loc_dis.setText(response.body().getResult().getGym_address());
                                txt_avaliability_dis.setText(response.body().getResult().getGym_days() + " " + response.body().getResult().getGym_timing_from() + " " + response.body().getResult().getGym_timing_to());
                                txt_postCount.setText(response.body().getResult().getPosts_count());
                                txt_FollowersCount.setText(response.body().getResult().getFollowers_count());
                                txt_FollowingsCount.setText(response.body().getResult().getFollowing_count());
                                rbAddRating.setScore(response.body().getResult().getRating());

                                ShowUserPosts(response.body().getResult().getPosts());
                            }
                        } else {
                            if (response.body().getResult().getId() != Integer.parseInt(prefHelper.getUserId())) {
                                btn_edit_or_follow.setVisibility(View.GONE);
                                btn_followTrainee.setVisibility(View.VISIBLE);

                            }

                            if (response.body().getResult().getIs_following() == 0) {
                                btn_followTrainee.setVisibility(View.VISIBLE);
                                btn_unfollowTrainee.setVisibility(View.GONE);
                            } else {
                                btn_followTrainee.setVisibility(View.GONE);
                                btn_unfollowTrainee.setVisibility(View.VISIBLE);

                            }

                            ll_one_button.setVisibility(View.VISIBLE);
                            ll_two_buttons.setVisibility(View.INVISIBLE);


                            ll_trainer.setVisibility(View.GONE);
                            ll_trainee.setVisibility(View.VISIBLE);

                            txt_Trainer.setVisibility(View.GONE);
                            rbAddRating.setVisibility(View.GONE);

                            ll_separator.setVisibility(View.GONE);

                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                // ShowTrianeeData();
                                txt_profileName.setText(response.body().getResult().getFirst_name() + " " + response.body().getResult().getLast_name());
                                imageLoader.displayImage(response.body().getResult().getProfile_image(), riv_profile_pic);
                                txt_postCount.setText(response.body().getResult().getPosts_count());
                                txt_FollowersCount.setText(response.body().getResult().getFollowers_count());
                                txt_FollowingsCount.setText(response.body().getResult().getFollowing_count());
                                rbAddRating.setScore(response.body().getResult().getRating());
                                txt_tagline.setText(response.body().getResult().getUser_status());
                                ShowUserPosts(response.body().getResult().getPosts());
                            }
                        }
                    }
                } else {
                    loadingFinished();
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserProfile>> call, Throwable t) {

                loadingFinished();
                UIHelper.showShortToastInCenter(getDockActivity(), t.getMessage());

            }
        });

    }

    private void ShowUserPosts(ArrayList<post> userPost) {

        dataCollection = new ArrayList<profilePostEnt>();
        ImageCollection = new ArrayList<>();
        try {
            for (post postsEnt : userPost) {

                dataCollection.add(new profilePostEnt(postsEnt.getPost_image(), postsEnt.getPost_thumb_image()));
                if (!postsEnt.getPost_image().contains(".mp4")) {
                    ImageCollection.add(new String(postsEnt.getPost_image()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        bindData(dataCollection, 3);
    }

    private void bindData(List<profilePostEnt> dataCollection, int noOfColumns) {
        adapter.clearList();
        gv_pics.setNumColumns(noOfColumns);
        adapter.addAll(dataCollection);
        gv_pics.setAdapter(adapter);
        adapter.notifyDataSetChanged();
/*
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });*/


    }

    private void setListener() {

        iv_grid.setOnClickListener(this);
        iv_list.setOnClickListener(this);
        iv_Home.setOnClickListener(this);
        iv_Calander.setOnClickListener(this);
        iv_Camera.setOnClickListener(this);
        iv_Fav.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        btn_follow.setOnClickListener(this);
        btn_request.setOnClickListener(this);
        btn_edit_or_follow.setOnClickListener(this);

        ll_grid.setOnClickListener(this);
        ll_list.setOnClickListener(this);
        btn_Unfollow.setOnClickListener(this);
        btn_followTrainee.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_unfollowTrainee.setOnClickListener(this);
        txt_postCount.setOnClickListener(this);
        txt_FollowersCount.setOnClickListener(this);
        txt_FollowingsCount.setOnClickListener(this);

        rbAddRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float rating = rbAddRating.getScore();
                int ratingInt = (int) rating + 1;
                rating(ratingInt);

                //   Toast.makeText(getDockActivity(),String.valueOf(rbAddRating.getScore()),Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    private void rating(int ratingInt) {

        Call<ResponseWrapper<User>> callBack = webService.rating(
                prefHelper.getUserId(),
                ratingInt,
                user_id);

        callBack.enqueue(new Callback<ResponseWrapper<User>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<User>> call, Response<ResponseWrapper<User>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<User>> call, Throwable t) {
                loadingFinished();
                UIHelper.showShortToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.profile));
        titleBar.hideSearchBar();
        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefHelper.getUser().getUser_type().equals(AppConstants.trainer))
                    getDockActivity().addDockableFragment(SearchTraineeFragment.newInstance(), "SearchTraineeFragment");
                else
                    popUpDropDown(v);


            }
        });

        titleBar.showSettingButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDockActivity().addDockableFragment(SettingsFragment.newInstance(isNotificationOn, isPublicAccount), "SettingsFragment");

            }
        });

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_list:

                iv_list.setImageResource(R.drawable.list_view);
                iv_grid.setImageResource(R.drawable.grid_view_unselected);
                gv_pics.setNumColumns(1);
                adapter.notifyDataSetChanged();

                break;

            case R.id.iv_grid:

                iv_list.setImageResource(R.drawable.list_view_unselected);
                iv_grid.setImageResource(R.drawable.grid_view);
                gv_pics.setNumColumns(3);
                adapter.notifyDataSetChanged();

                break;

            case R.id.ll_list:

                iv_list.setImageResource(R.drawable.list_view);
                iv_grid.setImageResource(R.drawable.grid_view_unselected);
                gv_pics.setNumColumns(1);
                adapter.notifyDataSetChanged();

                break;

            case R.id.ll_grid:

                iv_list.setImageResource(R.drawable.list_view_unselected);
                iv_grid.setImageResource(R.drawable.grid_view);
                gv_pics.setNumColumns(3);
                adapter.notifyDataSetChanged();

                break;

            case R.id.btn_request:

                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                getDockActivity().addDockableFragment(CalendarFragment.newInstance(user_id, TrainerGymAddress, Speciality), "CalendarFragment");


                break;

            case R.id.btn_follow:

                btn_follow.setVisibility(View.GONE);
                btn_Unfollow.setVisibility(View.VISIBLE);

                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                followUser();

                break;

            case R.id.btn_Unfollow:

                btn_follow.setVisibility(View.VISIBLE);
                btn_Unfollow.setVisibility(View.GONE);

                unfollowUser();

                break;

            case R.id.btn_followTrainee:
                btn_followTrainee.setVisibility(View.GONE);
                btn_edit_or_follow.setVisibility(View.GONE);
                btn_unfollowTrainee.setVisibility(View.VISIBLE);

                followUser();
                break;

            case R.id.btn_unfollowTrainee:
                btn_followTrainee.setVisibility(View.VISIBLE);
                btn_edit_or_follow.setVisibility(View.GONE);
                btn_unfollowTrainee.setVisibility(View.GONE);

                unfollowUser();

                break;


            case R.id.iv_profile:

                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(Integer.parseInt(prefHelper.getUserId())), "TrainerProfileFragment");

                break;

            case R.id.iv_Fav:

                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                getDockActivity().addDockableFragment(FollowingFragment.newInstance(), "FollowingFragment");

                break;

            case R.id.iv_Camera:

                CameraHelper.uploadMedia(getMainActivity());

                break;

            case R.id.iv_Calander:

                if (prefHelper.getUser().getUser_type().equals(AppConstants.trainer)) {
                    getDockActivity().addDockableFragment(TrainingBookingCalenderFragment.newInstance(), "TrainingBookingCalenderFragment");
                } else {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(), "TraineeScheduleFragment");

                }

                break;

            case R.id.iv_Home:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                // rating();
                break;

            case R.id.btn_edit_or_follow:

                getDockActivity().addDockableFragment(EditTraineeProfileFragment.newInstance(), "EditTraineeProfileFragment");

                break;

            case R.id.btn_edit:

                getDockActivity().addDockableFragment(EditTrainerProfileFragment.newInstance(), "EditTrainerProfileFragment");

                break;

            case R.id.rbAddRating:
                //rating();
                // Toast.makeText(getDockActivity(),String.valueOf(rbAddRating.getScore()),Toast.LENGTH_LONG).show();

            case R.id.txt_FollowersCount:
                getDockActivity().addDockableFragment(FollowersCountListFragment.newInstance(Integer.parseInt(user_id)), "FollowingCountListFragment");
                break;

            case R.id.txt_FollowingsCount:
                getDockActivity().addDockableFragment(FollowingCountListFragment.newInstance(Integer.parseInt(user_id)), "FollowersCountListFragment");
                break;


        }
    }


    private void followUser() {

        Call<ResponseWrapper<FollowUser>> callBack = webService.follow(
                prefHelper.getUserId(),
                user_id
        );

        callBack.enqueue(new Callback<ResponseWrapper<FollowUser>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<FollowUser>> call, Response<ResponseWrapper<FollowUser>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                   /* btn_follow.setVisibility(View.GONE);
                    btn_Unfollow.setVisibility(View.VISIBLE);*/

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<FollowUser>> call, Throwable t) {

                loadingFinished();
                UIHelper.showShortToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }

    private void unfollowUser() {

        Call<ResponseWrapper<FollowUser>> callBack = webService.unfollow(
                prefHelper.getUserId(),
                user_id
        );

        callBack.enqueue(new Callback<ResponseWrapper<FollowUser>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<FollowUser>> call, Response<ResponseWrapper<FollowUser>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                   /* btn_follow.setVisibility(View.VISIBLE);
                    btn_Unfollow.setVisibility(View.GONE);*/

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<FollowUser>> call, Throwable t) {

                loadingFinished();
                UIHelper.showShortToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }

    void popUpDropDown(View v) {
        LayoutInflater layoutInflater = (LayoutInflater) getDockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.home_search_items, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                (int) getResources().getDimension(R.dimen.x100),
                (int) getResources().getDimension(R.dimen.x100));

        txt_TrainerProfileFrag = (AnyTextView) popupView.findViewById(R.id.txt_Trainer);
        txt_TrainingProfileFrag = (AnyTextView) popupView.findViewById(R.id.txt_Training);
        txt_LocationProfileFrag = (AnyTextView) popupView.findViewById(R.id.txt_Location);

               /* txt_Trainer.setOnClickListener(this);
                txt_Training.setOnClickListener(this);
                txt_Location.setOnClickListener(this);*/


        txt_TrainerProfileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(SearchTrainerFragment.newInstance(), "SearchTraineeFragment");
                popupWindow.dismiss();
            }
        });

        txt_TrainingProfileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(TrainingSearchFragment.newInstance(), "TrainingSearchFragment");
                popupWindow.dismiss();
            }
        });

        txt_LocationProfileFrag.setOnClickListener(new View.OnClickListener() {
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
    public void OnImageListener(int position) {

    }
}
