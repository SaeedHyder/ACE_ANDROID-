package com.app.ace.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.inject.Inject;

public class FileHelper {
	
	private Context ctx;
	
	@Inject
	public FileHelper( Context ctx ) {
		this.ctx = ctx;
		
	}
	
	/**
	 * This image file can be used for a temporary location to store image file
	 * on an external location which can be used for tasks such as displaying an
	 * internal use of app.
	 * 
	 * Also deletes existing image file.
	 * 
	 * Dont Use it for external purposes.
	 * 
	 * @return
	 */
	public File getTempImageFile() {
		File file = new File( getAvailableCache(), "tempImage.jpg" );
		if ( file.exists() )
			file.delete();
		return file;
		
	}
	
	public File getTempTxtFile(String fileName) {
		File file = new File( getAvailableCache(), fileName + ".txt" );
		if ( file.exists() )
			file.delete();
		return file;
		
	}
	
	public File getHttpCacheFile() {
		File file = new File( getAvailableCache(), "httpcache" );
		return file;
		
	}
	
	public File getAvailableCache() {
		if ( OSHelper.isExerternalStorageAvailable() ) {
			if ( ctx.getExternalCacheDir() != null )
				ctx.getExternalCacheDir().mkdirs();
			return ctx.getExternalCacheDir();
		} else {
			ctx.getCacheDir().mkdirs();
			return ctx.getCacheDir();
		}
	}
	
	public void writeToFile( File file  , String content) {
		
		try {
			
			FileOutputStream fos = new FileOutputStream( file );
			String string = content ;
			fos.write( string.getBytes() );
			fos.close();
			
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static Uri getImageUri(Context context, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	public static String getRealPathFromURI(Context context,Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}
	
}
