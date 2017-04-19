package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.NewMessageDataItem;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.NewMessageListItemBinder;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

import static android.R.attr.filter;
import static android.R.attr.key;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class NewMessageFragment extends BaseFragment implements TextWatcher {

    @InjectView(R.id.lv_newMessage)
    private ListView lv_newMessage;

    @InjectView(R.id.edit_sendTo)
    AnyEditTextView edit_sendTo;

    private ArrayListAdapter<NewMessageDataItem> adapter;

    private ArrayList<NewMessageDataItem> userCollection = new ArrayList<>();

    public static NewMessageFragment newInstance() {
        return new NewMessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<NewMessageDataItem>(getDockActivity(), new NewMessageListItemBinder(getDockActivity()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_message, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_sendTo.addTextChangedListener(this);

        getNewMsgUserData();
    }

    private void getNewMsgUserData() {
        userCollection = new ArrayList<>();
        userCollection.add(new NewMessageDataItem("drawable://" + R.drawable.profile_pic, "Charlie Hannan"));
        userCollection.add(new NewMessageDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith"));
        userCollection.add(new NewMessageDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam)));

        bindData(userCollection);
    }

    private void bindData(ArrayList<NewMessageDataItem> userCollection) {
        adapter.clearList();
        lv_newMessage.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("New Message");

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //UIHelper.showShortToastInCenter(getContext(), " Testing " + s.toString());
        bindData(getSearchedArray(s.toString()));


    }

    public ArrayList<NewMessageDataItem> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<NewMessageDataItem> arrayList = new ArrayList<>();

        for (NewMessageDataItem item : userCollection) {
            if (item.getUserName().contains(keyword)) {
                arrayList.add(item);
            }
        }
        return arrayList;

    }
}
