package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.NotificationEnt;
import com.app.ace.fragments.ChatFragment;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class NotificationListItemBinder extends ViewBinder<NotificationEnt> implements View.OnClickListener {

    private ImageLoader imageLoader;
    private DockActivity context;
    public NotificationListItemBinder(DockActivity context) {
        super(R.layout.notification_list_item);
        this.context = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new NotificationListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(NotificationEnt entity, int position, int grpPosition,
                         View view, Activity activity) {


        NotificationListItemBinder.ViewHolder viewHolder = (NotificationListItemBinder.ViewHolder) view.getTag();
        viewHolder.container.setTag(entity);
        viewHolder.container.setOnClickListener(this);
        viewHolder.txtNotificationText.setText(entity.getMessage());
        viewHolder.txtNotificationDate.setText(entity.getCreated_at());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notificationContainer:
                NotificationEnt entity = (NotificationEnt)v.getTag();
                switch (entity.getAction_type()){
                    case "conversation":
                        context.addDockableFragment(ChatFragment.newInstance(String.valueOf(entity.getAction_id()),String.valueOf(entity.getReceiver_id()),"UserName"), "ChatFragment");
                        break;
                }
                break;
        }
    }

    public static class ViewHolder extends BaseViewHolder {


        private AnyTextView txtNotificationText;
        private AnyTextView txtNotificationDate;
        private LinearLayout container;

        public ViewHolder(View view) {
            container = (LinearLayout) view.findViewById(R.id.notificationContainer);
            txtNotificationText = (AnyTextView) view.findViewById(R.id.txtNotificationText);
            txtNotificationDate = (AnyTextView) view.findViewById(R.id.txtNotificationDate);


        }
    }
}
