package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.ChatDataItem;
import com.app.ace.entities.ConversationEnt;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.fragments.NewMsgChat_Screen_Fragment.USERNAME;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class ChatFragment extends BaseFragment implements View.OnClickListener {

    public static String SIGNUP_MODEL = "signup_model";
    public static String CONVERSATION_ID = "conversation_id";
    public static String Receiver_ID = "Receiver_id";
    public static String USERNAME = "UserName";
    public static String POSTPATH = "postPath";
    public static String ISFOLLOWING = "isfollowing";
    public static String PROFILEIMAGE = "profileimage";
    public static String FULLNAME = "fullname";
    public static String SENDERBLOCK = "senderblock";
    public static String RECEIVERBLOCK = "receiverblock";
    public static String SENDERMUTE = "sendermute";
    public static String RECEIVERMUTE = "receivermute";
    public static String BLOCKRECEIVER = "blockReceiver";
    String blockReceiverId;
    public static String SENDER_ID = "0";
    public String sender_block;
    public String receiver_block;
    public String sender_mute;
    public String receiver_mute;
    public String IsFollowing;
    public String ProfileImage;
    public String FullName;
    public String PostPath;
    public String UserName;
    public String receiverId;
    public int SenderId;
    boolean isSender = true;
    String stringer;
    String SenderFullName;
    String ConversationId;
    CommentToChatMsgConstants commentToChatMsgConstants;
    @InjectView(R.id.listView)
    private ListView listView;
    @InjectView(R.id.edtChat)
    private AnyEditTextView edtChat;
    @InjectView(R.id.imgSend)
    private ImageView imgSend;
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;
    private ArrayListAdapter<ChatDataItem> adapter;
    private ArrayList<ChatDataItem> collection = new ArrayList<>();
    private Integer isReceiver_mute = 0;
    public static ChatFragment newInstance() {
        return new ChatFragment();
    }


    public static ChatFragment newInstance(String conversationId,String receiver_id,String UserName ) {
        Bundle args = new Bundle();
        args.putString(CONVERSATION_ID, conversationId);
        args.putString(Receiver_ID, receiver_id);
        args.putString(USERNAME, UserName);

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static ChatFragment newInstance(String conversationId,
                                           String receiver_id,
                                           String sender_id,
                                           String UserName,
                                           String IsFollowing,
                                           String ProfileImage,
                                           String FullName,
                                           int senderblock,
                                           int receiverblock,
                                           int sendermute,
                                           int receivermute,
                                           String blockReceiverId) {
        Bundle args = new Bundle();
        args.putString(CONVERSATION_ID, conversationId);
        args.putString(Receiver_ID, receiver_id);
        args.putString(SENDER_ID, sender_id);
        args.putString(USERNAME, UserName);
        args.putString(ISFOLLOWING,IsFollowing);
        args.putString(PROFILEIMAGE,ProfileImage);
        args.putString(FULLNAME,FullName);
        args.putString(SENDERBLOCK, String.valueOf(senderblock));
        args.putString(RECEIVERBLOCK, String.valueOf(receiverblock));
        args.putString(SENDERMUTE, String.valueOf(sendermute));
        args.putString(RECEIVERMUTE, String.valueOf(receivermute));
        args.putString(BLOCKRECEIVER,blockReceiverId);

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }
    public static ChatFragment newInstance(String conversationId,
                                           String receiver_id,
                                           String UserName,
                                           String Post_Path,
                                           String IsFollowing,
                                           String ProfileImage,
                                           String FullName,
                                           int sender_block,
                                           int receiver_block) {
        Bundle args = new Bundle();
        args.putString(CONVERSATION_ID, conversationId);
        args.putString(Receiver_ID, receiver_id);
        args.putString(USERNAME, UserName);
        args.putString(POSTPATH, Post_Path);
        args.putString(ISFOLLOWING,IsFollowing);
        args.putString(PROFILEIMAGE,ProfileImage);
        args.putString(FULLNAME,FullName);
        args.putString(SENDERBLOCK, String.valueOf(sender_block));
        args.putString(RECEIVERBLOCK, String.valueOf(receiver_block));

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
            receiverId = getArguments().getString(Receiver_ID);
            if (getArguments().getString(SENDER_ID)==null){
                SenderId = Integer.parseInt(prefHelper.getUserId());
            }
            else{
                SenderId = Integer.parseInt(getArguments().getString(SENDER_ID));
            }
            UserName=getArguments().getString(USERNAME);
            PostPath=getArguments().getString(POSTPATH);
            IsFollowing=getArguments().getString(ISFOLLOWING);
            ProfileImage=getArguments().getString(PROFILEIMAGE);
            FullName=getArguments().getString(FULLNAME);
            sender_block=getArguments().getString(SENDERBLOCK);
            receiver_block=getArguments().getString(RECEIVERBLOCK);
            sender_mute=getArguments().getString(SENDERMUTE);
            receiver_mute=getArguments().getString(RECEIVERMUTE);
            blockReceiverId=getArguments().getString(BLOCKRECEIVER);

            // Toast.makeText(getDockActivity(), ConversationId, Toast.LENGTH_LONG).show();
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

        Call<ResponseWrapper<ArrayList<ConversationEnt>>> callBack = webService.GetConversation(
                ConversationId,prefHelper.getUserId());
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<ConversationEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ConversationEnt>>> call,
                                   Response<ResponseWrapper<ArrayList<ConversationEnt>>>response) {
                loadingFinished();
                if (response.body().getResult().size()>0){
                    if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {
                        if (String.valueOf(response.body().getResult().get(0).getConversation().getSenderId()).equals(prefHelper.getUserId())){
                            isReceiver_mute = response.body().getResult().get(0).getConversation().getSenderMute();
                        }
                        else if (String.valueOf(response.body().getResult().get(0).getConversation().getReceiverId()).equals(prefHelper.getUserId())){
                            isReceiver_mute = response.body().getResult().get(0).getConversation().getReceiverMute();
                        }
                        sender_block = String.valueOf(response.body().getResult().get(0).getConversation().getSenderBlock());
                        receiver_block = String.valueOf(response.body().getResult().get(0).getConversation().getReceiverBlock());
                        IsFollowing = String.valueOf(response.body().getResult().get(0).getConversation().getIsFollowing());

                         setConversation(response.body().getResult().get(0).getMessages());

                        setUserName(response.body().getResult());

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ConversationEnt>>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    private void setUserName(ArrayList<ConversationEnt> result) {

        if (result.get(0).getConversation().getSenderId() == Integer.parseInt(prefHelper.getUserId())) {
            isSender = false;
            USERNAME = result.get(0).getMessages().get(0).getReceiver().getFirst_name()
                    +" "+result.get(0).getMessages().get(0).getReceiver().getLast_name();
           // ProfileImage = result.get(0).getMessages().get(0).getReceiver().getProfile_image();
        } else {
            isSender = true;
            USERNAME = result.get(0).getMessages().get(0).getSender().getFirst_name()
                    +" "+result.get(0).getMessages().get(0).getSender().getLast_name();
          //  ProfileImage = result.get(0).getMessages().get(0).getSender().getProfile_image();
        }

       // getMainActivity().titleBar.setSubHeading(USERNAME);


    }




    private void setConversation(ArrayList<MsgEnt> msgArrayList) {

        collection = new ArrayList<>();
       /* if (msgArrayList.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }*/





        for (MsgEnt msgEnt : msgArrayList) {


            if (msgEnt.getSender().getId() == Integer.parseInt(prefHelper.getUserId())) {
                isSender = false;
               /* USERNAME = msgArrayList.get(0).getReceiver().getFirst_name()
                        +" "+msgArrayList.get(0).getReceiver().getLast_name();*/
                ProfileImage = msgArrayList.get(0).getReceiver().getProfile_image();
            } else {
                isSender = true;
               /* USERNAME = msgArrayList.get(0).getSender().getFirst_name()
                        +" "+msgArrayList.get(0).getSender().getLast_name();*/
                ProfileImage = msgArrayList.get(0).getSender().getProfile_image();
            }
            if(PostPath!=null)
            {


                if (msgEnt.getSender()!=null && msgEnt.getReceiver() != null && msgEnt.getMessage() !=null) {
                    collection.add(new ChatDataItem(msgEnt.getSender().getProfile_image(),
                            PostPath, getDockActivity().getDate(msgEnt.getMessage().getCreated_at()),
                            msgEnt.getReceiver().getProfile_image(), msgEnt.getMessage().getMessage_text(),
                            getDockActivity().getDate(msgEnt.getMessage().getCreated_at()), isSender, msgEnt.getSender().getId()));
                }

            }
            else {

                if (msgEnt.getSender() != null && msgEnt.getReceiver() != null && msgEnt.getMessage() != null) {

                    collection.add(new ChatDataItem(msgEnt.getSender().getProfile_image(),
                            msgEnt.getMessage().getMessage_text(),getDockActivity().getDate(msgEnt.getMessage().getCreated_at()),
                            msgEnt.getReceiver().getProfile_image(), msgEnt.getMessage().getMessage_text(),
                            getDockActivity().getDate(msgEnt.getMessage().getCreated_at()), isSender, msgEnt.getSender().getId()));
                }
            }

        }
        if (msgArrayList.size()>0){
            if (isSender){

            }
            else{

            }
        }


        getMainActivity().titleBar.invalidate();
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
        if(!receiverId.equals(prefHelper.getUserId())){
        titleBar.showHelpButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                getDockActivity().addDockableFragment(FriendsInfoFragment.newInstance(
                        ConversationId,
                        blockReceiverId,
                        IsFollowing,
                        ProfileImage,
                        UserName,
                        sender_block,
                        receiver_block
                         ,String.valueOf(isReceiver_mute)), "FriendsInfoFragment");
            }
        });}

        titleBar.setSubHeading(UserName);
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
                receiverId,
                edtChat.getText().toString());

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
                    else {
                        ArrayList<MsgEnt> msg = response.body().getResult();
                        msg.get(msg.size()-1);
                        hideKeyboard();
                        listView.post(new Runnable() {
                            @Override
                            public void run() {
                                // Select the last row so it will scroll into view...
                                listView.setSelection(adapter.getCount() - 1);
                            }
                        });



                        collection.add(new ChatDataItem(msg.get(msg.size()-1).getSender().getProfile_image(), msg.get(msg.size()-1).getMessage().getMessage_text(),getDockActivity().getDate(msg.get(msg.size()-1).getMessage().getCreated_at()), msg.get(msg.size()-1).getReceiver().getProfile_image(), msg.get(msg.size()-1).getMessage().getMessage_text(), getDockActivity().getDate(msg.get(msg.size()-1).getMessage().getCreated_at()), false, msg.get(msg.size()-1).getSender_id()));
                        bindData(collection);
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


}
