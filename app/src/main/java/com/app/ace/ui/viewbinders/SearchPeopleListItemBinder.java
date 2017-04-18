package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 4/4/2017.
 */

public class SearchPeopleListItemBinder extends ViewBinder<SearchPeopleDataItem> {

    private ImageLoader imageLoader;

    public SearchPeopleListItemBinder() {
        super(R.layout.searchpeople_list_item);
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new SearchPeopleListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(SearchPeopleDataItem entity, int position, int grpPosition, View view, Activity activity) {

        SearchPeopleListItemBinder.ViewHolder viewHolder = (SearchPeopleListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());


    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;



        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.SearchPuserImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.SearchPtxtUserName);

        }
    }
}
