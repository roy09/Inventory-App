package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity {
	
	private String username;
	private String password;
	private String message;
	
	/**
	 * text fields
	 */
	TextView mName;
	TextView mUsername;
	TextView mEmail;
	TextView mPhone;
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		Bundle extras = getIntent().getExtras();
		username = extras.getString("username");
		
		mName = (TextView) findViewById(R.id.name);
		mUsername = (TextView)findViewById(R.id.username);
		mEmail = (TextView) findViewById(R.id.email);
		mPhone = (TextView) findViewById(R.id.phone);
		
		db = new DatabaseHandler(this);
		
		setInfo();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}
	
	public void setInfo() {
		ArrayList<String> userInfo = new ArrayList<String>();
		userInfo = db.getUserInfo(username);
		password = userInfo.get(2);
		mName.setText(userInfo.get(0));
		mUsername.setText(userInfo.get(1));
		mEmail.setText(userInfo.get(3));
		mPhone.setText(userInfo.get(4));
	}
	
	public void changePassword (View v) {
		
	}
	
	public void logout(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You want to logout?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(getApplicationContext(), "Logged out as " + username, Toast.LENGTH_SHORT).show();  
				Intent intent = new Intent(getApplicationContext(), UserLogin.class);
				startActivity(intent);
				finish();				
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		AlertDialog d = builder.create();
		d.setTitle("Are you sure");
		d.show();
	}
	
	public void deleteAccount(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You want to delete your account?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				db.deleteUser(username);

				Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();  
				Intent intent = new Intent(getApplicationContext(), UserLogin.class);
				startActivity(intent);
			
				finish();

			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		AlertDialog d = builder.create();
		d.setTitle("Are you sure");
		d.show();
	}

}
