package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SalesOrderAddItem extends Activity {

	List<String> categories = new ArrayList<String>();

	String productName;
	String productCategory;
	String productUnitPrice; // parse to integer
	String productQuantity;  // parse to integer
	
	AutoCompleteTextView product_name;
	EditText product_quantity;
	TextView unit_price;
	TextView subTotal;
	Spinner category;
	Button add_item;
	
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_order_add_item);

		
		product_quantity = (EditText) findViewById(R.id.etAddItemProductQuantity);
		unit_price = (TextView) findViewById(R.id.tvAddItemProductQuantity);
		subTotal = (TextView) findViewById(R.id.tvAddItemTotalPrice);		
		category = (Spinner) findViewById(R.id.spAddItemCategory);
		add_item = (Button) findViewById(R.id.btnAddItem);
		
		// constructing Spinner;
		// product categories
		categories.add("Ink");
		categories.add("Paper");
		categories.add("Khata");
		categories.add("Toy");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(dataAdapter);
		
		
		
		// managing auto completion
		db = new DatabaseHandler (this);
		ArrayList <String> product_list = db.getAllProductsName();
		
		ArrayAdapter <String> productAdapter = new ArrayAdapter <String> (this, android.R.layout.simple_dropdown_item_1line, product_list);
		product_name = (AutoCompleteTextView) findViewById(R.id.autoCompleteAddItemProductName);

		product_name.setThreshold(1);
		product_name.setAdapter(productAdapter);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sales_order_add_item, menu);
		return true;
	}

}
