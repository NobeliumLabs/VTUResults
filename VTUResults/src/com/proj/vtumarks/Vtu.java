package com.proj.vtumarks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Vtu extends Activity implements OnClickListener {

	TextView display;
	EditText input1;
	Button b1;
	String usn = null;
	String input = "http://results.vtu.ac.in/vitavi.php?rid=";
	Boolean asubmit = false;
	Button bStartService, bStopService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input1 = (EditText) findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		display = (TextView) findViewById(R.id.textView1);
		bStartService = (Button) findViewById(R.id.button2);
		bStopService = (Button) findViewById(R.id.button3);

		bStartService.setOnClickListener(this);
		bStopService.setOnClickListener(this);

		SharedPreferences getPrefs2 = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		usn = getPrefs2.getString("name", "");
		input1.setText(usn);
		asubmit = getPrefs2.getBoolean("a_submit", false);

		if (asubmit) {
			if (!isNetworkAvailable()) {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						Vtu.this);
				alertDialog.setTitle("No Internet Connectivity");
				alertDialog
						.setMessage("Please connect to the internet and then continue");
				alertDialog.setIcon(R.drawable.delete);
				alertDialog.setNeutralButton("OK", null);
				alertDialog.show();

			} else if (usn.matches("")) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						Vtu.this);
				alertDialog.setTitle("Default USN requlred");
				alertDialog
						.setMessage("Please set 'Default USN' in preferences to use auto submit feature");
				alertDialog.setIcon(R.drawable.delete);
				alertDialog.setNeutralButton("OK", null);
				alertDialog.show();
			} else {
				usn += "&submit=SUBMIT";
				input += usn;
				Bundle basket = new Bundle();
				basket.putString("key", input);
				Intent display_res = new Intent(Vtu.this, ParseUSN.class);
				display_res.putExtras(basket);
				startActivity(display_res);
				input = "http://results.vtu.ac.in/vitavi.php?rid=";
			}
		}

		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				usn = input1.getText().toString();
				if (!isNetworkAvailable()) {

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Vtu.this);
					alertDialog.setTitle("No Internet Connectivity");
					alertDialog
							.setMessage("Please connect to the internet and then continue");
					alertDialog.setIcon(R.drawable.delete);
					alertDialog.setNeutralButton("OK", null);
					alertDialog.show();

				} else {
					if (usn.matches("")) {

						SharedPreferences getPrefs2 = PreferenceManager
								.getDefaultSharedPreferences(getBaseContext());
						usn = getPrefs2.getString("name", "");
						input1.setText(usn);

					}
					if (usn.matches("")) {
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								Vtu.this);
						alertDialog.setTitle("Usn Field is Empty");
						alertDialog
								.setMessage("Please enter USN in the field above or set 'default USN' in Preferences");
						alertDialog.setIcon(R.drawable.delete);
						alertDialog.setNeutralButton("OK", null);
						alertDialog.show();
					} else {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(input1.getWindowToken(), 0);
						usn += "&submit=SUBMIT";
						input += usn;
						Bundle basket = new Bundle();
						basket.putString("key", input);
						// new loadUsn().execute(input);
						Intent display_res = new Intent(Vtu.this,
								ParseUSN.class);
						display_res.putExtras(basket);
						startActivity(display_res);
						input = "http://results.vtu.ac.in/vitavi.php?rid=";
					}
				}
			}
		});

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button2:
			SharedPreferences getPrefs2 = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			String usn_noti = getPrefs2.getString("name", "");
			if (usn_noti.matches("")) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						Vtu.this);
				alertDialog.setTitle("Set default USN");
				alertDialog
						.setMessage("Please set 'Default USN' in preferences to continue.");
				alertDialog.setIcon(R.drawable.delete);
				alertDialog.setNeutralButton("OK", null);
				alertDialog.show();
			}
			// the following elseif condition is made useless by the removal of
			// en (enable notification) option from preferences
			/*
			 * else if (!enotification) { AlertDialog.Builder alertDialog = new
			 * AlertDialog.Builder( Vtu.this);
			 * alertDialog.setTitle("Enable Notification"); alertDialog
			 * .setMessage
			 * ("Please enable notification option in preferences to continue");
			 * alertDialog.setIcon(R.drawable.delete);
			 * alertDialog.setNeutralButton("OK", null); alertDialog.show(); }
			 */else {
				String usn_toast = getPrefs2.getString("name", "");
				Toast.makeText(getApplicationContext(),
						"Polling Started for USN: " + usn_toast,
						Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(),
						"You may now close this app", Toast.LENGTH_LONG).show();
				// Intent mIntent = new Intent();
				// mIntent.setClass(this, StartedServiceVTU.class);
				// startService(mIntent);
				Intent ialarm = new Intent("android.intent.action.ALARMINI");
				ialarm.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				ialarm.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				ialarm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(ialarm);
			}
			// finish();
			break;
		case R.id.button3:
			Toast.makeText(getApplicationContext(), "Polling Stoped",
					Toast.LENGTH_LONG).show();
			Intent intentstop = new Intent(this, AlarmReceiver.class);
			PendingIntent senderstop = PendingIntent.getBroadcast(this, 900900,
					intentstop, 0);
			AlarmManager alarmManagerstop = (AlarmManager) getSystemService(ALARM_SERVICE);

			alarmManagerstop.cancel(senderstop);
			break;
		default:
			break;
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menu_vtu = getMenuInflater();
		menu_vtu.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.aboutus:
			Intent i = new Intent("android.intent.action.ABOUTUS_VTU");
			startActivity(i);
			break;

		case R.id.contact:
			Intent j = new Intent("android.intent.action.EMAIL_US");
			startActivity(j);
			break;

		case R.id.prefs:
			Intent k = new Intent("android.intent.action.PREFS_VTU");
			startActivity(k);
			break;

		case R.id.exit:
			finish();
			break;

		}
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (usn.matches("")) {
			SharedPreferences getPrefs2 = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			usn = getPrefs2.getString("name", "");
			input1.setText(usn);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
