package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseOrderAddItem extends Activity {


	/**
	 * class variables
	 */
	List<String> categories = new ArrayList<String>();

	String productName;
	String productCategory;
	String productUnitPrice; // parse to integer
	String productQuantity;  // parse to integer

	
	String dealer;
	
	/**
	 * Text Fields
	 */
	EditText product_name;
	EditText product_quantity;
	EditText unit_price;
	TextView subTotal;
	AutoCompleteTextView category;
	Button add_item;

	//Declaring the database
	DatabaseHandler db;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_order_add_item);

		/**
		 * creating text field's objects
		 */
		product_name = (EditText) findViewById (R.id.etProductNamePOAI);
		product_quantity = (EditText) findViewById(R.id.etQuantityPOAI);
		unit_price = (EditText) findViewById(R.id.etNormalPricePOAI);
		subTotal = (TextView) findViewById(R.id.etSubtotalPOAI);		
		category = (AutoCompleteTextView) findViewById(R.id.etCategoryPOAI);
		add_item = (Button) findViewById(R.id.btnSubmitPOAI);
		
		Bundle extras = getIntent().getExtras();
		dealer = extras.getString("dealer");
		
		db = new DatabaseHandler (this);

		categories = db.getDistinctProductsCategory();

		// constructing Spinner;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);
		
		setQuantityListener();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.purchase_order_add_item, menu);
		return true;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	/**
	* add Item button click listener
	*/

	
	public void addItem(View v) {
		Toast toast = null;
		String productName = getProductName();
		int unitPrice = getUnitPrice();
		int quantity = getQuantity();
		int subtotal = getSubtotal();
		if (subtotal == 0) {
			toast = Toast.makeText(this, "No item added", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
				
		try {
			db.addNewItemPurchaseOrder(productName, unitPrice, quantity, subtotal);
			toast = Toast.makeText(this, "Item successfully added", Toast.LENGTH_LONG);
		}
		catch(Exception e) {
			toast = Toast.makeText(this, "Item couldn't be added", Toast.LENGTH_LONG);
			e.printStackTrace();
		}
		finally {
			toast.show();
			Bundle databundle = new Bundle();
			databundle.putString("dealerName", dealer);
			Intent intent = new Intent (this, PurchaseOrder.class);
			intent.putExtras(databundle);
			startActivity(intent);
			finish();
		}
	}
	
	/**
	 * get values from text fields
	 */
	public String getProductName() {
		String productName = product_name.getText().toString();
		return productName;
	}
	public int getQuantity() {
		int quantity;
		String getQ = product_quantity.getText().toString();
		if (getQ.equals(""))
			return 0;
		quantity = Integer.parseInt(getQ);
		return quantity;
	}
	public int getUnitPrice() {
		int price;
		String unitPrice = unit_price.getText().toString();
		if (unitPrice.equals(""))
			price = 0;
		else
			price = Integer.parseInt(unitPrice);
		return price;
	}
	public int getSubtotal() {
		int price;
		String subtotal = subTotal.getText().toString();
		if (subtotal.equals(""))
			price = 0;
		else
			price = Integer.parseInt(subtotal);
		return price;
	}
	
	public void setQuantityListener() {
		product_quantity.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				//subTotal.setText(0);
				String q = product_quantity.getText().toString();
				String up = unit_price.getText().toString();
				if (q.equals("") || up.equals(""))
					subTotal.setText("" + 0);
				else {
					int quantity = Integer.parseInt(q);
					int unitPrice = Integer.parseInt(up);
					int total = quantity * unitPrice;
					subTotal.setText("" + total);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				subTotal.setText("" + 0);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String q = product_quantity.getText().toString();
				String up = unit_price.getText().toString();
				if (q.equals("") || up.equals(""))
					subTotal.setText("" + 0);
				else {
					int quantity = Integer.parseInt(q);
					int unitPrice = Integer.parseInt(up);
					int total = quantity * unitPrice;
					subTotal.setText("" + total);
				}
				
			}
		});
	}

}
