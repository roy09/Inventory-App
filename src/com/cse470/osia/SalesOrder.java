package com.cse470.osia;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class SalesOrder extends Activity {
	
	
	
	Button setDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_order);
		
		setDate  = (Button) findViewById(R.id.setSalesDate);
		setCurrentDate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sales_order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {

		case R.id.add_order:
			Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);
            Intent intent = new Intent(getApplicationContext(),com.cse470.osia.SalesOrderAddItem.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
            return true; 
		default: 
			return super.onOptionsItemSelected(item);

		}

	}

	
	/**
	 * set date on button
	 */
	public void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		
		setDate.setText(new StringBuilder()
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
	}

}