package com.sportscar.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RejectedExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportscar.app.AsyncTaskHelper;
import com.sportscar.app.ISportsCar;
import com.sportscar.app.R;
import com.sportscar.database.DBLogin;
import com.sportscar.database.SQLiteHelper;
import com.sportscar.utils.CursorUtils;
import com.sportscar.utils.NetConnectionUtil;
import com.sportscar.utils.SDK11;
import com.sportscar.utils.SessionProvider;

public class SportsCarDetailActivity extends FragmentActivity implements ISportsCar {

	private SQLiteHelper mDatabaseHelper;
	private Activity activity;
	private Context mContext;
	private TextView manufacturerModel, model, manufacturer, style, origin, hiUser;
	private SportsCarLoadTask mSportsCarLoadTask;
	
	private ImageView ivLogOut;
	private DBLogin dbLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		mContext = this;
        setContentView(R.layout.sports_car_detailview_layout);
        
        dbLogin = new DBLogin(mContext);
        mDatabaseHelper = SQLiteHelper.getInstance(mContext);
        
        // Set greetings
        hiUser = (TextView) findViewById(R.id.hiUser);
        HashMap<String, String> loginMap = SessionProvider.sessionManager(mContext);
		if (loginMap.size() > 1) {
			String userFirstName = loginMap.get(DBLogin.KEY_USER_FIRST_NAME);
			if(!TextUtils.isEmpty(userFirstName)) {
				hiUser.setText(getString(R.string.hi) + " " + capitalizeFirstLetter(userFirstName));
				hiUser.setVisibility(View.VISIBLE);
			}
		}
		
		// Set logout functionality
        ivLogOut = (ImageView) findViewById(R.id.ivLogOut);
        ivLogOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(view != null) {
					removeSession();
				}
			}
		});
        
        if(findViewCorrectly()) {
        	Intent in = getIntent();
        	if(in != null) {
        		String id = in.getStringExtra(ID);
        		Log(id+" onDetail");
        		if(!TextUtils.isEmpty(id)) {
        			if(NetConnectionUtil.haveNetConnection(mContext)) {
        				mSportsCarLoadTask = new SportsCarLoadTask(mContext, true, id);
        				try {
        					String url = mSportsCarLoadTask.setupRequest() + id;
        					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        						SDK11.executeOnThreadPool(mSportsCarLoadTask, url);
        					} else {
        						mSportsCarLoadTask.execute(url);
        					}
        				} catch (RejectedExecutionException e) {
        				}
        			} else {
        				offlineSetData(id);
        			}
        		}
        	}
        }
	}
	
	private void offlineSetData(String id) {
		Cursor cursor = mDatabaseHelper.getDetailById(SQLiteHelper.TABLE_SPORTS_CAR, id);
		if(cursor != null && cursor.moveToFirst()) {
			String manufacturerStr = cursor.getString(cursor.getColumnIndex(MANUFACTURER));
        	String modelStr = cursor.getString(cursor.getColumnIndex(MODEL));
        	String styleStr = cursor.getString(cursor.getColumnIndex(STYLE));
        	String originStr = cursor.getString(cursor.getColumnIndex(ORIGIN));
        	CursorUtils.manageCursor(cursor);
        	
        	manufacturerModel.setText(manufacturerStr + " (" + modelStr + ")");
        	model.setText(modelStr);
        	manufacturer.setText(manufacturerStr);
        	style.setText(styleStr);
        	origin.setText(originStr);
		}
	}
	
	private void removeSession() {
		dbLogin.deleteAllRows();
		Intent in = new Intent(mContext, LoginViewActivity.class);
		startActivity(in);
		finish();
	}
	
	private String capitalizeFirstLetter(String word){
		if(word != null){
			word = word.trim();
			if(word.length() > 0){
				if(word.length() == 1){
					return word.toUpperCase();
				}
				if(word.length() > 1){
					return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
				}
			}
			if(word.length() == 0){
				return word;
			}
		}
		return "";
	}
	
	private class SportsCarLoadTask extends AsyncTaskHelper {

		private String id;
		public SportsCarLoadTask(Context context, boolean genericLoad, String id) {
			super(context, genericLoad);
			this.id = id;
		}

		@Override
		public void onPrePostExecute(CopyOnWriteArrayList<ConcurrentHashMap<String, String>> result) {
			if(result != null && result.size() > 0){
				ConcurrentHashMap<String, String> map = result.get(0);
				if(map != null && map.size() > 0){
					String manufacturerStr = map.get(MANUFACTURER);
		        	String modelStr = map.get(MODEL);
		        	String styleStr = map.get(STYLE);
		        	String originStr = map.get(ORIGIN);
		        	
		        	manufacturerModel.setText(manufacturerStr + " (" + modelStr + ")");
		        	model.setText(modelStr);
		        	manufacturer.setText(manufacturerStr);
		        	style.setText(styleStr);
		        	origin.setText(originStr);
				}
			} else {
				dismissProgressDialog(mProgressDialog);
				offlineSetData(id);
			}
		}
		
		@Override
		protected void onPostExecute(Void unusedresult) {
			super.onPostExecute(unusedresult);
		}

		@Override
		protected Void doInBackground(final String... urls) {
			Log("" + "URL CALLED: " + urls[0]);
			// ONLINE CASE
			if (NetConnectionUtil.haveNetConnection(mContext)) {
				final CopyOnWriteArrayList<ConcurrentHashMap<String, String>> mDataSet = parseJSON(queryRESTurl(
						getActivity(), urls[0], null, null));
				runOnUiThread(new Runnable() {
					public void run() {
						onPrePostExecute(mDataSet);
					}
				});
				mDatabaseHelper.createOrUpdate2(mDataSet, SQLiteHelper.TABLE_SPORTS_CAR);
			} else {
				runOnUiThread(new Runnable() {
					public void run() {
						dismissProgressDialog(mProgressDialog);
						offlineSetData(id);
					}
				});
			}
			return (null);
		}

		@Override
		public String setupRequest() {
			return getBaseUrl() + "sportscar/sample/id/";
		}
    }
	
	private Activity getActivity() {
    	return activity;
    }
	
	void Log(String stringParam) {
    	android.util.Log.e("", stringParam);
    }
	
	private boolean findViewCorrectly() {
		try {
			manufacturerModel = (TextView) findViewById(R.id.manufacturerModel);
			model = (TextView) findViewById(R.id.model);
			manufacturer = (TextView) findViewById(R.id.manufacturer);
			style = (TextView) findViewById(R.id.style);
			origin = (TextView) findViewById(R.id.origin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	private CopyOnWriteArrayList<ConcurrentHashMap<String, String>> parseJSON(final String json) {
		CopyOnWriteArrayList<ConcurrentHashMap<String, String>> mDataSetJSON = new CopyOnWriteArrayList<ConcurrentHashMap<String, String>>();
		ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<String, String>();
		try {
			JSONObject jObject = new JSONObject(json);
			Iterator innerKeys = jObject.keys();
			while(innerKeys.hasNext()) {
				String currentInnerKey = (String)innerKeys.next();
				dataMap.put(currentInnerKey, jObject.optString(currentInnerKey));
			}
			mDataSetJSON.add(dataMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mDataSetJSON;
    }

}
