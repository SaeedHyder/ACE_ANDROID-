package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.entities.Specialities;
import com.app.ace.entities.UserProfile;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 4/4/2017.
 */

public class SearchPeopleListItemBinder extends ViewBinder<UserProfile> {

    private ImageLoader imageLoader;
    private ArrayList<String> specialtyArray = new ArrayList<>();
    private String Specialities = "";

    public SearchPeopleListItemBinder() {
        super(R.layout.searchpeople_list_item);
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new SearchPeopleListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(UserProfile entity, int position, int grpPosition, View view, Activity activity) {

        SearchPeopleListItemBinder.ViewHolder viewHolder = (SearchPeopleListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getProfile_image(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getFirst_name()+" "+entity.getLast_name());

        specialtyArray=new ArrayList<>();
        for (Specialities item : entity.getSpecialities()) {
            specialtyArray.add(item.getSpeciality().getTitle());
        }
        Specialities = TextUtils.join(",",specialtyArray);
        viewHolder.txtUserDetail.setText(Specialities);

    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txtUserDetail;

        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.SearchPuserImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.SearchPtxtUserName);
            txtUserDetail = (AnyTextView) view.findViewById(R.id.txtUserDetail);

        }
    }
}
