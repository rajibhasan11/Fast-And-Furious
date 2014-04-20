package com.sportscar.utils;

import java.util.Currency;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

@SuppressWarnings("deprecation")
public final class AndroidUtils {
	
	public void setActivityBackgroundColor(Activity context, int color) {
	    View view = context.getWindow().getDecorView();
	    view.setBackgroundColor(color);
	}
	
	public static void setSessionLocale(Activity activity, String localeString) {
		if(activity != null) {
			if(TextUtils.isEmpty(localeString) || localeString.contains("en")) {
	    		String default_locale  = "en_US";
	    		Locale locale = new Locale(default_locale);
	    		Locale.setDefault(locale);
	    		Configuration config = new Configuration();
	    		config.locale = locale;
	    		activity.getBaseContext().getResources().updateConfiguration(config, null);
	    	} else {
	    		Locale locale = new Locale(localeString);
	    		Locale.setDefault(locale);
	    		Configuration config = new Configuration();
	    		config.locale = locale;
	    		activity.getBaseContext().getResources().updateConfiguration(config, null);
	    	}
		}
    }
	
	public static View drawRoundedView(View view, float cornerRadius, int fill, int stroke, int strokeWidth){
		try {
			if(view != null){
				GradientDrawable gd = new GradientDrawable();
				gd.setColor(fill);
				gd.setCornerRadius(cornerRadius);
				gd.setStroke(strokeWidth, stroke);
				view.setBackgroundDrawable(gd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	public static View drawRoundedView(View view, float cornerRadius, int fill, int strokeWidth){
        float[] outerRadii = new float[]{ cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius};
        ShapeDrawable rndrect = new ShapeDrawable(new RoundRectShape(outerRadii, null , null));
        Paint fillpaint = rndrect.getPaint();
        fillpaint.setColor(fill);
        view.setBackgroundDrawable(rndrect);
		return view;
	}
	
	public static View drawRoundedBackGround(View view, float cornerRadius, int fill, int stroke, int strokeWidth) {
        float[] outerRadii = new float[] { cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius};
        RectF insetRectangle = new RectF(strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        ShapeDrawable rndrect = new ShapeDrawable(new RoundRectShape(outerRadii, insetRectangle, outerRadii));
        rndrect.getPaint().setColor(fill);
        rndrect.setIntrinsicHeight(view.getHeight());
        rndrect.setIntrinsicWidth(view.getWidth());
        view.setBackgroundDrawable(rndrect);
		return view;
	}
	
	public static ShapeDrawable getRoundShapeDrawable(float cornerRadius, int fill, int stroke, int strokeWidth){
        float[] outerRadii = new float[]{ cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius};
        ShapeDrawable rndrect = new ShapeDrawable(new RoundRectShape(outerRadii, null , null));
        rndrect.getPaint().setColor(fill);
        return rndrect;
	}
	
	public static Button drawRoundedButton(Button button, int[] statecolors, float cornerRadius, int stroke, int strokeWidth) {
		StateListDrawable gd = (StateListDrawable) button.getBackground();
		if(gd != null) {
			GradientDrawable gdDefault = new GradientDrawable();
			GradientDrawable gdPressed = new GradientDrawable();
			
			gdDefault.setColor(statecolors[0]);
			gdDefault.setCornerRadius(cornerRadius);
			gdDefault.setStroke(strokeWidth, stroke);
			
			gdPressed.setColor(statecolors[1]);
			gdPressed.setCornerRadius(cornerRadius);
			gdPressed.setStroke(strokeWidth, stroke);
			
			StateListDrawable stateList = new StateListDrawable();
			stateList.addState(new int[] { android.R.attr.state_pressed }, gdPressed);
			stateList.addState(new int[] { }, gdDefault);
			button.setBackgroundDrawable(stateList);
			return button;
		}
		return button;
    }
	
	// Is tablet or not
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
 

	// Get the API level of mobile os
	public static int getAPILevel() {
		 return  android.os.Build.VERSION.SDK_INT;
	}
	
	// Getting the locale from mobile language setting
	public static String getCountryCode(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String ccode = telephonyManager.getNetworkCountryIso();
		if(TextUtils.isEmpty(ccode))
			ccode = telephonyManager.getSimCountryIso();
		if(TextUtils.isEmpty(ccode))
			ccode = getLocaleCountry(context);
        return ccode;
    }
	
	// TEST
	public static String getCurrencyCodeNormal(Context context, String currencyCode) {
		return Currency.getInstance(currencyCode).getSymbol(getLocale(context));
	}
	
	public static Locale getLocaleLC(Context context) {
		return new Locale(getLanguageCode(context), getCountryCode(context));
	}
	
	public static String getCurrencyCode(Context context) {
		Currency currency = Currency.getInstance(getLocaleLC(context));
		return currency.getCurrencyCode();
	}
	
	public static String getCurrencySymbol(Context context) {
		Locale locale = new Locale(getLanguageCode(context), getCountryCode(context));
		Currency currency = Currency.getInstance(locale);
		return currency.getSymbol();
	}
	
	public static String getMobileNumber(Context context) {
		TelephonyManager tMgr =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tMgr.getLine1Number();
	}
	
	// Getting the locale from mobile language setting
	public static String getLocaleCountry(Context context) {
        return context.getResources().getConfiguration().locale.getCountry().toLowerCase();
    }
	
	public static String getStringLocale(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }
	
	public static String getLanguageCode(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().toLowerCase();
    }
	
	public static String getCCode(Context context) {
        return context.getResources().getConfiguration().locale.getISO3Country().toLowerCase();
    }
	
	public static Locale getLocale(Context context) {
        return context.getResources().getConfiguration().locale;
    }
	
	public static boolean hideSoftKeyboard(Activity activity) {
	    try {
			InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	    return true;
	}
	
	public static String getDeviceID(Activity activity){
		TelephonyManager tManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		return tManager.getDeviceId();
	}
	
	public static void setupUI(View view, final Activity activity) {
	    if(!(view instanceof EditText)) {
	        view.setOnTouchListener(new OnTouchListener() {
	        	@Override
	            public boolean onTouch(View v, MotionEvent event) {
	                AndroidUtils.hideSoftKeyboard(activity);
	                return false;
	            }
	        });
	    }
	}
	
	// Change argument for dynamicity of fill color, corner radius, stroke width (instead only strokecolor send styleMap)
	public static void onDrawBoarder(final View curView, final int strokeColor, final String bgType){
		final GradientDrawable gd = new GradientDrawable();
		if(curView != null && bgType != null && strokeColor != 0) {
			if(bgType.equalsIgnoreCase("onFocus")) {
				curView.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View view, MotionEvent motionEvent) {
						gd.setColor(Color.WHITE);
						gd.setCornerRadius(4);
						gd.setStroke(2, strokeColor);
						curView.setBackgroundDrawable(gd);
				        return false;
				    }
				});
			} else {
				gd.setColor(Color.WHITE);
				gd.setCornerRadius(4);
				gd.setStroke(2, strokeColor);
				curView.setBackgroundDrawable(gd);
			}
		} else {
			gd.setColor(Color.WHITE);
			gd.setCornerRadius(4);
			gd.setStroke(2, strokeColor);
			curView.setBackgroundDrawable(gd);
		}
	}
	
	public static float getDensity(Activity activity){
		return activity.getResources().getDisplayMetrics().density;
	}
	
	public static float getDensity(Context context){
		return context.getResources().getDisplayMetrics().density;
	}
	
	public static String getPackageName(Context context){
		return context.getApplicationContext().getPackageName();
	}
	
	public static int getVersionCode(Context ctx) {
	    int versionCode = 1;
		PackageInfo pinfo;
		try {
			pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			versionCode = pinfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

}
