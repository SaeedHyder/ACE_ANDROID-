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
import com.app.ace.helpers.PreferenceHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.InboxListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

import static com.app.ace.R.id.SearchTrainer_ListView;
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
    String UserName;

    private ArrayList<MsgEnt> userCollection = new ArrayList<>();

    public static InboxListFragment newInstance() {

        return new InboxListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<MsgEnt>(getDockActivity(), new InboxListItemBinder(getDockActivity(),prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inboxlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setInboxData();
        ListViewItemListner();
       // getUserData();
    }

    private void setInboxData() {

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
    }

    private void AddInboxData(ArrayList<MsgEnt> result) {

        if (result.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else {
            txt_noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        userCollection = new ArrayList<>();

            userCollection.addAll(result);
            bindData(userCollection);


    }




    private void ListViewItemListner() {

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(userCollection.get(i).getSender().getId()==Integer.parseInt(prefHelper.getUserId()))
                {
                    if(!(prefHelper.getUser().getFirst_name().equals(userCollection.get(i).getReceiver().getFirst_name())))
                    UserName=userCollection.get(i).getReceiver().getFirst_name()+" "+userCollection.get(i).getReceiver().getLast_name();
                    else
                        UserName=userCollection.get(i).getSender().getFirst_name()+" "+userCollection.get(i).getSender().getLast_name();

                    receivebyReceiver(userCollection.get(i));
                }
                else if(userCollection.get(i).getReceiver().getId()==Integer.parseInt(prefHelper.getUserId())) {
                    if(!(prefHelper.getUser().getFirst_name().equals(userCollection.get(i).getSender().getFirst_name())))
                         UserName=userCollection.get(i).getSender().getFirst_name()+" "+userCollection.get(i).getSender().getLast_name();
                    else
                        UserName=userCollection.get(i).getReceiver().getFirst_name()+" "+userCollection.get(i).getReceiver().getLast_name();
                    receivebySender(userCollection.get(i));
                }
              //  getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(userCollection.get(i).));
            }
        });
    }

    private void receivebyReceiver(MsgEnt entity) {
        getDockActivity().addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getMessage().getConversation_id())
                ,String.valueOf(entity.getReceiver_id())
                ,String.valueOf(entity.getSender_id())
                ,UserName, String.valueOf(entity.getIs_following())
                ,entity.getReceiver().getProfile_image()
                ,entity.getReceiver().getFirst_name()+" "+entity.getReceiver().getLast_name()
                ,entity.getSender_block(),entity.getReceiver_block()
                ,entity.getSender_mute(),entity.getReceiver_mute()), "ChatFragment");
    }

   /* private void receivebySender(MsgEnt entity) {
        getDockActivity().addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getMessage().getConversation_id())
                ,String.valueOf(entity.getMessage().getSender_id())
                ,String.valueOf(entity.getMessage().getSender_id())
                ,UserName, String.valueOf(entity.getIs_following())
                ,entity.getReceiver().getProfile_image()
                ,entity.getReceiver().getFirst_name()+" "+entity.getReceiver().getLast_name()
                ,entity.getSender_block(),entity.getReceiver_block()
                ,entity.getSender_mute(),entity.getReceiver_mute()), "ChatFragment");
    }*/

    private void receivebySender(MsgEnt entity) {
        getDockActivity().addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getMessage().getConversation_id())
                ,String.valueOf(entity.getSender_id())
                ,String.valueOf(entity.getReceiver_id())
                ,UserName, String.valueOf(entity.getIs_following())
                ,entity.getSender().getProfile_image()
                ,entity.getSender().getFirst_name()+" "+entity.getSender().getLast_name()
                ,entity.getSender_block(),entity.getReceiver_block()
                ,entity.getSender_mute(),entity.getReceiver_mute()), "ChatFragment");
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