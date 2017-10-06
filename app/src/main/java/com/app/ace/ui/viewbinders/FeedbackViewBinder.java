package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.TrainerReviews;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 10/4/2017.
 */

public class FeedbackViewBinder extends ViewBinder<TrainerReviews> {

    private ImageLoader imageLoader;

    DockActivity context;

    public FeedbackViewBinder(DockActivity context) {
        super(R.layout.row_item_feedback);
        this.context = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new FeedbackViewBinder.ViewHolder(view);
    }

    @Override
    public void bindView(TrainerReviews entity, int position, int grpPosition, View view, Activity activity) {

        FeedbackViewBinder.ViewHolder viewHolder = (FeedbackViewBinder.ViewHolder) view.getTag();

        String[] receiverTimeArray = entity.getCreatedAt().split(" ");
        String time = receiverTimeArray[1];

        imageLoader.displayImage(entity.getUser().getProfileImage(), viewHolder.userImage);
        viewHolder.txtUserName.setText(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        viewHolder.txtFeedback.setText(entity.getReview() + "");
        viewHolder.txtTime.setText(entity.getTimeAgo()+"");
        if (entity.getReviewType().equals("positive")) {
            viewHolder.txt_positive.setText("+1 positive");
            viewHolder.txt_positive.setTextColor(context.getResources().getColor(R.color.txtview_title_color));
        } else {
            viewHolder.txt_positive.setText("-1 negative");
            viewHolder.txt_positive.setTextColor(context.getResources().getColor(R.color.negative_text));
        }


    }

    public static class ViewHolder extends BaseViewHolder {


        private CircleImageView userImage;
        private AnyTextView txtUserName;
        private AnyTextView txt_positive;
        private AnyTextView txtFeedback;
        private AnyTextView txtTime;


        public ViewHolder(View view) {

            userImage = (CircleImageView) view.findViewById(R.id.userImage);
            txtUserName = (AnyTextView) view.findViewById(R.id.txtUserName);
            txtFeedback = (AnyTextView) view.findViewById(R.id.txtFeedback);
            txt_positive = (AnyTextView) view.findViewById(R.id.txt_positive);
            txtTime = (AnyTextView) view.findViewById(R.id.txtTime);

        }
    }
}
