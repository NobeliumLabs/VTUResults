package com.proj.vtumarks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Email extends Activity {

	EditText emailid, emailmessage;
	String emailmessage1;
	Button emailsend;
	String emailid1 = "arpith@live.com";
	String emailid2 = "nnagabharan@gmail.com";
	String versionName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		initializeVars();

		try {
			versionName = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emailsend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
				emailmessage1 = emailmessage.getText().toString();
				String osinfo = "\nBoard: " + Build.BOARD.toString()
						+ "\nBrand: " + Build.BRAND.toString() + "\nDevice: "
						+ Build.DEVICE.toString() + "\nDisplay: "
						+ Build.DISPLAY.toString() + "\nFingerprint: "
						+ Build.FINGERPRINT.toString() + "\nHardware: "
						+ Build.HARDWARE.toString() + "\nID: "
						+ Build.ID.toString() + "\nManufacturer: "
						+ Build.MANUFACTURER.toString() + "\nModel: "
						+ Build.MODEL.toString() + "\nProduct: "
						+ Build.PRODUCT.toString() + "\nSDK Version: "
						+ Build.VERSION.SDK_INT + "\nType: "
						+ Build.TYPE.toString()
						+"\nAndroidVersion="+Build.VERSION.RELEASE;
				String msg = emailmessage1
						+ "\n\n\n\n\n\n**************************\nDevice info:\n"
						+ osinfo;
				String emailaddress[] = { emailid1, emailid2 };
				Intent i = new Intent(android.content.Intent.ACTION_SEND);
				i.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
				i.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"[Feedback]VTU Results, Version: " + versionName);
				i.setType("plain/text");
				i.putExtra(android.content.Intent.EXTRA_TEXT, msg);
				startActivity(i);}
				catch(Exception e){
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Email.this);
					alertDialog.setTitle("No Email Client Found");
					alertDialog
							.setMessage("Please install and configure an application that can send e-mails.\n" +
									"Alternatively, send in your feedback to:\n" +
									"\tnnagabharan@gmail.com\n" +
									"\tarpith@live.com\n" +
									"using your browser.");
					alertDialog.setIcon(R.drawable.delete);
					alertDialog.setNeutralButton("OK", null);
					alertDialog.show();
				}
			}
		});
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		emailmessage = (EditText) findViewById(R.id.emailmessage);
		emailsend = (Button) findViewById(R.id.emailsend);
	}
}
