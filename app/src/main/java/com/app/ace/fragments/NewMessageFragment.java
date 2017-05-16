package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.NewMessageDataItem;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.entities.UserProfile;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.NewMessageListItemBinder;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static android.R.attr.filter;
import static android.R.attr.key;
import static com.app.ace.R.id.txt_noresult;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class NewMessageFragment extends BaseFragment implements TextWatcher {

    @InjectView(R.id.lv_newMessage)
    private ListView lv_newMessage;

    @InjectView(R.id.edit_sendTo)
    AnyEditTextView edit_sendTo;

    @InjectView(R.id.txt_noresult)
    AnyTextView txt_noresult;

    private ArrayListAdapter<UserProfile> adapter;

    private ArrayList<UserProfile> userCollection = new ArrayList<>();

    public static NewMessageFragment newInstance() {
        return new NewMessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<UserProfile>(getDockActivity(), new NewMessageListItemBinder(getDockActivity()));
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
        if (edit_sendTo != null) {
            Call<ResponseWrapper<ArrayList<UserProfile>>> callBack = webService.getSearchUser(edit_sendTo.getText().toString(), AppConstants.trainer);

            callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserProfile>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Response<ResponseWrapper<ArrayList<UserProfile>>> response) {
                    bindview(response.body().getResult());
                }

                @Override
                public void onFailure(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Throwable t) {
                    Log.e("Search", t.toString());
                }
            });

        }
    }

    private void bindview(ArrayList<UserProfile> resultuser) {
        userCollection = new ArrayList<>();
        if (resultuser.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            lv_newMessage.setVisibility(View.GONE);
        }
        else {
            txt_noresult.setVisibility(View.GONE);
            lv_newMessage.setVisibility(View.VISIBLE);
        }

        for (UserProfile user : resultuser
                ) {
            userCollection.add(user);
        }


        bindData(userCollection);
    }

    private void bindData(ArrayList<UserProfile> userCollection) {
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

    public ArrayList<UserProfile> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<UserProfile> arrayList = new ArrayList<>();

        for (UserProfile item : userCollection) {
            String UserName=item.getFirst_name()+" "+item.getLast_name();
            if (UserName.contains(keyword)) {
                arrayList.add(item);
            }
        }
        return arrayList;

    }
}
