/********************************************************
 * This is the Async task parent class to call API for 
 * list view
 * 
 *  Architecture Name: Load from JSON, Show and Save
 *  (FSLL: first show last load && FLLS)
 * @author: Mohammad Hasan Khan email: rajibhasan11@gmail.com
 * @date: 19/04/2014
 * 
 * ********************************************/
package com.sportscar.app;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.mkh.customdialogs.CustomProgressDialog;
import com.sportscar.utils.NetConnectionUtil;

public abstract class AsyncTaskHelper extends android.os.AsyncTask<String, Integer, Void> implements IAsyncTask {
	
	protected CustomProgressDialog mProgressDialog;
	private Context mContext;
	private boolean showProgressDialog = false;
	private int progressMsgId = R.string.std_loading_text;
	
	public AsyncTaskHelper(Context context, final boolean genericLoad) {
		this.mContext = context;
		this.showProgressDialog = genericLoad;
	}
	
	public AsyncTaskHelper(Context context, final CustomProgressDialog dialog) {
		this.mContext = context;
		this.showProgressDialog = false;
		this.mProgressDialog = dialog;
	}
	
	public AsyncTaskHelper(final Context context, final boolean genericLoad, final int progressMsgId) {
		this.mContext = context;
		this.showProgressDialog = genericLoad;
		this.progressMsgId = progressMsgId;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(showProgressDialog){
			mProgressDialog = showProgressDialog(mProgressDialog);
		}
	}

	@Override
	protected Void doInBackground(String... params) {
		return null;
	}
	
	@Override
	protected void onPostExecute(Void unusedresult) {
		super.onPostExecute(unusedresult);
		dismissProgressDialog(mProgressDialog);
	}
	
	@Override
	public String queryRESTurl(Activity activity, String url, View eView, View pBar) {
		String result = "";
		if(!TextUtils.isEmpty(url) && NetConnectionUtil.haveNetConnection(mContext)) {
			if(url.contains(BASEURL)) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
		        HttpGet httpget = new HttpGet(url);
		        try  {
		        	HttpResponse response = httpclient.execute(httpget);
		        	if(response != null){
		        		HttpEntity entity = response.getEntity();
			        	if (entity != null) {
			        		try {
								result = EntityUtils.toString(entity, HTTP.UTF_8);
								if(result != null) {
									Log.i("REST", "JSON SIZE " + result.length());
									Log.i("Rest", "Result of converstion: [" + result + "]");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
			        	}
		        	}
		        } catch (SocketTimeoutException e) {
		        	e.printStackTrace();
		        } catch (ClientProtocolException e) {
		        	e.printStackTrace();
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
			} else {
				// handleFailOver(activity, eView, pBar, IErrorView.NODATA);
			}
		} else {
			// handleFailOver(activity, eView, pBar, IErrorView.NODATA);
		}
        return result;
	}
	
	/*protected void handleFailOver(final Activity activity, final View eView, final View pBar, final String errorType) {
		if(activity != null && eView != null) {
			activity.runOnUiThread(new Runnable() {
				public void run() {
					if(eView != null) {
						if(eView instanceof ErrorView) {
							ErrorView errorView = (ErrorView) eView;
							if(errorType.equalsIgnoreCase(IErrorView.NODATA))
								errorView.setSymbolicImage(IErrorView.NODATA);
							if(errorType.equalsIgnoreCase(IErrorView.NONET))
								errorView.setSymbolicImage(IErrorView.NONET);
						}
						eView.setVisibility(ErrorView.VISIBLE);
					}
					if(pBar != null)
						pBar.setVisibility(ErrorView.GONE);
				}
			});
		}
	}*/
	
	@Override
	public CustomProgressDialog showProgressDialog(CustomProgressDialog p){
		if(p != null && p.isShowing()){
			dismissProgressDialog(p);
		}
		if (p == null) {
			p = new CustomProgressDialog(mContext, R.style.Theme_Transparent);
			p.setTitle(R.string.app_name);
			if(progressMsgId != 0) p.setMessage(progressMsgId);
			else p.setMessage(R.string.std_loading_text);
			p.setCancelable(false);
			p.setIndeterminate(true);
			p.setProgress(0);
			try {
				if(mContext != null)
					p.show();
			} catch (Exception e) {}
		}
		if(!p.isShowing()){
		}
		return p;
	}
	
	@Override
	public void dismissProgressDialog(CustomProgressDialog p) {
		if(p != null && p.isShowing()){ 
			try {
				p.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				p = null;
			}
			p = null;
		}
		mProgressDialog = p;
	}
	
	public String getBaseUrl(){
		return BASEURL;
	}
	
	/*public String convertStreamToString(Activity activity, final InputStream is, View eView, View pBar) {
        try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8), 8192);
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while (( line = reader.readLine() ) != null){
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				handleFailOver(activity, eView, pBar, NONET);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					handleFailOver(activity, eView, pBar, NONET);
					e.printStackTrace();
			    }
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			handleFailOver(activity, eView, pBar, NONET);
			e.printStackTrace();
		}
		return null;
	}*/

}
