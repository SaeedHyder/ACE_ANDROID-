package com.app.ace.map.abstracts;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;

import com.app.ace.R;
import com.app.ace.activities.DockActivity;
import com.app.ace.activities.MainActivity;
import com.app.ace.activities.MapsActivity;
import com.app.ace.entities.MapScreenItem;
import com.app.ace.fragments.MapScreenFragment;
import com.app.ace.global.AppConstants;
import com.app.ace.helpers.BitmapHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.io.Resources;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class MapMarkerItemBinder extends MapMarkerBinder<MapScreenItem> {

    private MainActivity activity;

    public MapMarkerItemBinder(MainActivity activity){
        this.activity = activity;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void addMarker(GoogleMap googleMap, MapScreenItem entity, int position) {


        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(entity.getImage());
      //  image=BitmapHelper.scaleCenterCrop(bitmap,150,150);



       // bitmap = Bitmap.createScaledBitmap(bitmap,150, 150, true);
       // BitmapHelper.getRoundedCornerImage(bitmap)
       // RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, bitmap);
      //  dr.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);



        if (entity.getLat() != null && entity.getLng() != null) {
            if (!entity.getLat().equals("null") && !entity.getLng().equals("null"))
                if (entity.getLat().length() > 0 && entity.getLng().length() > 0) {

                    try {
                        googleMap.addMarker(new MarkerOptions()
                                .title(String.valueOf(position))
                                .position(new LatLng(Double.valueOf(entity.getLat()), Double.valueOf(entity.getLng())))
                                //.icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                .icon(BitmapDescriptorFactory.fromBitmap( BitmapHelper.getRoundCircleImage(bitmap))));
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.profile_container1)));
                        //.fromPath(tempUri.toString())));
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(entity.getLat()), Double.valueOf(entity.getLng())), AppConstants.zoomIn));
                    googleMap.animateCamera(CameraUpdateFactory.zoomOut());
                }
        }
    }
}

