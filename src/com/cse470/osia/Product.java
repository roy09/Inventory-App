package com.cse470.osia;

public class Product {
	int productID;
	String productName;
	String productCategory;
	String productNormalPrice;
	String productCostingPrice;
	String productQuantity;
	
	public Product(){
		
	}
	
	public Product(String productName, String productCategory, String productNormalPrice, String productCostingPrice, String productQuantity){
		this.productName = productName;
		this.productCategory = productCategory;
		this.productNormalPrice = productNormalPrice;
		this.productCostingPrice = productCostingPrice;
		this.productQuantity = productQuantity;
	}
	
	public int getProductID(){
		return productID;
	}
	
	public void setProductID(int id){
		productID = id;
	}
	
	public String getProductName(){
		return productName;
	}
	
	public void setProductName(String name){
		productName = name;
	}
	
	public String getProductCategory(){
		return productCategory;
	}
	
	public void setProductCategory(String category){
		productCategory = category;
	}
	
	public String getNormalPrice(){
		return productNormalPrice;
	}
	
	public void setNormalPrice(String price){
		productNormalPrice = price;
	}
	
	public String getCostingPrice(){
		return productCostingPrice;
	}
	
	public void setCostingPrice(String price){
		productCostingPrice = price;
	}
	
	public String getQuantity(){
		return productQuantity;
	}
	
	public void setQuantity(String quantity){
		productQuantity = quantity;
	}
}
