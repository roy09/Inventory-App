/**
 * 
 */
package com.cse470.osia;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author nayeem
 *
 */
public class SalesProductAdapter extends BaseAdapter {
	private Context context;
	private List<String> productName;
	private List<String> productQuantity;
	private List<String> perUnitPrice;
	private List<String> subtotal;
	private static LayoutInflater inflater = null;

	public SalesProductAdapter(Context context, List<String> productName, List<String> productQuantity,
			List<String> perUnitPrice, List<String> subtotal){
		this.context = context;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.perUnitPrice = perUnitPrice;
		this.subtotal = subtotal;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount(){
		return productName.size();
	}

	public Object getItem(int position){
		return productName.get(position);
	}

	public long getItemId(int position){
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		View vi = convertView;
		if (vi == null){
			vi = inflater.inflate(R.layout.sales_product_row, null);
		}

		// Set Product Name
		TextView productName = (TextView) vi.findViewById(R.id.listSalesProduct);
		productName.setText(this.productName.get(position));

		// Set Product quantity
		TextView productQuantity = (TextView) vi.findViewById(R.id.listSalesQuantity);
		productQuantity.setText(this.productQuantity.get(position) + " x");

		// Set Product perUnitPrice
		TextView perUnitPrice = (TextView) vi.findViewById(R.id.listSalesUnitPrice);
		perUnitPrice.setText(this.perUnitPrice.get(position));

		// Set Product subTotal price
		TextView subtotal = (TextView) vi.findViewById(R.id.listSalesSubtotal);
		subtotal.setText(this.subtotal.get(position));

		return vi;
	}

}
