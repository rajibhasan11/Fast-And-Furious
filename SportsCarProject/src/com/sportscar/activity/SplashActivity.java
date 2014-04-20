package com.sportscar.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.sportscar.app.R;
import com.sportscar.utils.SessionProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.TextView;

public class SplashActivity extends FragmentActivity {

	private Context mContext;
	private Timer mTimerStart;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mContext = this;
		TextView tvSplash = new TextView(mContext);
		tvSplash.setText("Sports Cars");
		tvSplash.setTextSize(28);
		
		// tvSplash.setTextColor(Color.WHITE);
		// tvSplash.setBackgroundColor(Color.parseColor("#666666"));
		
		tvSplash.setTextColor(Color.parseColor("#666666"));
		tvSplash.setBackgroundResource(R.drawable.splash_gradient);
		
		tvSplash.setGravity(Gravity.CENTER);
		setContentView(tvSplash);
		
		mTimerStart = new Timer();
		mTimerStart.schedule(new TimerTask() {
			@Override
			public void run() {
				HashMap<String, String> loginMap = SessionProvider.sessionManager(mContext);
				if (loginMap.size() > 1) {
					Intent in = new Intent(mContext, SportsCarListActivity.class);
					startActivity(in);
					finish();
				} else {
					Intent in = new Intent(mContext, LoginViewActivity.class);
					startActivity(in);
					finish();
				}
			}
		}, 1500);
	}
	
	

}
