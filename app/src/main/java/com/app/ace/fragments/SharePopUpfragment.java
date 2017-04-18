package com.app.ace.fragments;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.entities.SharePopUpItemsEnt;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.adapters.RecyclerViewAdapterSharePop;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;


public class SharePopUpfragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.btn_Cancel)
    Button btn_Cancel;
/*

    @InjectView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
*/


    private List<SharePopUpItemsEnt> SharePopUpList = new ArrayList<>();
    private RecyclerViewAdapterSharePop mAdapter;

    private ArrayList<SharePopUpItemsEnt> userCollection = new ArrayList<>();

    public static SharePopUpfragment newInstance() {

        return new SharePopUpfragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mAdapter = new ArrayListAdapter<SharePopUpItemsEnt>(getDockActivity(), new RecyclerViewAdapterSharePop());*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_pop, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lv_SendTo);
        recyclerView.setLayoutManager(layoutManager);

       /* hlistViewSend.setHasFixedSize(true);
        hlistViewSend.setLayoutManager(new LinearLayoutManager(getDockActivity()));*/



        getUserData();

        mAdapter = new RecyclerViewAdapterSharePop(userCollection,getDockActivity());
       /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());*/
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // getUserData();


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();

    }

    private void setListener() {

        btn_Cancel.setOnClickListener(this);
      //  CircularImageSharePop.setOnClickListener(this);

     /*   btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btn_Cancel:
                        getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                        break;
                }
                switch (view.getId()) {
                    case R.id.CircularImageSharePop:
                        getDockActivity().addDockableFragment(ChatFragment.newInstance(), "ChatFragment");
                        break;
                }

            }
        });*/
        /*listViewCommentSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().addDockableFragment(ChatFragment.newInstance(), "ChatFragment");
            }
        });*/
    }

    private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new SharePopUpItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt) ));
        userCollection.add(new SharePopUpItemsEnt("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith"));
        userCollection.add(new SharePopUpItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam)));
        userCollection.add(new SharePopUpItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam)));
        userCollection.add(new SharePopUpItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam)));
        userCollection.add(new SharePopUpItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam)));


        /*bindData(userCollection);*/

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_Cancel:
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                break;
           /* case R.id.CircularImageSharePop:
                getDockActivity().addDockableFragment(ChatFragment.newInstance(), "ChatFragment");
                break;*/
        }
    }
}
