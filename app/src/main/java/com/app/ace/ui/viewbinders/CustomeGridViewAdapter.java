package com.app.ace.ui.viewbinders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.app.ace.R;

import java.util.ArrayList;

/**
 * Created by muniyemiftikhar on 4/18/2017.
 */

public class CustomeGridViewAdapter extends ArrayAdapter<Integer> {

   private ArrayList<Integer> arrChildCollection = new ArrayList<>();


    public CustomeGridViewAdapter(Context context, int resource, ArrayList<Integer> objects) {
        super(context, resource, objects);
        this.arrChildCollection = objects;


    }

    @Override
    public int getCount() {

        return arrChildCollection.size();
       //return (arrChildCollection == null) ? 0 : arrChildCollection.size();
}

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder;

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.grid_item, null);

            holder = new Viewholder();


            holder.imgChildGrid = (ImageView) view.findViewById(R.id.imgChildGrid);

            view.setTag(holder);
        }
        else {
            holder = (Viewholder) view.getTag();
        }


        //FollowChildItem currentText = getItem(position);

        //imageLoader.displayImage(childArray.get(position).getImage(), holder.imgChildGrid);

        //holder.imgChildGrid.setImageResource(R.drawable.images);//mi

//        holder.imgChildGrid.setImageResource(arrChildCollection.get(position).getImage());

        holder.imgChildGrid.setImageResource(arrChildCollection.get(position));

        //holder.imgChildGrid.setImageResource(arrChildCollection.get(getPosition(currentText)).getImage());

       /* holder.imgChildGrid.setImageResource();
        name.setText(TxtArray.get(position).getName());*/

        return view;
    }
}

class Viewholder {
    public ImageView imgChildGrid;
}
