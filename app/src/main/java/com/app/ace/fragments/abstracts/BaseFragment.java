package com.app.ace.fragments.abstracts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;
import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.activities.MainActivity;
import com.app.ace.global.WebServiceConstants;
import com.app.ace.helpers.BasePreferenceHelper;
import com.app.ace.helpers.GPSTracker;
import com.app.ace.helpers.UIHelper;
import com.app.ace.interfaces.LoadingListener;
import com.app.ace.retrofit.WebService;
import com.app.ace.retrofit.WebServiceFactory;
import com.app.ace.ui.views.AnyEditTextView;
import com.app.ace.ui.views.TitleBar;
import com.google.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import roboguice.fragment.RoboFragment;

public abstract class BaseFragment extends RoboFragment {
	
	protected Handler handler = new Handler();

	@Inject
	protected BasePreferenceHelper prefHelper;

	protected static WebService webService;
	protected static WebService googleWebService ;

	private DockActivity activity;

	protected GPSTracker mGpsTracker;

	protected static DockActivity myDockActivity;
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		getDockActivity().lockDrawer();

		mGpsTracker = new GPSTracker(getDockActivity());

		if (googleWebService == null) {
			googleWebService = WebServiceFactory.getWebServiceInstanceWithDefaultInterceptor(getDockActivity(), WebServiceConstants.GOOGLE_LAT_LONG_INFO_URL);
		}

		if (webService == null) {
			webService = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(getDockActivity(), WebServiceConstants.SERVICE_BASE_URL);
		}

		myDockActivity = getDockActivity();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setTitleBar( ((MainActivity) getDockActivity()).titleBar );
	}

	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.activity = (DockActivity) activity;
	}*/

	protected void createClient() {
		// webService = WebServiceFactory.getInstanceWithBasicGsonConversion();
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if ( getDockActivity().getWindow() != null )
			if ( getDockActivity().getWindow().getDecorView() != null )
				UIHelper.hideSoftKeyboard( getDockActivity(), getDockActivity()
						.getWindow().getDecorView() );
		
	}
	
	protected void loadingStarted() {
		
		if ( getParentFragment() != null )
			((LoadingListener) getParentFragment()).onLoadingStarted();
		else
			getDockActivity().onLoadingStarted();
		
		isLoading = true;
	}
	
	protected void loadingFinished() {
		
		if ( getParentFragment() != null )
			((LoadingListener) getParentFragment()).onLoadingFinished();
		else if ( getDockActivity() != null )
			getDockActivity().onLoadingFinished();
		
		isLoading = false;
		// else
		// ( (LoadingListener) super.getParentFragment() ).onLoadingFinished();
	}

	private String getDate(String OurDate)
	{
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date value = formatter.parse(OurDate);

			SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable
			dateFormatter.setTimeZone(TimeZone.getDefault());
			OurDate = dateFormatter.format(value);

			//Log.d("OurDate", OurDate);
		}
		catch (Exception e)
		{
			OurDate = "00-00-0000 00:00";
		}
		return OurDate;
	}
	
	protected DockActivity getDockActivity() {
		
		/*DockActivity activity = (DockActivity) getActivity();
		*//*while ( activity == null ) {
			activity = (DockActivity) getActivity();
			try {
				Thread.sleep( 50 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}*/
		return activity;
		
	}

	public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getDockActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = getDockActivity().getCurrentFocus();
		if (view == null) {
			view = new View(getDockActivity());
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	
	protected MainActivity getMainActivity() {
		return (MainActivity) getActivity();
	}
	
	protected TitleBar getTitleBar() {
		return getMainActivity().titleBar;
	}
	
	public String getTitleName() {
		return "";
	}
	
	/**
	 * This is called in the end to modify titlebar. after all changes.
	 *
	 * @param activity
	 */
	public void setTitleBar( TitleBar titleBar ) {
		titleBar.showTitleBar();
		titleBar.invalidate();
		// titleBar.refreshListener();
	}
	
	/**
	 * Gets the preferred height for each item in the ListView, in pixels, after
	 * accounting for screen density. ImageLoader uses this value to resize
	 * thumbnail images to match the ListView item height.
	 *
	 * @return The preferred height in pixels, based on the current theme.
	 */
	protected int getListPreferredItemHeight() {
		final TypedValue typedValue = new TypedValue();
		
		// Resolve list item preferred height theme attribute into typedValue
		getActivity().getTheme().resolveAttribute(
				android.R.attr.listPreferredItemHeight, typedValue, true );
		
		// Create a new DisplayMetrics object
		final DisplayMetrics metrics = new android.util.DisplayMetrics();
		
		// Populate the DisplayMetrics
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics( metrics );
		
		// Return theme value based on DisplayMetrics
		return (int) typedValue.getDimension( metrics );
	}
	
	protected String getStringTrimed( AnyEditTextView edtView ) {
		return edtView.getText().toString().trim();
	}
	
	/**
	 * This generic method to add validator to a text view should be used
	 * FormEditText
	 * 
	 * Usage : Takes Array of AnyEditTextView ;
	 * 
	 * @return void
	 */
	protected void addEmptyStringValidator( AnyEditTextView... allFields ) {
		
		for ( AnyEditTextView field : allFields ) {
			field.addValidator( new EmptyStringValidator() );
		}
		
	}
	
	protected void notImplemented() {
		UIHelper.showLongToastInCenter( getActivity(), "Coming Soon" );
	}
	
	protected void serverNotFound() {
		UIHelper.showLongToastInCenter( getActivity(),
				"Unable to connect to the server, "
						+ "are you connected to the internet?" );
	}
	
	/**
	 * This generic null string validator to be used FormEditText
	 * 
	 * Usage : formEditText.addValicator(new EmptyStringValidator);
	 * 
	 * @return Boolean and setError on respective field.
	 */
	protected class EmptyStringValidator extends Validator {
		
		public EmptyStringValidator() {
			super( "The field must not be empty" );
		}
		
		@Override
		public boolean isValid( EditText et ) {
			return et.getText().toString().trim().length() >= 1;
		}
		
	}
	
	/**
	 * Trigger when receives broadcasts from device to check wifi connectivity
	 * using connectivity manager
	 * 
	 * Usage : registerBroadcastReceiver() on resume of activity to receive
	 * notifications where needed and unregisterBroadcastReceiver() when not
	 * needed.
	 * 
	 * @return The connectivity of wifi/mobile carrier connectivity.
	 * 
	 */
	
	protected BroadcastReceiver mConnectionReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive( Context context, Intent intent ) {
			
			boolean isWifiConnected = false;
			boolean isMobileConnected = false;
			
			ConnectivityManager connMgr = (ConnectivityManager) context
					.getSystemService( Context.CONNECTIVITY_SERVICE );
			
			NetworkInfo networkInfo = connMgr
					.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
			
			if ( networkInfo != null )
				isWifiConnected = networkInfo.isConnected();
			
			networkInfo = connMgr
					.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
			
			if ( networkInfo != null )
				isMobileConnected = networkInfo.isConnected();
			
			Log.d( "NETWORK STATUS", "wifi==" + isWifiConnected + " & mobile=="
					+ isMobileConnected );
		}
	};
	
	private boolean isLoading;
	
	protected void finishLoading() {
		getActivity().runOnUiThread( new Runnable() {
			
			@Override
			public void run() {
				loadingFinished();
			}
		} );
	}
	
	protected boolean checkLoading() {
		if ( isLoading ) {
			UIHelper.showLongToastInCenter( getActivity(),
					R.string.message_wait );
			return false;
		} else {
			return true;
		}
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		activity = (DockActivity) context;
	}
}
