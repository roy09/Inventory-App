package com.cse470.osia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	
	SalesProductAdapter orderListAdapter;
	
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
		netPayable = (TextView) findViewById(R.id.tvNameOfDealerTO);
		orderList = (ListView) findViewById(R.id.lvSalesAddedItem);
		
		/**
		 * get items from the database
		 */
		productName = db.getAllSalesAddedProductName();
		productQuantity = db.getAllSalesAddedProductQuantity();
		perUnitPrice = db.getAllSalesAddedProductUnitPrice();
		subtotal = db.getAllSalesAddedProductSubtotalPrice();
		
		orderListAdapter = new SalesProductAdapter(this, productName, productQuantity, perUnitPrice, subtotal);
		orderList.setAdapter(orderListAdapter);
		setOrderListItemClickListener();
		//setOrderListChoiceModeListener();
		setNetPayable();
		setCurrentDate();
		setCurrentOrder();
		
		
		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * listView item click listener
	 */
	public void setOrderListItemClickListener() {
		orderList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?>a, View v, int position, long id) {
				AlertDialog.Builder adb=new AlertDialog.Builder(SalesOrder.this);
		        adb.setTitle("Are you sure");
		        adb.setMessage("You want to delete this item?");
		        final int positionToRemove = position;
		        //customer.setHint(""+positionToRemove);
		        adb.setNegativeButton("Cancel", null);
		        adb.setPositiveButton("Okay", new AlertDialog.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                db.removeSalesAddedItem(productName.get(positionToRemove));
		                //orderListAdapter.notifyDataSetChanged();
		                Intent intent = new Intent(getApplicationContext(),com.cse470.osia.SalesOrder.class);
						startActivity(intent);
						Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();  
						
						finish();
		            }});
		        adb.show();
			}
		});
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * menu options
	 * onOptionsItemSelected
	 */
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
            //this.finish();
            return true;
		
		case R.id.clear_items:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You want to clear the list?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					db.removeAllSalesAddedProduct();
					//orderListAdapter.notifyDataSetChanged();
					
					Intent intent = new Intent(getApplicationContext(),com.cse470.osia.SalesOrder.class);
					startActivity(intent);
					Toast.makeText(getApplicationContext(), "List Cleared", Toast.LENGTH_SHORT).show();  
					
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

			return true;			
			
		default: 
			return super.onOptionsItemSelected(item);

		}

	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * onClick listener for checkout (salesOrder)
	 * insert on TABLE_SALES_ORDER
	 */
	public void checkout (View v) {
		
	}
	
	/**
	 *  hardware back button listener
	 *  clear the list if yes.
	 */
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
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
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
	}

}