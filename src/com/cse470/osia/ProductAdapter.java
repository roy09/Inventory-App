package com.cse470.osia;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private Context context;
	private List<String> productName;
	private List<String> productCategory;
	private List<String> productNormalPrice;
	private List<String> productCostingPrice;
	private List<String> productQuantity;
	private static LayoutInflater inflater = null;
	
	public ProductAdapter(Context context, List<String> productName, List<String> productCategory,
			List<String> productNormalPrice, List<String> productCostingPrice, List<String> productQuantity){
		this.context = context;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productCostingPrice = productCostingPrice;
		this.productQuantity = productQuantity;
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
			vi = inflater.inflate(R.layout.product_row, null);
		}
		
		// Set Product Name
		TextView productName = (TextView) vi.findViewById(R.id.tvProductNameVI);
		productName.setText(this.productName.get(position));
		
		// Set Product Category
		TextView productCategory = (TextView) vi.findViewById(R.id.tvProductCategoryVI);
		productCategory.setText(this.productCategory.get(position));
		
		// Set Product Price
		TextView productNormalPrice = (TextView) vi.findViewById(R.id.tvProductNormalPriceVI);
		productNormalPrice.setText(this.productCategory.get(position));
		
		// Set Product Costing
		TextView productCostingPrice = (TextView) vi.findViewById(R.id.tvProductCostingPriceVI);
		productCostingPrice.setText(this.productCostingPrice.get(position));
		
		// Set Product Quantity
		TextView productQuantity = (TextView) vi.findViewById(R.id.tvProductCategoryVI);
		productQuantity.setText(this.productQuantity.get(position));
		
		return vi;
	}
}
