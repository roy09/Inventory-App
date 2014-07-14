package com.cse470.osia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProduct extends Activity {

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
		
		db.addNewProduct(new Product(productName, productCategory, productNormalPrice, productCostingPrice, productQuantity));
	}
	
	
}
