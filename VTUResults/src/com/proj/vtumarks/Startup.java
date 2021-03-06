package com.proj.vtumarks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Startup extends Activity {

	boolean flag = true;
	String versionName=null;
	TextView app_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		boolean startup = getPrefs.getBoolean("splash", false);
		if (!startup) {
			Intent openMainPage = new Intent("android.intent.action.VTU");
			startActivity(openMainPage);
		} else {
			app_version=(TextView)findViewById(R.id.appversion);
			try {
				versionName = this.getPackageManager().getPackageInfo(
						this.getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			app_version.setText("Version: "+versionName);
			Thread timer = new Thread() {
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {
						Intent openMainPage = new Intent(
								"android.intent.action.VTU");
						startActivity(openMainPage);
					}
				}
			};
			timer.start();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
