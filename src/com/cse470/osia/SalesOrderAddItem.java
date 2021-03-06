package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SalesOrderAddItem extends Activity {
	
	
	/**
	 * class variables
	 */
	List<String> categories = new ArrayList<String>();

	String productName;
	String productCategory;
	String productUnitPrice; // parse to integer
	String productQuantity;  // parse to integer
	
	
	/**
	 * Text Fields
	 */
	AutoCompleteTextView product_name;
	EditText product_quantity;
	TextView unit_price;
	TextView subTotal;
	Spinner category;
	Button add_item;
	
	//Declaring the database
	DatabaseHandler db;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_order_add_item);

		/**
		 * creating text field's objects
		 */
		product_name = (AutoCompleteTextView) findViewById (R.id.autoCompleteAddItemProductName);
		product_quantity = (EditText) findViewById(R.id.etAddItemProductQuantity);
		unit_price = (TextView) findViewById(R.id.tvSalesAddItemUnitPrice);
		subTotal = (TextView) findViewById(R.id.tvSalesAddItemSubtotal);		
		category = (Spinner) findViewById(R.id.spAddItemCategory);
		add_item = (Button) findViewById(R.id.btnAddItem);
		
		db = new DatabaseHandler (this);
		
		
		// product categories
//		categories.add("Any");
//		categories.add("Ink");
//		categories.add("Paper");
//		categories.add("Khata");
//		categories.add("Toy");
		
		categories = db.getDistinctProductsCategory();
		
		// constructing Spinner;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);
		
		setCategorySelectedListener();
		setProductNameListener();
		setQuantityListener();
		
		
		
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sales_order_add_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		
		case R.id.inventory:
			Intent intent = new Intent(this, ViewInventory.class);
			startActivity(intent);
            //this.finish();
            return true;
		
	
		default: 
			return super.onOptionsItemSelected(item);

		}

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
		
		int itemStock = getQuantity(productName);
//		 CHECK AVAILABILITY
		if (itemStock == 0) {
			toast = Toast.makeText(this, "This item is out of stock", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		if (itemStock < quantity) {
			toast = Toast.makeText(this, "Quantity available of this item: " + itemStock, Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		
		try {
			db.addNewItemSalesOrder(productName, unitPrice, quantity, subtotal);
			toast = Toast.makeText(this, "Item successfully added", Toast.LENGTH_LONG);
		}
		catch(Exception e) {
			toast = Toast.makeText(this, "Item couldn't be added", Toast.LENGTH_LONG);
			e.printStackTrace();
		}
		finally {
			toast.show();
			Intent intent = new Intent (this, SalesOrder.class);
			startActivity(intent);
			finish();
		}
	}
	
	public int getQuantity(String productName) {
		int quantity = db.getProductQuantity(productName);
		return quantity;
	}
	
	/**
	 * set quantity listener for editText(quantity)
	 */
	
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
	
	
	/**
	 * set on product listener for editText (product_name)
	 * call for the unit price of the product from the database
	 * set it on the unit_price testView box
	 * set the price on subTotal
	 */
	
	public void setProductNameListener() {
		product_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				unit_price.setText("" + 0);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				unit_price.setText("" + 0);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String productName = product_name.getText().toString();
				String price = db.getUnitPriceOfProduct(productName);
				unit_price.setText(price);
				setSubTotal();
				
			}
		});
	}
	
	public void setSubTotal() {
		String q = product_quantity.getText().toString();
		String up = unit_price.getText().toString();
		if (up.equals(""))  {
			unit_price.setText("" + 0);
			subTotal.setText("" + 0);
		}
		else if (q.equals(""))
			subTotal.setText("" + 0);
		else {
			int quantity = Integer.parseInt(q);
			int unitPrice = Integer.parseInt(up);
			int total = quantity * unitPrice;
			subTotal.setText("" + total);
		}
	}
	
	
	/**
	 *  Set on item selected listener for spinner (category)
	 *  filter the products of that item
	 *  send them to auto completion box
	 */
	public void setCategorySelectedListener () {
		category.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        String item_category = category.getSelectedItem().toString();
		        setAutoCompletion(item_category);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
	}
	
	public void setAutoCompletion(String category) {
		
		ArrayList <String> product_list;
		if (category.equals("Any"))
			product_list = db.getAllProductsName();
		else
			product_list = db.getCategoryBasedProductName(category);
		
		ArrayAdapter <String> productAdapter = new ArrayAdapter <String> (this, android.R.layout.simple_dropdown_item_1line, product_list);

		product_name.setThreshold(1);
		product_name.setAdapter(productAdapter);
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

}
