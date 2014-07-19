package com.cse470.osia;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Data
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "inventory.db";

	// List of tables
	private static final String TABLE_PRODUCT = "product";
	private static final String TABLE_ORDER_INFO = "orderInfo";
	private static final String TABLE_SALES_ORDER = "salesOder";

	// Table product
	private static final String PRODUCT_ID = "id";
	private static final String PRODUCT_NAME = "productName";
	private static final String PRODUCT_CATEGORY = "productCategory";
	private static final String PRODUCT_NORMAL_PRICE = "productNormalPrice";
	private static final String PRODUCT_COSTING_PRICE = "productProductPrice";
	private static final String PRODUCT_QUANTITY = "productQuantity";

	private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" + PRODUCT_ID 
			+ " INT PRIMARY KEY, " + PRODUCT_NAME + " TEXT, " + PRODUCT_CATEGORY + " TEXT, " 
			+ PRODUCT_NORMAL_PRICE + " TEXT, " + PRODUCT_COSTING_PRICE + " TEXT, " + PRODUCT_QUANTITY + " TEXT " + ")"; 


	// Table salesOrder
	private static final String SALES_NO = "salesNo";
	private static final String SALES_DATE = "salesDate";
	private static final String CUSTOMER_NAME = "customer";
	private static final String TOTAL_AMMOUNT = "totalAmmount";

	private static final String CREATE_SALES_ORDER_TABLE = "CREATE TABLE " + TABLE_SALES_ORDER + " (" + SALES_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CUSTOMER_NAME + " TEXT NOT NULL, " + SALES_DATE + " TEXT NOT NULL, " + TOTAL_AMMOUNT + " INT NOT NULL" + ")";


	// Table orderInfo
	private static final String ORDER_NO = "orderNo";  // Foreign key of TABLE_SALES_ORDER
	private static final String ORDER_ID = "orderId";  // Primary key of this table
	private static final String ADDED_PRODUCT = "addedProduct";
	private static final String PER_UNIT_PRICE = "perUnitPrice";
	private static final String ORDER_QUANTITY = "orderQuantity";
	private static final String SUBTOTAL_PRICE = "subtotalPrice";

	private static final String CREATE_ORDER_INFO_TABLE = "CREATE TABLE " + TABLE_ORDER_INFO + " (" + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ADDED_PRODUCT + " TEXT NOT NULL, " + PER_UNIT_PRICE + " INT, " + ORDER_QUANTITY
			+ " INT NOT NULL, " + SUBTOTAL_PRICE + " INT NOT NULL, " + ORDER_NO + " INTEGER FOREIGN KEY (" + ORDER_NO + ") REFERENCES " 
			+ TABLE_SALES_ORDER + " (" + SALES_NO + ")" +")";



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db){

		db.execSQL(CREATE_PRODUCT_TABLE);
//		db.execSQL(CREATE_ORDER_INFO_TABLE);
//		db.execSQL(CREATE_SALES_ORDER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_INFO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES_ORDER);

		onCreate(db);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	/**
	 * All CRUD(Create, Read, Update, Delete Operation
	 * FUNCTIONS OF TABLE_PRODUCT
	 */

	// Adding new product TO TABLE_PRODUCT
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


	// Get Product Names
	public ArrayList <String> getAllProductsName()
	{
		ArrayList <String> array_list = new ArrayList <String>();
		//hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =  db.rawQuery( "select * from product", null );
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false){
			array_list.add(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
			cursor.moveToNext();
		}
		
		db.close();
		return array_list;
	}
	
	// Get Product Categories
	public ArrayList <String> getAllProductsCategory()
	{
		ArrayList <String> array_list = new ArrayList <String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =  db.rawQuery( "select * from product", null );
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false){
			array_list.add(cursor.getString(cursor.getColumnIndex(PRODUCT_CATEGORY)));
			cursor.moveToNext();
		}
		
		db.close();
		return array_list;
	}
	
	// Get product normal price
	public ArrayList <String> getAllProductsNormalPrice()
	{
		ArrayList <String> array_list = new ArrayList <String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =  db.rawQuery( "select * from product", null );
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false){
			array_list.add(cursor.getString(cursor.getColumnIndex(PRODUCT_NORMAL_PRICE)));
			cursor.moveToNext();
		}
		
		db.close();
		return array_list;
	}
	
	// Get product costing
	public ArrayList <String> getAllProductsCostingPrice()
	{
		ArrayList <String> array_list = new ArrayList <String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =  db.rawQuery( "select * from product", null );
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false){
			array_list.add(cursor.getString(cursor.getColumnIndex(PRODUCT_COSTING_PRICE)));
			cursor.moveToNext();
		}
		
		db.close();
		return array_list;
	}
	
	// Get product quantity
	public ArrayList <String> getAllProductsQuantity()
	{
		ArrayList <String> array_list = new ArrayList <String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =  db.rawQuery( "select * from product", null );
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false){
			array_list.add(cursor.getString(cursor.getColumnIndex(PRODUCT_QUANTITY)));
			cursor.moveToNext();
		}
		
		db.close();
		return array_list;
	}
	
	
	

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * FUNCTIONS OF TALBE_SALES_ORDER
	 */
	
	void addNewSalesOrder (String salesDate, String customerName, int totalAmmount) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(SALES_DATE, salesDate);
		values.put(CUSTOMER_NAME, customerName);
		values.put(TOTAL_AMMOUNT, totalAmmount);
		
		db.insert(TABLE_SALES_ORDER, null, values);
		db.close();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * FUNCTIONS OF TABLE_ORDER_INFO
	 */

}
