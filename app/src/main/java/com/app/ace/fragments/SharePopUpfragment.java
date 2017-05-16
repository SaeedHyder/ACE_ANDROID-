package com.app.ace.fragments;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.SharePopUpItemsEnt;
import com.app.ace.entities.UserProfile;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.ui.adapters.RecyclerViewAdapterSharePop;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.fragments.CommentSectionFragment.POSTID;


public class SharePopUpfragment extends BaseFragment implements View.OnClickListener,TextWatcher {

    @InjectView(R.id.btn_Cancel)
    Button btn_Cancel;

    @InjectView(R.id.txt_noresult)
    AnyTextView txt_noresult;

    @InjectView(R.id.tv_Search)
    AnyEditTextView tv_Search;

    View SharePopup;
    public static String POSTPICPATH = "post_id";
    String post_pic_path;


    private List<SharePopUpItemsEnt> SharePopUpList = new ArrayList<>();
    private RecyclerViewAdapterSharePop mAdapter;

    private ArrayList<UserProfile> userCollection = new ArrayList<>();

    public static SharePopUpfragment newInstance() {

        return new SharePopUpfragment();
    }

    public static SharePopUpfragment newInstance(String PostPicPath) {

        Bundle args = new Bundle();
        args.putString(POSTPICPATH, String.valueOf(PostPicPath));
        SharePopUpfragment fragment = new SharePopUpfragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post_pic_path = getArguments().getString(POSTPICPATH);
        }
        /*mAdapter = new ArrayListAdapter<SharePopUpItemsEnt>(getDockActivity(), new RecyclerViewAdapterSharePop());*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share_pop, container, false);
        SharePopup=view;
        getNewMsgUserData(view);
       // getUserData();

        // getUserData();

        return view;
    }

    private void setDataInAdapter(View view, ArrayList<UserProfile> searchedArray) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lv_SendTo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapterSharePop(searchedArray,getDockActivity(),post_pic_path);
       /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());*/
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        tv_Search.addTextChangedListener(this);

    }



    private void setListener() {

        btn_Cancel.setOnClickListener(this);
    }

    private void getNewMsgUserData(final View view) {
        String search;
        if(tv_Search!=null) search = tv_Search.getText().toString();
        else search="";


            Call<ResponseWrapper<ArrayList<UserProfile>>> callBack = webService.getSearchUser(search, AppConstants.trainer);

            callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserProfile>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Response<ResponseWrapper<ArrayList<UserProfile>>> response) {
                    bindview(response.body().getResult());
                    setDataInAdapter(view, userCollection);
                }

                @Override
                public void onFailure(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Throwable t) {
                    Log.e("Search", t.toString());
                }
            });

    }

    private void bindview(ArrayList<UserProfile> resultuser) {
        userCollection = new ArrayList<>();
        if (resultuser.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            //lv_newMessage.setVisibility(View.GONE);
        }
        else {
            txt_noresult.setVisibility(View.GONE);
           // lv_newMessage.setVisibility(View.VISIBLE);
        }

        for (UserProfile user : resultuser
                ) {
            userCollection.add(user);
        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


        setDataInAdapter(SharePopup,getSearchedArray(s.toString()));


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
