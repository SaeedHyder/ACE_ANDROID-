package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.SpecialityEnt;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;

/**
 * Created by saeedhyder on 11/15/2017.
 */

public class specialityItemBinder extends ViewBinder<SpecialityEnt> {

    DockActivity dockActivity;

    public specialityItemBinder( DockActivity dockActivity) {
        super(R.layout.row_item_speciality);
        this.dockActivity=dockActivity;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new specialityItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final SpecialityEnt entity, int position, int grpPosition, View view, Activity activity) {

        specialityItemBinder.ViewHolder viewHolder = (specialityItemBinder.ViewHolder) view.getTag();
        viewHolder.checkBox.setText(entity.getTitle());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entity.setIschecked(isChecked);
            }
        });

    }

    public static class ViewHolder extends BaseViewHolder {

        private CheckBox checkBox;

        public ViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.cb_speciality);

        }
    }
}
