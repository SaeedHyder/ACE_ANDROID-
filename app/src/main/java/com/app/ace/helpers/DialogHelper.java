package com.app.ace.helpers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created on 4/27/2017.
 */

public class DialogHelper {
    Dialog dialog;


    public DialogHelper(Context context) {
        this.dialog = new Dialog(context);
    }


    public Dialog initDialog(int layoutID) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        return this.dialog;
    }

    public void SetTitle(String title) {
       this.dialog.setTitle(title);
    }

    public void setMessage(int txtmessageID, String message) {
        TextView txtmessage = (TextView) this.dialog.findViewById(txtmessageID);
        txtmessage.setText(message);
    }

    public void initPositiveBtn(int btnPositiveID, String btnText, View.OnClickListener onClickListener) {
        Button btnpositive = (Button) this.dialog.findViewById(btnPositiveID);
        btnpositive.setText(btnText);
        btnpositive.setOnClickListener(onClickListener);
    }

    public void initNegativeBtn(int btnNegativeID, String btnText, View.OnClickListener onClickListener) {
        Button btnnegative = (Button) this.dialog.findViewById(btnNegativeID);
        btnnegative.setText(btnText);
        btnnegative.setOnClickListener(onClickListener);
    }

    public void showDialog() {
        this.dialog.show();
    }

    public void dismissDialog() {
        this.dialog.dismiss();
    }


}
