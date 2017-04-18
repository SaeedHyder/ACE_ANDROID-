package com.app.ace.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app.ace.R;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;

import roboguice.inject.InjectView;

/**
 * Created by khan_muhammad on 3/14/2017.
 */

public class VerifyPhoneFragment extends BaseFragment implements View.OnClickListener{


    @InjectView(R.id.edtCountry)
    private AnyEditTextView edtCountry;

    @InjectView(R.id.edtPhone_number)
    private AnyEditTextView edtPhone_number;

    @InjectView(R.id.btnSubmit)
    private Button btnSubmit;

    public static VerifyPhoneFragment newInstance() {
        return new VerifyPhoneFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verify_phone, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();
    }

    private void setListener() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.verify_your_number));
    }

    private boolean validateFields() {
        return edtCountry.testValidity() && edtPhone_number.testValidity();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSubmit:

                if(validateFields()) {
                    getDockActivity().addDockableFragment(VarificationCodeFragment.newInstance("",""), "VarificationCodeFragment");
                }

                break;

        }

    }

}
