package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewInventory extends Activity {

	ListView listview;
	List<String> productName = new ArrayList<String>();
	List<String> productCategory = new ArrayList<String>();
	List<String> productNormalPrice = new ArrayList<String>();
	List<String> productCostingPrice = new ArrayList<String>();
	List<String> productQuantity = new ArrayList<String>();
	
	Context context;
	
	Spinner selectCategory;
	
	String selectedCategory;
	
	DatabaseHandler db;
	
	EditText productNameAB ;
	AutoCompleteTextView productCategoryAB;
	EditText productPriceAB;
	EditText productCostingAB;
	EditText productQuantityAB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_inventory);
		setTitle("View Inventory");
		context = this;
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
		
//		productNameAB.setText("alu");
		
		listview.setOnItemClickListener(new OnItemClickListener(){

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position,
	                long id) {
	            // TODO Auto-generated method stub
	        	Dialog dialog = new Dialog(context);
	        	dialog.setContentView(R.layout.view_inventory_alert_box);
	        	
	        	dialog.setTitle("Edit product info");
	        	
	        	// Itesm of the alert box
	        	productNameAB = (EditText) dialog.findViewById(R.id.etProductNameAB);
	        	productCategoryAB = (AutoCompleteTextView) dialog.findViewById(R.id.etCategoryAB);
	        	productPriceAB = (EditText) dialog.findViewById(R.id.etNormalPriceAB);
	        	productCostingAB = (EditText) dialog.findViewById(R.id.etCostingPriceAB);
	        	productQuantityAB = (EditText) dialog.findViewById(R.id.etQuantityAB);
	        	
	        	productNameAB.setText(productName.get(position));
	        	productCategoryAB.setText(productCategory.get(position));
	        	productPriceAB.setText(productNormalPrice.get(position));
	        	productCostingAB.setText(productCostingPrice.get(position));
	        	productQuantityAB.setText(productQuantity.get(position));
	        	
	        	
	        	dialog.show();
	        }});
	}
}
