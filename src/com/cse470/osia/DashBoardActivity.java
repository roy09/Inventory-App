package com.cse470.osia;

import com.cse470.osia.purchaseOrder.OrderActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashBoardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
	}
	
	public void addProduct(View view){
		Intent intent = new Intent(this, AddProduct.class);
		startActivity(intent);
	}
	
	public void purchaseOrder(View view){
		Intent intent = new Intent(this, OrderActivity.class);
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
		Intent intent = new Intent (this, OrderActivity.class);
		startActivity(intent);
	}
	
	
}
