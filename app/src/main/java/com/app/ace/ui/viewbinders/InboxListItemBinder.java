package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.InboxDataItem;
import com.app.ace.fragments.ChatFragment;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class InboxListItemBinder extends ViewBinder<InboxDataItem> {

    private ImageLoader imageLoader;
    DockActivity context;

    public InboxListItemBinder(DockActivity context) {
        super(R.layout.inbox_list_item);


        imageLoader = ImageLoader.getInstance();

        this.context=context;

    }


    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new InboxListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final InboxDataItem entity, int position, int grpPosition,
                         View view, Activity activity) {


        InboxListItemBinder.ViewHolder viewHolder = (InboxListItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getUserImage(), viewHolder.userImage);
        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getConversationId())), "ChatFragment");

            }
        });

        viewHolder.txtUserName.setText(entity.getUserName());
        viewHolder.txtUserMessage.setText(entity.getUserMessage());
    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txtUserMessage;


        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserName);
            txtUserMessage = (AnyTextView) view.findViewById(R.id.txtUserMessage);


        }
    }
}
