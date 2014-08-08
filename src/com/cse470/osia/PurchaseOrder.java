package com.cse470.osia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PurchaseOrder extends Activity {

	DatabaseHandler db;

	String dealerName;
	String dealerPhone;
	String dealerEmail;
	String dealerAddress;

	List<String> productName = new ArrayList<String>();
	List<String> productQuantity = new ArrayList<String>();
	List<String> perUnitPrice = new ArrayList<String>();
	List<String> subtotal = new ArrayList<String>();

	ListView purchaseOrderList;

	/**
	 * text fields
	 */

	TextView netPayable;
	TextView purchaseOrderNo;
	TextView purchaseOrderDate;

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

		ArrayList<String> everythingAboutDealer = db
				.getEverythingAboutDealer(dealer);
		dealerPhone = everythingAboutDealer.get(1);
		dealerEmail = everythingAboutDealer.get(2);
		dealerAddress = everythingAboutDealer.get(3);

		Toast test = Toast.makeText(this, this.dealerName + " "
				+ this.dealerEmail, Toast.LENGTH_LONG);
		test.show();

		TextView dealerPhone = (TextView) findViewById(R.id.tvDealerPhoneNo);
		dealerPhone.setText(this.dealerPhone);

		TextView dealerEmail = (TextView) findViewById(R.id.tvDealerEmail);
		dealerEmail.setText(this.dealerEmail);

		TextView dealerAddress = (TextView) findViewById(R.id.tvDealerAddress);
		dealerAddress.setText(this.dealerAddress);

		netPayable = (TextView) findViewById(R.id.totalValuePO);
		purchaseOrderDate = (TextView) findViewById(R.id.tvSetPurchaseDatePO);

		// Adding up stuff to the list
		productName = db.getAllSalesAddedProductName();
		productQuantity = db.getAllSalesAddedProductQuantity();
		perUnitPrice = db.getAllSalesAddedProductUnitPrice();
		subtotal = db.getAllSalesAddedProductSubtotalPrice();

		// For the purchase List
		purchaseOrderList = (ListView) findViewById(R.id.purchaseOrderList);
		SalesProductAdapter orderListAdapter = new SalesProductAdapter(this,
				productName, productQuantity, perUnitPrice, subtotal);
		purchaseOrderList.setAdapter(orderListAdapter);

		setOrderListItemClickListener();
		// setOrderListChoiceModeListener();
		setNetPayable();
		setCurrentDate();
		// setCurrentOrder();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * listView item click listener
	 */
	public void setOrderListItemClickListener() {
		purchaseOrderList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						PurchaseOrder.this);
				adb.setTitle("Are you sure");
				adb.setMessage("You want to delete this item?");
				final int positionToRemove = position;
				// customer.setHint(""+positionToRemove);
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Okay",
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								db.removeSalesAddedItem(productName
										.get(positionToRemove));
								// orderListAdapter.notifyDataSetChanged();
								Intent intent = new Intent(
										getApplicationContext(),
										com.cse470.osia.SalesOrder.class);
								startActivity(intent);
								Toast.makeText(getApplicationContext(),
										"Item removed", Toast.LENGTH_SHORT)
										.show();

								finish();
							}
						});
				adb.show();
			}
		});
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * menu options onOptionsItemSelected
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
		switch (item.getItemId()) {

		case R.id.add_order:
			Bundle dataBundle = new Bundle();
			dataBundle.putInt("id", 0);
			Intent intent = new Intent(getApplicationContext(),
					com.cse470.osia.SalesOrderAddItem.class);
			intent.putExtras(dataBundle);
			startActivity(intent);
			// this.finish();
			return true;

		case R.id.clear_items:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You want to clear the list?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									db.removeAllSalesAddedProduct();
									// orderListAdapter.notifyDataSetChanged();

									Intent intent = new Intent(
											getApplicationContext(),
											com.cse470.osia.SalesOrder.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"List Cleared", Toast.LENGTH_SHORT)
											.show();

									finish();

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
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

	// //
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * onClick listener for checkout (salesOrder) insert on TABLE_SALES_ORDER
	 */
	public void checkout(View v) {
		DatabaseHandler db = new DatabaseHandler(this);
		
		ArrayList<String> productsToAdd = db.getAllSalesAddedProductName();
		ArrayList<String> productAmount = db.getAllSalesAddedProductQuantity();
		
		int counter = 1;
		if (productsToAdd.size() > 1){
			for(String product: productsToAdd){
				if (product != "Item"){
					Log.d("baal", productAmount.get(counter));
					db.updateProductQuantity(product, "positive", Integer.parseInt(productAmount.get(counter)));
					counter++;
				}
			}
		}
		
		db.removeAllSalesAddedProduct();
		Intent intent = new Intent(this, DashBoardActivity.class);
		startActivity(intent);
		
	}

	// /**
	// * hardware back button listener clear the list if yes.
	// */
	//
	// //
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// /**
	// * set net payable money on textView
	// */
	//
	public void setNetPayable() {
		int grandTotal = db.getNetPayable();
		netPayable.setText("" + grandTotal);
	}

	//
	// /**
	// * set sales orderNo on textView
	// */
	// public void setCurrentOrder() {
	// int salesNo = db.getSalesOrderNo();
	// purchaseOrderNo.setText("" + salesNo);
	// }
	//
	/**
	 * set date on button
	 */
	public void setCurrentDate() {
		final Calendar c = Calendar.getInstance();

		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);

		purchaseOrderDate.setText(new StringBuilder().append(day).append("-")
				.append(month + 1).append("-").append(year).append(" "));
	}
}
