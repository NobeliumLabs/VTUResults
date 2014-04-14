package com.proj.vtumarks;

import android.app.Activity;
import android.os.Bundle;

public class Nores extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nores);
	}
	
	@Override
	  public void onStop() {
	    super.onStop();
	  }
	

}
