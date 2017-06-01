package com.app.ace;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class BaseApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "GFNahvyiL750wgFv4xV2pGNtw";
    private static final String TWITTER_SECRET = "	SwCGoLyeYjaImwmsCJFNJRiW01QH6zeqIVUoLWeR0Ml6TtXGUt";


	public static Bus bus;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		MultiDex.install(this);

		super.onCreate();
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));
		initImageLoader();

		bus = new Bus(ThreadEnforcer.MAIN);
	}
	
	public void initImageLoader() {
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri( R.color.black )
				.showImageOnFail( R.color.black ).resetViewBeforeLoading( true )
				.cacheInMemory( true ).cacheOnDisc( true )
				
				.imageScaleType( ImageScaleType.IN_SAMPLE_POWER_OF_2 )
				.displayer( new FadeInBitmapDisplayer( 850 ) )
				.bitmapConfig( Bitmap.Config.RGB_565 ).build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext() ).defaultDisplayImageOptions( options )
				.build();
		
		ImageLoader.getInstance().init( config );
		L.disableLogging();
	}


	public static Bus getBus(){
		return bus;
	}
	
}
