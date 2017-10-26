package com.app.ace.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ace.R;
import com.app.ace.helpers.UIHelper;

import berlin.volders.badger.BadgeShape;
import berlin.volders.badger.Badger;
import berlin.volders.badger.CountBadge;

public class TitleBar extends RelativeLayout {

    private TextView txtTitle;
    private TextView txtTitle2;
    private TextView txtRight;
    private ImageView btnLeft;

    private ImageView btnRight;
    private ImageView btnRight2;
    private ImageView btnRight3;
    private ImageView imgNotificationCounter;
    private CountBadge badge;
    private AnyEditTextView searchbar;


    private View.OnClickListener menuButtonListener;
    private OnClickListener backButtonListener;

    private Context context;


    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initLayout(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
    }

    private void bindViews() {

        txtTitle = (TextView) this.findViewById(R.id.txt_subHead);
        txtTitle2 = (TextView) this.findViewById(R.id.txt_subHead2);
        txtRight = (TextView) this.findViewById(R.id.txtRight);
        btnRight = (ImageView) this.findViewById(R.id.btnRight);
        btnRight2 = (ImageView) this.findViewById(R.id.btnRight2);
        btnRight3 = (ImageView) this.findViewById(R.id.btnRight3);
        btnLeft = (ImageView) this.findViewById(R.id.btnLeft);
        imgNotificationCounter = (ImageView) this.findViewById(R.id.imgNotificationCount);
        searchbar = (AnyEditTextView) this.findViewById(R.id.edit_searchBarr);


    }

