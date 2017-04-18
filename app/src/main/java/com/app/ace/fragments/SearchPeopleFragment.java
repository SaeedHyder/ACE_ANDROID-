package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.ace.R;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.adapters.ArrayListAdapter;
import com.app.ace.ui.viewbinders.SearchPeopleListItemBinder;
import com.app.ace.ui.views.TitleBar;

import java.util.ArrayList;

import roboguice.inject.InjectView;

import static com.app.ace.global.AppConstants.trainer;

/**
 * Created by saeedhyder on 4/4/2017.
 */

public class SearchPeopleFragment extends BaseFragment {

    @InjectView(R.id.SearchPeople_ListView)
    private ListView SearchPeople_ListView;

    private ArrayListAdapter<SearchPeopleDataItem> adapter;

    private ArrayList<SearchPeopleDataItem> userCollection = new ArrayList<>();

    public static SearchPeopleFragment newInstance() {

        return new SearchPeopleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<SearchPeopleDataItem>(getDockActivity(), new SearchPeopleListItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_people, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchPeople_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(Integer.parseInt(prefHelper.getUserId())), "TrainerProfileFragment");
            }
        });

        getSearchUserData();
    }

    private void getSearchUserData() {
        userCollection= new ArrayList<>();
        userCollection.add(new SearchPeopleDataItem("drawable://" + R.drawable.profile_pic,"Charlie Hannan"));
        userCollection.add(new SearchPeopleDataItem("drawable://" + R.drawable.profile_pic_trainer, "Tori Smith"));
        userCollection.add(new SearchPeopleDataItem("drawable://" + R.drawable.profile_pic, getString(R.string.charlie_hunnam)));

        bindData(userCollection);
    }

    private void bindData(ArrayList<SearchPeopleDataItem> userCollection) {
        adapter.clearList();
        SearchPeople_ListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showCancelButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
            }
        });


    }

}
