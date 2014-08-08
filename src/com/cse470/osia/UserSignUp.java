package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserSignUp extends Activity {
	
	/**
	 * user info variables
	 */
	String name;
	String username;
	String password;
	String email;
	String phone;
	boolean checked;
	
	/**
	 * text fields
	 */
	
	EditText mName;
	EditText mUsername;
	EditText mPassword;
	EditText mEmail;
	EditText mPhone;
	
	DatabaseHandler db;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_sign_up);
		
		// creating text field objects
		mName = (EditText) findViewById(R.id.etSignUpCustomerName);
		mUsername = (EditText) findViewById(R.id.etSignUpCustomerUsername);
		mPassword = (EditText) findViewById(R.id.etSignUpCustomerPassword);
		mEmail = (EditText) findViewById(R.id.etSignUpCustomerEmail);
		mPhone = (EditText) findViewById(R.id.etSignUpCustomerPhone);
		
		db = new DatabaseHandler(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_sign_up, menu);
		return true;
	}
	
	public void signUp(View view) {
		Toast toast = null;
		name = mName.getText().toString();
		username = mUsername.getText().toString();
		password = mPassword.getText().toString();
		email = mEmail.getText().toString();
		phone = mPhone.getText().toString();
		
		// user info validations
		if (name.equals("") || username.equals("") || password.equals("")
			|| !email.contains("@") || !email.contains(".com") || phone.equals("")) {
			Toast.makeText(this, "Fill up the form properly", Toast.LENGTH_LONG).show();
			return;
		}
		if (!checked) {
			Toast.makeText(this, "Agree to term of services", Toast.LENGTH_LONG).show();
			return;
		}
		if (!isUserNameExist(username)) {
			Toast.makeText(this, "This username is already exist", Toast.LENGTH_LONG).show();
			return;
		}
		try {
		db.addNewUser(name, username, password, email, phone);
		toast = Toast.makeText(this, "Created new account successfully", Toast.LENGTH_LONG);
		}
		catch(Exception e) {
			toast = Toast.makeText(this, "Unable to Sign up", Toast.LENGTH_LONG);
			e.printStackTrace();
		}
		finally {
			toast.show();
			finish();
		}
	}
	
	private boolean isUserNameExist(String username) {
		List<String> usernames = new ArrayList<String>();
		usernames = db.getAllUsernames();
		for (int c = 0; c < usernames.size(); c++) {
			if (usernames.get(c).equals(username))
				return false;
		}
		return true;
	}
	void gotoisworking() {
		Toast.makeText(this, "Sign up validations is working", Toast.LENGTH_LONG).show();		
	}
	
	public void onCheck (View v) {
		CheckBox cb = (CheckBox) v;
		if (cb.isChecked())
			checked = true;
		if (!cb.isChecked())
			checked = false;
	}

}
