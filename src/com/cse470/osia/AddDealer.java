package com.cse470.osia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDealer extends Activity {
	
	DatabaseHandler db;

	
	String dealerName;
	String dealerPhone;
	String dealerEmail;
	String dealerAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dealer);
		
		setTitle("Adding a new dealer");
		db = new DatabaseHandler(this);
		
	}
	
	public void addDealer(View view){
		EditText dealerName = (EditText) findViewById(R.id.etDealerNameAD);
		this.dealerName = dealerName.getText().toString();
		
		EditText dealerPhone = (EditText) findViewById(R.id.etDealerPhoneAD);
		this.dealerPhone = dealerPhone.getText().toString();
		
		EditText dealerEmail = (EditText) findViewById(R.id.etDealerEmailAD);
		this.dealerEmail = dealerEmail.getText().toString();
		
		EditText dealerAddress = (EditText) findViewById(R.id.etDealerAddressAD);
		this.dealerAddress = dealerAddress.getText().toString();
		
		Toast toast = null; 
		try{
			db.addNewDealer(this.dealerName, this.dealerPhone, this.dealerEmail, this.dealerAddress);
			toast = Toast.makeText(this, "Dealer successfully added to the Database", Toast.LENGTH_LONG);
			
		
		} catch(Exception e){
			toast = Toast.makeText(this, "Dealer couldn't be added", Toast.LENGTH_LONG);
			e.printStackTrace();
		} finally {
			toast.show();

			Intent intent = new Intent (this, PurchaseOrderDealer.class);
			startActivity(intent);
			
			finish();
		}
		
	}
}
