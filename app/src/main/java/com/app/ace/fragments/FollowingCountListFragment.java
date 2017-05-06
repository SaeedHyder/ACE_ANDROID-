package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.FollowingCountDataItem;
import com.app.ace.entities.FollowingCountListEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.FollowingCountListBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 5/2/2017.
 */

public class FollowingCountListFragment extends BaseFragment {

    @InjectView(R.id.listViewFollowingCount)
    private ListView listView;

    private ArrayListAdapter<FollowingCountDataItem> adapter;

    private ArrayList<FollowingCountDataItem> FollowingsuserCollection = new ArrayList<>();

    public static String USER_ID = "User_Id";
    String user_id;

    public static FollowingCountListFragment newInstance() {


        return new FollowingCountListFragment();
    }

    public static FollowingCountListFragment newInstance(int user_id) {

        Bundle args = new Bundle();
        args.putString(USER_ID, String.valueOf(user_id));
        FollowingCountListFragment fragment = new FollowingCountListFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user_id = getArguments().getString(USER_ID);
        } else {
            user_id = prefHelper.getUserId();
        }

        adapter = new ArrayListAdapter<FollowingCountDataItem>(getDockActivity(), new FollowingCountListBinder(getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following_count, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListener();
       // getUserData();
        getAllFollowings();
    }
    private void getFollowing(ArrayList<FollowingCountListEnt> followingCountListEntArrayList) {

        FollowingsuserCollection = new ArrayList<>();

        for(FollowingCountListEnt msg : followingCountListEntArrayList){

            FollowingsuserCollection.add(new FollowingCountDataItem(msg.getProfile_image(),msg.getFirst_name()+" "+msg.getLast_name(),msg.getId()));



        }

        bindData(FollowingsuserCollection);
    }
   /* private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new FollowingCountDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt) ));
        userCollection.add(new FollowingCountDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith"));


        bindData(userCollection);
    }*/

  /*  private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().addDockableFragment(FollowingCountListFragment.newInstance(), "TrainersAvailabeFragment");
            }
        });
    }*/

    private void bindData(ArrayList<FollowingCountDataItem> userCollection) {
        adapter.clearList();
        listView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Followings");


       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }

    private void getAllFollowings() {

        loadingStarted();

        Call<ResponseWrapper<ArrayList<FollowingCountListEnt>>> callBack = webService.GetFollowingCountList(user_id);
        loadingStarted();
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<FollowingCountListEnt>>>() {

            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<FollowingCountListEnt>>> call, Response<ResponseWrapper<ArrayList<FollowingCountListEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    getFollowing(response.body().getResult());

                }

                else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<FollowingCountListEnt>>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }
}
