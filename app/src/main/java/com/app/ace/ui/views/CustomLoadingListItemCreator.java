package com.app.ace.ui.views;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ace.R;
import com.paginate.abslistview.LoadingListItemCreator;

public class CustomLoadingListItemCreator implements LoadingListItemCreator {
    @Override
    public View newView(int position, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
        view.setTag(new VH(view));
        return view;
    }

    @Override
    public void bindView(int position, View view) {
        VH vh = (VH) view.getTag();
        vh.tvLoading.setText("Loading");
    }
}

class VH {
    TextView tvLoading;

    public VH(View itemView) {
        tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
    }
}

