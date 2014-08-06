package com.cse470.osia;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PurchaseOrderDealer extends Activity {

	List<String> dealerNames;
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_order_dealer);

		setTitle("Select the dealer");
		db = new DatabaseHandler(this);

		dealerNames = db.getAllDealerName();

		ListView dealerNameList = (ListView) findViewById(R.id.dealerNameList);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dealerNames);
		dealerNameList.setAdapter(adapter);
	}

	public void addDealer(View view) {
		Intent intent = new Intent(this, AddDealer.class);
		startActivity(intent);
	}
}
