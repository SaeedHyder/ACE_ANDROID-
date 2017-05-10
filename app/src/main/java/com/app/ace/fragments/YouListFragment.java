package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.YouDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.YouListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouListFragment extends BaseFragment {

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

        adapter = new ArrayListAdapter<YouDataItem>(getDockActivity(), new YouListItemBinder());
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
        getUserData();
    }

    private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new YouDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt),"like your photo",R.drawable.sunset ));
        userCollection.add(new YouDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith", "like your photo",R.drawable.sunset));
       // userCollection.add(new TrainerAvailDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam), getString(R.string.please_reply)));

        bindData(userCollection);
    }

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
        titleBar.setSubHeading("Follow");

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }
}