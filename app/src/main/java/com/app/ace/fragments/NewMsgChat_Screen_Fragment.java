package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class NewMsgChat_Screen_Fragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.iv_sendbtn)
    ImageView iv_sendbtn;

    public static NewMsgChat_Screen_Fragment newInstance()
    {

        return new NewMsgChat_Screen_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_screen_msg, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListner();
    }

    private void setListner() {

        iv_sendbtn.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("New Message");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_sendbtn:
                getDockActivity().addDockableFragment(ChatFragment.newInstance(),"ChatFragment");
                break;
        }
    }
}
