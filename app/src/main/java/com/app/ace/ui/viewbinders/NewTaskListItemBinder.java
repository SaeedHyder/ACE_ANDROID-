package com.app.ace.ui.viewbinders;

import android.app.Activity;
import android.view.View;

import com.app.ace.ui.viewbinders.abstracts.ViewBinder;

public class NewTaskListItemBinder extends ViewBinder<String> {
	
	public NewTaskListItemBinder( int layoutResId ) {
		super( layoutResId );
	}
	
	@Override
	public com.app.ace.ui.viewbinders.abstracts.ViewBinder.BaseViewHolder createViewHolder(
			View view ) {
		return new NewListItemViewHolder( view );
	}
	
	@Override
	public void bindView( String entity, int position, int grpPosition,
			View view, Activity activity ) {
	}
	
	public static class NewListItemViewHolder extends BaseViewHolder {
		
		public NewListItemViewHolder( View view ) {
		}
	}
	
}
