package com.cse470.osia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SalesOrder extends Activity {
	
	
	/**
	 * text fields
	 */
	
	TextView setDate;
	TextView orderNo;
	ListView orderList;
	EditText customer;
	TextView netPayable;
	/**
	 * ArrayLists for handling the added item.
	 */
	List <String> productName = new ArrayList<String>();
	List <String> productQuantity = new ArrayList<String>();
	List <String> perUnitPrice = new ArrayList<String>();
	List <String> subtotal = new ArrayList<String>();
	
	/**
	 * database object
	 */
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_order);
		db = new DatabaseHandler(this);
		
		setDate  = (TextView) findViewById(R.id.tvSetSalesDate);
		orderNo = (TextView) findViewById(R.id.tvSalesOrderNo);
		customer = (EditText) findViewById(R.id.etSalesCustomerName);
		netPayable = (TextView) findViewById(R.id.textView1);
		orderList = (ListView) findViewById(R.id.lvSalesAddedItem);
		
		/**
		 * get items from the database
		 */
		productName = db.getAllSalesAddedProductName();
		productQuantity = db.getAllSalesAddedProductQuantity();
		perUnitPrice = db.getAllSalesAddedProductUnitPrice();
		subtotal = db.getAllSalesAddedProductSubtotalPrice();
		
		
		orderList.setAdapter(new SalesProductAdapter(this, productName, productQuantity, perUnitPrice, subtotal));
		setNetPayable();
		setCurrentDate();
		setCurrentOrder();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sales_order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {

		case R.id.add_order:
			Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);
            Intent intent = new Intent(getApplicationContext(),com.cse470.osia.SalesOrderAddItem.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
            return true; 
		default: 
			return super.onOptionsItemSelected(item);

		}

	}
	
	/**
	 * set net payable money on textView
	 */
	
	public void setNetPayable() {
		int grandTotal = db.getNetPayable();
		netPayable.setText("" + grandTotal);
	}

	/**
	 * set sales orderNo on textView
	 */
	public void setCurrentOrder() {
		int salesNo = db.getSalesOrderNo();
		orderNo.setText("" + salesNo);
	}
	
	/**
	 * set date on button
	 */
	public void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		
		setDate.setText(new StringBuilder()
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
	}

}