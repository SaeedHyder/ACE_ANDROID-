package com.app.ace.ui.views;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ace.R;

public class TitleBar extends RelativeLayout {

	private TextView txtTitle;
	private TextView txtTitle2;
	private TextView txtRight;
	private ImageView btnLeft;

	private ImageView btnRight;
	private ImageView btnRight2;
	private ImageView btnRight3;

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
		searchbar=(AnyEditTextView)this.findViewById(R.id.edit_searchBarr);


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

	}

	public void showBackButton() {
		btnLeft.setVisibility(View.VISIBLE);
		btnLeft.setImageResource(R.drawable.back);
		btnLeft.setOnClickListener(backButtonListener);

	}

	public void showSearchBar() {
		searchbar.setVisibility(View.VISIBLE);
	}

	public void hideSearchBar() {
		searchbar.setVisibility(View.GONE);
	}


	public void showAddButton(View.OnClickListener addBtnListener) {
		btnRight.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.add);
		btnRight.setOnClickListener(addBtnListener);

	}

	public void showHelpButton(View.OnClickListener helpBtnListener) {
		btnRight.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.help);
		btnRight.setOnClickListener(helpBtnListener);

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

	}

    public void showTickButton(View.OnClickListener tickBtnListener) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setImageResource(R.drawable.tick);
        btnRight2.setOnClickListener(tickBtnListener);

    }
	public void showRepeatButton(View.OnClickListener repeatBtnListener) {
		btnRight.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.repeat);
		btnRight.setOnClickListener(repeatBtnListener);

	}

	public void showSettingButton(View.OnClickListener settingBtnListener) {
		btnRight.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.settings_icon);
		btnRight.setOnClickListener(settingBtnListener);

	}

	public void showCancelButton(View.OnClickListener cancelBtn) {
		btnRight.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.cancel_icon);
		btnRight.setOnClickListener(cancelBtn);


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

	public void setSubHeadingWithDiscription(String heading,String heading2,Context con) {
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
