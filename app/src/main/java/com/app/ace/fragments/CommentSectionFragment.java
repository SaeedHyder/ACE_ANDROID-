package com.app.ace.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ace.R;
import com.app.ace.entities.CommentsSectionItemsEnt;
import com.app.ace.entities.HomeListDataEnt;
import com.app.ace.entities.PostsEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.ShowComments;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.global.CommentToChatMsgConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.CommentSection;
import com.app.ace.interfaces.LastPostComment;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.CommentSectionItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.lv_CommentSection;
import static com.app.ace.fragments.TrainerProfileFragment.USER_ID;
import static com.app.ace.global.AppConstants.user_id;


public class CommentSectionFragment extends BaseFragment implements  CommentSection, View.OnClickListener{

    @InjectView(lv_CommentSection)
    private ListView listViewCommentSection;

    @InjectView(R.id.et_CommentBar)
    AnyEditTextView et_CommentBar;

    @InjectView(R.id.iv_pointer)
    ImageView iv_pointer;

    public static String POSTID = "post_id";
    String post_id;

    Spannable tagName;


    private ArrayListAdapter<CommentsSectionItemsEnt> adapter;

    private ArrayList<CommentsSectionItemsEnt> userCollection = new ArrayList<>();

    public static CommentSectionFragment newInstance(int postId) {
        Bundle args = new Bundle();
        args.putString(POSTID, String.valueOf(postId));
        CommentSectionFragment fragment = new CommentSectionFragment();
        fragment.setArguments(args);

        return fragment;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<CommentsSectionItemsEnt>(getDockActivity(), new CommentSectionItemBinder(this) {
        });
        if (getArguments() != null) {
            post_id = getArguments().getString(POSTID);
        }
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

        ShowComments();
        setListener();

        //getUserData();
    }

    private void ShowComments() {

        Call<ResponseWrapper<ArrayList<ShowComments>>> callBack = webService.ShowComments(post_id);

        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<ShowComments>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ShowComments>>> call, Response<ResponseWrapper<ArrayList<ShowComments>>> response) {

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                   // if(response.body().getResult().size()!=0) {
                        SetAllComments(response.body().getResult());
                   // }
                    if(response.body().getMessage().contains("No Comments")) {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }

                }
                else {

                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ShowComments>>> call, Throwable t) {

                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

            }
        });
    }

    private void SetAllComments(ArrayList<ShowComments> result) {

        userCollection = new ArrayList<>();

        for(ShowComments item : result){

            userCollection.add(new CommentsSectionItemsEnt(item.getUser().getProfile_image(),
                    item.getUser().getFirst_name()+" "+item.getUser().getLast_name(),item.getComment_text(),getDockActivity().getDate(item.getCreated_at()),String.valueOf(item.getUser_id())));



        }



        bindData(userCollection);


    }


  /*  private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt),"I wont be able to make it today","13m" ));
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic_trainer, "Rebecca Black","What other training expertise do you have","30m"));
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic,"Steve Camb","Please reply back when you get this message","46m"));
        userCollection.add(new CommentsSectionItemsEnt("drawable://" + R.drawable.profile_pic,"Steve Camb","Please reply back when you get this message","46m"));

        bindData(userCollection);
    }
*/


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


       final String name ="@"+ShowName.getNameCommentor().toLowerCase()+" " ;

        et_CommentBar.setText(parseActiveReply(name,et_CommentBar.getText().toString()));
        et_CommentBar.setTag(ShowName.getUser_id());


    }

    public SpannableString parseActiveReply(String name, String body) {
        SpannableString sp = new SpannableString(name + " " + body);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#656565")), 0,
                name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // sp.setSpan(new ForegroundColorSpan(Color.parseColor("#5FADC7")), 0,
        // 0,
        // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pointer:

                SendComment();

              //  CommentToChatMsgConstants commentToChatMsgConstants = new CommentToChatMsgConstants();

               // commentToChatMsgConstants.setCommentC(et_CommentBar.getText().toString());


              // getDockActivity().addDockableFragment(ChatFragment.newInstance(commentToChatMsgConstants), "ChatFragment");
                break;
        }
    }
    String user_id = "0";
    private void SendComment() {

        if ( et_CommentBar.getTag()!=null){
            user_id =  (String) et_CommentBar.getTag();
        }
        else{
            user_id = "0";
        }
        Call<ResponseWrapper<ShowComments>> callBack = webService.CreateComment(
                prefHelper.getUserId(),
                post_id,
                et_CommentBar.getText().toString(),
                user_id);

        callBack.enqueue(new Callback<ResponseWrapper<ShowComments>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ShowComments>> call, Response<ResponseWrapper<ShowComments>> response) {

                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    userCollection.add(new CommentsSectionItemsEnt(prefHelper.getUser().getProfile_image(),prefHelper.getUserName(),response.body().getResult().getComment_text(),getDockActivity().getDate(response.body().getResult().getCreated_at()),String.valueOf(response.body().getResult().getUser_id())));
                    bindData(userCollection);
                    et_CommentBar.setText("");
                }
                else
                {
                    UIHelper.showLongToastInCenter(getDockActivity(),"Please Enter Comment");
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<ShowComments>> call, Throwable t) {
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });

    }

}
