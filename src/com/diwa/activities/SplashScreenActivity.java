package com.diwa.activities;

import com.diwa.db.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (isTheFirstLogin()) {
					startActivity(new Intent(SplashScreenActivity.this,
							UserNameCaptureActivity.class));
				} else {
					startActivity(new Intent(SplashScreenActivity.this,
							QuestionActivity.class));
				}

				finish();
			}
		}, SPLASH_TIME_OUT);
	}

	protected boolean isTheFirstLogin() {
		DBAdapter dba = new DBAdapter(SplashScreenActivity.this);
		dba.open();
		Cursor cr = dba.fetchUserName();
		if (cr.getCount() != 0) {
			dba.close();
			return false;
		} else {
			dba.close();
			return true;
		}
	}

}
