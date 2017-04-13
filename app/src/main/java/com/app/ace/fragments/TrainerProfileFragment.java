package com.app.ace.fragments;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;

import com.app.ace.BaseApplication;
import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.UserPicItemBinder;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.CustomRatingBar;
import com.app.ace.ui.views.ExpandableGridView;
import com.app.ace.ui.views.TitleBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.txt_Location;
import static com.app.ace.R.id.txt_Training;

/**
 * Created by khan_muhammad on 3/17/2017.
 */

public class TrainerProfileFragment extends BaseFragment implements View.OnClickListener{

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


    private ArrayListAdapter<String> adapter;
    private List<String> dataCollection;

    private DockActivity activity;
    AnyTextView txt_TrainerProfileFrag;
    AnyTextView txt_TrainingProfileFrag;
    AnyTextView txt_LocationProfileFrag;


    public static TrainerProfileFragment newInstance()
    {


        return new TrainerProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        adapter = new ArrayListAdapter<String>(getDockActivity(), new UserPicItemBinder());

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

        if(AppConstants.is_show_trainer)
        {

            ll_one_button.setVisibility(View.INVISIBLE);
            ll_two_buttons.setVisibility(View.VISIBLE);

            ll_trainer.setVisibility(View.VISIBLE);
            ll_trainee.setVisibility(View.GONE);

            txt_Trainer.setVisibility(View.VISIBLE);
            rbAddRating.setVisibility(View.VISIBLE);

            txt_profileName.setText(getString(R.string.charlie_hunnam));
            riv_profile_pic.setBackgroundResource(R.drawable.profile_pic_trainer);


            ll_separator.setVisibility(View.VISIBLE);

        }else{

            ll_one_button.setVisibility(View.VISIBLE);
            ll_two_buttons.setVisibility(View.INVISIBLE);

            ll_trainer.setVisibility(View.GONE);
            ll_trainee.setVisibility(View.VISIBLE);

            txt_Trainer.setVisibility(View.GONE);
            rbAddRating.setVisibility(View.GONE);

            txt_profileName.setText(getString(R.string.james_blunt));
            riv_profile_pic.setBackgroundResource(R.drawable.profile_pic);

            ll_separator.setVisibility(View.GONE);

        }

        getBrowsedAdData();
        setListener();
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

    }


    private void getBrowsedAdData() {

        dataCollection = new ArrayList<String>();

        dataCollection.add("drawable://" + R.drawable.pic1);
        dataCollection.add("drawable://" + R.drawable.pic3);
        dataCollection.add("drawable://" + R.drawable.pic4);
        dataCollection.add("drawable://" + R.drawable.pic1);
        dataCollection.add("drawable://" + R.drawable.pic3);
        dataCollection.add("drawable://" + R.drawable.pic4);
        dataCollection.add("drawable://" + R.drawable.pic1);
        dataCollection.add("drawable://" + R.drawable.pic3);
        dataCollection.add("drawable://" + R.drawable.pic4);

        bindData(dataCollection,3);
    }

    private void bindData(List<String> dataCollection,int noOfColumns) {
        adapter.clearList();
        gv_pics.setAdapter(adapter);
        gv_pics.setNumColumns(noOfColumns);
        adapter.addAll(dataCollection);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.profile));

        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDockActivity().addDockableFragment(HomeFragment.newInstance(),"HomeFragment");
             //   homeFragment.popUpDropdown(v);
                popUpDropDown(v);

            }
        });

        titleBar.showSettingButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDockActivity().addDockableFragment(SettingsFragment.newInstance(),"SettingsFragment");

            }
        });

    }


    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){
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
                getDockActivity().addDockableFragment(CalendarFragment.newInstance(),"CalendarFragment");


                break;

            case R.id.btn_follow:

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

            case R.id.iv_profile:

                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(),"TrainerProfileFragment");

                break;

            case R.id.iv_Fav:

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

                break;

            case R.id.iv_Camera:


                break;

            case R.id.iv_Calander:

                if(AppConstants.is_show_trainer){
                    getDockActivity().addDockableFragment(TrainerBookingCalendarFragment.newInstance(),"TrainerBookingCalendarFragment");

                }
                else
                {
                    getDockActivity().addDockableFragment(TraineeScheduleFragment.newInstance(),"TraineeScheduleFragment");

                }

                break;

            case R.id.iv_Home:

                getDockActivity().addDockableFragment(HomeFragment.newInstance(),"HomeFragment");

                break;

            case R.id.btn_edit_or_follow:

                if(AppConstants.is_show_trainer){

                    getDockActivity().addDockableFragment(EditTrainerProfileFragment.newInstance(),"EditTrainerProfileFragment");

                }else{

                    getDockActivity().addDockableFragment(EditTraineeProfileFragment.newInstance(),"EditTraineeProfileFragment");

                }



                break;

        }
    }

    void popUpDropDown(View v)
    {
        LayoutInflater layoutInflater = (LayoutInflater)getDockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.home_search_items, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                (int)getResources().getDimension(R.dimen.x100),
                (int)getResources().getDimension(R.dimen.x100));

        txt_TrainerProfileFrag=(AnyTextView)popupView.findViewById(R.id.txt_Trainer);
        txt_TrainingProfileFrag=(AnyTextView)popupView.findViewById(R.id.txt_Training);
        txt_LocationProfileFrag=(AnyTextView)popupView.findViewById(R.id.txt_Location);

               /* txt_Trainer.setOnClickListener(this);
                txt_Training.setOnClickListener(this);
                txt_Location.setOnClickListener(this);*/


        txt_TrainerProfileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(SearchPeopleFragment.newInstance(), "SearchPeopleFragment");
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


}
