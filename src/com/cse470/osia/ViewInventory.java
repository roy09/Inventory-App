package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewInventory extends Activity {

	ListView listview;
	List<String> productList = new ArrayList<String>();
	
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_inventory);
		
		productList = db.getAllProducts();
		
		
		

		
		listview = (ListView) findViewById(R.id.productList);
//		listview.setAdapter(new ProductAdapter(this, productList));
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productList);
		listview.setAdapter(adapter);
		
	}
}
