package com.proj.vtumarks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class NotifRes extends ListActivity {

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

	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "cost";
	static final String KEY_DESC = "description";

	TextView nameTV;
	
	TextView semTV;
	TextView semesterTV;
	TextView resultTV;
	TextView totTV;
	TextView totalTV;
	String result = null;
	Boolean chk = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initilize();
		try {
			USNres();
			display_result();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void display_result() {
		if (!chk) {
			finish();
			Intent i_nores = new Intent("android.intent.action.NORES");
			startActivity(i_nores);
		} else {
			nameTV.setText(name);
			semTV.setText("Semester: ");
			semesterTV.setText(sem);
			resultTV.setText(res1);
			totTV.setText("Overall Total: ");
			totalTV.setText(t);

			String i_s = null;
			ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < len; i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				i_s = "" + i;
				map.put(KEY_ID, i_s);
				map.put(KEY_NAME, sub[i]);
				map.put(KEY_COST, total[i]);
				map.put(KEY_DESC, "Externals:  " + internal[i]
						+ "\t\tInternals:  " + external[i] + "\t\tResult: "
						+ pass[i]);

				// adding HashList to ArrayList
				menuItems.add(map);
			}
			ListAdapter adapter = new SimpleAdapter(this, menuItems,
					R.layout.list_item, new String[] { KEY_NAME, KEY_DESC,
							KEY_COST }, new int[] { R.id.name, R.id.desciption,
							R.id.total });

			setListAdapter(adapter);
		}
	}

	public void initilize() {
		// TODO Auto-generated method stub
		nameTV = (TextView) findViewById(R.id.textView1);
		semTV = (TextView) findViewById(R.id.textView2);
		semesterTV = (TextView) findViewById(R.id.textView3);
		resultTV = (TextView) findViewById(R.id.textView4);
		totTV = (TextView) findViewById(R.id.textView5);
		totalTV = (TextView) findViewById(R.id.textView6);

	}

	public String USNres() throws IOException {

		// Check if the result is out
		String str = "Results are not yet available for this university seat number or it might not be a valid university seat number";

		File stinp = new File(Environment.getExternalStorageDirectory()
				+ "/result.txt");
		Document doc = Jsoup.parse(stinp, "UTF-8", "");
		Element ts = null;
		ts = doc.select("table").get(16);

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

}