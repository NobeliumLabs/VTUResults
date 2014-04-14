package com.proj.vtumarks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AlarmReceiver extends BroadcastReceiver {

	private static Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		mContext = context;

		if (isNetworkAvailable()) {
			Intent mIntent = new Intent(context, ForegorundService.class);
			context.startService(mIntent);
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
	/*
	 * public void startDownloadResults() throws IOException { // TODO
	 * Auto-generated method stub
	 * 
	 * input =
	 * "http://results.vtu.ac.in/vitavi.php?rid=1pe11mba27&submit=SUBMIT";
	 * //result="700"; result = ParseUSN2(input); }
	 * 
	 * public String ParseUSN2(String input) throws IOException {
	 * 
	 * // Check if the result is out
	 * 
	 * String str =
	 * "Results are not yet available for this university seat number or it might not be a valid university seat number"
	 * ;
	 * 
	 * Document doc = Jsoup.connect(input).get(); Element ts = null; ts =
	 * doc.select("table").get(16);
	 * 
	 * String s2 = ts.select("td[width=513]").first().text(); if
	 * (s2.contains(str)) { chk = false; return null; } name =
	 * ts.select("b").eq(0).text(); sem = ts.select("b").eq(2).text(); res1 =
	 * ts.select("b").eq(3).text(); res = res1.replace("Result:", ""); s =
	 * ts.select("tr").size(); i = 0; sub = new String[s - 10]; internal = new
	 * String[s - 10]; external = new String[s - 10]; total = new String[s -
	 * 10]; pass = new String[s - 10]; t = ts.select("tr").get(s - 7).text(); t
	 * = t.replace("Total Marks:", ""); for (Element row :
	 * ts.select("tr").subList(4, s - 6)) { Elements tds = row.select("td"); if
	 * (tds.size() == 5) { sub[i] = tds.get(0).text(); internal[i] =
	 * tds.get(1).text(); external[i] = tds.get(2).text(); total[i] =
	 * tds.get(3).text(); pass[i] = tds.get(4).text(); i++; } } len = i; chk =
	 * true; return t; }
	 */
}
