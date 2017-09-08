package com.app.ace.map.abstracts;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.view.View;

import com.app.ace.activities.DockActivity;
import com.app.ace.activities.MainActivity;
import com.app.ace.entities.MapScreenItem;
import com.app.ace.helpers.BitmapHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayOutputStream;

import static com.app.ace.R.drawable.marker;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class MapMarkerItemBinder extends MapMarkerBinder<MapScreenItem> {

    private MainActivity activity;
    DockActivity dockActivity;
    Bitmap bitmap;
    ImageLoader imageLoader;

    public MapMarkerItemBinder(MainActivity activity, DockActivity dockActivity) {
        this.activity = activity;
        this.dockActivity = dockActivity;
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


    @Override
    public void addMarker(final GoogleMap googleMap, final MapScreenItem entity, final int position) {

        imageLoader = ImageLoader.getInstance();
        //bitmap = imageLoader.loadImageSync(entity.getImage());
        imageLoader.loadImage(entity.getImage(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (entity.getLat() != null && entity.getLng() != null) {
                    if (!entity.getLat().equals("null") && !entity.getLng().equals("null"))
                        if (entity.getLat().length() > 0 && entity.getLng().length() > 0) {

                            try {
                                Marker marker= googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(entity.getLat()), Double.valueOf(entity.getLng())))
                                        //.icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapHelper.getRoundCircleImage(loadedImage))));
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.profile_container1)));
                                //.fromPath(tempUri.toString())));
                                marker.setTag(entity.getUserId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            // googleMap.moveCamera();

                        }
                }
            }
        });


        //  image=BitmapHelper.scaleCenterCrop(bitmap,150,150);


        // bitmap = Bitmap.createScaledBitmap(bitmap,150, 150, true);
        // BitmapHelper.getRoundedCornerImage(bitmap)
        // RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, bitmap);
        //  dr.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);


    }
}

