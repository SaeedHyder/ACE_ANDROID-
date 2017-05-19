package com.app.ace.fragments;

/**
 * Created by muniyemiftikhar on 4/29/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.SpinnerDataItem;
import com.app.ace.interfaces.EditTrainerData;
import com.app.ace.ui.views.AnyTextView;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.When;


public class SpinerAdapter extends ArrayAdapter<SpinnerDataItem> {
    private DockActivity mContext;
    private ArrayList<SpinnerDataItem> listState;
    private ArrayList<SpinnerDataItem> education=new ArrayList();
    private ArrayList<SpinnerDataItem> specialty=new ArrayList();
    EditTrainerData editTrainerData;
    private SpinerAdapter myAdapter;
    private boolean isFromView = false;

    boolean[] itemChecked;

    public SpinerAdapter(DockActivity context, int resource, List<SpinnerDataItem> objects,EditTrainerData editTrainerData) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SpinnerDataItem>) objects;
        this.editTrainerData=editTrainerData;
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

            convertView.setTag(holder);
            convertView.setTag(R.id.text, holder.mTextView);
            convertView.setTag(R.id.cb_text, holder.mCheckBox);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setTag(position);


        holder.mTextView.setText(listState.get(position).getTitle());
       holder.mCheckBox.setChecked(listState.get(position).isSelected());

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean ischeck) {
                int getPosition = (Integer) buttonView.getTag();

                listState.get(getPosition).setSelected(buttonView.isChecked());

                if (listState.get(0).getTitle().contains(buttonView.getContext().getString(R.string.Select_Certification))) {
                    education = listState;
                    editTrainerData.updateEducationData(education);
                }
                if (listState.get(0).getTitle().contains(buttonView.getContext().getString(R.string.Select_Speciality))) {
                    specialty = listState;
                    editTrainerData.updateSpecialtyData(specialty);
                }
            }
        });




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