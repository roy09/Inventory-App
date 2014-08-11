package com.cse470.osia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SeeReport extends Activity {
	String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_report);
		
		username = getIntent().getExtras().getString("username");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_report, menu);
		return true;
	}
	
	public void storeSalesReport(View v){
		Intent intent = new Intent(this, SeeSalesReport.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public void storePurchaseReport(View v) {
		Intent intent = new Intent(this, SeePurchaseReport.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	
}
