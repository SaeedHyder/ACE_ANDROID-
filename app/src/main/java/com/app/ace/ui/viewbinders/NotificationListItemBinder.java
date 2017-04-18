package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.NotificationDataItem;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 3/20/2017.
 */

public class NotificationListItemBinder extends ViewBinder<NotificationDataItem> {

    private ImageLoader imageLoader;

    public NotificationListItemBinder() {
        super(R.layout.notification_list_item);

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new NotificationListItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(NotificationDataItem entity, int position, int grpPosition,
                         View view, Activity activity) {


        NotificationListItemBinder.ViewHolder viewHolder = (NotificationListItemBinder.ViewHolder) view.getTag();

        viewHolder.txtNotificationText.setText(entity.getNotificationText());
        viewHolder.txtNotificationDate.setText(entity.getNotificationDate());
    }

    public static class ViewHolder extends BaseViewHolder {


        private AnyTextView txtNotificationText;
        private AnyTextView txtNotificationDate;


        public ViewHolder(View view) {

            txtNotificationText = (AnyTextView) view.findViewById(R.id.txtNotificationText);
            txtNotificationDate = (AnyTextView) view.findViewById(R.id.txtNotificationDate);


        }
    }
}
