/********************************************************
 *
 * @author: Mohammad Hasan Khan email: rajibhasan11@gmail.com
 * @date: 
 * 
 * ********************************************/
package com.sportscar.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.sportscar.utils.AndroidUtils;
import com.sportscar.utils.CursorUtils;

public class SQLiteHelper extends SQLiteOpenHelper implements IDataBase {
	
	private Context mContext;
	
	private static SQLiteHelper mInstance;

	private SQLiteDatabase mSQLiteDatabase;
	
	public static int DATABASE_VERSION = 1;
	
	// Write
	public SQLiteDatabase openToWrite() {
		mSQLiteDatabase = getWritableDatabase();
		return mSQLiteDatabase;
	}
	
	// Read
	public SQLiteDatabase openToRead() {
		mSQLiteDatabase = getReadableDatabase();;
		return mSQLiteDatabase;
	}
	
	public Context getContext(){
		return mContext;
	}

    @Override 
    public SQLiteDatabase getWritableDatabase() {
		return super.getWritableDatabase();
	}

	@Override 
	public SQLiteDatabase getReadableDatabase() {
		return super.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SPORTS_CAR_TABLE);
	}
	
	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE + TABLE_SPORTS_CAR);
		onCreate(db);
	}

	// Singleton
	public static SQLiteHelper getInstance(Context ctx) {
		if (mInstance == null){
			mInstance = new SQLiteHelper(ctx.getApplicationContext(), AndroidUtils.getVersionCode(ctx.getApplicationContext()));
		}
		return mInstance;
	}

	private SQLiteHelper(Context context, int db_version) {
		super(context, DATABASE_NAME, null, db_version);
		DATABASE_VERSION = db_version;
		this.mContext = context;
		openToWrite();
	}
	
	@Override 
	public synchronized void close() {
		super.close();
	}
	
	/**********NEW METHODS***************************************************************************/
	// GET UNIQUE ID
	public String getUniqueID(String table, long id) {
		Cursor x = mSQLiteDatabase.rawQuery(SELECT_IDASID + UNIQUEID + FROM + table + WHERE + ID + EquationBuilder.SQLEqual(id), null);
		x.moveToFirst();
		String uId = "";
		try {
			uId = x.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CursorUtils.manageCursor(x);
		// x.close();
		return uId;
	}
	
	// Item exist or not
	public boolean isExisted(String table, String uniqueid){
		boolean itemExisted = false;
		if(rowCounterMethod(table, uniqueid, UNIQUEID) > 0){
			itemExisted = true;
		}
		return itemExisted;
	}
	
	public boolean isExisted2(String table, String id){
		boolean itemExisted = false;
		if(rowCounterMethod(table, id, ID) > 0){
			itemExisted = true;
		}
		return itemExisted;
	}
	
	// ROW COUMTER WITH ROWID / UNIQUEID
	public int rowCounterMethod(String table, String uniqueid, String id_type) {
		
		String count = "";
		
		if(id_type.equalsIgnoreCase(UNIQUEID) || TextUtils.isEmpty(id_type)) {
			count = SQLCOUNT + FROM + table + WHERE + UNIQUEID + SET_VALUE;
		}
		else if(id_type.equalsIgnoreCase(ID)) {
			count = SQLCOUNT + FROM + table + WHERE + ID + SET_VALUE;
		}
		
		Cursor c;
		if(uniqueid == null || uniqueid.trim().length() == 0) {
			count = SQLCOUNT + FROM + table;
			c = mSQLiteDatabase.rawQuery(count, null);
		} else {
			c = mSQLiteDatabase.rawQuery(count, new String[] { uniqueid });
		}
		
	    c.moveToFirst();
	    int icount = c.getInt(0);
	    CursorUtils.manageCursor(c);
	    return icount;
	}
	
	public void deleteAllFromTable(String table) {
		mSQLiteDatabase.delete(table, null, null);
	}

	// SAVE OR UPDATE ROW/S
	public void createOrUpdate(CopyOnWriteArrayList<ConcurrentHashMap<String, String>> dataSet, String table) {
		if(dataSet != null) {
			if(dataSet.size() > 0) {
				try {
					mSQLiteDatabase.beginTransaction();
					// Map<String, String> noSuchColumnsMap = getNoSuchColumsMap(table, dataSet.get(0));
					for (ConcurrentHashMap<String, String> item : dataSet) {
						ContentValues cv = new ContentValues();
						Map<String, String> noSuchColumnsMap = getNoSuchColumsMap(table, item);
						// Log( "NO SUCH COLUMNS MAP SIZE: " + noSuchColumnsMap.size() + "  .  " + noSuchColumnsMap.keySet());
						if(noSuchColumnsMap.size() == 0) {
							for (Map.Entry<String, String> entry : item.entrySet()) {
								cv.put(entry.getKey(), entry.getValue());
							}
						} else {
							for (Map.Entry<String, String> entry : item.entrySet()) {
								if(!noSuchColumnsMap.containsKey(entry.getKey())) {
									cv.put(entry.getKey(), entry.getValue());
								}
							}
						}
						
						String uniqid = item.get(UNIQUEID);
						if (isExisted(table, item.get(UNIQUEID))) {
							mSQLiteDatabase.update(table, cv, UNIQUEID + SET_VALUE, new String[] { uniqid });
						} else {
							mSQLiteDatabase.insert(table, null, cv);
						}
					}
					mSQLiteDatabase.setTransactionSuccessful();
				} catch (SQLiteException e) {
					e.printStackTrace();
				} finally {
					mSQLiteDatabase.endTransaction();
				}
			}
		}
	}
	
	public void createOrUpdate2(CopyOnWriteArrayList<ConcurrentHashMap<String, String>> dataSet, String table) {
		if(dataSet != null) {
			if(dataSet.size() > 0) {
				try {
					mSQLiteDatabase.beginTransaction();
					// Map<String, String> noSuchColumnsMap = getNoSuchColumsMap(table, dataSet.get(0));
					for (ConcurrentHashMap<String, String> item : dataSet) {
						ContentValues cv = new ContentValues();
						Map<String, String> noSuchColumnsMap = getNoSuchColumsMap(table, item);
						// Log( "NO SUCH COLUMNS MAP SIZE: " + noSuchColumnsMap.size() + "  .  " + noSuchColumnsMap.keySet());
						if(noSuchColumnsMap.size() == 0) {
							for (Map.Entry<String, String> entry : item.entrySet()) {
								cv.put(entry.getKey(), entry.getValue());
							}
						} else {
							for (Map.Entry<String, String> entry : item.entrySet()) {
								if(!noSuchColumnsMap.containsKey(entry.getKey())) {
									cv.put(entry.getKey(), entry.getValue());
								}
							}
						}
						
						String id = item.get(ID);
						if (isExisted2(table, id)) {
							mSQLiteDatabase.update(table, cv, ID + SET_VALUE, new String[] { id });
						} else {
							mSQLiteDatabase.insert(table, null, cv);
						}
					}
					mSQLiteDatabase.setTransactionSuccessful();
				} catch (SQLiteException e) {
					e.printStackTrace();
				} finally {
					mSQLiteDatabase.endTransaction();
				}
			}
		}
	}
	
	// DELETE LOCATION/S ROW/S
	public void deleteData(CopyOnWriteArrayList<String> dataSet, String table) {
		for (String uniqueid : dataSet) {
			try {
				// Log( " DELETING ROW..." + table.toLowerCase());
				mSQLiteDatabase.delete(table, UNIQUEID + SET_VALUE, new String[] {uniqueid});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// METHOD TO CHECK FOR FIRST INSERT CONTAIN KEY/S
	private Map<String, String> getNoSuchColumsMap(String table, Map<String, String> inputMap) {
		Map<String, String> noSuchColums = new HashMap<String, String>();
		if(!TextUtils.isEmpty(table) && inputMap != null) {
			Map<String, String> tableMap = getTableColumnSet(table);
			if((tableMap != null && tableMap.size() > 0) && (inputMap != null && inputMap.size() > 0)){
				for (Map.Entry<String, String> entry : inputMap.entrySet()) {
					if(!tableMap.containsKey(entry.getKey())) {
						noSuchColums.put(entry.getKey(), entry.getKey());
					}
				}
			}
		}
		return noSuchColums;
	}
	
	// GET KEY SET OF COLUMN NAMES OF A SQLite TABLE
	private Map<String, String> getTableColumnSet(String table) {
		if(!TextUtils.isEmpty(table)) {
			Cursor dbCursor = null;
			try {
				dbCursor = mSQLiteDatabase.query(table, null, null, null, null, null, null);
				if(dbCursor != null) {
					String[] columnNames = dbCursor.getColumnNames();
					HashMap<String, String> map = new HashMap<String, String>();
					for (String columnName : columnNames) {
						map.put(columnName, columnName);
					}
					dbCursor.close();
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Cursor getDetailByUniqueId(String table, String uniqueid) {
		return mSQLiteDatabase.rawQuery(SELECT_ALL_FROM + table + WHERE + UNIQUEID + EquationBuilder.SQLEqual(uniqueid), null);
	}
	
	public Cursor getDetailById(String table, String id) {
		return mSQLiteDatabase.rawQuery(SELECT_ALL_FROM + table + WHERE + ID + EquationBuilder.SQLEqual(id), null);
	}
	
	/***************SPORTS CAR*****************************/
	public Cursor getAllCars() {
		return mSQLiteDatabase.rawQuery(SELECT_ALL_FROM + TABLE_SPORTS_CAR, null);
	}
	/***************SPORTS CAR*****************************/
   
	/************************************************************************************
	 *                             END NEW METHODS
	 * 
	 * **********************************************************************************/

}
