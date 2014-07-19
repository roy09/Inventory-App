package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
	
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		
		// Adding some categories
		// TODO: Add a central category db
		categories.add("Ink");
		categories.add("Paper");
		categories.add("Khata");
		categories.add("Toy");
		
		Spinner category = (Spinner) findViewById(R.id.spCategory);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		category.setAdapter(dataAdapter);
	}
	
	public void validationChecker(View view){
		EditText name = (EditText) findViewById(R.id.etProductName);
		this.productName = name.getText().toString();
		
		Spinner category = (Spinner) findViewById(R.id.spCategory);
		this.productCategory = category.toString();
		
		EditText normalPrice = (EditText) findViewById(R.id.etNormalPrice);
		this.productNormalPrice = normalPrice.getText().toString();
		
		EditText costingPrice = (EditText) findViewById(R.id.etCostingPrice);
		this.productCostingPrice = costingPrice.getText().toString();
		
		EditText quantity = (EditText) findViewById(R.id.etQuantity);
		this.productQuantity = quantity.getText().toString();
		
//		Log.d("LEL", this.productName + " " + this.productCategory + " " + this.productNormalPrice + " " + this.productCostingPrice + " " + this.productQuantity);
		
		Toast toast = null; 
		try{
			db.addNewProduct(new Product(productName, productCategory, productNormalPrice, productCostingPrice, productQuantity));
			toast = Toast.makeText(this, "Product successfully added", Toast.LENGTH_LONG);
			
		
		} catch(Exception e){
			toast = Toast.makeText(this, "Product couldn't be added", Toast.LENGTH_LONG);
		} finally {
			toast.show();

			Intent intent = new Intent (this, DashBoardActivity.class);
			startActivity(intent);
		}
	}
	
	
}
