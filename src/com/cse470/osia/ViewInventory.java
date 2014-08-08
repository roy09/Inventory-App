package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewInventory extends Activity {

	ListView listview;
	List<String> productName = new ArrayList<String>();
	List<String> productCategory = new ArrayList<String>();
	List<String> productNormalPrice = new ArrayList<String>();
	List<String> productCostingPrice = new ArrayList<String>();
	List<String> productQuantity = new ArrayList<String>();
	
	Spinner selectCategory;
	
	String selectedCategory;
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_inventory);
		setTitle("View Inventory");
		
		db = new DatabaseHandler(this);
		
		selectCategory = (Spinner) findViewById(R.id.VIselectCategory);
		ArrayList<String> productCategories = db.getDistinctProductsCategory();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			     android.R.layout.simple_spinner_item, productCategories);
		selectCategory.setAdapter(adapter);
		
		selectCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				selectedCategory = selectCategory.getSelectedItem().toString();
		        
//				Toast stuff = Toast.makeText(getApplicationContext(), selectedCategory, Toast.LENGTH_SHORT);
//				stuff.show();
				
				setInventoryList(selectedCategory);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    	
				productName = db.getAllProductsName();
				productCategory = db.getAllProductsCategory();
				productNormalPrice = db.getAllProductsNormalPrice();
				productCostingPrice = db.getAllProductsCostingPrice();
				productQuantity = db.getAllProductsQuantity();
		    }
		});
		

		

			
	}

	private void setInventoryList(String selectedCategory) {
		productName = db.getCategoryBasedProductName(selectedCategory);
		productCategory = db.getCategoryBasedCategoryName(selectedCategory);
		productNormalPrice = db.getCategoryBasedProductNormalPrice(selectedCategory);
		productCostingPrice = db.getCategoryBasedProductCostingPrice(selectedCategory);
		productQuantity = db.getCategoryBasedProductQuantity(selectedCategory);
		
		listview = (ListView) findViewById(R.id.productList);
		listview.setAdapter(new ProductAdapter(this, productName, productCategory, productNormalPrice, productCostingPrice, productQuantity));
	}
}
