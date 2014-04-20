package com.sportscar.database;

public interface IDBLogin {
	
	int DATABASE_VERSION = 1;
	
	String KEY_ID = "id";
	String KEY_UNIQUEID = "uniqid";
	String KEY_USERNAME = "username";
	String KEY_PASSWORD = "password";
	String KEY_USER_FIRST_NAME = "name";
	String KEY_USER_SURNAME = "surname";
	String KEY_USER_NICK_NAME = "nickname";
	String KEY_EMAIL = "email";
	String KEY_TOKEN = "token";
	String KEY_GENDER = "gender";
	String KEY_PHONE = "phone";
	
	String KEY_PROFILE = "profile";
	String KEY_MOBILE = "mobile";
	String KEY_BDATE = "bdate";
	String KEY_SEX = "sex";
	String KEY_CITY = "city";
	String KEY_CCODE = "ccode";
	String REG_WITH_FB = "registered_with_facebook";
	
	String DATABASE_NAME = "login";
	String TABLE_CREDENTIALS = "app_login";
	
	String DVARCHAR_END = " VARCHAR DEFAULT '')";
	String DVARCHAR = " VARCHAR DEFAULT '',";
	String VARCHAR = " VARCHAR,";
	
	String UNIQUE_ID = KEY_UNIQUEID + " VARCHAR NOT NULL UNIQUE,";
	String ROW_ID = "(id INTEGER PRIMARY KEY NOT NULL,";
}
