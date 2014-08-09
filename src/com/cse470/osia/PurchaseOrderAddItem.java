package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
	String productQuantity; // parse to integer

	String dealer;

	/**
	 * Text Fields
	 */
	AutoCompleteTextView product_name;
	EditText product_quantity;
	EditText mUnit_price;
	TextView subTotal;
	AutoCompleteTextView mCategory;
	Button add_item;
	EditText mMrp;

	// Declaring the database
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_order_add_item);
		db = new DatabaseHandler(this);
		
		/**
		 * creating text field's objects
		 */
		product_name = (AutoCompleteTextView) findViewById(R.id.etProductNamePOAI);
		setAutoComplete();
		setProductNameListener();
		product_quantity = (EditText) findViewById(R.id.etQuantityPOAI);
		mUnit_price = (EditText) findViewById(R.id.etNormalPricePOAI);
		subTotal = (TextView) findViewById(R.id.etSubtotalPOAI);
		mCategory = (AutoCompleteTextView) findViewById(R.id.etCategoryPOAI);
		add_item = (Button) findViewById(R.id.btnSubmitPOAI);
		mMrp = (EditText) findViewById(R.id.etMRPPOAI);

		Bundle extras = getIntent().getExtras();
		dealer = extras.getString("dealer");



		categories = db.getDistinctProductsCategory();

		// constructing Spinner;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategory.setAdapter(dataAdapter);

		setQuantityListener();

	}

	public void setAutoComplete() {
		String category = "Any";
		
		ArrayList<String> product_list;
		if (category.equals("Any"))
			product_list = db.getAllProductsName();
		else
			product_list = db.getCategoryBasedProductName(category);

		ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, product_list);

		product_name.setThreshold(1);
		product_name.setAdapter(productAdapter);
		
		
	}
	
	public void setProductNameListener() {
		product_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String productName = product_name.getText().toString();
				String category = db.getCategoryOfProduct(productName);
				String productBuyingprice = db.getUnitCostingOfProduct(productName);
				String productSellingPrice = db.getUnitPriceOfProduct(productName);
				String quantity = db.getQuantityProduct(productName);
				
				Log.e("mathamota", productName);
				
				mCategory.setText(category);
				mUnit_price.setText(productBuyingprice);
				product_quantity.setHint(quantity + " in stock");
				mMrp.setText(productSellingPrice);
		
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.purchase_order_add_item, menu);
		return true;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * add Item button click listener
	 */

	public void addItem(View v) {
		Toast toast = null;
		String productName = getProductName();
		int unitPrice = getUnitPrice();
		String productCatoryx = addedProductCategory();
		String mrp = getMrp();
		int quantity = getQuantity();
		int subtotal = getSubtotal();
		if (subtotal == 0) {
			toast = Toast.makeText(this, "No item added", Toast.LENGTH_LONG);
			toast.show();
			return;
		}

//		String productName, String productCategory, int productUnitPrice,
//		int productQuantity, int subtotalPrice)
		try {
			db.addNewItemPurchaseOrder(productName, productCatoryx, unitPrice, quantity,
					subtotal, mrp);
			toast = Toast.makeText(this, "Item successfully added",
					Toast.LENGTH_LONG);
		} catch (Exception e) {
			toast = Toast.makeText(this, "Item couldn't be added",
					Toast.LENGTH_LONG);
			e.printStackTrace();
		} finally {
			toast.show();
			Bundle databundle = new Bundle();
			databundle.putString("dealerName", dealer);
			Intent intent = new Intent(this, PurchaseOrder.class);
			intent.putExtras(databundle);
			startActivity(intent);
			finish();
		}
	}

	private String getMrp() {
		// TODO Auto-generated method stub
		String mrp = "";
		EditText mrpX = (EditText) findViewById(R.id.etMRPPOAI);
		mrp = mrpX.getText().toString();
		return mrp;
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
		String unitPrice = mUnit_price.getText().toString();
		if (unitPrice.equals(""))
			price = 0;
		else
			price = Integer.parseInt(unitPrice);
		return price;
	}
	
	public String addedProductCategory() {
		
		EditText sth = (EditText) findViewById(R.id.etCategoryPOAI);
		String category= sth.getText().toString();
		
		return category;
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// subTotal.setText(0);
				String q = product_quantity.getText().toString();
				String up = mUnit_price.getText().toString();
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
				String up = mUnit_price.getText().toString();
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
