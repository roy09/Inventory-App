package com.cse470.osia;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseOrder extends Activity {

	DatabaseHandler db;
	
	String dealerName;
	String dealerPhone;
	String dealerEmail;
	String dealerAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_order);
		db = new DatabaseHandler(this);
		
		Intent something = getIntent();
		String dealer = something.getStringExtra("dealerName");
		this.dealerName = dealer;

		
		TextView dealerName = (TextView) findViewById(R.id.tvNameOfDealer);
		dealerName.setText(dealer);
		
		ArrayList<String> everythingAboutDealer = db.getEverythingAboutDealer(dealer);
		dealerPhone = everythingAboutDealer.get(1);
		dealerEmail = everythingAboutDealer.get(2);
		dealerAddress = everythingAboutDealer.get(3);
		
//		Toast test = Toast.makeText(this, this.dealerName + " " + this.dealerPhone, Toast.LENGTH_LONG);
//		test.show();
		
		TextView dealerPhone = (TextView) findViewById(R.id.tvDealerPhoneNo);
		dealerPhone.setText(this.dealerPhone);
		
		TextView dealerEmail = (TextView) findViewById(R.id.tvDealerEmail);
		dealerEmail.setText(this.dealerAddress);
		
		TextView dealerAddress = (TextView) findViewById(R.id.tvDealerAddress);
		dealerAddress.setText(this.dealerAddress);
		
		
		
	}
	
	
}
