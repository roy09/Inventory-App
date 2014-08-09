package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class ViewInventory extends Activity {
	
	Dialog dialog;
	
	ListView listview;
	List<String> productName = new ArrayList<String>();
	List<String> productCategory = new ArrayList<String>();
	List<String> productNormalPrice = new ArrayList<String>();
	List<String> productCostingPrice = new ArrayList<String>();
	List<String> productQuantity = new ArrayList<String>();
	
	Context context;
	BaseAdapter adapter;
	
	Spinner selectCategory;
	
	String selectedCategory;
	
	DatabaseHandler db;
	
	EditText productNameAB ;
	AutoCompleteTextView productCategoryAB;
	EditText productPriceAB;
	EditText productCostingAB;
	EditText productQuantityAB;
	Button btnOkayAB;
	Button btnCancelAB;
	String prevProductName;
	
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
		if (selectedCategory.equals("Any")) {
			productName = db.getAllProductsName();
			productCategory = db.getAllProductsCategory();
			productNormalPrice = db.getAllProductsNormalPrice();
			productCostingPrice = db.getAllProductsCostingPrice();
			productQuantity = db.getAllProductsQuantity();
			
		}
		else {
			productName = db.getCategoryBasedProductName(selectedCategory);
			productCategory = db.getCategoryBasedCategoryName(selectedCategory);
			productNormalPrice = db.getCategoryBasedProductNormalPrice(selectedCategory);
			productCostingPrice = db.getCategoryBasedProductCostingPrice(selectedCategory);
			productQuantity = db.getCategoryBasedProductQuantity(selectedCategory);
		}
		listview = (ListView) findViewById(R.id.productList);
		adapter  = new ProductAdapter(this, productName, productCategory, productNormalPrice, productCostingPrice, productQuantity);
		listview.setAdapter(adapter);
		
//		productNameAB.setText("alu");
		
		listview.setOnItemClickListener(new OnItemClickListener(){

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position,
	                long id) {
	        	

	        	
	        	
	        	LayoutInflater inflater = getLayoutInflater();
	        	View dialoglayout = inflater.inflate(R.layout.view_inventory_alert_box, null);
	        	
	        	// Itesm of the alert box
	        	productNameAB = (EditText) dialoglayout.findViewById(R.id.etProductNameAB);
	        	productCategoryAB = (AutoCompleteTextView) dialoglayout.findViewById(R.id.etCategoryAB);
	        	productPriceAB = (EditText) dialoglayout.findViewById(R.id.etNormalPriceAB);
	        	productCostingAB = (EditText) dialoglayout.findViewById(R.id.etCostingPriceAB);
	        	productQuantityAB = (EditText) dialoglayout.findViewById(R.id.etQuantityAB);
	        	
	        	productNameAB.setText(productName.get(position));
	        	productCategoryAB.setText(productCategory.get(position));
	        	productPriceAB.setText(productNormalPrice.get(position));
	        	productCostingAB.setText(productCostingPrice.get(position));
	        	productQuantityAB.setText(productQuantity.get(position));
	        	
	        	prevProductName = productNameAB.getText().toString();
	        	
	        	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        	builder.setView(dialoglayout);
	        	builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int id) {
	        	    	String productNameX = productNameAB.getText().toString();
	        	    	String productCateogryX = productCategoryAB.getText().toString();
	        	    	String productPriceX = productPriceAB.getText().toString();
	        	    	String productCostingX = productCostingAB.getText().toString();
	        	    	String productQuantityX = productQuantityAB.getText().toString();
	        	        db.updateProductInfo(prevProductName, productNameX, productCateogryX, productPriceX, productCostingX, productQuantityX);
	        	        
	        	        //Dataset Notify didn't work :/
//	        	        adapter.notifyDataged();
//	        	        ((BaseAdapter)listview.getAdapter()).notifyDataSetChanged();
	        	        Intent intent = new Intent(context, ViewInventory.class);
	        	        startActivity(intent);
	        	        finish();
	        	     }
	        	});
	        	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int id) {
	        	        
	        	     }
	        	});
	        	builder.show();
//	        	dialog = new Dialog(context);
//	        	dialog.setContentView(R.layout.view_inventory_alert_box);
//	        	
//	        	dialog.setTitle("Edit product info");
//	        	

//	        	
//	        	btnCancelAB = (Button) dialog.findViewById(R.id.btnCancelAB);
//	        	
//	        	
//	        	
//	        	
//	        	btnOkayAB = (Button) dialog.findViewById(R.id.btnOkayAB);
//	        	btnOkayAB.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//					}
//				});
//	        	
//	        	
//	        	dialog.show();
	        }});
		
	}
	
	
}
