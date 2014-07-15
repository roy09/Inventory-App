package com.cse470.osia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SeeReport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_report);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_report, menu);
		return true;
	}

}
