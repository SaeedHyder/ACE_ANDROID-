package com.app.ace.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.entities.SharePopUpItemsEnt;
import com.app.ace.entities.UserProfile;
import com.app.ace.fragments.ChatFragment;
import com.app.ace.fragments.NewMsgChat_Screen_Fragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.ace.R.id.tv_NameSharePop;

/**
 * Created by ahmedsyed on 4/7/2017.
 */

public class RecyclerViewAdapterSharePop extends RecyclerView.Adapter<RecyclerViewAdapterSharePop.MyViewHolder> {


    DockActivity context;
    private ImageLoader imageLoader;
    public String post_pic_path;


    private List<UserProfile> SharePopUpList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView images;
        public TextView tv_name;

        public MyViewHolder(View view) {
            super(view);
            images = (CircleImageView) view.findViewById(R.id.CircularImageSharePop);
            tv_name = (TextView) view.findViewById(tv_NameSharePop);

        }
    }


    public RecyclerViewAdapterSharePop(List<UserProfile> sharePopUpList, DockActivity a, String post_pic_path) {
        this.SharePopUpList = sharePopUpList;
        this.context=a;
        imageLoader = ImageLoader.getInstance();
        this.post_pic_path=post_pic_path;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_share_pop_up, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final UserProfile sharePopUpItemsEnt =SharePopUpList.get(position);

        holder.tv_name.setText(sharePopUpItemsEnt.getFirst_name()+" "+sharePopUpItemsEnt.getLast_name());
        imageLoader.displayImage(sharePopUpItemsEnt.getProfile_image(),holder.images);

        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context.addDockableFragment(ChatFragment.newInstance(), "Chat Fragment");
                String UserName=sharePopUpItemsEnt.getFirst_name()+" "+sharePopUpItemsEnt.getLast_name();
                context.addDockableFragment(NewMsgChat_Screen_Fragment.newInstance(sharePopUpItemsEnt.getId(),UserName,post_pic_path), "NewMsgChat_Screen_Fragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return SharePopUpList.size();
    }
}