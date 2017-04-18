package com.app.ace.global;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.helpers.UIHelper;

/**
 * Created by khan_muhammad on 3/23/2017.
 */

public class PasswordEditTextChangeListener implements TextWatcher {

    private View view;
    DockActivity activity;

    public PasswordEditTextChangeListener(DockActivity activity,View view){
        this.view = view;
        this.activity = activity;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (view.getId()){
            case R.id.edtPassword:
                String pasword = s.toString();

                if(s.length()<6){
                    UIHelper.showShortToastInCenter(activity,activity.getString(R.string.password_length_alert));
                }

                break;

        }
    }
}
