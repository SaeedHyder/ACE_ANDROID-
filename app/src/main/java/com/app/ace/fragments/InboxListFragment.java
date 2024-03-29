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
import com.app.ace.entities.MsgEnt;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.DeleteChatInterface;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.InboxListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class InboxListFragment extends BaseFragment implements DeleteChatInterface{

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

        adapter = new ArrayListAdapter<MsgEnt>(getDockActivity(), new InboxListItemBinder(getDockActivity(), prefHelper,this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inboxlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            setInboxData();
        }
        ListViewItemListner();
        DeleteChat();
        // getUserData();
    }

    private void DeleteChat() {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final DialogHelper dialog = new DialogHelper(getDockActivity());
                dialog.initDeleteChat(R.layout.delete_chat_dialoge, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteService(position);
                        dialog.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.hideDialog();
                    }
                });
                dialog.showDialog();

                return true;
            }
        });
    }

    private void deleteService(final int position) {
        loadingStarted();
        Call<ResponseWrapper> callBack = webService.deleteConversation(prefHelper.getUserId(),userCollection.get(position).getMessage().getConversation_id(),getMainActivity().selectedLanguage());

        callBack.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
             //   if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    if (response.body().getUserDeleted() == 0) {
                        if(response.body().getMessage().equals("Success")) {
                            UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                            userCollection.remove(position);
                            adapter.clearList();
                            adapter.addAll(userCollection);
                            adapter.notifyDataSetChanged();

                            if (userCollection.size() <= 0) {
                                txt_noresult.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            } else {
                                txt_noresult.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
                        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                        dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogHelper.hideDialog();
                                getDockActivity().popBackStackTillEntry(0);
                                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                            }
                        },response.body().getMessage());
                        dialogHelper.showDialog();
                    }

               /* } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }*/

            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                System.out.println(t.getMessage());
                System.out.println(t.getCause());
            }
        });

    }

    private void setInboxData() {

        Call<ResponseWrapper<ArrayList<MsgEnt>>> callBack = webService.userinbox(prefHelper.getUserId());
        loadingStarted();
        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<MsgEnt>>>() {

            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<MsgEnt>>> call, Response<ResponseWrapper<ArrayList<MsgEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(AppConstants.CODE_SUCCESS)) {

                    if (response.body().getUserDeleted() == 0) {
                        AddInboxData(response.body().getResult());
                    } else {
                        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                        dialogHelper.initLogoutDialog(R.layout.dialogue_deleted, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogHelper.hideDialog();
                                getDockActivity().popBackStackTillEntry(0);
                                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                            }
                        },response.body().getMessage());
                        dialogHelper.showDialog();
                    }

                } else {
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
        } else {
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
                if (userCollection.get(i).getSender().getId() == Integer.parseInt(prefHelper.getUserId())) {
                    if (!(prefHelper.getUser().getFirst_name().equals(userCollection.get(i).getReceiver().getFirst_name())))
                        UserName = userCollection.get(i).getReceiver().getFirst_name() + " " + userCollection.get(i).getReceiver().getLast_name();
                    else
                        UserName = userCollection.get(i).getSender().getFirst_name() + " " + userCollection.get(i).getSender().getLast_name();

                    receivebyReceiver(userCollection.get(i));
                } else if (userCollection.get(i).getReceiver().getId() == Integer.parseInt(prefHelper.getUserId())) {
                    if (!(prefHelper.getUser().getFirst_name().equals(userCollection.get(i).getSender().getFirst_name())))
                        UserName = userCollection.get(i).getSender().getFirst_name() + " " + userCollection.get(i).getSender().getLast_name();
                    else
                        UserName = userCollection.get(i).getReceiver().getFirst_name() + " " + userCollection.get(i).getReceiver().getLast_name();
                    receivebySender(userCollection.get(i));
                }
                //  getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(userCollection.get(i).));
            }
        });
    }

    private void receivebyReceiver(MsgEnt entity) {
        getDockActivity().addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getMessage().getConversation_id())
                , String.valueOf(entity.getReceiver_id())
                , String.valueOf(entity.getSender_id())
                , UserName, String.valueOf(entity.getIs_following())
                , entity.getReceiver().getProfile_image()
                , entity.getReceiver().getFirst_name() + " " + entity.getReceiver().getLast_name()
                , entity.getSender_block(), entity.getReceiver_block()
                , entity.getSender_mute(), entity.getReceiver_mute(), String.valueOf(entity.getReceiver_id())), "ChatFragment");
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
                , String.valueOf(entity.getSender_id())
                , String.valueOf(entity.getReceiver_id())
                , UserName, String.valueOf(entity.getIs_following())
                , entity.getSender().getProfile_image()
                , entity.getSender().getFirst_name() + " " + entity.getSender().getLast_name()
                , entity.getSender_block(), entity.getReceiver_block()
                , entity.getSender_mute(), entity.getReceiver_mute(), String.valueOf(entity.getReceiver_id())), "ChatFragment");
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

    @Override
    public void deleteMessage(int position, int Position) {

    }

    @Override
    public void deleteChat(final int Position) {



    }
}