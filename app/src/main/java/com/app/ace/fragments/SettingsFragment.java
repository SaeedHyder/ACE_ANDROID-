package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.helpers.UIHelper;
import com.app.ace.ui.views.AnyTextView;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener{

    @InjectView(R.id.txt_logout)
    private AnyTextView txt_logout;

    public static SettingsFragment newInstance() {

        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_settings, container, false);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.showTitleBar();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getActivity().getResources().getString(R.string.settings));

        titleBar.showSaveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented));

            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();

    }

    private void setListeners() {

        txt_logout.setOnClickListener(this);
    }

    @Override
    public void onClick( View v ) {
        // TODO Auto-generated method stub
        switch (v.getId()){

            case R.id.txt_logout:
                prefHelper.setLoginStatus(false);
                prefHelper.setIsTwitterLogin(false);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");

                break;
        }
    }
}
