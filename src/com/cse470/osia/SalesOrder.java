package com.cse470.osia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SalesOrder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_order);
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
			LayoutInflater li = LayoutInflater.from(this);
			View promptsView = li.inflate(R.layout.add_item_popup, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setView(promptsView);
			//final TextView textView = (TextView) promptsView.findViewById(R.id.textView1);
			//textView.setText("PopUp is working!");
			//final EditText userInput = (EditText) promptsView.findViewById(R.id.player2Input);
			alertDialogBuilder
			.setCancelable(true)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// add items to the database;

				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		default: 
			return super.onOptionsItemSelected(item);

		}

	}

}
