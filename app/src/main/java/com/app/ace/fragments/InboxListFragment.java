package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.InboxDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.InboxListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class InboxListFragment extends BaseFragment {

    @InjectView(R.id.listView)
    private ListView listView;

    private ArrayListAdapter<InboxDataItem> adapter;

    private ArrayList<InboxDataItem> userCollection = new ArrayList<>();

    public static InboxListFragment newInstance() {

        return new InboxListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<InboxDataItem>(getDockActivity(), new InboxListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inboxlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
        getUserData();
    }

    private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new InboxDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt), getString(R.string.i_wont_be) ));
        userCollection.add(new InboxDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith", getString(R.string.what_other_training)));
        userCollection.add(new InboxDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam), getString(R.string.please_reply)));

        bindData(userCollection);
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().addDockableFragment(ChatFragment.newInstance(), "ChatFragment");
            }
        });
    }

    private void bindData(ArrayList<InboxDataItem> userCollection) {
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
        titleBar.setSubHeading(getString(R.string.inbox));

        titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
            }
        });



    }
}