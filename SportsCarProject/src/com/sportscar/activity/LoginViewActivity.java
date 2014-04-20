/********************************************************
 * LoginViewActivity is the Activity for Log In into application
 * 
 * @author: Mohammad Hasan Khan email: rajibhasan11@gmail.com
 * Company: MP Mobile Srl
 * 
 * ********************************************/
package com.sportscar.activity;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RejectedExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mkh.customdialogs.CustomDialog;
import com.mkh.customdialogs.CustomProgressDialog;
import com.sportscar.app.IAsyncTask;
import com.sportscar.app.R;
import com.sportscar.database.DBLogin;
import com.sportscar.utils.AppPreference;
import com.sportscar.utils.Md5Encoder;
import com.sportscar.utils.NetConnectionUtil;
import com.sportscar.utils.SDK11;
import com.sportscar.utils.SessionProvider;
import com.sportscar.widget.ClearableEditText;


@SuppressWarnings({ "unused" })
public class LoginViewActivity extends FragmentActivity implements OnClickListener {

	private ClearableEditText user, pwd;
	private Button loginBtn ;
	private View mView;
	private DBLogin dblogin;

	private String utente = "";
	private String psd = "";
	private String token = "";
	private String uniqid = "";
	private String nickname = "";
	private String firstName = "";
	private String surname = "";
	private String emailAddress = "";
	private String gender = "";

	private CustomProgressDialog mProgressDialog;
	private LoginAuthTask authTask;
	private Activity activity;
	private Context mContext;
	private AppPreference mAppPreference;
	