    private void initLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_main, this);
        bindViews();
    }

    public void hideButtons() {
        txtTitle.setVisibility(View.GONE);
        txtTitle2.setVisibility(View.GONE);
        btnLeft.setVisibility(View.GONE);
        txtRight.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        btnRight2.setVisibility(View.GONE);
        btnRight3.setVisibility(View.GONE);
        hideBadge();
    }

    public void showBackButton() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setImageResource(R.drawable.back);
        btnLeft.setOnClickListener(backButtonListener);

    }

    public AnyEditTextView showSearchBar() {
        searchbar.setVisibility(View.VISIBLE);
        return searchbar;
    }

    public void hideSearchBar() {
        searchbar.setVisibility(View.GONE);
        clearSearchedText();
    }
    private void clearSearchedText(){
        searchbar.setText("");
    }

    public void showAddButton(View.OnClickListener addBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.add);
        btnRight.setOnClickListener(addBtnListener);
        hideBadge();
    }

    public void showHelpButton(View.OnClickListener helpBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.help);
        btnRight.setOnClickListener(helpBtnListener);
        hideBadge();
    }

    public void showSearchButton(View.OnClickListener searchBtnListener) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setImageResource(R.drawable.search_icon);
        btnLeft.setOnClickListener(searchBtnListener);

    }

    public void showCommentButton(View.OnClickListener commentBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.comment_icon);
        btnRight.setOnClickListener(commentBtnListener);

    }

    public void showNotificationButton(View.OnClickListener notificationBtnListener) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setImageResource(R.drawable.notification_icon);
        btnRight2.setOnClickListener(notificationBtnListener);

    }

    public void showSaveButton(View.OnClickListener saveBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.tick_icon);
        btnRight.setOnClickListener(saveBtnListener);
        hideBadge();

    }

    public void showTickButton(View.OnClickListener tickBtnListener) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setImageResource(R.drawable.tick);
        btnRight2.setOnClickListener(tickBtnListener);
        hideBadge();

    }

    public void showRepeatButton(View.OnClickListener repeatBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.repeat);
        btnRight.setOnClickListener(repeatBtnListener);
        hideBadge();

    }

    public void showSettingButton(View.OnClickListener settingBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.settings_icon);
        btnRight.setOnClickListener(settingBtnListener);
        hideBadge();

    }

    public void showMessageButton(View.OnClickListener msgBtnListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.icon);
        btnRight.setOnClickListener(msgBtnListener);
        hideBadge();

    }

    public void hideMessageButton() {
        btnRight.setVisibility(View.GONE);
        btnRight.setImageResource(R.drawable.icon);
        hideBadge();

    }



    public void showCancelButton(View.OnClickListener cancelBtn) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(R.drawable.cancel_icon);
        btnRight.setOnClickListener(cancelBtn);
        hideBadge();
    }

    public void hideBadge() {
        imgNotificationCounter.setVisibility(View.GONE);
    }

    public ImageView getImgNotificationCounter(){
        return imgNotificationCounter;
    }

    public CountBadge initBadge(Context mcontext) {
        imgNotificationCounter.setVisibility(View.VISIBLE);
        CountBadge.Factory circle = new CountBadge.Factory(BadgeShape.circle(.6f, Gravity.END | Gravity.TOP),
                getResources().getColor(R.color.white), getResources().getColor(R.color.black));

        Badger<CountBadge> badger = Badger.sett(getResources().getDrawable(R.drawable.ic_badge), circle);
        imgNotificationCounter.setImageDrawable(badger.drawable);
        badge = badger.badge;
        badge.setCount(0);
        return badge;

    }

    public void addtoBadge(int count) {
        try {
            if (count > 99) {
                CountBadge.Factory circle = new CountBadge.Factory(BadgeShape.oval(0.8f, 1.6f, Gravity.BOTTOM),
                        getResources().getColor(R.color.white), getResources().getColor(R.color.black));

                Badger<CountBadge> badger = Badger.sett(getResources().getDrawable(R.drawable.ic_badge), circle);
                imgNotificationCounter.setImageDrawable(badger.drawable);
                badge = badger.badge;
                badge.setCount(count);
            } else
                badge.setCount(count);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    public void addtoBadge(int count,CountBadge countBadge) {
        try {
            if (count > 99) {
                CountBadge.Factory circle = new CountBadge.Factory(BadgeShape.oval(1f, 2f, Gravity.BOTTOM),
                        getResources().getColor(R.color.white), getResources().getColor(R.color.black));

                Badger<CountBadge> badger = Badger.sett(getResources().getDrawable(R.drawable.ic_badge), circle);
                imgNotificationCounter.setImageDrawable(badger.drawable);
                countBadge = badger.badge;
                countBadge.setCount(count);
            } else
                countBadge.setCount(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isBadgeVisible(){
    return imgNotificationCounter.getVisibility()==VISIBLE;
    }
    public void showBadge(){
        imgNotificationCounter.setVisibility(VISIBLE);
    }
    public int getBadgeCount() {
        try {
            return badge.getCount();
        } catch (Exception e) {
            UIHelper.showShortToastInCenter(context, "Initialize Badge First");
        }
        return -1;
    }

    public void showMenuButton() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setOnClickListener(menuButtonListener);
        btnLeft.setImageResource(R.drawable.ic_launcher);
    }

    public void setSubHeading(String heading) {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(heading);
    }

    public void setSubHeadingWithDiscription(String heading, String heading2, Context con) {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle2.setVisibility(View.VISIBLE);

        txtTitle.setText(heading);
        txtTitle2.setText(heading2);

        //txtTitle.setTextSize(con.getResources().getDimension(R.dimen.x7));

		/*if(Build.VERSION.SDK_INT >= 24){
            //txtTitle.setText(Html.fromHtml(heading, Html.FROM_HTML_MODE_COMPACT));
		}else{
			txtTitle.setText(Html.fromHtml(heading));
		}*/
    }

    public void showTitleBar() {
        this.setVisibility(View.VISIBLE);
    }

    public void hideTitleBar() {
        this.setVisibility(View.GONE);
    }

    public void setMenuButtonListener(View.OnClickListener listener) {
        menuButtonListener = listener;
    }

    public void setBackButtonListener(View.OnClickListener listener) {
        backButtonListener = listener;
    }


}
