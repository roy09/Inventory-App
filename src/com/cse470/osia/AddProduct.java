package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProduct extends Activity {
	
	List<String> categories = new ArrayList<String>();
	
	
	// TODO: change some var to int
	String productName;
	String productCategory;
	String productNormalPrice;
	String productCostingPrice;
	String productQuantity;
	
	AutoCompleteTextView productCategorySelect;
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		db = new DatabaseHandler(this);
		// Adding some categories
		// TODO: Add a central category db
		categories.add("Ink");
		categories.add("Paper");
		categories.add("Khata");
		categories.add("Toy");
		
		productCategorySelect = (AutoCompleteTextView) findViewById(R.id.etCategory);
		
		setAutoCompletion();
	}
	
	public void validationChecker(View view){
		EditText name = (EditText) findViewById(R.id.etProductName);
		this.productName = name.getText().toString();
		
		AutoCompleteTextView category = (AutoCompleteTextView) findViewById(R.id.etCategory);
		this.productCategory = category.getText().toString();
		
		EditText normalPrice = (EditText) findViewById(R.id.etNormalPrice);
		this.productNormalPrice = normalPrice.getText().toString();
		
		EditText costingPrice = (EditText) findViewById(R.id.etCostingPrice);
		this.productCostingPrice = costingPrice.getText().toString();
		
		EditText quantity = (EditText) findViewById(R.id.etQuantity);
		this.productQuantity = quantity.getText().toString();
		
		Log.d("LEL", this.productName + " " + this.productCategory + " " + this.productNormalPrice + " " + this.productCostingPrice + " " + this.productQuantity);
		
		Toast toast = null; 
		try{
			db.addNewProduct(new Product(productName, productCategory, productNormalPrice, productCostingPrice, productQuantity));
			toast = Toast.makeText(this, "Product successfully added", Toast.LENGTH_LONG);
			
		
		} catch(Exception e){
			toast = Toast.makeText(this, "Product couldn't be added", Toast.LENGTH_LONG);
			e.printStackTrace();
		} finally {
			toast.show();

//			Intent intent = new Intent (this, DashBoardActivity.class);
//			startActivity(intent);
			
			finish();
		}
	}
	
	public void setAutoCompletion() {
		
		ArrayList <String> categoryList;
		
		categoryList = db.getDistinctProductsCategory();
		
		
		ArrayAdapter <String> productAdapter = new ArrayAdapter <String> (this, android.R.layout.simple_dropdown_item_1line, categoryList);

		productCategorySelect.setThreshold(1);
		productCategorySelect.setAdapter(productAdapter);
	}
	
	
}
