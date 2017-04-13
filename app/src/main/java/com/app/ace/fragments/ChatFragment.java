package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.ChatDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.CommentToChatMsgConstants;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.ChatListBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;

import roboguice.inject.InjectView;

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

    String stringer;

    public static String SIGNUP_MODEL = "signup_model";

    private ArrayListAdapter<ChatDataItem> adapter;
    private ArrayList<ChatDataItem> collection = new ArrayList<>();


    CommentToChatMsgConstants commentToChatMsgConstants;


    public static ChatFragment newInstance() {
        return new ChatFragment();
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



        setListener();
        getChatData();
    }

    private void setListener() {
        imgSend.setOnClickListener(this);
    }

    private void getChatData() {

        collection = new ArrayList<>();

        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, "Hi", "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", true));
        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, "Hello", "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", false));
        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.lorem_ipsum), "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", true));
        collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, "Hello", "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, getString(R.string.lorem_ipsum), "6 mins ago", false));
        if (commentToChatMsgConstants!=null) {
            collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.i_wont_be), "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, commentToChatMsgConstants.getCommentC(), "6 mins ago", false));
        }
        bindData(collection);
    }

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
                    collection.add(new ChatDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.i_wont_be), "3 mins ago", "drawable://" + R.drawable.profile_pic_trainer, edtChat.getText().toString(), "6 mins ago", false));
                    edtChat.getText().clear();
                    bindData(collection);
                }
                break;
        }
    }
}
