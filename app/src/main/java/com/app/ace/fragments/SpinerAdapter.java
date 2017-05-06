package com.app.ace.fragments;

/**
 * Created by muniyemiftikhar on 4/29/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.ace.R;
import com.app.ace.ui.views.AnyTextView;

import java.util.ArrayList;
import java.util.List;


public class SpinerAdapter extends ArrayAdapter<SpinnerDataItem> {
    private Context mContext;
    private ArrayList<SpinnerDataItem> listState;
    private SpinerAdapter myAdapter;
    private boolean isFromView = false;

    boolean[] itemChecked;

    public SpinerAdapter(Context context, int resource, List<SpinnerDataItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SpinnerDataItem>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (AnyTextView) convertView.findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cb_text);

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean ischeck) {
                    int getPosition = (Integer) buttonView.getTag();

                    listState.get(getPosition).setSelected(buttonView.isChecked());
                }
            });
            convertView.setTag(holder);
            convertView.setTag(R.id.text, holder.mTextView);
            convertView.setTag(R.id.cb_text, holder.mCheckBox);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setTag(position);

        holder.mTextView.setText(listState.get(position).getTitle());
        holder.mCheckBox.setChecked(listState.get(position).isSelected());

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        return convertView;

    }

    private class ViewHolder {
        private AnyTextView mTextView;
        private CheckBox mCheckBox;
    }
}