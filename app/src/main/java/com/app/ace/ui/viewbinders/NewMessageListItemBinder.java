package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.NewMessageDataItem;
import com.app.ace.entities.SearchPeopleDataItem;
import com.app.ace.fragments.ChatFragment;
import com.app.ace.fragments.NewMessageFragment;
import com.app.ace.fragments.NewMsgChat_Screen_Fragment;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class NewMessageListItemBinder extends ViewBinder<NewMessageDataItem> {

    private ImageLoader imageLoader;
    private DockActivity context;

    public NewMessageListItemBinder(DockActivity context) {
        super(R.layout.new_message_list_item);
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new NewMessageListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(NewMessageDataItem entity, int position, int grpPosition, View view, Activity activity) {

        NewMessageListItemBinder.ViewHolder viewHolder = (NewMessageListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUserName());

        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.is_show_trainer = false;
                context.addDockableFragment(NewMsgChat_Screen_Fragment.newInstance(), "NewMsgChat_Screen_Fragment");
            }
        });


    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;



        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.img_NewMsgProfile);
            txtUserName = (AnyTextView) view.findViewById(R.id.txt_NewMsgUserName);

        }
    }

}