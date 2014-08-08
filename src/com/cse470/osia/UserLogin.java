package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UserLogin extends Activity {

	/**
	 * text fields
	 */
	EditText mUsername;
	EditText mPassword;
	ProgressBar pb;

	/**
	 * login variables
	 */
	String username;
	String password;
	
	DatabaseHandler db;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);

		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);

		mUsername = (EditText) findViewById(R.id.username);
		mPassword = (EditText) findViewById(R.id.password);
		
		db = new DatabaseHandler(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
		return true;
	}

	public void signIn(View view) {
		username = mUsername.getText().toString();
		password = mPassword.getText().toString();

		if(!isMatched(username, password)) {
			Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
			return;
		}
		if (username.startsWith("store.")) {
			pb.setVisibility(View.VISIBLE);
			Bundle dataBundle = new Bundle();
			dataBundle.putString("username", username);
			Intent intent = new Intent(this, DashBoardActivity.class);
			intent.putExtras(dataBundle);
			startActivity(intent);
			finish();
		}
		else {
			pb.setVisibility(View.VISIBLE);
			Bundle dataBundle = new Bundle();
			dataBundle.putString("username", username);
			Intent intent = new Intent(this, CustomerDashBoardActivity.class);
			intent.putExtras(dataBundle);
			startActivity(intent);
			finish();
		}

	}

	public void signUp(View view) {
		Intent intent = new Intent(this, UserSignUp.class);
		startActivity(intent);
	}

	private boolean isMatched(String username, String password) {
		if (isUserNameMatched(username)) {
			if (getPassword(username).equals(password))
				return true;
		}
		return false;
	}
	
	private boolean isUserNameMatched(String username) {
		List<String> usernames = new ArrayList<String>();
		usernames = db.getAllUsernames();
		for (int c = 0; c < usernames.size(); c++) {
			if (usernames.get(c).equals(username))
				return true;
		}
		return false;
	}
	
	private String getPassword(String username) {
		String password = db.getUserPassword(username);
		return password;
	}

}
