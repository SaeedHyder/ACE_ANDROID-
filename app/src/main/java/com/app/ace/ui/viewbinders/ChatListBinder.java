package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.ChatDataItem;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class ChatListBinder extends ViewBinder<ChatDataItem> {

    private ImageLoader imageLoader;

    private DockActivity context;

    public ChatListBinder(DockActivity context) {
        super(R.layout.list_chat_item);

        this.context = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new ChatListBinder.ViewHolder(view);
    }

    @Override
    public void bindView(ChatDataItem entity, int position, int grpPosition,
                         View view, Activity activity) {


        ChatListBinder.ViewHolder viewHolder = (ChatListBinder.ViewHolder) view.getTag();

        if(entity.isSender()){
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.RightLayout.setVisibility(View.GONE);

            imageLoader.displayImage(entity.getSenderImage(), viewHolder.userImage);
            imageLoader.displayImage(entity.getReceiverImage(), viewHolder.userImage2);
            viewHolder.txtSenderChat.setText(entity.getSenderMessage());
            viewHolder.txtSenderDate.setText(entity.getSenderMessageTime());



        }

        else{
            viewHolder.RightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);

            viewHolder.txtReceiverChat.setText(entity.getReceiverMessage());
            viewHolder.txtReceiverDate.setText(entity.getReceiverMessageTime());
        }

        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.is_show_trainer = false;
                context.addDockableFragment(TrainerProfileFragment.newInstance(), "TrainerProfileFragment");
            }
        });

        viewHolder.userImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.is_show_trainer = true;
                context.addDockableFragment(TrainerProfileFragment.newInstance(), "TrainerProfileFragment");
            }
        });



    }

    public static class ViewHolder extends BaseViewHolder {

        private RelativeLayout leftLayout;
        private CircleImageView userImage,userImage2;
        private AnyTextView txtSenderChat;
        private AnyTextView txtSenderDate;

        private RelativeLayout RightLayout;
        private AnyTextView txtReceiverChat;
        private AnyTextView txtReceiverDate;

        public ViewHolder(View view) {

            leftLayout = (RelativeLayout) view.findViewById(R.id.leftLayout);
            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            userImage2 = (CircleImageView) view.findViewById(R.id.userImage2);
            txtSenderChat = (AnyTextView) view.findViewById(R.id.txtSenderChat);
            txtSenderDate = (AnyTextView) view.findViewById(R.id.txtSenderDate);

            RightLayout = (RelativeLayout) view.findViewById(R.id.RightLayout);
            txtReceiverChat = (AnyTextView) view.findViewById(R.id.txtReceiverChat);
            txtReceiverDate = (AnyTextView) view.findViewById((R.id.txtReceiverDate));

        }
    }
}
