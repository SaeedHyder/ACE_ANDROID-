package com.app.ace.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.CommentsSectionItemsEnt;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.CommentToChatMsgConstants;
import com.app.ace.interfaces.CommentSection;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.CommentSectionItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.lv_CommentSection;



public class CommentSectionFragment extends BaseFragment implements  CommentSection, View.OnClickListener{

    @InjectView(lv_CommentSection)
    private ListView listViewCommentSection;

    @InjectView(R.id.et_CommentBar)
    AnyEditTextView et_CommentBar;

    @InjectView(R.id.iv_pointer)
    ImageView iv_pointer;

    private ArrayListAdapter<CommentsSectionItemsEnt> adapter;

    private ArrayList<CommentsSectionItemsEnt> userCollection = new ArrayList<>();

    public static CommentSectionFragment newInstance() {
        return new CommentSectionFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<CommentsSectionItemsEnt>(getDockActivity(), new CommentSectionItemBinder(this) {

        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_comment_section, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
        getUserData();
    }



    private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt),"I wont be able to make it today","13m" ));
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic_trainer, "Rebecca Black","What other training expertise do you have","30m"));
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic,"Steve Camb","Please reply back when you get this message","46m"));
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic,"Steve Camb","Please reply back when you get this message","46m"));

        bindData(userCollection);
    }



    private void setListener() {

        iv_pointer.setOnClickListener(this);
    }

    private void bindData(ArrayList<CommentsSectionItemsEnt> userCollection) {
        adapter.clearList();
        listViewCommentSection.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Comments));

    }


    @Override
    public void onReplyClicked(CommentsSectionItemsEnt ShowName) {

        String name = ShowName.getNameCommentor().toLowerCase();
        et_CommentBar.setText("@"+name);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pointer:


                CommentToChatMsgConstants commentToChatMsgConstants = new CommentToChatMsgConstants();

                commentToChatMsgConstants.setCommentC(et_CommentBar.getText().toString());


               getDockActivity().addDockableFragment(ChatFragment.newInstance(commentToChatMsgConstants), "ChatFragment");
                break;
        }
    }
}
