package com.cse470.osia;

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
	
	public void viewInventory(View view){
		
	}
	
	public void salesOrder(View view){
		
	}
	
	public void seeReport(View view){
		
	}
	
	
}
