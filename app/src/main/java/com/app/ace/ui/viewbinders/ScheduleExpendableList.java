package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.helpers.Datedialoghelper;
import com.app.ace.ui.viewbinders.abstracts.ExpandableListViewBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created on 5/4/2017.
 */

public class ScheduleExpendableList<E> extends ExpandableListViewBinder<String, E> {
    public ScheduleExpendableList() {
        super(R.layout.trainer_schedule_header_item, R.layout.trainer_schedule_child_item);
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new NewExpListGroupItemViewHolder(view);
    }

    @Override
    public BaseChildViewHolder createChildViewHolder(View view) {
        return new NewExpListChildItemViewHolder(view);
    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, View view, Activity activity,boolean isExpended) {
        try {
            if (!entity.equals("null,null") ) {
                NewExpListGroupItemViewHolder groupItemViewHolder = (NewExpListGroupItemViewHolder) view.getTag();
                if (isExpended){
                    groupItemViewHolder.indicator.setImageResource(R.drawable.dropdownup);
                }
                else
                    groupItemViewHolder.indicator.setImageResource(R.drawable.dropdowndown);
                String head = entity;
                String[] date = head.split(",");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(date[0]);
                Date endDate = dateFormat.parse(date[1]);
                System.out.println(startDate.getDate());
                groupItemViewHolder.txtHeader.setText(startDate.getDate()+" "+
                        getShortMonthName(startDate.getYear(),startDate.getMonth(),startDate.getDate())+ "  -  " +
                        endDate.getDate()+" "+getShortMonthName(endDate.getYear(),endDate.getMonth(),endDate.getDate()));
        /*
        String[] startDatearray = date[0].split("-");
        String startDate = startDatearray[2]+" "+Datedialoghelper.getShortMonthName(Integer.parseInt(startDatearray[0])
                ,Integer.parseInt(startDatearray[1])
                ,Integer.parseInt(startDatearray[2]));
        String EndDate = startDatearray[2]+" "+Datedialoghelper.getShortMonthName(Integer.parseInt(startDatearray[0])
                ,Integer.parseInt(startDatearray[1])
                ,Integer.parseInt(startDatearray[2]));*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  String getShortMonthName(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        return new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
    }
    @Override
    public void bindChildView(E entity, int position, int grpPosition, View view, Activity activity) {
        try {
            NewExpListChildItemViewHolder childItemViewHolder = (NewExpListChildItemViewHolder) view.getTag();
            String child = (String) entity;
            String[] time = child.split(",");
            childItemViewHolder.txtStart.setText(time[0]);
            childItemViewHolder.txtEnd.setText(time[1]);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static class NewExpListGroupItemViewHolder extends BaseGroupViewHolder {
        TextView txtHeader;
        ImageView indicator;
        public NewExpListGroupItemViewHolder(View view) {
            txtHeader = (TextView) view.findViewById(R.id.txt_date_group);
            indicator = (ImageView)view.findViewById(R.id.groupindicator);
        }
    }

    public static class NewExpListChildItemViewHolder extends BaseChildViewHolder {
        TextView txtStart;
        TextView txtEnd;

        public NewExpListChildItemViewHolder(View view) {
            txtStart = (TextView) view.findViewById(R.id.txtStart);
            txtEnd = (TextView) view.findViewById(R.id.txtEnd);
        }
    }
}
