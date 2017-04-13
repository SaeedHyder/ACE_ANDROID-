package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.ace.R;
import com.app.ace.entities.CommentsSectionItemsEnt;
import com.app.ace.fragments.HomeFragment;
import com.app.ace.fragments.abstracts.BaseFragment;
import com.app.ace.interfaces.CommentSection;
import com.app.ace.ui.viewbinders.abstracts.ViewBinder;
import com.app.ace.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;



public class CommentSectionItemBinder extends ViewBinder<CommentsSectionItemsEnt> {



    private ImageLoader imageLoader;
    public CommentSection commentSection;

    public CommentSectionItemBinder( CommentSection commentSection) {
        super(R.layout.items_comment_section);
        this.commentSection = commentSection;
        imageLoader = ImageLoader.getInstance();
    }
    @Override
    public ViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new CommentSectionItemBinder.ViewHolder(view);
    }


    @Override
    public void bindView(final CommentsSectionItemsEnt entity, int position, int grpPosition, View view, Activity activity)
    {

        final CommentSectionItemBinder.ViewHolder viewHolder = (CommentSectionItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImageCommentor(), viewHolder.CircularImageCommentor);
        viewHolder.tv_NameCommentor.setText(entity.getNameCommentor());
        viewHolder.tv_CommentBox.setText(entity.getComment());
        viewHolder.tv_Time.setText(entity.getTime());

        viewHolder.tv_Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                commentSection.onReplyClicked(entity);
            }

        });
    }


    public static class ViewHolder extends BaseViewHolder {

        private CircleImageView CircularImageCommentor;
        private AnyTextView tv_NameCommentor;
        private AnyTextView tv_CommentBox;
        private AnyTextView tv_Time;
        private AnyTextView tv_Reply;



        public ViewHolder(View view) {

            CircularImageCommentor= (CircleImageView) view.findViewById(R.id.CircularImageCommentor);
            tv_NameCommentor = (AnyTextView) view.findViewById(R.id.tv_NameCommentor);
            tv_CommentBox = (AnyTextView) view.findViewById(R.id.tv_CommentBox);
            tv_Time = (AnyTextView) view.findViewById(R.id.tv_Time);
            tv_Reply = (AnyTextView) view.findViewById(R.id.tv_Reply);
        }
    }
}


