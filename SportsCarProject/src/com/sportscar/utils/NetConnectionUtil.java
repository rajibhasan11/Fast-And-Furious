package com.sportscar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetConnectionUtil {
	
	public static boolean haveNetConnection(Context context){
		boolean haveConnectionWifi = false;
		boolean haveConnectionMobile = false;
		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm != null) {
				NetworkInfo[] netInfo = cm.getAllNetworkInfo();
				if (netInfo.length > 0) {
					for (NetworkInfo ni : netInfo) {
						if (ni.getTypeName().equalsIgnoreCase(
								Context.WIFI_SERVICE)
								&& ni.isConnected())
							haveConnectionWifi = true;
						if (ni.getTypeName().equalsIgnoreCase("MOBILE")
								&& ni.isConnected())
							haveConnectionMobile = true;
					}
				}
			}
		}
		return haveConnectionMobile || haveConnectionWifi;
	 }
}
