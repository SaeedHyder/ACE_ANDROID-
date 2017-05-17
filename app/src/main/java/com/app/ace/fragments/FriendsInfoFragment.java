package com.app.ace.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.app.ace.R;
import com.app.ace.entities.FollowUser;
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;


import static com.app.ace.R.id.riv_profile_pic;
import static com.app.ace.fragments.ChatFragment.CONVERSATION_ID;
import static com.app.ace.fragments.ChatFragment.FULLNAME;
import static com.app.ace.fragments.ChatFragment.ISFOLLOWING;
import static com.app.ace.fragments.ChatFragment.PROFILEIMAGE;
import static com.app.ace.fragments.ChatFragment.RECEIVERBLOCK;
import static com.app.ace.fragments.ChatFragment.Receiver_ID;
import static com.app.ace.fragments.ChatFragment.SENDERBLOCK;
import static com.app.ace.global.AppConstants.user_id;


public class FriendsInfoFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.toggle_private_or_public)
    ToggleButton toggle_private_or_public;

    @InjectView(R.id.btn_block)
    Button btn_block;

    @InjectView(R.id.btn_Unblock)
    Button btn_Unblock;

    @InjectView(R.id.ProfileImage)
    CircleImageView ProfileImage;

    @InjectView(R.id.txt_UserName)
    AnyTextView txt_UserName;

    @InjectView(R.id.btn_follow)
    Button btn_follow;

    @InjectView(R.id.btn_Unfollow)
    Button btn_Unfollow;

    private ImageLoader imageLoader;


    public static String CONVERSATION_ID = "conversation_id";
    public static String Receiver_ID = "Receiver_id";
    public static String ISFOLLOWING = "isfollowing";
    public static String PROFILEIMAGE = "profileimage";
    public static String FULLNAME = "fullname";
    public static String SENDERBLOCK = "senderblock";
    public static String RECEIVERBLOCK = "receiverblock";

    public String SenderBlock;
    public String ReceiverBlock;
    String IsFollowing;
    String ProfilePicture;
    String FullName;
    public String receiverId;
    String ConversationId;
    int sender_block;
    int receiver_block;


    public static FriendsInfoFragment newInstance() {

        return new FriendsInfoFragment();
    }

    public static FriendsInfoFragment newInstance(String conversationId, String receiver_ID, String isFollowing, String profileImage, String fullName, String senderblock, String receiverblock) {

        Bundle args = new Bundle();
        args.putString(CONVERSATION_ID, conversationId);
        args.putString(Receiver_ID, receiver_ID);
        args.putString(ISFOLLOWING,isFollowing);
        args.putString(PROFILEIMAGE,profileImage);
        args.putString(FULLNAME,fullName);
        args.putString(SENDERBLOCK, String.valueOf(senderblock));
        args.putString(RECEIVERBLOCK, String.valueOf(receiverblock));

        FriendsInfoFragment fragment = new FriendsInfoFragment();
        fragment.setArguments(args);

        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ConversationId = getArguments().getString(CONVERSATION_ID);
            receiverId = getArguments().getString(Receiver_ID);
            IsFollowing=getArguments().getString(ISFOLLOWING);
            ProfilePicture=getArguments().getString(PROFILEIMAGE);
            FullName=getArguments().getString(FULLNAME);
            SenderBlock=getArguments().getString(SENDERBLOCK);
            ReceiverBlock=getArguments().getString(RECEIVERBLOCK);

        }
        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Listners();
        setData();
    }

    private void Listners() {
        btn_block.setOnClickListener(this);
        btn_follow.setOnClickListener(this);
        btn_Unfollow.setOnClickListener(this);
        btn_Unblock.setOnClickListener(this);
    }

    private void setData() {

        imageLoader.displayImage(ProfilePicture, ProfileImage);
        txt_UserName.setText(FullName);

        if(prefHelper.getUserId().equals(receiverId)) {
            if(SenderBlock.contains("0"))
            {
                btn_Unblock.setVisibility(View.GONE);
                btn_block.setVisibility(View.VISIBLE);
            }
            else
            {
                btn_Unblock.setVisibility(View.VISIBLE);
                btn_block.setVisibility(View.GONE);
            }
        }
        else {
            if(ReceiverBlock.contains("0"))
            {
                btn_Unblock.setVisibility(View.GONE);
                btn_block.setVisibility(View.VISIBLE);
            }
            else
            {
                btn_Unblock.setVisibility(View.VISIBLE);
                btn_block.setVisibility(View.GONE);
            }

        }


        if(IsFollowing.contains("0"))
        {
            btn_follow.setVisibility(View.VISIBLE);
            btn_Unfollow.setVisibility(View.GONE);
        }
        else
        {
            btn_follow.setVisibility(View.GONE);
            btn_Unfollow.setVisibility(View.VISIBLE);
        }


        toggle_private_or_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggle_private_or_public.isChecked())
                {

                    Toast.makeText(getDockActivity(),"checked",Toast.LENGTH_LONG).show();
                }
                else

                    Toast.makeText(getDockActivity(),"Unchecked",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setMuteService(int x, int y) {

        sender_block=x;
        receiver_block=y;
      /*  if(prefHelper.getUserId().equals(receiverId)){
             sender_block=x;
             receiver_block=y;}
        else
        {
            sender_block=0;
            receiver_block=1;
        }*/
        Call<ResponseWrapper> callBack = webService.muteConversation(ConversationId,sender_block,receiver_block);
        callBack.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    private void followUser() {

        Call<ResponseWrapper<FollowUser>> callBack = webService.follow(
                prefHelper.getUserId(),
                receiverId
        );

        callBack.enqueue(new Callback<ResponseWrapper<FollowUser>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<FollowUser>> call, Response<ResponseWrapper<FollowUser>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                   /* btn_follow.setVisibility(View.GONE);
                    btn_Unfollow.setVisibility(View.VISIBLE);*/

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<FollowUser>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }

    private void unfollowUser() {

        Call<ResponseWrapper<FollowUser>> callBack = webService.unfollow(
                prefHelper.getUserId(),
                receiverId
        );

        callBack.enqueue(new Callback<ResponseWrapper<FollowUser>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<FollowUser>> call, Response<ResponseWrapper<FollowUser>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                   /* btn_follow.setVisibility(View.VISIBLE);
                    btn_Unfollow.setVisibility(View.GONE);*/

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<FollowUser>> call, Throwable t) {

                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Details");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_block:

                btn_Unblock.setVisibility(View.VISIBLE);
                btn_block.setVisibility(View.GONE);

                if(prefHelper.getUserId().equals(receiverId)) {
                    setMuteService(1, 0);
                }
                else
                {
                    setMuteService(0,1);
                }
                break;

            case R.id.btn_Unblock:

                btn_Unblock.setVisibility(View.GONE);
                btn_block.setVisibility(View.VISIBLE);

                if(prefHelper.getUserId().equals(receiverId)) {
                    setMuteService(0, 0);
                }
                else
                {
                    setMuteService(0,0);
                }
                break;


            case R.id.btn_follow:

                btn_follow.setVisibility(View.GONE);
                btn_Unfollow.setVisibility(View.VISIBLE);

                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                followUser();

                break;

            case R.id.btn_Unfollow:

                btn_follow.setVisibility(View.VISIBLE);
                btn_Unfollow.setVisibility(View.GONE);

                unfollowUser();

                break;



    }}
}
