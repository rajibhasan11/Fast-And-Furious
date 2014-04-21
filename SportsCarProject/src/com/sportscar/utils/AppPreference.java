package com.sportscar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class AppPreference implements IAppPreference {
	
	private static final String SHARED_PREFERENCE = "SHARED_PREFERENCES";
	private final Context mContext;
	private final SharedPreferences mSettings;
	private static AppPreference mAppPreference;
	
	private AppPreference(Context context) {
		this.mContext = context;
		this.mSettings = mContext.getSharedPreferences(SHARED_PREFERENCE, Activity.MODE_PRIVATE);
	}
	
	public static AppPreference getInstance(Context context){
		if(mAppPreference == null){
			mAppPreference = new AppPreference(context.getApplicationContext());
		}
		return mAppPreference;
	}

	public boolean putString(String valueKey, String value) {
		if(mContext == null || TextUtils.isEmpty(valueKey) || value == null){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mContext != null){
		    SharedPreferences.Editor editor = mSettings.edit();
		    editor.putString(valueKey, value);
			if(!editor.commit()){
				return false;
			}
		    return true;
		}
		return false;
	}
	
	public String getString(String valueKey) {
		if(mContext == null || TextUtils.isEmpty(valueKey)){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mContext != null) {
			return mSettings.getString(valueKey, "");
		}
		return "";
	}
	
	public boolean putInt(String valueKey, int value) {
		if(mContext == null || TextUtils.isEmpty(valueKey) || value < 0){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mContext != null){
		    SharedPreferences.Editor editor = mSettings.edit();
		    editor.putInt(valueKey, value);
			if(!editor.commit()){
				return false;
			}
		    return true;
		}
		return false;
	}
	
	public int getInt(String valueKey) {
		if(mContext == null || TextUtils.isEmpty(valueKey)){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mContext != null) {
			return mSettings.getInt(valueKey, 0);
		}
		return 0;
	}
	
	public boolean putBoolean(String valueKey, boolean value) {
		if(mContext == null || TextUtils.isEmpty(valueKey)){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mContext != null){
		    SharedPreferences.Editor editor = mSettings.edit();
		    editor.putBoolean(valueKey, value);
			if(!editor.commit()){
				return false;
			}
		    return true;
		}
		return false;
	}
	
	public boolean getBoolean(String valueKey) {
		if(mContext == null || TextUtils.isEmpty(valueKey)){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mContext != null) {
			return mSettings.getBoolean(valueKey, false);
		}
		return false;
	}

}
