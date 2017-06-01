package com.app.ace.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.interfaces.SetTimeDataOnTextView;
import com.app.ace.ui.adapters.GridListAdapter;
import com.app.ace.ui.views.AnyTextView;
import com.google.gson.Gson;

import java.util.ArrayList;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * Created by muniyemiftikhar on 5/9/2017.
 */

public class DialogBox extends RoboDialogFragment implements View.OnClickListener {
    private Context context;

    @InjectView(R.id.rl_dialog)
    RelativeLayout rl_dialog;

    @InjectView(R.id.list_view)
    private ListView list_view;

    @InjectView(R.id.txtHeader)
    private AnyTextView txtHeader;

    @InjectView(R.id.btn_show)
    private Button btn_show;

    String itemPosition;

    private String title;

    private View.OnClickListener ShowDialoButton;

    private boolean isShow_btndialog_1 = true;
    private ArrayList<String> arrayList;

    private GridListAdapter adapter;

    DockActivity dockActivity;

    SetTimeDataOnTextView setTimeDataOnTextView;

    public static String TAG = "tag";
    int tag=-1;


    public static DialogBox newInstance() {
        return new DialogBox();
    }

    public static DialogBox newInstance(int tag)
    {
        Bundle args = new Bundle();
        args.putInt(TAG,tag);
        DialogBox fragment = new DialogBox();
        fragment.setArguments(args);

        return fragment;

    }
    public void setDocActivityContext(DockActivity dockActivity)
    {
        this.dockActivity=dockActivity;
    }

    public void SetTimeDataOnTextViewContext(SetTimeDataOnTextView setTimeDataOnTextView)
    {
        this.setTimeDataOnTextView=setTimeDataOnTextView;
    }



    public void setbtndialog_1_Listener(View.OnClickListener ShowDialoButton) {
        this.ShowDialoButton = ShowDialoButton;
    }


    public void setPopupData(String title, boolean isShow_btndialog_1) {

        this.title = title;

        this.isShow_btndialog_1 = isShow_btndialog_1;

    }

    public void dismissDialog() {
        DialogBox.this.dismiss();

    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getArguments()!=null)
        {
            tag=getArguments().getInt(TAG);
        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDialogText();
        loadListView(view);

        setListener();
    }

    private void setDialogText() {
        txtHeader.setText(title);
    }


    private void setListener() {


        btn_show.setOnClickListener(this);


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void loadListView(View view) {

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            arrayList.add(i + ":00");
        }

        adapter = new GridListAdapter(context, arrayList, true);
        list_view.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_show:
                itemPosition=  adapter.getSelectedItem();
                //dockActivity.addDockableFragment(TrainingBookingCalenderFragment.newInstance(itemPosition), "TrainingBookingCalenderFragment");
                setTimeDataOnTextView.setData(itemPosition,tag);
                DialogBox.this.dismiss();
                // /Get the selected position
                break;
        }
    }



}

