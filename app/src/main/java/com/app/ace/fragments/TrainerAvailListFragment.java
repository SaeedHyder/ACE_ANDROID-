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
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.UserProfile;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TraineravaialListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class TrainerAvailListFragment extends BaseFragment {

    public static String body_building_type = "body_building_type";
    @InjectView(R.id.listViewTrainerAvailable)
    private ListView listView;
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;
    private ArrayListAdapter<UserProfile> adapter;

    private ArrayList<UserProfile> userCollection = new ArrayList<>();

    public static TrainerAvailListFragment newInstance() {

        return new TrainerAvailListFragment();
    }

    public static TrainerAvailListFragment newInstance(String bodyBuildingType) {
        Bundle args = new Bundle();
        args.putString(body_building_type, bodyBuildingType);
        TrainerAvailListFragment fragment = new TrainerAvailListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            body_building_type = getArguments().getString(body_building_type);
        } else {
            body_building_type = "";
        }
        adapter = new ArrayListAdapter<UserProfile>(getDockActivity(), new TraineravaialListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer_available, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Call<ResponseWrapper<ArrayList<UserProfile>>> callBack = webService.getTrainingSearch("null", body_building_type, getMainActivity().selectedLanguage());
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserProfile>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserProfile>>> call,
                                   Response<ResponseWrapper<ArrayList<UserProfile>>> response) {

                if (response.body().getUserDeleted() == 0) {
                    getUserData(response.body().getResult());
                } else {
                    final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                    dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogHelper.hideDialog();
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        }
                    });
                    dialogHelper.showDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
        setListener();

    }

    private void getUserData(ArrayList<UserProfile> resultuser) {

        userCollection = new ArrayList<>();
        if (resultuser.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        for (UserProfile user : resultuser
                ) {
            userCollection.add(user);
        }
        // userCollection.add(new TrainerAvailDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam), getString(R.string.please_reply)));

        bindData(userCollection);
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(userCollection.get(position).getId()));
            }
        });
    }

    private void bindData(ArrayList<UserProfile> userCollection) {
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
        titleBar.setSubHeading(getString(R.string.Trainers_Available));

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }
}
