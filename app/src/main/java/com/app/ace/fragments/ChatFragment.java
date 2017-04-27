package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.ChatDataItem;
import com.app.ace.entities.HomeListDataEnt;
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.global.CommentToChatMsgConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.ChatListBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static android.os.Build.VERSION_CODES.M;
import static com.app.ace.fragments.TrainerProfileFragment.USER_ID;
import static com.app.ace.global.AppConstants.user_id;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class ChatFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.listView)
    private ListView listView;

    @InjectView(R.id.edtChat)
    private AnyEditTextView edtChat;

    @InjectView(R.id.imgSend)
    private ImageView imgSend;

    boolean isSender=true;

    String stringer;
    String SenderFullName;
    String ConversationId;
    public int receiverId;
    public int SenderId;

    public static String SIGNUP_MODEL = "signup_model";
    public static String CONVERSATION_ID = "conversation_id";

    private ArrayListAdapter<ChatDataItem> adapter;
    private ArrayList<ChatDataItem> collection = new ArrayList<>();


    CommentToChatMsgConstants commentToChatMsgConstants;

    public static ChatFragment newInstance()
    {
        return new ChatFragment();
    }

    public static ChatFragment newInstance(String conversationId)
    {
        Bundle args = new Bundle();
        args.putString(CONVERSATION_ID, conversationId);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static ChatFragment newInstance(CommentToChatMsgConstants commentToChatMsgConstants) {

        Bundle args = new Bundle();
        args.putString(SIGNUP_MODEL, new Gson().toJson(commentToChatMsgConstants));
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ConversationId = getArguments().getString(CONVERSATION_ID);
           // Toast.makeText(getDockActivity(), ConversationId, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getDockActivity(), "No Conversation", Toast.LENGTH_LONG).show();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (getArguments() != null) {


            String jsonString = getArguments().getString(SIGNUP_MODEL);
            if (jsonString != null)
                commentToChatMsgConstants = new Gson().fromJson(jsonString, CommentToChatMsgConstants.class);
        }
        adapter = new ArrayListAdapter<ChatDataItem>(getDockActivity(), new ChatListBinder(getDockActivity()));
        return inflater.inflate(R.layout.layout_chatfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getAllMsges();

        setListener();
        //getChatData();
    }

    private void getAllMsges() {

        loadingStarted();

        Call<ResponseWrapper<ArrayList<MsgEnt>>> callBack = webService.GetConversation(
                ConversationId);
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<MsgEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Response<ResponseWrapper<ArrayList<MsgEnt>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    setConversation(response.body().getResult());

                }
                else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }



    private void setConversation(ArrayList<MsgEnt> msgArrayList) {

        collection = new ArrayList<>();

        SenderId= msgArrayList.get(0).getSender().getId();
        receiverId=msgArrayList.get(0).getReceiver().getId();

        for(MsgEnt msgEnt : msgArrayList){


            if(msgEnt.getSender().getId()== Integer.parseInt(prefHelper.getUserId()))
            {
                isSender=false;
            }
            else {
                isSender = true;
            }

            collection.add(new ChatDataItem(msgEnt.getSender().getProfile_image(),msgEnt.getMessage().getMessage_text(),msgEnt.getMessage().getCreated_at(),msgEnt.getReceiver().getProfile_image(),msgEnt.getMessage().getMessage_text(),msgEnt.getMessage().getCreated_at(),isSender,msgEnt.getSender().getId()));

        }
        bindData(collection);

    }

    private void setListener() {
        imgSend.setOnClickListener(this);
    }

 /*   private void getChatData() {

        collection = new ArrayList<>();

        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, "Hi", "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", true));
        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, "Hello", "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", false));
        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.lorem_ipsum), "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", true));
        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, "Hello", "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", false));
        if (commentToChatMsgConstants!=null) {
            collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.i_wont_be), "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, commentToChatMsgConstants.getCommentC(), "6 mins ago", false));
        }
        bindData(collection);
    }*/

    private void bindData(ArrayList<ChatDataItem> collection) {
        adapter.clearList();
        listView.setAdapter(adapter);
        adapter.addAll(collection);
        adapter.notifyDataSetChanged();

        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(adapter.getCount() - 1);
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();

        titleBar.showHelpButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                getDockActivity().addDockableFragment(FriendsInfoFragment.newInstance(), "FriendsInfoFragment");
            }
        });

        titleBar.setSubHeading(getString(R.string.james_blunt));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSend:
                if (edtChat.getText().length() > 0) {

                    sendMsg();
                   // collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.i_wont_be), "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, edtChat.getText().toString(), "6 mins ago", false));
                    edtChat.getText().clear();
                  //  bindData(collection);
                }
                break;
        }
    }

    private void sendMsg() {

        loadingStarted();

        Call<ResponseWrapper<ArrayList<MsgEnt>>> callBack = webService.SendMsg(
                prefHelper.getUserId(),
                String.valueOf(receiverId),
                edtChat.getText().toString());
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<MsgEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Response<ResponseWrapper<ArrayList<MsgEnt>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    ArrayList<MsgEnt> msg =response.body().getResult();
                    msg.get(0);

                    collection.add(new ChatDataItem(msg.get(0).getSender().getProfile_image(),msg.get(0).getMessage_text(),msg.get(0).getCreated_at(),msg.get(0).getReceiver().getProfile_image(),msg.get(0).getMessage_text(),msg.get(0).getCreated_at(),false,msg.get(0).getSender_id()));
                    bindData(collection);
                }
                else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });


    }
}
