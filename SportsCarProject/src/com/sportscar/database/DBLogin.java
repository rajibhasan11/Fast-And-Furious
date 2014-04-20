/********************************************************
 * DBLogin is the credential manager for login
 * 
 * @author: Mohammad Hasan Khan email: rajibhasan11@gmail.com
 * Company: MP Mobile Srl
 * 
 * ********************************************/
package com.sportscar.database;

import java.util.HashMap;
import java.util.Map;

import com.sportscar.utils.AndroidUtils;
import com.sportscar.utils.CursorUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLogin implements IDBLogin {
    
	private static DbHelper ourHelper;
	
	private final Context ourContext;
	
	private SQLiteDatabase ourDatabase;
	
	private int DATABASE_VERSION = 1;
		
	private static class DbHelper extends SQLiteOpenHelper {
		
		public static DbHelper getInstance(Context ctx, int dv) {
			if (ourHelper == null) {
				ourHelper = new DbHelper(ctx.getApplicationContext(), dv);
			}
			return ourHelper;
		}

		private DbHelper(Context context, int DATABASE_VERSION) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);			
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {	
			final String CREATE_CREDENTIAL_TABLE = "CREATE TABLE " + TABLE_CREDENTIALS
					+ ROW_ID
					// + UNIQUE_ID
				    + KEY_USERNAME + DVARCHAR
				    + KEY_PASSWORD + DVARCHAR
				    + KEY_USER_FIRST_NAME + DVARCHAR
				    + KEY_USER_SURNAME + DVARCHAR
				    + KEY_USER_NICK_NAME + DVARCHAR
				    + KEY_EMAIL + DVARCHAR
				    + KEY_PHONE + DVARCHAR
				    + KEY_GENDER + DVARCHAR
				    + KEY_TOKEN + DVARCHAR_END;
			db.execSQL(CREATE_CREDENTIAL_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
			onCreate(db);
		}
		
	}
	
	public DBLogin(Context context){
		ourContext = context;
		DATABASE_VERSION = AndroidUtils.getVersionCode(context);
	}
	
	public DBLogin openToWrite() {
		ourHelper = DbHelper.getInstance(ourContext, DATABASE_VERSION);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public DBLogin openToRead() {
		ourHelper = DbHelper.getInstance(ourContext, DATABASE_VERSION);
		ourDatabase = ourHelper.getReadableDatabase();
		return this;
	}
		
	public synchronized void closeDB() {
		if(ourHelper != null) {
			ourHelper.close();
			ourHelper = null;
		}
	}

	public long createEntry(HashMap<String, String> map) {
		ContentValues cv = new ContentValues();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			cv.put(entry.getKey(), entry.getValue());
		}
		return ourDatabase.insert(TABLE_CREDENTIALS, null, cv);
	}

	public int rowCounterMethod(){
		ourHelper = DbHelper.getInstance(ourContext, DATABASE_VERSION);
		ourDatabase = ourHelper.getWritableDatabase();		
		String numRows = "SELECT COUNT(*) FROM " + TABLE_CREDENTIALS;
	    Cursor c = ourDatabase.rawQuery(numRows, null);
	    c.moveToFirst();
	    int icount = c.getInt(0);
	    // c.close();
	    CursorUtils.manageCursor(c);
		ourDatabase.close();
	    return icount;
	}

	public String[] getCredentials() {
		String[] columns = new String[]{ KEY_USERNAME, KEY_PASSWORD, KEY_USER_FIRST_NAME, KEY_USER_SURNAME, KEY_TOKEN, KEY_GENDER, KEY_PHONE };
		Cursor c = ourDatabase.query(TABLE_CREDENTIALS, columns, null, null, null, null, null);
		if(c != null && c.moveToLast()) {
			String[] array = { c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6) };
			CursorUtils.manageCursor(c);
			return array;
		}
		CursorUtils.manageCursor(c);
		return null;
	}
	
	public String[] getFirstCredentials() {
		String[] columns = new String[]{ KEY_USERNAME, KEY_PASSWORD, KEY_USER_FIRST_NAME, KEY_USER_SURNAME, KEY_TOKEN, KEY_GENDER, KEY_PHONE };
		Cursor c = ourDatabase.query(TABLE_CREDENTIALS, columns, null, null, null, null, null);
		if(c != null && c.moveToFirst()) {
			String username = c.getString(0);
			String password = c.getString(1);
			String firstname = c.getString(2);
			String surname = c.getString(3);
			String token = c.getString(4);
			String gender = c.getString(5);
			String phone = c.getString(6);
			String[] arr = { username, password, firstname, surname, token, gender, phone };
			CursorUtils.manageCursor(c);
			return arr;
		}		
		CursorUtils.manageCursor(c);
		return null;														
	}

	public DBLogin deleteAllRows() throws RuntimeException {
		ourHelper = DbHelper.getInstance(ourContext, DATABASE_VERSION);
		ourDatabase = ourHelper.getWritableDatabase();		
		String numRows = "SELECT COUNT(*) FROM " + TABLE_CREDENTIALS;
	    Cursor c = ourDatabase.rawQuery(numRows, null);
	    c.moveToFirst();
	    int icount = c.getInt(0);
	    if(icount > 0){
			String delAllRows = "DELETE FROM " + TABLE_CREDENTIALS;		
			ourDatabase.rawQuery(delAllRows, null);
			ourDatabase.delete(TABLE_CREDENTIALS, null, null);
		}
		CursorUtils.manageCursor(c);
		ourDatabase.close();
		return this;
		
	}

}

