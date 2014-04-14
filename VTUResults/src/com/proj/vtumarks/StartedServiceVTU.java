package com.proj.vtumarks;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class StartedServiceVTU extends Service {

	Boolean flag = true;

	private static final String tag = StartedServiceVTU.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(tag, "onCreate");
		Toast.makeText(getApplicationContext(), "Running service",
				Toast.LENGTH_SHORT).show();
		

		// super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(tag, "onStartCommand startId=" + startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d(tag, "onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(tag, "onUnbind");
		return super.onUnbind(intent);
	}

}
