package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.ace.R;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 3/17/2017.
 */

public class UserPicItemBinder extends ViewBinder<String> {


    private ImageLoader imageLoader;

    public UserPicItemBinder() {
        super(R.layout.user_pic_list_item);

        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String picpath, int position, int grpPosition,
                         View view, Activity activity) {


        ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader.displayImage(picpath, viewHolder.iv_pic);



    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView iv_pic;

        public ViewHolder(View view) {
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);

        }
    }


}
