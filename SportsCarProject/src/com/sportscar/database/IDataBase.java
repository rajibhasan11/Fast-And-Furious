package com.sportscar.database;

import com.sportscar.app.ISportsCar;

public interface IDataBase extends ISqlStrings, ISportsCar {

	String DATABASE_NAME = "db.db";
	
	String UNIQUEID = "uniqueid";
	String TABLE_SPORTS_CAR = "app_sports_car";

	String CREATE_TABLE = "CREATE TABLE ";
	String DROP_TABLE = "DROP TABLE IF EXISTS ";
	String ROW_ID = "(id INTEGER PRIMARY KEY NOT NULL,";
	
	/* 
	 * Unique id to create sql statements
	 * 
	 * */
	String UNIQUE_ID = UNIQUEID + " VARCHAR NOT NULL UNIQUE,";

	String DINTEGER = " INTEGER DEFAULT (0),";
	String DVARCHAR = " VARCHAR DEFAULT '',";
	String DDOUBLE = " DOUBLE DEFAULT (0.0),";
	String DTEXT = " TEXT DEFAULT '',";

	String VARCHAR = " VARCHAR,";
	String INTEGER = " INTEGER,";
	String DOUBLE = " DOUBLE,";
	
	String DVARCHAR_END = " VARCHAR DEFAULT '')";
	String DINTEGER_END = " INTEGER DEFAULT (0))";
	String VARCHAR_END = " VARCHAR)";
	String INTEGER_END = " INTEGER)";
	
	
	// TABLE SPORTS CLUB
	String CREATE_SPORTS_CAR_TABLE = CREATE_TABLE + TABLE_SPORTS_CAR + ROW_ID
			// + UNIQUE_ID
			+ MANUFACTURER + DVARCHAR
			+ MODEL + DVARCHAR
			+ STYLE + DVARCHAR
			+ ORIGIN + DVARCHAR_END;
}
