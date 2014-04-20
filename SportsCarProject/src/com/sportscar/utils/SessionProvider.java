package com.sportscar.utils;

import java.util.HashMap;

import com.sportscar.database.DBLogin;

import android.content.Context;

public class SessionProvider {
    
    public static HashMap<String, String> sessionManager(Context c) {
    	
    	HashMap<String, String> map = new HashMap<String, String>();
    	DBLogin check = new DBLogin(c);
    	if(check.rowCounterMethod() == 0) {
    		map.put("activity", "LoginViewActivity");
    		return map;
    	} else {
		    // Read credentials
	        DBLogin read = new DBLogin(c);
	        read.openToRead();
	        String[] credentials = read.getCredentials();
	        read.closeDB();
	        try {
				map.put(DBLogin.KEY_USERNAME, credentials[0]);
				map.put(DBLogin.KEY_PASSWORD, credentials[1]);
				map.put(DBLogin.KEY_USER_FIRST_NAME, credentials[2]);
				map.put(DBLogin.KEY_USER_SURNAME, credentials[3]);
				map.put(DBLogin.KEY_TOKEN, credentials[4]);
				map.put(DBLogin.KEY_GENDER, credentials[5]);
				map.put(DBLogin.KEY_PHONE, credentials[6]);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        return map;
    	}
    }

}
