package com.proj.vtumarks;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.proj.vtumarks.ParseUSN.loadUsn;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ForegorundService extends Service {

	static final int unqueID = 9009001;
	String name = null;
	String sem = null;
	String res1 = null;
	String res = null;
	int s, i, len;
	String[] sub = null;
	String[] internal = null;
	String[] external = null;
	String[] total = null;
	String[] pass = null;
	String t = null;

	String input = null;
	String result = null;
	Boolean chk = false;
	private static final String tag = ForegorundService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(tag, "onBind");
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(tag, "onCreate");

		Notification notification = new Notification(R.drawable.ic_launcher,
				getText(R.string.app_name), System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, Vtu.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this, "Checking for results",
				"Connecting to VTU servers", pendingIntent);
		startForeground(1, notification);

		new loadUsn().execute(input);

	}

	@SuppressWarnings("deprecation")
	private void displayres() {
		// TODO Auto-generated method stub
		Intent intentstop = new Intent(this, AlarmReceiver.class);
		PendingIntent senderstop = PendingIntent.getBroadcast(this, 900900,
				intentstop, 0);
		AlarmManager alarmManagerstop = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManagerstop.cancel(senderstop);
		NotificationManager nm;
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		String body = "Your Total: " + result;
		String title = "Results are out!";
		Notification n = new Notification(R.drawable.ic_launcher, body,
				System.currentTimeMillis());
		Intent intent = new Intent(this, NotifRes.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		n.setLatestEventInfo(this, title, body, pi);
		n.defaults = Notification.DEFAULT_ALL;
		nm.notify(unqueID, n);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(tag, "onStart startId=" + startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(tag, "onStartCommand startId=" + startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(tag, "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy");
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	public void startDownloadResults() throws IOException {
		// TODO Auto-generated method stub
		SharedPreferences getPrefs2 = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String usnauto = getPrefs2.getString("name", "");
		usnauto += "&submit=SUBMIT";
		input = "http://results.vtu.ac.in/vitavi.php?rid=" + usnauto;
		result = ParseUSN2(input);
	}

	public String ParseUSN2(String input) throws IOException {

		String str = "Results are not yet available for this university seat number or it might not be a valid university seat number";
		Document doc = Jsoup.connect(input).get();
		Element ts = null;
		ts = doc.select("table").get(16);

		FileWriter fWriter;
		try {
			fWriter = new FileWriter(Environment.getExternalStorageDirectory()
					+ "/" + "result.txt");
			fWriter.write(doc.toString());
			fWriter.flush();
			fWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String s2 = ts.select("td[width=513]").first().text();
		if (s2.contains(str)) {
			chk = false;
			return null;
		}
		name = ts.select("b").eq(0).text();
		sem = ts.select("b").eq(2).text();
		res1 = ts.select("b").eq(3).text();
		res = res1.replace("Result:", "");
		s = ts.select("tr").size();
		i = 0;
		sub = new String[s - 10];
		internal = new String[s - 10];
		external = new String[s - 10];
		total = new String[s - 10];
		pass = new String[s - 10];
		t = ts.select("tr").get(s - 7).text();
		t = t.replace("Total Marks:", "");
		for (Element row : ts.select("tr").subList(4, s - 6)) {
			Elements tds = row.select("td");
			if (tds.size() == 5) {
				sub[i] = tds.get(0).text();
				internal[i] = tds.get(1).text();
				external[i] = tds.get(2).text();
				total[i] = tds.get(3).text();
				pass[i] = tds.get(4).text();
				i++;
			}
		}
		len = i;
		chk = true;
		return t;
	}

	public class loadUsn extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog;

		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// String result = null;
				startDownloadResults();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(String result) {
			if (!chk) {
				//to do when results are not out
				/*
				 * Toast.makeText(getApplicationContext(), "Results not out",
				 * Toast.LENGTH_SHORT).show();
				 */
			} else {
				displayres();
			}
			stopSelf();
		}

	}
}