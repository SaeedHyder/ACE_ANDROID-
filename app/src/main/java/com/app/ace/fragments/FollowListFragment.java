package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.ace.R;

import com.app.ace.entities.FollowDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.FollowListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class FollowListFragment extends BaseFragment {

    @InjectView(R.id.listViewFollow)
    private ListView listView;

    private ArrayListAdapter<FollowDataItem> adapter;

    private ArrayList<FollowDataItem> userCollection ;

    private ArrayList<Integer> arrChildCollection ;


    public static FollowListFragment newInstance() {

        return new FollowListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<FollowDataItem>(getDockActivity(), new FollowListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListener();
        getUserData();
    }

    private void getUserData() {

        userCollection= new ArrayList<>();
       /* arrChildCollection= new ArrayList<>();*/

       int i,j;
        for(i=1; i<5; i++)
        {
           /* userCollection= new ArrayList<>();*/
            arrChildCollection= new ArrayList<>();

            for(j=1; j<=i; j++)
            {
                arrChildCollection.add(R.drawable.nature);

            }
            userCollection.add(new FollowDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith", "My Detail", arrChildCollection));

        }


          bindData(userCollection);

    }

    private void bindData(ArrayList<FollowDataItem> userCollection) {
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
