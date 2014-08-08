package com.cse470.osia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserSignUp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_sign_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_sign_up, menu);
		return true;
	}

}
