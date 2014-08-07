package com.cse470.osia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
	private static final String TABLE_SALES_ORDER_INFO = "salesOrderInfo";
	private static final String TABLE_SALES_ORDER = "salesOrder";
	private static final String TABLE_DEALER = "dealerTable";

	// Table product
	private static final String PRODUCT_ID = "id";
	private static final String PRODUCT_NAME = "productName";
	private static final String PRODUCT_CATEGORY = "productCategory";
	private static final String PRODUCT_NORMAL_PRICE = "productNormalPrice";
	private static final String PRODUCT_COSTING_PRICE = "productProductPrice";
	private static final String PRODUCT_QUANTITY = "productQuantity";

	private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PRODUCT
			+ " ("
			+ PRODUCT_ID
			+ " INT PRIMARY KEY, "
			+ PRODUCT_NAME
			+ " TEXT, "
			+ PRODUCT_CATEGORY
			+ " TEXT, "
			+ PRODUCT_NORMAL_PRICE
			+ " TEXT, "
			+ PRODUCT_COSTING_PRICE
			+ " TEXT, " + PRODUCT_QUANTITY + " TEXT " + ")";

	// Table salesOrder
	private static final String SALES_NO = "salesNo";
	private static final String SALES_DATE = "salesDate";
	private static final String CUSTOMER_NAME = "customer";
	private static final String TOTAL_AMMOUNT = "totalAmmount";

	private static final String CREATE_SALES_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_SALES_ORDER
			+ " ("
			+ SALES_NO
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CUSTOMER_NAME
			+ " TEXT NOT NULL, "
			+ SALES_DATE
			+ " TEXT NOT NULL, "
			+ TOTAL_AMMOUNT + " INT NOT NULL" + ")";

	// Table orderInfo
	private static final String ORDER_NO = "orderNo"; // Foreign key of
														// TABLE_SALES_ORDER
														// references salesNo
	private static final String ORDER_ID = "orderId"; // Primary key of this
														// table
	private static final String ADDED_PRODUCT = "addedProduct";
	private static final String ADDED_PRODUCT_CATEGORY = "addedProductCategory";
	private static final String PER_UNIT_PRICE = "perUnitPrice";
	private static final String ORDER_QUANTITY = "orderQuantity";
	private static final String SUBTOTAL_PRICE = "subtotalPrice";

	private static final String CREATE_SALES_ORDER_INFO_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_SALES_ORDER_INFO
			+ " ("
			+ ORDER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ADDED_PRODUCT
			+ " TEXT NOT NULL, "
			+ PER_UNIT_PRICE
			+ " INT, "
			+ ORDER_QUANTITY
			+ " INT NOT NULL, "
			+ SUBTOTAL_PRICE
			+ " INT NOT NULL, "
			+ ORDER_NO
			+ " INTEGER REFERENCES "
			+ TABLE_SALES_ORDER
			+ " ("
			+ SALES_NO
			+ ")" + ")";

	// Table dealer
	private static final String DEALER_NAME = "dealerName";
	private static final String DEALER_PHONE = "dealerPhone";
	private static final String DEALER_EMAIL = "dealerEmail";
	private static final String DEALER_ADDRESS = "dealerAddress";
	
	private static final String CREATE_DEALER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_DEALER
			+ " ("
			+ DEALER_NAME
			+ " TEXT PRIMARY KEY, "
			+ DEALER_PHONE
			+ " TEXT, "
			+ DEALER_EMAIL
			+ " TEXT, "
			+ DEALER_ADDRESS
			+ " TEXT " + ")";

	
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_PRODUCT_TABLE);
		db.execSQL(CREATE_SALES_ORDER_TABLE);
		db.execSQL(CREATE_SALES_ORDER_INFO_TABLE);
		db.execSQL(CREATE_DEALER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES_ORDER_INFO);

		onCreate(db);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * All CRUD(Create, Read, Update, Delete Operation FUNCTIONS OF
	 * TABLE_PRODUCT
	 */

	// Adding new product TO TABLE_PRODUCT
	void addNewProduct(Product product) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put(PRODUCT_NAME, product.getProductName());
		values.put(PRODUCT_CATEGORY, product.getProductCategory());
		values.put(PRODUCT_NORMAL_PRICE, product.getNormalPrice());
		values.put(PRODUCT_COSTING_PRICE, product.getCostingPrice());
		values.put(PRODUCT_QUANTITY, product.getQuantity());

		db.insert(TABLE_PRODUCT, null, values);
		db.close();
	}

	// Get Product Names
	public ArrayList<String> getAllProductsName() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from product", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list
					.add(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get category based product name
	public ArrayList<String> getCategoryBasedProductName(String category) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from product where productCategory = \"" + category
						+ "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list
					.add(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get Product Categories
	public ArrayList<String> getAllProductsCategory() {
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from product", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_CATEGORY)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get product normal price
	public ArrayList<String> getAllProductsNormalPrice() {
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from product", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_NORMAL_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get a product's unit price
	public String getUnitPriceOfProduct(String product) {
		String price;
		Queue<String> array_list = new LinkedList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.rawQuery("select * from product where productName = \""
						+ product + "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_NORMAL_PRICE)));
			cursor.moveToNext();
		}
		price = array_list.poll();
		return price;
	}

	// Get product costing
	public ArrayList<String> getAllProductsCostingPrice() {
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from product", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_COSTING_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get all products quantity
	public ArrayList<String> getAllProductsQuantity() {
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from product", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_QUANTITY)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * FUNCTIONS OF TALBE_SALES_ORDER
	 */

	void addNewSalesOrder(String salesDate, String customerName,
			int totalAmmount) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put(SALES_DATE, salesDate);
		values.put(CUSTOMER_NAME, customerName);
		values.put(TOTAL_AMMOUNT, totalAmmount);

		db.insert(TABLE_SALES_ORDER, null, values);
		db.close();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * CRUD for TABLE DEALER
	 */
	
	void addNewDealer(String dealerName, String dealerPhone, String dealerEmail, String dealerAddress){
		SQLiteDatabase db = this.getReadableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DEALER_NAME, dealerName);
		values.put(DEALER_PHONE, dealerPhone);
		values.put(DEALER_EMAIL, dealerAddress);
		values.put(DEALER_ADDRESS, dealerAddress);
		
		db.insert(TABLE_DEALER, null, values);
		db.close();
	}
	
	public ArrayList<String> getAllDealerName() {
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_DEALER, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(DEALER_NAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	
	public ArrayList<String> getEverythingAboutDealer(String dealerName){
		ArrayList<String> dealerInfo = new ArrayList<String>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_DEALER + " where " + DEALER_NAME + " = " +  '"' + dealerName + '"', null);
		cursor.moveToFirst();
		
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_NAME)));
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_PHONE)));
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_EMAIL)));
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_ADDRESS)));
//		while (cursor.isAfterLast() == false) {
//			dealerInfo[count] = cursor.getString(cursor
//					.getColumnIndex(infos[count]));
//			cursor.moveToNext();
//			count++;
//		}
		
		db.close();
		return dealerInfo;
	}
	/**
	 * FUNCTIONS OF TABLE_SALES_ORDER_INFO ADDED_PRODUCT ADDED_PRODUCT_CATEGORY
	 * PER_UNIT_PRICE ORDER_QUANTITY SUBTOTAL_PRICE ORDER_ID +
	 * " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADDED_PRODUCT +
	 * " TEXT NOT NULL, " + PER_UNIT_PRICE + " INT, " + ORDER_QUANTITY +
	 * " INT NOT NULL, " + SUBTOTAL_PRICE + " INT NOT NULL, " + ORDER_NO +
	 * " INTEGER REFERENCES " + TABLE_SALES_ORDER + " (" + SALES_NO + ")" +")
	 */

	void addNewItemSalesOrder(String productName, int productUnitPrice,
			int productQuantity, int subtotalPrice) {
		// SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ADDED_PRODUCT, productName);
		values.put(PER_UNIT_PRICE, productUnitPrice);
		values.put(ORDER_QUANTITY, productQuantity);
		values.put(SUBTOTAL_PRICE, subtotalPrice);
		values.put(ORDER_NO, getSalesOrderNo());

		SQLiteDatabase db = this.getReadableDatabase();
		db.insert(TABLE_SALES_ORDER_INFO, null, values);
		db.close();
	}

	/**
	 * remove a sales added item
	 */

	public void removeSalesAddedItem(String product) {
		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();

		db.delete(TABLE_SALES_ORDER_INFO, ORDER_NO + " = ? and "
				+ ADDED_PRODUCT + " = ?",
				new String[] { salesNo + "", product });

		db.close();
	}

	/**
	 * remove all added product of a sales order
	 */

	public void removeAllSalesAddedProduct() {
		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		// db.execSQL( "delete from " + TABLE_SALES_ORDER_INFO + " where "
		// + ORDER_NO + " = " + salesNo, null );

		db.delete(TABLE_SALES_ORDER_INFO, ORDER_NO + " = ?",
				new String[] { (salesNo + "") });

		db.close();

	}

	/**
	 * getAllSalesAddedProductName()
	 * 
	 * @return arrayList <String>
	 */
	public ArrayList<String> getAllSalesAddedProductName() {
		ArrayList<String> array_list = new ArrayList<String>();

		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER_INFO
				+ " where " + ORDER_NO + " = " + salesNo, null);

		array_list.add("Item");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(ADDED_PRODUCT)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	/**
	 * getAllSalesAddedProductQuantity()
	 * 
	 * @return arrayList <Integer>
	 */
	public ArrayList<String> getAllSalesAddedProductQuantity() {
		ArrayList<String> array_list = new ArrayList<String>();

		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER_INFO
				+ " where " + ORDER_NO + " = " + salesNo, null);

		array_list.add("Quantity");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(ORDER_QUANTITY)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	/**
	 * getAllSalesAddedProductUnitPrice()
	 * 
	 * @return arrayList <Integer>
	 */
	public ArrayList<String> getAllSalesAddedProductUnitPrice() {
		ArrayList<String> array_list = new ArrayList<String>();

		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER_INFO
				+ " where " + ORDER_NO + " = " + salesNo, null);

		array_list.add("Price");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PER_UNIT_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	/**
	 * getAllSalesAddedProductSubtotalPrice()
	 * 
	 * @return arrayList <Integer>
	 */
	public ArrayList<String> getAllSalesAddedProductSubtotalPrice() {
		ArrayList<String> array_list = new ArrayList<String>();

		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER_INFO
				+ " where " + ORDER_NO + " = " + salesNo, null);

		array_list.add("Total");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(SUBTOTAL_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	public int getNetPayable() {
		int salesNo = getSalesOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select sum (" + SUBTOTAL_PRICE + ") from "
				+ TABLE_SALES_ORDER_INFO + " where " + ORDER_NO + " = "
				+ salesNo, null);

		cursor.moveToFirst();
		int total = cursor.getInt(0);
		db.close();
		return total;
	}

	int getSalesOrderNo() {
		int fk = 0;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select count(*) from " + TABLE_SALES_ORDER, null);

		cursor.moveToFirst();
		fk = cursor.getInt(0) + 1;

		db.close();
		return fk;
	}

}
