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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseOrder extends Activity {

	DatabaseHandler db;

	String dealerName;
	String dealerPhone;
	String dealerEmail;
	String dealerAddress;
	String date;

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
		purchaseOrderNo = (TextView) findViewById(R.id.purchaseOrderNo);
		purchaseOrderDate = (TextView) findViewById(R.id.tvSetPurchaseDatePO);
		date = purchaseOrderDate.getText().toString();

		// Adding up stuff to the list
		productName = db.getAllPurchaseAddedProductName();
		productQuantity = db.getAllPurchaseAddedProductQuantity();
		perUnitPrice = db.getAllPurchaseAddedProductUnitPrice();
		subtotal = db.getAllPurchaseAddedProductSubtotalPrice();

		// For the purchase List
		purchaseOrderList = (ListView) findViewById(R.id.purchaseOrderList);
		SalesProductAdapter orderListAdapter = new SalesProductAdapter(this,
				productName, productQuantity, perUnitPrice, subtotal);
		purchaseOrderList.setAdapter(orderListAdapter);

		setOrderListItemClickListener();
		// setOrderListChoiceModeListener();
		setNetPayable();
		setCurrentDate();
		setCurrentOrder();
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
						db.removePurchaseAddedItem(productName
								.get(positionToRemove));
						// orderListAdapter.notifyDataSetChanged();
						Intent intent = new Intent(
								getApplicationContext(),
								com.cse470.osia.PurchaseOrder.class);
						intent.putExtra("dealerName", dealerName);
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
		getMenuInflater().inflate(R.menu.purchase_order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.purchase_add_order:
			Bundle dataBundle = new Bundle();
			dataBundle.putString("dealer", dealerName);
			Intent intent = new Intent (getApplicationContext(), PurchaseOrderAddItem.class);
			intent.putExtras(dataBundle);
			startActivity(intent);
			this.finish();
			return true;

		case R.id.purchase_clear_items:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You want to clear the list?")
			.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					db.removeAllPurchaseAddedProduct();
					
					Intent intent = new Intent(
							getApplicationContext(),
							com.cse470.osia.PurchaseOrder.class);
					intent.putExtra("dealerName", dealerName);
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
	 * onClick listener for checkout (PurchaseOrder) insert on TABLE_Purchase_ORDER
	 */
	public void checkout(View v) {
		DatabaseHandler db = new DatabaseHandler(this);

		ArrayList<String> productsToAdd = db.getAllPurchaseAddedProductName();
		ArrayList<String> productAmount = db.getAllPurchaseAddedProductQuantity();
		ArrayList<String> allProducts = db.getAllProductsName();
		String boughtFrom = dealerName;
		String date = this.date;
		
		Toast notif = Toast.makeText(this, boughtFrom, Toast.LENGTH_SHORT);
		notif.show();
				
		int counter = 1;
		if (productsToAdd.size() > 1){
			for(String product: productsToAdd){
				if (product != "Item"){
					
					Log.d("baal", productAmount.get(counter));
					// if not in database, add the product to the database
					if(!allProducts.contains(product)){
						Toast msg = Toast.makeText(getApplicationContext(), "bhetore nai", Toast.LENGTH_LONG);
						msg.show();
						
						Product newProduct = new Product();
						newProduct.setProductName(product);
						newProduct.setProductCategory(category)
//						db.addNewProduct(product);
					}
					
					
//					db.addNewPurchaseOrder(boughtFrom, date, db.getUnitCostingOfProduct(product)
					
					db.updateProductQuantity(product, "positive", Integer.parseInt(productAmount.get(counter)));
					counter++;
				}
			}
		}

		db.removeAllPurchaseAddedProduct();
//		Intent intent = new Intent(this, DashBoardActivity.class);
//		startActivity(intent);

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
		int grandTotal = db.getPurchaseNetPayable();
		netPayable.setText("" + grandTotal);
	}


	/**
	 * set Purchase orderNo on textView
	 */
	public void setCurrentOrder() {
		int purchaseNo = db.getPurchaseOrderNo();
		purchaseOrderNo.setText("" + purchaseNo);
	}

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
