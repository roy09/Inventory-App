package com.cse470.osia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class CustomerDashBoardActivity extends Activity {
	
	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_dash_board);
		
		Bundle extras = getIntent().getExtras();
		username = extras.getString("username");

		Toast.makeText(getApplicationContext(), "Logged in as " + username, Toast.LENGTH_SHORT).show();  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer_dash_board, menu);
		return true;
	}
	
	public void purchaseOrder(View v) {
		Intent intent = new Intent (this, SalesOrder.class);
		startActivity(intent);
	}
	
	public void viewInventory (View v) {
		Intent intent = new Intent (this, ViewInventory.class);
		startActivity(intent);
	}
	
	public void seeReport (View v) {
		Bundle dataBundle = new Bundle();
		dataBundle.putString("username", username);
		Intent intent = new Intent (this, CustomerSeeReport.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}
	
	public void userInfo (View v) {
		Bundle dataBundle = new Bundle();
		dataBundle.putString("username", username);
		Intent intent = new Intent(this, UserInfoActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

}
