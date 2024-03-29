package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.FollowersCountDataItem;
import com.app.ace.entities.FollowersCountListEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.FollowersCountListBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 5/2/2017.
 */

public class FollowersCountListFragment extends BaseFragment {

    @InjectView(R.id.listViewFollowersCount)
    private ListView listView;
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;

    private ArrayListAdapter<FollowersCountDataItem> adapter;

    private ArrayList<FollowersCountDataItem> FollowersuserCollection = new ArrayList<>();

    String user_id;
    public static String USER_ID = "User_Id";

    public static FollowersCountListFragment newInstance() {

        return new FollowersCountListFragment();
    }

    public static FollowersCountListFragment newInstance(int user_id) {

        Bundle args = new Bundle();
        args.putString(USER_ID, String.valueOf(user_id));
        FollowersCountListFragment fragment = new FollowersCountListFragment();
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

        adapter = new ArrayListAdapter<FollowersCountDataItem>(getDockActivity(), new FollowersCountListBinder(getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followers_count, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListener();
        //getFollowers();
        getAllFollowers();
        listViewListner();

    }

    private void listViewListner() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(FollowersuserCollection.get(i).getId()), "TrainerProfileFragment");

            }
        });

    }

    private void getFollowers(ArrayList<FollowersCountListEnt> followersCountListEntArrayList) {

        FollowersuserCollection = new ArrayList<>();


        if (followersCountListEntArrayList.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        for (FollowersCountListEnt msg : followersCountListEntArrayList) {

            FollowersuserCollection.add(new FollowersCountDataItem(msg.getProfile_image(), msg.getFirst_name()
                    + " " + msg.getLast_name(), msg.getId()));

            //  userCollection.add(new InboxDataItem(msg.getReceiver().getProfile_image(),msg.getReceiver().getFirst_name()+" "+msg.getReceiver().getLast_name(),msg.getMessage().getMessage_text(),msg.getMessage().getConversation_id()));

        }

        bindData(FollowersuserCollection);
    }


    private void bindData(ArrayList<FollowersCountDataItem> userCollection) {
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
        titleBar.setSubHeading(getString(R.string.Followers));


       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }


    private void getAllFollowers() {

        loadingStarted();

        Call<ResponseWrapper<ArrayList<FollowersCountListEnt>>> callBack = webService.GetFollowersCountList(user_id);
        loadingStarted();
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<FollowersCountListEnt>>>() {

            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<FollowersCountListEnt>>> call,
                                   Response<ResponseWrapper<ArrayList<FollowersCountListEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    if (response.body().getUserDeleted() == 0) {
                        getFollowers(response.body().getResult());
                    } else {
                        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                        dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogHelper.hideDialog();
                                getDockActivity().popBackStackTillEntry(0);
                                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                            }
                        },response.body().getMessage());
                        dialogHelper.showDialog();
                    }
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<FollowersCountListEnt>>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }
}
