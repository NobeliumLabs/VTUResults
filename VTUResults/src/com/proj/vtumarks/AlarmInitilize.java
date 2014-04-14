package com.proj.vtumarks;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class AlarmInitilize extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		//boolean enoti = getPrefs.getBoolean("en", true);
		int val=Integer.valueOf(getPrefs.getString("ti", "2"));
		if (val == 0) {
			val = 1;
		} else{
			Intent myIntent = new Intent(getBaseContext(), AlarmReceiver.class);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getBaseContext(), 900900, myIntent, 0);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 10);
			long interval = 1000*60*val;  //CHECK!
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), interval, pendingIntent);
		}
		finish();
	}
}
