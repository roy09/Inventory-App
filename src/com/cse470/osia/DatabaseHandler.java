package com.cse470.osia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// Database Data
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "inventory";

	// List of tables
	private static final String TABLE_PRODUCT = "product";
	
	// Table Product
	private static final String PRODUCT_ID = "id";
	private static final String PRODUCT_NAME = "productName";
	private static final String PRODUCT_CATEGORY = "productCategory";
	private static final String PRODUCT_NORMAL_PRICE = "productNormalPrice";
	private static final String PRODUCT_COSTING_PRICE = "productProductPrice";
	private static final String PRODUCT_QUANTITY = "productQuality";
	
	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" + PRODUCT_ID 
				+ " INT PRIMARY KEY, " + PRODUCT_NAME + " TEXT, " + PRODUCT_CATEGORY + " TEXT, " 
				+ PRODUCT_NORMAL_PRICE + " TEXT, " + PRODUCT_COSTING_PRICE + " TEXT, " + PRODUCT_QUANTITY + " TEXT " + ")"; 
		db.execSQL(CREATE_PRODUCT_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		
		onCreate(db);
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete Operation
	 */
	
	// Adding new product
	void addNewProduct(Product product){
		SQLiteDatabase db = this.getReadableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(PRODUCT_NAME, product.getProductName());
		values.put(PRODUCT_CATEGORY, product.getProductCategory());
		values.put(PRODUCT_NORMAL_PRICE, product.getNormalPrice());
		values.put(PRODUCT_COSTING_PRICE, product.getCostingPrice());
		values.put(PRODUCT_CATEGORY, product.getProductCategory());
		
		db.insert(TABLE_PRODUCT, null, values);
		db.close();
	}
}
