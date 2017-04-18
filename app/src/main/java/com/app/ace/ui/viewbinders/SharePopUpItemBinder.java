package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.entities.SharePopUpItemsEnt;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ahmedsyed on 4/6/2017.
 */

public class SharePopUpItemBinder extends ViewBinder<SharePopUpItemsEnt> {

    private ImageLoader imageLoader;

    public SharePopUpItemBinder() {
        super(R.layout.items_share_pop_up);

        imageLoader = ImageLoader.getInstance();
    }
    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new SharePopUpItemBinder.ViewHolder(view);
    }


    @Override
    public void bindView(SharePopUpItemsEnt entity, int position, int grpPosition, View view, Activity activity) {

        SharePopUpItemBinder.ViewHolder viewHolder = (SharePopUpItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImages(), viewHolder.CircularImageSharePop);
        viewHolder.tv_NameSharePop.setText(entity.getTv_name());

    }
    public static class ViewHolder extends BaseViewHolder {

        private CircleImageView CircularImageSharePop;
        private AnyTextView tv_NameSharePop;

        public ViewHolder(View view) {
            CircularImageSharePop= (CircleImageView) view.findViewById(R.id.CircularImageSharePop);
            tv_NameSharePop = (AnyTextView) view.findViewById(R.id.tv_NameSharePop);
        }
    }
}
