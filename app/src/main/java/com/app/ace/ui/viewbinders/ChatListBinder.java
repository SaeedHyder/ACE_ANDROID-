package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.ChatDataItem;
import com.app.ace.fragments.HomeFragment;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.DialogHelper;
import com.app.ace.interfaces.DeleteChatInterface;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.ace.R.id.txtSenderDateImage;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class ChatListBinder extends ViewBinder<ChatDataItem> {

    private ImageLoader imageLoader;

    private DockActivity context;
    private DeleteChatInterface deleteChatInterface;

    private BasePreferenceHelper prefHelper;

    public ChatListBinder(DockActivity context,DeleteChatInterface deleteChatInterface) {
        super(R.layout.list_chat_item);

        this.context = context;
        imageLoader = ImageLoader.getInstance();
        this.deleteChatInterface=deleteChatInterface;
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new ChatListBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final ChatDataItem entity, final int position, int grpPosition,
                         View view, Activity activity) {

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteChatInterface.deleteMessage(position,entity.getId());
                return true;
            }
        });

        ChatListBinder.ViewHolder viewHolder = (ChatListBinder.ViewHolder) view.getTag();

        String[] senderTimeArray=entity.getSenderMessageTime().split(" ");
        String senderTime=senderTimeArray[1];

        String[] receiverTimeArray=  entity.getReceiverMessageTime().split(" ");
        String receiverTime=receiverTimeArray[1];



        if(entity.isSender()){
            if(entity.getSenderMessage().contains("http"))
            {
                viewHolder.leftLayout.setVisibility(View.VISIBLE);
                viewHolder.RightLayout.setVisibility(View.GONE);
                viewHolder.leftLayoutChild.setVisibility(View.GONE);
                viewHolder.leftLayoutImageChild.setVisibility(View.VISIBLE);

                imageLoader.displayImage(entity.getSenderMessage(),viewHolder.iv_postPicLeft);
                imageLoader.displayImage(entity.getSenderImage(), viewHolder.userImage);
                viewHolder.txtSenderDateImage.setText(senderTime);

                viewHolder.leftLayoutImageChild.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogHelper postImage=new DialogHelper(context);
                        postImage.postImage(R.layout.postimage_dialog,context,entity.getSenderMessage());
                        postImage.showDialog();
                    }
                });


            }
            else
            {
                viewHolder.leftLayout.setVisibility(View.VISIBLE);
                viewHolder.RightLayout.setVisibility(View.GONE);
                viewHolder.leftLayoutChild.setVisibility(View.VISIBLE);
                viewHolder.leftLayoutImageChild.setVisibility(View.GONE);

                imageLoader.displayImage(entity.getSenderImage(), viewHolder.userImage);

                viewHolder.txtSenderChat.setText(entity.getSenderMessage());
                viewHolder.txtSenderDate.setText(senderTime);
            }

        }

        else{
            if(entity.getReceiverMessage().contains("http"))
            {
                viewHolder.RightLayout.setVisibility(View.VISIBLE);
                viewHolder.rightLayout.setVisibility(View.GONE);
                viewHolder.leftLayout.setVisibility(View.GONE);
                viewHolder.rightLayoutImage.setVisibility(View.VISIBLE);
                imageLoader.displayImage(entity.getReceiverMessage(),viewHolder.iv_postPic);
                imageLoader.displayImage(entity.getSenderImage(), viewHolder.userImage2);
               // viewHolder.txtReceiverChat.setText(entity.getReceiverMessage());
                viewHolder.txtReceiverDateImage.setText(receiverTime);

                viewHolder.rightLayoutImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DialogHelper postImage=new DialogHelper(context);
                        postImage.postImage(R.layout.postimage_dialog,context,entity.getReceiverMessage());
                        postImage.showDialog();

                    }
                });



            }
            else {
                viewHolder.RightLayout.setVisibility(View.VISIBLE);
                viewHolder.leftLayout.setVisibility(View.GONE);
                viewHolder.rightLayout.setVisibility(View.VISIBLE);
                viewHolder.rightLayoutImage.setVisibility(View.GONE);

                imageLoader.displayImage(entity.getSenderImage(), viewHolder.userImage2);
                viewHolder.txtReceiverChat.setText(entity.getReceiverMessage());
                viewHolder.txtReceiverDate.setText(receiverTime);
            }
        }

        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(TrainerProfileFragment.newInstance(entity.getUserId()), "TrainerProfileFragment");
    }
});

        viewHolder.userImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(TrainerProfileFragment.newInstance(entity.getUserId()), "TrainerProfileFragment");
            }
        });



    }

    public static class ViewHolder extends BaseViewHolder {

        private RelativeLayout leftLayout;
        private CircleImageView userImage,userImage2;
        private AnyTextView txtSenderChat;
        private AnyTextView txtSenderDate;

        private RelativeLayout RightLayout;
        private RelativeLayout rightLayoutImage;
        private LinearLayout rightLayout;

        private AnyTextView txtReceiverChat;
        private AnyTextView txtReceiverDate;
        private ImageView iv_postPic;

        private LinearLayout leftLayoutChild;
        private RelativeLayout leftLayoutImageChild;
        private ImageView iv_postPicLeft;

        private AnyTextView txtSenderDateImage;
        private AnyTextView txtReceiverDateImage;





        public ViewHolder(View view) {

            leftLayout = (RelativeLayout) view.findViewById(R.id.leftLayout);
            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            userImage2 = (CircleImageView) view.findViewById(R.id.userImage2);
            txtSenderChat = (AnyTextView) view.findViewById(R.id.txtSenderChat);
            txtSenderDate = (AnyTextView) view.findViewById(R.id.txtSenderDate);

            RightLayout = (RelativeLayout) view.findViewById(R.id.RightLayout);
            txtReceiverChat = (AnyTextView) view.findViewById(R.id.txtReceiverChat);
            txtReceiverDate = (AnyTextView) view.findViewById((R.id.txtReceiverDate));

            iv_postPic=(ImageView) view.findViewById(R.id.iv_postPic);
            rightLayoutImage=(RelativeLayout) view.findViewById(R.id.rightLayoutImage);
            rightLayout=(LinearLayout) view.findViewById(R.id.rightLayout);

            iv_postPicLeft=(ImageView) view.findViewById(R.id.iv_postPicLeft);
            leftLayoutChild=(LinearLayout) view.findViewById(R.id.leftLayoutChild);
            leftLayoutImageChild=(RelativeLayout) view.findViewById(R.id.leftLayoutImageChild);

            txtSenderDateImage = (AnyTextView) view.findViewById(R.id.txtSenderDateImage);
            txtReceiverDateImage = (AnyTextView) view.findViewById(R.id.txtReceiverDateImage);





        }
    }
}
