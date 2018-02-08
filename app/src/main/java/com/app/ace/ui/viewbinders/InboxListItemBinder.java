package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.InboxDataItem;
import com.app.ace.entities.MsgEnt;
import com.app.ace.fragments.ChatFragment;
import com.app.ace.fragments.TrainerProfileFragment;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.PreferenceHelper;
import com.app.ace.interfaces.DeleteChatInterface;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class InboxListItemBinder extends ViewBinder<MsgEnt> {

    private ImageLoader imageLoader;
    DockActivity context;
    public BasePreferenceHelper preferenceHelper;
    private DeleteChatInterface deleteChatInterface;
    public String UserName;

    public InboxListItemBinder(DockActivity context,BasePreferenceHelper preferenceHelper,DeleteChatInterface deleteChatInterface) {
        super(R.layout.inbox_list_item);

        imageLoader = ImageLoader.getInstance();
        this.preferenceHelper=preferenceHelper;
        this.context=context;
        this.deleteChatInterface=deleteChatInterface;

    }


    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {


        return new InboxListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final MsgEnt entity, final int position, int grpPosition,
                         View view, Activity activity) {

   /*     view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteChatInterface.deleteChat(position);
                return true;
            }
        });*/


        InboxListItemBinder.ViewHolder viewHolder = (InboxListItemBinder.ViewHolder) view.getTag();

        showInboxData(entity, viewHolder);


    }

    private void showInboxData(final MsgEnt entity, ViewHolder viewHolder) {
        if (entity.getSender() == null || entity.getMessage() == null){

        }
        else {
            try{
            if(entity.getSender().getId()==Integer.parseInt(preferenceHelper.getUserId()))
           {
                   imageLoader.displayImage(entity.getReceiver().getProfile_image(), viewHolder.userImage);
                    viewHolder.txtUserName.setText(entity.getReceiver().getFirst_name() + " " + entity.getReceiver().getLast_name());
                   viewHolder.txtUserMessage.setText(entity.getMessage().getMessage_text());
            }
            else if(entity.getReceiver().getId()==Integer.parseInt(preferenceHelper.getUserId())) {
                imageLoader.displayImage(entity.getSender().getProfile_image(), viewHolder.userImage);
                viewHolder.txtUserName.setText(entity.getSender().getFirst_name() + " " + entity.getSender().getLast_name());
                viewHolder.txtUserMessage.setText(entity.getMessage().getMessage_text());
            }}catch (Exception e)
            {
                e.printStackTrace();
            }


          /*  viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(entity.getSender().getId()==Integer.parseInt(preferenceHelper.getUserId()))
                    {
                        UserName=entity.getReceiver().getFirst_name()+" "+entity.getReceiver().getLast_name();
                        receivebyReceiver(entity);
                    }
                    else if(entity.getReceiver().getId()==Integer.parseInt(preferenceHelper.getUserId())) {
                        UserName=entity.getSender().getFirst_name()+" "+entity.getSender().getLast_name();
                        receivebySender(entity);
                    }
                }
            });
*/
           /* viewHolder.txtUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(entity.getMessage().getSender_id()==Integer.parseInt(preferenceHelper.getUserId()))
                    {
                        UserName=entity.getReceiver().getFirst_name()+" "+entity.getReceiver().getLast_name();
                        receivebyReceiver(entity);


                    }
                    else if(entity.getMessage().getReceiver_id()==Integer.parseInt(preferenceHelper.getUserId())) {
                        UserName=entity.getSender().getFirst_name()+" "+entity.getSender().getLast_name();
                        receivebySender(entity);


                    }
                }
            });*/


        }
    }

 /*   private void receivebyReceiver(MsgEnt entity) {
        context.addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getMessage().getConversation_id())
                ,String.valueOf(entity.getMessage().getReceiver_id())
                ,String.valueOf(entity.getMessage().getSender_id())
                ,UserName, String.valueOf(entity.getIs_following())
                ,entity.getReceiver().getProfile_image()
                ,entity.getReceiver().getFirst_name()+" "+entity.getReceiver().getLast_name()
                ,entity.getSender_block(),entity.getReceiver_block()
                ,entity.getSender_mute(),entity.getReceiver_mute()), "ChatFragment");
    }

    private void receivebySender(MsgEnt entity) {
        context.addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getMessage().getConversation_id())
                ,String.valueOf(entity.getMessage().getSender_id())
                ,String.valueOf(entity.getMessage().getSender_id())
                ,UserName, String.valueOf(entity.getIs_following())
                ,entity.getReceiver().getProfile_image()
                ,entity.getReceiver().getFirst_name()+" "+entity.getReceiver().getLast_name()
                ,entity.getSender_block(),entity.getReceiver_block()
                ,entity.getSender_mute(),entity.getReceiver_mute()), "ChatFragment");
    }*/

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
