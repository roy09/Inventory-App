package com.cse470.osia;

public class Product {
	String productName;
	String productCategory;
	String productNormalPrice;
	String productCostingPrice;
	String productQuantity;
	
	public Product(String productName, String productCategory, String productNormalPrice, String productCostingPrice, String productQuantity){
		this.productName = productName;
		this.productCategory = productCategory;
		this.productNormalPrice = productNormalPrice;
		this.productCostingPrice = productCostingPrice;
		this.productQuantity = productQuantity;
	}
	
	public String getProductName(){
		return productName;
	}
	
	public String getProductCategory(){
		return productCategory;
	}
	
	public String getNormalPrice(){
		return productNormalPrice;
	}
	
	public String getCostingPrice(){
		return productCostingPrice;
	}
	
	public String getQuantity(){
		return productQuantity;
	}
}
