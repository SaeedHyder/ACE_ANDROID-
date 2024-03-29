package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.entities.ResponseWrapper;
import com.app.ace.entities.UserProfile;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.helpers.InternetHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created on 5/11/2017.
 */

public class SearchTrainerFragment extends BaseFragment {

    @InjectView(R.id.Searchtrainee_ListView)
    private ListView SearchTrainee_ListView;
    @InjectView(R.id.txt_noresult)
    private TextView txt_noresult;

    private AnyEditTextView edtsearch;
    String language = "";
    private ArrayListAdapter<UserProfile> adapter;

    private ArrayList<UserProfile> userCollection = new ArrayList<>();

    public static SearchTrainerFragment newInstance() {

        return new SearchTrainerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<UserProfile>(getDockActivity(), new SearchPeopleListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_trainer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtsearch = getTitleBar().showSearchBar();
        SearchTrainee_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(userCollection.get(i).getId()));
            }
        });

        if (prefHelper.isLanguageArabic()) {
            language = "ar";
        } else
            language = "en";

        if(InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            getSearchUserData();
        }

    }

    private void getSearchUserData() {

        getMainActivity().onLoadingStarted();
        Call<ResponseWrapper<ArrayList<UserProfile>>> callBack = webService.getSearchUser(edtsearch.getText().toString(), AppConstants.trainer, getMainActivity().selectedLanguage());

        callBack.enqueue(new Callback<ResponseWrapper<ArrayList<UserProfile>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Response<ResponseWrapper<ArrayList<UserProfile>>> response) {
                //  resultuser = response.body().getResult();
                getMainActivity().onLoadingFinished();
                if (response.body() != null)
                    if (response.body().getUserDeleted() == 0) {
                        bindview(response.body().getResult());
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
                // System.out.println(response.body().getResult().get(0).getId());
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserProfile>>> call, Throwable t) {
                getMainActivity().onLoadingFinished();
                Log.e("Search", t.toString());
            }
        });

    }

    private void bindview(ArrayList<UserProfile> resultuser) {
        userCollection = new ArrayList<>();
        if (resultuser.size() <= 0) {
            txt_noresult.setVisibility(View.VISIBLE);
            SearchTrainee_ListView.setVisibility(View.GONE);
        } else {
            txt_noresult.setVisibility(View.GONE);
            SearchTrainee_ListView.setVisibility(View.VISIBLE);
        }

        for (UserProfile user : resultuser
                ) {
            userCollection.add(user);
        }


        bindData(userCollection);
    }

    private void bindData(ArrayList<UserProfile> userCollection) {
        adapter.clearList();
        SearchTrainee_ListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        edtsearch = titleBar.showSearchBar();
        edtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchUserData();
                }
                return false;
            }
        });
        titleBar.showBackButton();
        titleBar.showCancelButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().popFragment();
               /* getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");*/
            }
        });


    }

 /*   public void setUpdatedData(String data, int position) {
        SearchPeopleDataItem updatedItem =   (SearchPeopleDataItem) adapter.getItem(position);
        updatedItem.setUserName(data);

        adapter.add(updatedItem);

        adapter.notifyDataSetChanged();
    }*/

}
