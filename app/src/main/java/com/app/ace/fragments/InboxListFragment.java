package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.CreaterEnt;
import com.app.ace.entities.InboxDataItem;
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.PostsEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.InboxListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.global.AppConstants.user_id;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class InboxListFragment extends BaseFragment {

    @InjectView(R.id.listView)
    private ListView listView;
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;
    private ArrayListAdapter<MsgEnt> adapter;

    private ArrayList<MsgEnt> userCollection = new ArrayList<>();

    public static InboxListFragment newInstance() {

        return new InboxListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<MsgEnt>(getDockActivity(), new InboxListItemBinder(getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inboxlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Call<ResponseWrapper<ArrayList<MsgEnt>>> callBack = webService.userinbox(prefHelper.getUserId());
        loadingStarted();
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<MsgEnt>>>() {

                             @Override
                             public void onResponse(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Response<ResponseWrapper<ArrayList<MsgEnt>>> response) {
                                 loadingFinished();
                                 if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                                AddInboxData(response.body().getResult());

                                 }

                                 else {
                                     UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                                 }

                             }

                             @Override
                             public void onFailure(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Throwable t) {

                                 loadingFinished();
                                 System.out.println(t.getMessage());
                                 System.out.println(t.getCause());
                                 UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());

                             }
                         });

        setListener();
       // getUserData();
    }

    private void AddInboxData(ArrayList<MsgEnt> result) {

        userCollection = new ArrayList<>();
       /* if (result.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }*/


            userCollection.addAll(result);
            bindData(userCollection);




        /*for(MsgEnt msg : result){


           //    userCollection.add(new InboxDataItem(msg.getSender().getProfile_image(),msg.getSender().getFirst_name()+" "+msg.getSender().getLast_name(),msg.getMessage().getMessage_text(),msg.getMessage().getConversation_id()));
            userCollection.add(new InboxDataItem(msg.getSender().getProfile_image(),msg.getSender().getFirst_name()+" "+msg.getSender().getLast_name(),msg.getMessage().getMessage_text(),msg.getMessage().getConversation_id()));

            //  userCollection.add(new InboxDataItem(msg.getReceiver().getProfile_image(),msg.getReceiver().getFirst_name()+" "+msg.getReceiver().getLast_name(),msg.getMessage().getMessage_text(),msg.getMessage().getConversation_id()));

        }*/

    }




  /*  private void getUserData() {

        userCollection= new ArrayList<>();
        userCollection.add(new InboxDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.james_blunt), getString(R.string.i_wont_be) ));
        userCollection.add(new InboxDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith", getString(R.string.what_other_training)));
        userCollection.add(new InboxDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam), getString(R.string.please_reply)));


        bindData(userCollection);
    }*/

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //getDockActivity().addDockableFragment(ChatFragment.newInstance(), "ChatFragment");
            }
        });
    }

    private void bindData(ArrayList<MsgEnt> userCollection) {
        adapter.clearList();
        listView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.inbox));

        titleBar.showAddButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));
                getDockActivity().addDockableFragment(NewMessageFragment.newInstance(), "NewMessageFragment");
            }
        });
    }
}