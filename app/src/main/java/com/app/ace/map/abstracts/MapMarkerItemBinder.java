package com.app.ace.map.abstracts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.view.View;

import com.app.ace.R;
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
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Target;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class MapMarkerItemBinder extends MapMarkerBinder<MapScreenItem> {

    private MainActivity activity;
    DockActivity dockActivity;
    Bitmap bitmap;
    ImageLoader imageLoader;
    Bitmap myBitmap;
    Target loadtarget;

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
        imageLoader.loadImage(entity.getImage(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                if (entity.getLat() != null && entity.getLng() != null) {
                    if (!entity.getLat().equals("null") && !entity.getLng().equals("null"))
                        if (entity.getLat().length() > 0 && entity.getLng().length() > 0) {

                            try {

                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                loadedImage.compress(Bitmap.CompressFormat.JPEG, 10, out);

                                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

                                Marker marker = googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(entity.getLat()), Double.valueOf(entity.getLng())))
                                        //.icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapHelper.getRoundCircleImage(BitmapHelper.getResizedBitmap(decoded,
                                                (int) activity.getResources().getDimension(R.dimen.x60)), activity))));
                                //  .icon(BitmapDescriptorFactory.fromBitmap(loadedImage)));
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.profile_container1)));
                                //.fromPath(tempUri.toString())));
                                marker.setTag(entity.getUserId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                }
                //   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(entity.getUserLat(), entity.getUserLng()), AppConstants.zoomIn));

            }

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);


                if (entity.getLat() != null && entity.getLng() != null) {
                    if (!entity.getLat().equals("null") && !entity.getLng().equals("null"))
                        if (entity.getLat().length() > 0 && entity.getLng().length() > 0) {

                            try {

                                Marker marker = googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(entity.getLat()), Double.valueOf(entity.getLng())))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.profile_picc)));
                                marker.setTag(entity.getUserId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                }

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }
        });


        //  image=BitmapHelper.scaleCenterCrop(bitmap,150,150);


        // bitmap = Bitmap.createScaledBitmap(bitmap,150, 150, true);
        // BitmapHelper.getRoundedCornerImage(bitmap)
        // RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, bitmap);
        //  dr.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);


    }
}

