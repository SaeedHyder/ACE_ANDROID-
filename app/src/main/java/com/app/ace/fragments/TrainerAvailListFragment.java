package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.TrainerAvailDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.TraineravaialListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class TrainerAvailListFragment extends BaseFragment {

    @InjectView(R.id.listViewTrainerAvailable)
    private ListView listView;

    private ArrayListAdapter<TrainerAvailDataItem> adapter;

    private ArrayList<TrainerAvailDataItem> userCollection = new ArrayList<>();

    public static TrainerAvailListFragment newInstance() {

        return new TrainerAvailListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<TrainerAvailDataItem>(getDockActivity(), new TraineravaialListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer_available, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListener();
        getUserData();
    }

    private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new TrainerAvailDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt), getString(R.string.flexibility_training) ));
        userCollection.add(new TrainerAvailDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith", "My Detail"));
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

    private void bindData(ArrayList<TrainerAvailDataItem> userCollection) {
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
        titleBar.setSubHeading("Trainers Available");

       /* titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });*/
    }
}
