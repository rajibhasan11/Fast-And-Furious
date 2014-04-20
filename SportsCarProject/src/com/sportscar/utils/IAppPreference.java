package com.sportscar.utils;

public interface IAppPreference {
	
	// SHARED PREFERENCE VALUE KEYS
	String AUTH_SUCCESS = "auth_success";
	String DATA_RANGE = "offline_current_data_set_range";
	String DB_VERSION = "dbversion";
	String USER_EMAIL = "user_email";
	String USER_NAME = "user_name";
	String DEFAULT_LOCATION_ID = "default_location_id";
	String EVENT_VIEW_JSON = "offline_eventview_settings_json";
	String SKIP_LOGIN = "skip_login";
	String SKIP_FROM_DETAIL = "skipped_from_detail";
	String ONCLICK_POPUP = "on_click_popup";
	String IN4U_LOGIN_STATUS = "in4u_login_status";
	String PERM_NOTIFICATION = "askUserforPermissionNotification";
	String PICTURE_URL = "picture_url";
	String BG_LOADED = "bg_loaded";
	String MANIFEST_PERMISSIONS_OK = "manifest_permissions_ok";
	
	String APP_SETTINGS = "app_settings";
	String FULLNAME = "full_name";
	String FB_LOGIN = "fb_login";
	
	String PAY_SYSTEM = "paysystem";
	
	String DETAIL_VIEW_URL = "detail_view_url";
	
	String HOME_KEY_DOWN = "home_key_pressed";
	String CURRENT_PAGE = "current_page";
	
	String INFORU_USERNAME = "inforu_username";
	String INFORU_PASSWORD = "inforu_passwrod";
	
	String LOGINVIEW = "loginview";
	String GROUPVIEW = "groupproductsview";
	String PRODUCT_UNIQUEID = "product_uniqueid";
	String PRODUCT_PRICE = "product_price";
	String PRODUCT_TITLE = "product_title";
	
	public boolean putString(String valueKey, String value);
	
	public String getString(String valueKey);
	
	public boolean putInt(String valueKey, int value);
	
	public int getInt(String valueKey);
	
    public boolean putBoolean(String valueKey, boolean value);
	
	public boolean getBoolean(String valueKey);

}
