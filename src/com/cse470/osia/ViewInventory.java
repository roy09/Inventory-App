package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewInventory extends Activity {

	ListView listview;
	List<String> productName = new ArrayList<String>();
	List<String> productCategory = new ArrayList<String>();
	List<String> productNormalPrice = new ArrayList<String>();
	List<String> productCostingPrice = new ArrayList<String>();
	List<String> productQuantity = new ArrayList<String>();
	
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_inventory);
		db = new DatabaseHandler(this);
		productName = db.getAllProductsName();
		productCategory = db.getAllProductsCategory();
		productNormalPrice = db.getAllProductsNormalPrice();
		productCostingPrice = db.getAllProductsCostingPrice();
		productQuantity = db.getAllProductsCategory();
		

		
		listview = (ListView) findViewById(R.id.productList);
		listview.setAdapter(new ProductAdapter(this, productName, productCategory, productNormalPrice, productCostingPrice, productQuantity));
		
		
	}
}
