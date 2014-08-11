package com.cse470.osia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Data
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "inventory.db";

	// List of tables
	private static final String TABLE_PRODUCT = "product";
	private static final String TABLE_SALES_ORDER_INFO = "salesOrderInfo";
	private static final String TABLE_SALES_ORDER = "salesOrder";
	private static final String TABLE_DEALER = "dealerTable";
	private static final String TABLE_PURCHASE_ORDER = "purchaseOrder";
	private static final String TABLE_PURCHASE_ORDER_INFO = "purchaseOrderInfo";
	private static final String TABLE_SALES_ORDER_TRACK = "salesOrderTrack";
	private static final String TABLE_PURCHASE_ORDER_TRACK = "purchaseOrderTrack";
	private static final String TABLE_USER_INFO = "userInfo";

	// Table userInfo
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "name";
	private static final String USER_USERNAME = "username";
	private static final String USER_PASSWORD = "password";
	private static final String USER_EMAIL = "email";
	private static final String USER_PHONE = "phone";

	private static final String CREATE_USER_INFO_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_USER_INFO
			+ " ("
			+ USER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ USER_NAME
			+ " TEXT NOT NULL, "
			+ USER_USERNAME
			+ " TEXT NOT NULL, "
			+ USER_PASSWORD
			+ " TEXT NOT NULL, "
			+ USER_EMAIL
			+ " TEXT NOT NULL, " + USER_PHONE + " TEXT NOT NULL " + ")";

	// Table PURCHASEOrder
	private static final String PURCHASE_NO = "purchaseNo";
	private static final String PURCHASE_DATE = "purchaseDate";
	private static final String PURCHASE_DEALER_NAME = "customer";
	private static final String TOTAL_AMMOUNT = "totalAmmount";

	private static final String CREATE_PURCHASE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PURCHASE_ORDER
			+ " ("
			+ PURCHASE_NO
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ PURCHASE_DEALER_NAME
			+ " TEXT NOT NULL, "
			+ PURCHASE_DATE
			+ " TEXT NOT NULL, "
			+ TOTAL_AMMOUNT + " INT NOT NULL" + ")";

	// Table purchaseOrderInfo
	private static final String ORDER_NO = "purchaseNo"; // Foreign key of TABLE_SALES_ORDER
	// references salesNo
	private static final String ORDER_ID = "purchaseId"; // Primary key of this
	// table
	private static final String PURCHASE_ADDED_PRODUCT = "addedProduct";
	private static final String PURCHASE_ADDED_PRODUCT_CATEGORY = "addedProductCategory";
	private static final String PURCHASE_PER_UNIT_PRICE = "perUnitPrice";
	private static final String PURCHASE_ORDER_QUANTITY = "orderQuantity";
	private static final String PURCHASE_SUBTOTAL_PRICE = "subtotalPrice";
	private static final String PURCHASE_ORDER_INFO_MRP = "mrp";

	private static final String CREATE_PURCHASE_ORDER_INFO_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PURCHASE_ORDER_INFO
			+ " ("
			+ ORDER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ PURCHASE_ADDED_PRODUCT
			+ " TEXT NOT NULL, "
			+ PURCHASE_ADDED_PRODUCT_CATEGORY
			+ " TEXT NOT NULL, "
			+ PURCHASE_PER_UNIT_PRICE
			+ " INT, "
			+ PURCHASE_ORDER_QUANTITY
			+ " INT NOT NULL, "
			+ PURCHASE_SUBTOTAL_PRICE
			+ " INT NOT NULL, "
			+ PURCHASE_ORDER_INFO_MRP
			+ " INT NOT NULL, "
			+ ORDER_NO
			+ " INTEGER REFERENCES "
			+ TABLE_PURCHASE_ORDER
			+ " ("
			+ PURCHASE_NO
			+ ")" + ")";



	// Table product
	private static final String PRODUCT_ID = "id";
	private static final String PRODUCT_NAME = "productName";
	private static final String PRODUCT_CATEGORY = "productCategory";
	private static final String PRODUCT_NORMAL_PRICE = "productNormalPrice";
	private static final String PRODUCT_COSTING_PRICE = "productProductPrice";
	private static final String PRODUCT_QUANTITY = "productQuantity";
	private static final String PRODUCT_SUBTOTAL = "productSubtotal";
	private static final String PURCHASE_ID = "purchase_id"; //foreign key of table purchase order

	private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PRODUCT
			+ " ("
			+ PRODUCT_ID
			+ " INT PRIMARY KEY, "
			+ PRODUCT_NAME
			+ " TEXT NOT NULL, "
			+ PRODUCT_CATEGORY
			+ " TEXT NOT NULL, "
			+ PRODUCT_NORMAL_PRICE
			+ " TEXT NOT NULL, "
			+ PRODUCT_COSTING_PRICE
			+ " TEXT, " 
			+ PRODUCT_QUANTITY + " TEXT "
			//			+ PRODUCT_SUBTOTAL + ""
			//			//FOREIGN KEY CONSTRAINT
			//			+ PURCHASE_ID 
			//			+ " INTEGER REFERENCES "
			//			+ TABLE_PURCHASE_ORDER
			//			+ " (" + PURCHASE_NO+ ")" 
			+ ")";

	// Table salesOrder
	private static final String SALES_NO = "salesNo";
	private static final String SALES_DATE = "salesDate";
	private static final String CUSTOMER_NAME = "customer";		//WRITTEN BEFORE
	//	private static final String TOTAL_AMMOUNT = "totalAmmount"; //SAME

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
	//	private static final String ORDER_NO = "orderNo"; // Foreign key of TABLE_SALES_ORDER
	// references salesNo
	//	private static final String ORDER_ID = "orderId"; // Primary key of this
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
	private static final String DEALER_ID = "dealerId";
	private static final String DEALER_NAME = "dealerName";
	private static final String DEALER_PHONE = "dealerPhone";
	private static final String DEALER_EMAIL = "dealerEmail";
	private static final String DEALER_ADDRESS = "dealerAddress";

	private static final String CREATE_DEALER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_DEALER
			+ " ("
			+ DEALER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DEALER_NAME
			+ " TEXT NOT NULL, "
			+ DEALER_PHONE
			+ " TEXT, "
			+ DEALER_EMAIL
			+ " TEXT, "
			+ DEALER_ADDRESS + " TEXT " + ")";

	// Table Sales Order Track
	private static final String SALES_ORDER_TRACK_ID = "salesTrackId";
	private static final String SALES_ORDER_TRACK_DATE = "salesDate";
	private static final String SALES_ORDER_TRACK_NAME_OF_PRODUCT = "salesNameOfProduct";
	private static final String SALES_ORDER_TRACK_SOLD_TO = "salesSoldTo";
	private static final String SALES_ORDER_TRACK_QUANTITY = "salesQuantity";
	private static final String SALES_ORDER_TRACK_PROFIT = "salesProfit";

	private static final String CREATE_SALES_ORDER_TRACK_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_SALES_ORDER_TRACK
			+ " ("
			+ SALES_ORDER_TRACK_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SALES_ORDER_TRACK_DATE
			+ " TEXT NOT NULL, "
			+ SALES_ORDER_TRACK_NAME_OF_PRODUCT
			+ " TEXT, "
			+ SALES_ORDER_TRACK_SOLD_TO
			+ " TEXT, "
			+ SALES_ORDER_TRACK_QUANTITY
			+ " TEXT, "
			+ SALES_ORDER_TRACK_PROFIT
			+ " TEXT " + ")";

	// Table Purchase Order Track
	private static final String PURCHASE_ORDER_TRACK_ID = "purchaseTrackId";
	private static final String PURCHASE_ORDER_TRACK_DATE = "purchaseDate";
	private static final String PURCHASES_ORDER_TRACK_NAME_OF_PRODUCT = "purchaseNameOfProduct";
	private static final String PURCHASE_ORDER_TRACK_SOLD_TO = "purchaseSoldTo";
	private static final String PURCHASE_ORDER_TRACK_QUANTITY = "purchaseQuantity";
	private static final String PURCHASE_ORDER_TRACK_PROFIT = "purchaseProfit";

	private static final String CREATE_PURCHASE_ORDER_TRACK_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PURCHASE_ORDER_TRACK
			+ " ("
			+ PURCHASE_ORDER_TRACK_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ PURCHASE_ORDER_TRACK_DATE
			+ " TEXT NOT NULL, "
			+ PURCHASES_ORDER_TRACK_NAME_OF_PRODUCT
			+ " TEXT, "
			+ PURCHASE_ORDER_TRACK_SOLD_TO
			+ " TEXT, "
			+ PURCHASE_ORDER_TRACK_QUANTITY
			+ " TEXT, "
			+ PURCHASE_ORDER_TRACK_PROFIT + " TEXT " + ")";

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_USER_INFO_TABLE);
		db.execSQL(CREATE_PURCHASE_ORDER_TABLE);
		db.execSQL(CREATE_PURCHASE_ORDER_INFO_TABLE);
		db.execSQL(CREATE_PRODUCT_TABLE);
		db.execSQL(CREATE_SALES_ORDER_TABLE);
		db.execSQL(CREATE_SALES_ORDER_INFO_TABLE);
		db.execSQL(CREATE_DEALER_TABLE);
		db.execSQL(CREATE_SALES_ORDER_TRACK_TABLE);
		db.execSQL(CREATE_PURCHASE_ORDER_TRACK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE_ORDER_INFO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES_ORDER_INFO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEALER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES_ORDER_TRACK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE_ORDER_TRACK);

		onCreate(db);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * ALL THE FUNCTIONS OF TABLE_USER_INFO
	 */
	// add new user
	public void addNewUser(String name, String username, String password,
			String email, String phone) {
		// SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, name);
		values.put(USER_USERNAME, username);
		values.put(USER_PASSWORD, password);
		values.put(USER_EMAIL, email);
		values.put(USER_PHONE, phone);

		SQLiteDatabase db = this.getReadableDatabase();
		db.insert(TABLE_USER_INFO, null, values);
		db.close();
	}

	// get info of a specific user
	public ArrayList <String> getUserInfo(String username) {
		ArrayList <String> array_list = new ArrayList <String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =  db.rawQuery( "select * from " + TABLE_USER_INFO
				+ " where " + USER_USERNAME + " = '"+ username + "'", null );

		cursor.moveToFirst();
		array_list.add(cursor.getString(cursor.getColumnIndex(USER_NAME)));			
		array_list.add(cursor.getString(cursor.getColumnIndex(USER_USERNAME)));
		array_list.add(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
		array_list.add(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
		array_list.add(cursor.getString(cursor.getColumnIndex(USER_PHONE)));

		db.close();
		return array_list;
	}

	// get password of a specific user
	public String getUserPassword(String username) {
		String password;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select " + USER_PASSWORD + " from "
				+ TABLE_USER_INFO + " where " + USER_USERNAME + " = '"
				+ username + "'", null);

		cursor.moveToFirst();
		password = cursor.getString(0);

		db.close();
		return password;
	}

	// get all the username(s)
	public ArrayList<String> getAllUsernames() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_USER_INFO, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(USER_USERNAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	/**
	 * remove a user
	 */

	public void deleteUser(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		db.delete(TABLE_USER_INFO, USER_USERNAME + " = ?",
				new String[] {username});

		db.close();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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


	// Get all Distinct categories
	public ArrayList<String> getDistinctProductsCategory() {
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct " + PRODUCT_CATEGORY
				+ " from product", null);
		cursor.moveToFirst();
		array_list.add("Any");
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_CATEGORY)));
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

	// Get a product's unit costing
	public String getUnitCostingOfProduct(String product) {
		String price;
		Queue<String> array_list = new LinkedList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.rawQuery("select * from product where productName = \""
						+ product + "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PRODUCT_COSTING_PRICE)));
			cursor.moveToNext();
		}
		price = array_list.poll();
		return price;
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

	// get quantity of a specific product

	public int getProductQuantity(String productName) {
		int quantity;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select " + PRODUCT_QUANTITY + " from "
				+ TABLE_PRODUCT + " where " + PRODUCT_NAME + " = '"
				+ productName + "'", null);

		cursor.moveToFirst();
		quantity = Integer.parseInt(cursor.getString(0));

		db.close();
		return quantity;
	}

	// Category based updates

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

	// Get category based product category
	public ArrayList<String> getCategoryBasedCategoryName(String category) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from product where productCategory = \"" + category
				+ "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PRODUCT_CATEGORY)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get category based product normal price
	public ArrayList<String> getCategoryBasedProductNormalPrice(String category) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from product where productCategory = \"" + category
				+ "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PRODUCT_NORMAL_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get category based product costing price
	public ArrayList<String> getCategoryBasedProductCostingPrice(String category) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from product where productCategory = \"" + category
				+ "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PRODUCT_COSTING_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Get category based product Quantity
	public ArrayList<String> getCategoryBasedProductQuantity(String category) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from product where productCategory = \"" + category
				+ "\"", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PRODUCT_QUANTITY)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// Update any product row "_id=" + Id;
	public void updateProductInfo(String prevProductName, String productName, String productCategory, String productPrice, String productCosting, String productQuantity){
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues data = new ContentValues();
		data.put(PRODUCT_NAME, productName);
		data.put(PRODUCT_CATEGORY, productCategory);
		data.put(PRODUCT_NORMAL_PRICE, productPrice);
		data.put(PRODUCT_COSTING_PRICE, productCosting);
		data.put(PRODUCT_QUANTITY, productQuantity);

		db.update(TABLE_PRODUCT, data, PRODUCT_NAME + " = " + '"' + prevProductName + '"', null);
		db.close();

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	void addNewItemPurchaseOrder(String productName, String category, int productUnitPrice,
			int productQuantity, int subtotalPrice, int mrp) {
		// SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PURCHASE_ADDED_PRODUCT, productName);
		values.put(PURCHASE_ADDED_PRODUCT_CATEGORY,category);
		values.put(PURCHASE_PER_UNIT_PRICE, productUnitPrice);
		values.put(PURCHASE_ORDER_QUANTITY, productQuantity);
		values.put(PURCHASE_SUBTOTAL_PRICE, subtotalPrice);
		values.put(PURCHASE_NO, getSalesOrderNo());
		values.put(PURCHASE_ORDER_INFO_MRP, mrp);
		values.put(ORDER_NO, getPurchaseOrderNo());

		SQLiteDatabase db = this.getReadableDatabase();
		db.insert(TABLE_PURCHASE_ORDER_INFO, null, values);
		db.close();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	public void removePurchaseAddedItem(String product) {
		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();

		db.delete(TABLE_PURCHASE_ORDER_INFO, PURCHASE_NO + " = ? and "
				+ PURCHASE_ADDED_PRODUCT + " = ?",
				new String[] { purchaseNo + "", product });

		db.close();
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	public void removeAllPurchaseAddedProduct() {
		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		// db.execSQL( "delete from " + TABLE_SALES_ORDER_INFO + " where "
		// + ORDER_NO + " = " + salesNo, null );

		db.delete(TABLE_PURCHASE_ORDER_INFO, PURCHASE_NO + " = ?",
				new String[] { (purchaseNo + "") });

		db.close();

	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	public ArrayList<String> getAllPurchaseAddedProductName() {
		ArrayList<String> array_list = new ArrayList<String>();

		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE_ORDER_INFO
				+ " where " + PURCHASE_NO + " = " + purchaseNo, null);

		array_list.add("Item");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PURCHASE_ADDED_PRODUCT)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	public ArrayList<String> getAllPurchaseAddedProductQuantity() {
		ArrayList<String> array_list = new ArrayList<String>();

		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE_ORDER_INFO
				+ " where " + PURCHASE_NO + " = " + purchaseNo, null);

		array_list.add("Quantity");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PURCHASE_ORDER_QUANTITY)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllPurchaseAddedProductCategory() {
		ArrayList<String> array_list = new ArrayList<String>();

		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE_ORDER_INFO
				+ " where " + PURCHASE_NO + " = " + purchaseNo, null);

		array_list.add("category");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PURCHASE_ADDED_PRODUCT_CATEGORY)));

			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllPurchaseAddedProductNormalPrice() {
		ArrayList<String> array_list = new ArrayList<String>();

		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE_ORDER_INFO
				+ " where " + PURCHASE_NO + " = " + purchaseNo, null);

		array_list.add("mrp");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PURCHASE_ORDER_INFO_MRP)));

			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	public ArrayList<String> getAllPurchaseAddedProductUnitPrice() {
		ArrayList<String> array_list = new ArrayList<String>();

		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE_ORDER_INFO
				+ " where " + PURCHASE_NO + " = " + purchaseNo, null);

		array_list.add("Price");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PURCHASE_PER_UNIT_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	public ArrayList<String> getAllPurchaseAddedProductSubtotalPrice() {
		ArrayList<String> array_list = new ArrayList<String>();

		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE_ORDER_INFO
				+ " where " + PURCHASE_NO + " = " + purchaseNo, null);

		array_list.add("Total");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(PURCHASE_SUBTOTAL_PRICE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * purchase order 
	 */
	public int getPurchaseNetPayable() {
		int purchaseNo = getPurchaseOrderNo();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select sum (" + PURCHASE_SUBTOTAL_PRICE + ") from "
				+ TABLE_PURCHASE_ORDER_INFO + " where " + PURCHASE_NO + " = "
				+ purchaseNo, null);

		cursor.moveToFirst();
		int total = cursor.getInt(0);
		db.close();
		return total;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////



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
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * purchase order 
	 */
	int getPurchaseOrderNo() {
		int fk = 0;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select count(*) from " + TABLE_PURCHASE_ORDER, null);

		cursor.moveToFirst();
		fk = cursor.getInt(0) + 1;

		db.close();
		return fk;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////

	void updateProductQuantity(String productName, String operation, int num) {
		int newAmount = 0;
		SQLiteDatabase db = this.getWritableDatabase();

		// Getting the previous value from the right row
		Cursor cursor = db
				.rawQuery("SELECT  * FROM " + TABLE_PRODUCT + " WHERE "
						+ PRODUCT_NAME + " = " + '"' + productName + '"', null);

		if (cursor.moveToFirst()) {
			String amount = cursor.getString(cursor
					.getColumnIndex(PRODUCT_QUANTITY));
			if (operation == "positive") {
				newAmount = Integer.parseInt(amount) + num;
			} else {
				newAmount = Integer.parseInt(amount) - num;
			}
		}

		// updating the row
		try {
			db.execSQL("update product set productQuantity = " + newAmount
					+ " where productName = " + '"' + productName + '"');
		} catch (Exception e) {
			Log.e("error", "error while setting product quantity change");
		}

		db.close();
	}

	void updateProductCosting_MRP(String productName, int newCosting, int newMRP) {

		SQLiteDatabase db = this.getWritableDatabase();

		// updating the row
		try {
			db.execSQL("update product set productProductPrice = " + newCosting
					+ " where productName = " + '"' + productName + '"');
			db.execSQL("update product set productNormalPrice = " + newMRP
					+ " where productName = " + '"' + productName + '"');
		} catch (Exception e) {
			Log.e("error", "error while setting product quantity change");
		}

		db.close();
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * table purchase order
	 */
	void addNewPurchaseOrder(String dealerName, String date,
			int totalAmmount) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put(PURCHASE_DEALER_NAME, dealerName);
		values.put(PURCHASE_DATE, date);
		values.put(TOTAL_AMMOUNT, totalAmmount);

		db.insert(TABLE_PURCHASE_ORDER, null, values);
		db.close();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * CRUD for TABLE DEALER
	 */

	void addNewDealer(String dealerName, String dealerPhone,
			String dealerEmail, String dealerAddress) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put(DEALER_NAME, dealerName);
		values.put(DEALER_PHONE, dealerPhone);
		values.put(DEALER_EMAIL, dealerEmail);
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
			array_list
			.add(cursor.getString(cursor.getColumnIndex(DEALER_NAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	public ArrayList<String> getEverythingAboutDealer(String dealerName) {
		ArrayList<String> dealerInfo = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_DEALER + " where "
				+ DEALER_NAME + " = " + '"' + dealerName + '"', null);
		cursor.moveToFirst();

		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_NAME)));
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_PHONE)));
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_EMAIL)));
		dealerInfo.add(cursor.getString(cursor.getColumnIndex(DEALER_ADDRESS)));
		// while (cursor.isAfterLast() == false) {
		// dealerInfo[count] = cursor.getString(cursor
		// .getColumnIndex(infos[count]));
		// cursor.moveToNext();
		// count++;
		// }

		db.close();
		return dealerInfo;

	}

	// Adding purchase list log to SALES ORDER TRACK TABLE
	void addSalesRecord(String date, String nameOfProduct, String soldTo, String quantity, String profit) {
		SQLiteDatabase db = this.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put(SALES_ORDER_TRACK_DATE, date);
		values.put(SALES_ORDER_TRACK_NAME_OF_PRODUCT, nameOfProduct);
		values.put(SALES_ORDER_TRACK_SOLD_TO, soldTo);
		values.put(SALES_ORDER_TRACK_QUANTITY, quantity);
		values.put(SALES_ORDER_TRACK_PROFIT, profit);

		db.insert(TABLE_SALES_ORDER_TRACK, null, values);
		db.close();

	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	/**
	 * customer report functions
	 */
	public int getCustomerBasedTotalAmmountOfAllTransaction(String username) {
		int totalAmmount;
		SQLiteDatabase db = this.getReadableDatabase();
//		select sum (totalAmmount) from salesOrder
		Cursor cursor = db.rawQuery
				("select sum (totalAmmount) from salesOrder where customer = '" + username + "'", null);

		cursor.moveToFirst();
		totalAmmount = cursor.getInt(0);
		db.close();
		return totalAmmount;
	}

	public double getCustomerBasedAmmountPerTransaction(String username) {
//		int totalAmmount = getCustomerBasedTotalAmmountOfAllTransaction(username);
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select avg(totalAmmount) from salesOrder where customer = '" + username + "'", null);

		cursor.moveToFirst();
		double averageAmmount = cursor.getInt(0);
		db.close();
		return averageAmmount;
	}
	
	public double getStoreAvgPurchase() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select avg(totalAmmount) from purchaseOrder", null);

		cursor.moveToFirst();
		double averageAmmount = cursor.getInt(0);
		db.close();
		return averageAmmount;
	}

	public ArrayList<String> getUserBasedSalesOrderTrackID(String username) {
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER
				+ " where " + CUSTOMER_NAME + " = '" + username + "'", null);

		array_list.add("No.");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(SALES_NO)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	public ArrayList<String> getUserBasedSalesOrderTrackDate(String username) {
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER
				+ " where " + CUSTOMER_NAME + " = '" + username + "'", null);

		array_list.add("Date");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(SALES_DATE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

	public ArrayList<String> getUserBasedSalesOrderTrackAmmount(String username) {
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_SALES_ORDER
				+ " where " + CUSTOMER_NAME + " = '" + username + "'", null);

		array_list.add("Total");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			array_list.add(cursor.getString(cursor
					.getColumnIndex(TOTAL_AMMOUNT)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	/**
	 * Store see Report
	 */
	public int getTotalAmmountOfAllTransaction() {
		int totalAmmount;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select sum (totalAmmount) from salesOrder", null);

		cursor.moveToFirst();
		totalAmmount = cursor.getInt(0);
		db.close();
		return totalAmmount;
	}

	public double getAmmountPerTransaction() {
		int totalAmmount = getTotalAmmountOfAllTransaction();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select count (*) from salesOrder", null);

		cursor.moveToFirst();
		double averageAmmount = totalAmmount / cursor.getInt(0);
		db.close();
		return averageAmmount;
	}

	/**
	 * store see report
	 */
	public int getStoreBasedTotalAmmountOfAllTransaction() {
		int totalAmmount;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select sum (totalAmmount) from salesOrder", null);

		cursor.moveToFirst();
		totalAmmount = cursor.getInt(0);
		db.close();
		return totalAmmount;

	}

	public int getStoreTotalProfit() {
		int totalProfit;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select sum(salesProfit) from salesOrderTrack", null);

		cursor.moveToFirst();
		totalProfit = cursor.getInt(0);
		db.close();
		return totalProfit;

	}
	public int getStoreTotalPurchase() {
		int totalProfit = 0;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery
				("select sum(totalAmmount) from purchaseOrder", null);

		cursor.moveToFirst();
		totalProfit = cursor.getInt(0);
		db.close();
		return totalProfit;

	}
	public double getStoreAverageProfit() {
		double averageProfit;
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery
				("select avg(salesProfit) from salesOrderTrack", null);
		
		cursor.moveToFirst();
		averageProfit = cursor.getInt(0);
		db.close();
		return averageProfit;
	
	}
	
	public ArrayList<String> getAllSalesOrderNo() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from salesOrder", null);
		cursor.moveToFirst();
		array_list.add("No.");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(SALES_NO)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllSalesOrderDate() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from salesOrder", null);
		cursor.moveToFirst();
		array_list.add("Date");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(SALES_DATE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllSalesCustomer() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from salesOrder", null);
		cursor.moveToFirst();
		array_list.add("Customer");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllSalesOrderTotal() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from salesOrder", null);
		cursor.moveToFirst();
		array_list.add("Total");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(TOTAL_AMMOUNT)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public String getStarCustomer() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		String star = "";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select customer, sum(totalAmmount)" +
				" from salesOrder group by customer order" +
				" by totalAmmount desc limit 1", null);
		if (cursor != null && cursor.moveToFirst()) {
			star = cursor.getString(0);
		}
		//while (cursor.isAfterLast() == false) {
//			array_list
//			.add(cursor.getString(0));
//			cursor.moveToNext();
		//}
//		String star = cursor.getString(0);

		db.close();
		return star;
	}
	public String getTopDealer() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		String star = "";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select customer, sum(totalAmmount)" +
				" from purchaseOrder group by " +
				"customer order by totalAmmount desc limit 1", null);
		if (cursor != null && cursor.moveToFirst()) {
			star = cursor.getString(0);
		}
		//while (cursor.isAfterLast() == false) {
//			array_list
//			.add(cursor.getString(0));
//			cursor.moveToNext();
		//}
//		String star = cursor.getString(0);

		db.close();
		return star;
	}
	
	public String getCustomerMostPurchase(String username) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		String star = "";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select addedProduct, sum(orderQuantity) from salesOrderInfo s1," +
				" salesOrder s2 where s1.purchaseNo = s2.salesNo " +
				"and s2.customer = '"+ username +"' group by addedProduct order by subtotalPrice DESC limit 1", null);
		if (cursor != null && cursor.moveToFirst()) {
			star = cursor.getString(0);
		}

		db.close();
		return star;
	}
	
	public String getCustomerMostExpensive(String username) {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		String star = "";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select addedProduct, max(perUnitPrice) from salesOrderInfo s1, salesOrder s2 where s1.purchaseNo = s2.salesNo and s2.customer = '"+ username +"' group by addedProduct order by subtotalPrice DESC limit 1", null);
		if (cursor != null && cursor.moveToFirst()) {
			star = cursor.getString(0);
		}

		db.close();
		return star;
	}
	
	public String getStoreMostPurchased() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		String star = "";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select addedProduct, sum(subtotalPrice) " +
				"from purchaseOrderInfo group by addedProduct order by" +
				" subtotalPrice DESC limit 1", null);
		if (cursor != null && cursor.moveToFirst()) {
			star = cursor.getString(0);
		}
		//while (cursor.isAfterLast() == false) {
//			array_list
//			.add(cursor.getString(0));
//			cursor.moveToNext();
		//}
//		String star = cursor.getString(0);

		db.close();
		return star;
	}

	
	
	/**
	 * purchase see report
	 */
	public ArrayList<String> getAllPurchaseOrderNo() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from purchaseOrder", null);
		cursor.moveToFirst();
		array_list.add("No.");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PURCHASE_NO)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllPurchaseOrderDate() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from purchaseOrder", null);
		cursor.moveToFirst();
		array_list.add("Date");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PURCHASE_DATE)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllPurchaseCustomer() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from purchaseOrder", null);
		cursor.moveToFirst();
		array_list.add("Dealer");
		while (cursor.isAfterLast() == false) {
			array_list
			.add(cursor.getString(cursor.getColumnIndex(PURCHASE_DEALER_NAME)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}
	public ArrayList<String> getAllPurchaseOrderTotal() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from purchaseOrder", null);
		cursor.moveToFirst();
		array_list.add("Total");
		while (cursor.isAfterLast() == false) {
			
			array_list
			.add(cursor.getString(cursor.getColumnIndex(TOTAL_AMMOUNT)));
			cursor.moveToNext();
		}

		db.close();
		return array_list;
	}

}
