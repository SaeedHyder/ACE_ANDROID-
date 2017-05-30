package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.FollowUser;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.UserNotificatoin;
import com.app.ace.entities.YouDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.FollowService;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.YouListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.string.comments;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouListFragment extends BaseFragment implements FollowService {

    @InjectView(R.id.listViewFollow)
    private ListView listView;

    private ArrayListAdapter<YouDataItem> adapter;

    private ArrayList<YouDataItem> userCollection = new ArrayList<>();

    public static YouListFragment newInstance() {

        return new YouListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<YouDataItem>(getDockActivity(), new YouListItemBinder(this,getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_you, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListener();
        setData();
       // getUserData();
    }

    private void setData() {

        Call<ResponseWrapper<ArrayList<UserNotificatoin>>> callBack = webService.UserNotification(prefHelper.getUserId());

        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserNotificatoin>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserNotificatoin>>> call, Response<ResponseWrapper<ArrayList<UserNotificatoin>>> response) {
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    setDataInNOtificationList(response.body().getResult());

                }
                else
                {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserNotificatoin>>> call, Throwable t) {
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    private void setDataInNOtificationList(ArrayList<UserNotificatoin> result) {

        userCollection = new ArrayList<>();

        for(UserNotificatoin item : result) {
            try {

                if (item.getAction_type().contains("post")) {
                    userCollection.add(new YouDataItem(item.getSender().getProfile_image(), item.getSender().getFirst_name() + " " + item.getSender().getLast_name(), item.getMessage(), item.getPost().getPost_image(), item.getCreated_at(), item.getSender_id(), null));

                } else {
                    userCollection.add(new YouDataItem(item.getSender().getProfile_image(), item.getSender().getFirst_name() + " " + item.getSender().getLast_name(), item.getMessage(), null, item.getCreated_at(), item.getSender_id(), item.getIs_following()));

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        bindData(userCollection);
    }}

   /* private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new YouDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt),"like your photo",R.drawable.sunset ));
        userCollection.add(new YouDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith", "like your photo",R.drawable.sunset));
       // userCollection.add(new TrainerAvailDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam), getString(R.string.please_reply)));

        bindData(userCollection);
    }*/

    /*private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().addDockableFragment(ChatFragment.newInstance(), "TrainersAvailabeFragment");
            }
        });
    }*/

    private void bindData(ArrayList<YouDataItem> userCollection) {
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
        titleBar.setSubHeading(getString(R.string.follow));

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }

    @Override
    public void followUser(String senderId, int position, int i) {

            Call<ResponseWrapper<FollowUser>> callBack = webService.follow(
                    prefHelper.getUserId(),
                    senderId
            );

            callBack.enqueue(new Callback<ResponseWrapper<FollowUser>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<FollowUser>> call, Response<ResponseWrapper<FollowUser>> response) {

                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<FollowUser>> call, Throwable t) {

                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

                }
            });

        YouDataItem updatedItem = (YouDataItem) adapter.getItem(position);
        updatedItem.setIsfollowing(String.valueOf(i));
        adapter.add(updatedItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void UnFollowUser(String senderId, int position, int i) {
        Call<ResponseWrapper<FollowUser>> callBack = webService.unfollow(
                prefHelper.getUserId(),
                senderId
        );

        callBack.enqueue(new Callback<ResponseWrapper<FollowUser>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<FollowUser>> call, Response<ResponseWrapper<FollowUser>> response) {


                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());


                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<FollowUser>> call, Throwable t) {


                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });

        YouDataItem updatedItem = (YouDataItem) adapter.getItem(position);
        updatedItem.setIsfollowing(String.valueOf(i));
        adapter.add(updatedItem);
        adapter.notifyDataSetChanged();

    }
}
