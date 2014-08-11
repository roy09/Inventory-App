package com.cse470.osia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DashBoardActivity extends Activity {

	static String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
		
		Bundle extras = getIntent().getExtras();
		username = extras.getString("username");

		Toast.makeText(getApplicationContext(), "Logged in as " + username, Toast.LENGTH_SHORT).show();  

	}
	
	public void addProduct(View view){
		Intent intent = new Intent(this, AddProduct.class);
		startActivity(intent);
	}
	
	public void viewInventory(View view){
		Intent intent = new Intent(this, ViewInventory.class);
		startActivity(intent);
	}
	
	public void salesOrder(View view){
		Intent intent = new Intent (this, SalesOrder.class);
		startActivity(intent);
	}
	
	public void seeReport(View view){
		
		Intent intent = new Intent (this, SeeReport.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public void purchaseOrder(View view){
		Intent intent = new Intent(this, PurchaseOrderDealer.class);
		startActivity(intent);
	}
	
	public void userInfo (View view) {
		Bundle dataBundle = new Bundle();
		dataBundle.putString("username", username);
		Intent intent = new Intent(this, UserInfoActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}
	
	
}
