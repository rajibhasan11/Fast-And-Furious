package com.sportscar.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SDK11 {

    public static void addInBitmapOption(BitmapFactory.Options opts, Bitmap inBitmap) {
        opts.inBitmap = inBitmap;
    }
    public static <P> void executeOnThreadPool(AsyncTask<P, ?, ?> task, P... params) {
        try {
        	if(task != null && params.length > 0) {
        		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
