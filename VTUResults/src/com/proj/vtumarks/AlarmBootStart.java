package com.proj.vtumarks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AlarmBootStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(arg0);
		boolean enoti = getPrefs.getBoolean("enb", true);
		if (enoti) {
			Toast.makeText(
					arg0,
					"VTU polling services have started.\nYou can disable this in preferences",
					Toast.LENGTH_LONG).show();

			// Intent mIntent = new Intent();
			// mIntent.setClass(this, StartedServiceVTU.class);
			// startService(mIntent);

			Intent i = new Intent(arg0, AlarmInitilize.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			arg0.startActivity(i);
		}
	}
}