	public static final String URL_LOGIN  = IAsyncTask.BASEURL + "user";
	public static final String CODE  = "code";
	public static final String MESSAGE  = "message";
	public static final String DONT_GO = "nonGo";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		activity = this;
		mContext = this;
		mAppPreference = AppPreference.getInstance(mContext);
		onCreateContentView();
	}

	private void startListActivity(){
		Intent in = new Intent(mContext, SportsCarListActivity.class);
		startActivity(in);
		finish();
	}
	
	private void handleOnClickLogin() {
		loginBtn = (Button) findViewById(R.id.btnLogin);
		loginBtn.setOnClickListener(this);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		HashMap<String, String> loginMap = SessionProvider.sessionManager(getLoginActivity());
		if (loginMap.size() > 1) {
			startListActivity();
		}
		onCreateContentView();
	}

	private void onCreateContentView() {
		try {
			mView = findViewById(R.id.scroll_view_login);
			dblogin = new DBLogin(mContext);

			/* LOG IN BTN SETTINGS */
			handleOnClickLogin();
			/* END LOG IN BTN SETTINGS */

			// For test
			user = (ClearableEditText) mView.findViewById(R.id.etAccount);
			pwd = (ClearableEditText) mView.findViewById(R.id.etPwd);

			// user.setText("aurelio.muccio@gmail.com");
			// pwd.setText("sintetica");
			// user.setText("raj@gmail.com");
			// pwd.setText("1234");

			String user_email = mAppPreference.getString(AppPreference.USER_EMAIL);
			if (!TextUtils.isEmpty(user_email)) {
				user.setText(user_email);
				// pwd.requestFocus();
			} else {
				// user.requestFocus();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	void toast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		if (mAppPreference != null && user != null) {
			mAppPreference.putString(AppPreference.USER_EMAIL, user.getText().toString());
		}
		super.onStop();
	}
	
	 private static boolean isEmailValid(String email) {
	        boolean isValid = false;

	        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        CharSequence inputStr = email;

	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(inputStr);
	        if (matcher.matches()) {
	            isValid = true;
	        }
	        return isValid;
	    }

	@Override
	public void onClick(View v) {
		if (v != null) {
			HashMap<String, String> data = new HashMap<String, String>();
			// data.put("firstName", "rajib");
			// data.put("lastName", "hasan");
			data.put("email", user.getText().toString());
			data.put("password", Md5Encoder.encode(pwd.getText().toString()));

			try {
				String utente = user.getText().toString().toLowerCase().trim();
				if (user.checkEmpty()) {
					user.setError(R.string.lg_input_email_empty);
				} else if (!isEmailValid(utente)) {
					user.setError(R.string.lg_input_email_invalid);
				} else if (pwd.checkEmpty()) {
					pwd.setError(R.string.lg_input_pwd_empty);
				} else {
					if (NetConnectionUtil.haveNetConnection(mContext)) {
						if (authTask != null) {
							authTask.cancel(true);
						}
						authTask = new LoginAuthTask(data);
						try {
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
								SDK11.executeOnThreadPool(authTask, URL_LOGIN);
							} else {
								authTask.execute(URL_LOGIN);
							}
						} catch (RejectedExecutionException e) {
						}
					} else {
						if (mProgressDialog != null && mProgressDialog.isShowing()) {
							mProgressDialog.dismiss();
							mProgressDialog = null;
						}
						showAlertBoxForWIFI();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void showAlertBoxForWIFI() {
		if(mContext != null) {
			CustomDialog.Builder alertbox = new CustomDialog.Builder(mContext);
	        alertbox.setTitle(R.string.internet_dialog_title);
	        alertbox.setMessage(R.string.internet_dialog_message);

	        alertbox.setNegativeButton(R.string.gps_dialog_negative_btn, 
	        		new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	            }
	        });
	        
	        alertbox.setPositiveButton(R.string.gps_dialog_positive_btn,
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	                    }
	                });
	        alertbox.show();
		}
	}

	private class LoginAuthTask extends AsyncTask<String, String, String> {

		private HashMap<String, String> mData = null;

		public LoginAuthTask(HashMap<String, String> data) {
			this.mData = data;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new CustomProgressDialog(mContext, R.style.Theme_Transparent);
			mProgressDialog.setTitle(R.string.app_name);
			mProgressDialog.setMessage(R.string.login_progress_text);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setProgress(0);
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			String str = "";
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : mData.entrySet()) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().trim()));
				}
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				try {
					HttpResponse response = httpclient.execute(httppost);
					str = EntityUtils.toString(response.getEntity());
					Log.i("", "RESPONSE: " + str);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected void onPostExecute(String result) {
			boolean authError = false;
			if (!TextUtils.isEmpty(result)) {
				try {
					JSONObject jObject = new JSONObject(result);
					Iterator keys = jObject.keys();
					while (keys.hasNext()) {
						String currentKey = (String) keys.next();
						if (currentKey.equals(CODE) || currentKey.equals(MESSAGE)) {
							authError = true;
						}
						if (authError) {
							break;
						}
					}
					if (!authError) {
						firstName = jObject.optString("firstName");
						surname = jObject.optString("lastName");
						emailAddress = jObject.optString("email");
						utente = user.getText().toString().trim();
						psd = pwd.getText().toString();

						HashMap<String, String> sessionMap = new HashMap<String, String>();
						sessionMap.put(DBLogin.KEY_USER_FIRST_NAME, firstName);
						sessionMap.put(DBLogin.KEY_USER_SURNAME, surname);
						sessionMap.put(DBLogin.KEY_EMAIL, emailAddress);
						sessionMap.put(DBLogin.KEY_USERNAME, utente);
						sessionMap.put(DBLogin.KEY_PASSWORD, psd);

						if (dblogin == null)
							dblogin = new DBLogin(mContext);
						if (dblogin.rowCounterMethod() == 0) {
							dblogin.deleteAllRows();
							dblogin.openToWrite();
							dblogin.createEntry(sessionMap);
						}
						if (mProgressDialog != null && mProgressDialog.isShowing()) {
							mProgressDialog.dismiss();
							mProgressDialog = null;
						}
						startListActivity();
					} else {
						handleResult(DONT_GO);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					handleResult(DONT_GO);
				}
			} else {
				handleResult(DONT_GO);
			}
		}
	}

	private void accessDeniedAlert(int title, int message) {
		if (mContext != null) {
			CustomDialog.Builder alertbox = new CustomDialog.Builder(mContext);
			alertbox.setTitle(title);
			alertbox.setMessage(message);
			alertbox.setIcon(R.drawable.icon_alert);

			alertbox.setNegativeButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.cancel();
						}
					});
			alertbox.show();
		}
	}

	private void userExistedAlert(String title, String message) {
		if (mContext != null) {
			CustomDialog.Builder alertbox = new CustomDialog.Builder(mContext);
			alertbox.setTitle(title);
			alertbox.setMessage(message);
			alertbox.setIcon(R.drawable.icon_alert);

			alertbox.setNegativeButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			alertbox.show();
		}
	}

	private void userExistedAlert(int title, int message) {
		userExistedAlert(getString(title), getString(message));
	}

	private void unKnownError(int line) {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
		if (mContext != null) {
			CustomDialog.Builder alertbox = new CustomDialog.Builder(mContext);
			alertbox.setTitle(R.string.unknown_error_title);
			alertbox.setMessage(R.string.unknown_error_message);
			alertbox.setIcon(R.drawable.icon_alert);

			alertbox.setNegativeButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			alertbox.show();
		}
	}

	// Handle result calling inside onPostExecute
	private void handleResult(String result) {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
		if (result.equalsIgnoreCase(DONT_GO)) {
			accessDeniedAlert(R.string.login_failure_title, R.string.login_failure_text);
		}
	}

	private Activity getLoginActivity() {
		return activity;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onDestroy() {
		if (authTask != null) {
			authTask.cancel(true);
		}
		super.onDestroy();
	}

}