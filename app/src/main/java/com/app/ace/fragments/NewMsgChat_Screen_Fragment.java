package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.ace.R;
import com.app.ace.entities.ChatDataItem;
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.edtChat;
import static com.app.ace.fragments.ChatFragment.CONVERSATION_ID;
import static com.app.ace.fragments.ChatFragment.Receiver_ID;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class NewMsgChat_Screen_Fragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.iv_sendbtn)
    ImageView iv_sendbtn;

    @InjectView(R.id.edit_sendTo)
    AnyTextView edit_sendTo;

    @InjectView (R.id.edit_msgText)
    AnyEditTextView edit_msgText;

    public static String USERNAME = "userName";
    public static String ID = "id";
    public static String POSTPATH="postpath";
    public String post_path;
    public String username;
    public String Receiverid;

    public static NewMsgChat_Screen_Fragment newInstance()
    {
        return new NewMsgChat_Screen_Fragment();
    }


    public static NewMsgChat_Screen_Fragment newInstance(int id, String userName)
    {
        Bundle args = new Bundle();
        args.putString(ID, String.valueOf(id));
        args.putString(USERNAME, userName);
        NewMsgChat_Screen_Fragment fragment = new NewMsgChat_Screen_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NewMsgChat_Screen_Fragment newInstance(int id, String userName,String PostPath)
    {
        Bundle args = new Bundle();
        args.putString(ID, String.valueOf(id));
        args.putString(USERNAME, userName);
        args.putString(POSTPATH,PostPath);
        NewMsgChat_Screen_Fragment fragment = new NewMsgChat_Screen_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Receiverid = getArguments().getString(ID);
            username = getArguments().getString(USERNAME);
            post_path=getArguments().getString(POSTPATH);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_screen_msg, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_sendTo.setText(username);
        edit_msgText.setText(post_path);


        setListner();
    }

    private void setListner() {

        iv_sendbtn.setOnClickListener(this);
    }

    private void sendMsg() {

        loadingStarted();

        Call<ResponseWrapper<ArrayList<MsgEnt>>> callBack = webService.SendMsg(
                prefHelper.getUserId(),
                Receiverid,
                edit_msgText.getText().toString());
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<MsgEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Response<ResponseWrapper<ArrayList<MsgEnt>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    if(response.body().getResult().isEmpty())
                    {
                        hideKeyboard();
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                    else
                    {
                        hideKeyboard();
                        ArrayList<MsgEnt> msg =response.body().getResult();

                        getDockActivity().addDockableFragment(ChatFragment.newInstance(String.valueOf(msg.get(0).getMessage().getConversation_id()), String.valueOf(msg.get(0).getReceiver().getId()),username,post_path, String.valueOf(msg.get(0).getIs_following()),msg.get(0).getReceiver().getProfile_image(),msg.get(0).getReceiver().getFirst_name()+" "+msg.get(0).getReceiver().getLast_name(),msg.get(0).getSender_block(),msg.get(0).getReceiver_block()),"ChatFragment");

                    }
                }
                else
                {
                    hideKeyboard();
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Throwable t) {

                loadingFinished();
                hideKeyboard();
                System.out.println(t.toString());
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });


    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.New_Message));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_sendbtn:

                sendMsg();
                break;
        }
    }
}
