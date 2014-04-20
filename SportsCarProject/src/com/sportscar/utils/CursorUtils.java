package com.sportscar.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import android.database.Cursor;
import android.text.TextUtils;

public final class CursorUtils {
	
	public static boolean manageCursor(Cursor cursor) {
		if(cursor != null){
			if(!cursor.isClosed()) {
				cursor.close();
				return true;
			}
		}
		return false;
	}
	
	public static Cursor splitCursor(Cursor cursor) {
		return cursor;
	}
	
	public static Cursor mergeCursors(Cursor cursor) {
		return cursor;
	}
	
	public static Cursor muteCursors(Cursor cursor) {
		return cursor;
	}
	
	public static CopyOnWriteArrayList<ConcurrentHashMap<String, String>> converter(Cursor cursor) {
		CopyOnWriteArrayList<ConcurrentHashMap<String, String>> mDataSet = new CopyOnWriteArrayList<ConcurrentHashMap<String, String>>();
		try {
			if(cursor != null) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<String, String>();
					for(int i = 0; i < cursor.getColumnCount(); i++){
						if(i == 0) {
							continue;
						} else {
							if(!TextUtils.isEmpty(cursor.getColumnName(i))) {
								if(cursor.getString(i) == null)
									dataMap.put(cursor.getColumnName(i), "");
								else
									dataMap.put(cursor.getColumnName(i), cursor.getString(i));
							}
						}
					}
					mDataSet.add(dataMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDataSet;
	}

}
