package com.example.arsalansiddiq.beem.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.arsalansiddiq.beem.models.responsemodels.LoginResponse;

/**
 * Created by arsalansiddiq on 2/19/18.
 */

public class BeemDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "beem.db";

    // Table names
    private static final String TABLE_LOGIN = "user_info";
    public static String TABLE_USER_LOCATION = "ba_location_table";
    public static String TABLE_END_BA_ATTENDANCE = "end_ba_attendance_table";
    public static String TABLE_MARK_BA_ATTENDANCE = "mark_ba_attendance_table";
    public static String TABLE_ORDER_RECORDS = "order_records";
    public static String TABLE_ORDER_QUANTITY = "quantity_table";

    // user_info Columns names
    private static final String KEY_STATUS = "status";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_UT = "ut";
    private static final String KEY_STORE_ID = "store_id";

    //Location Table Columns Names
    private static final String KEY_LOCATION_ID = "location_ids";
    private static final String KEY_PERIMETER_LATITUDE = "latitude";
    private static final String KEY_PERIMETER_LONGITUDE = "longitude";

    //Quantity Table Columns Names
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_LOOSE_QUANTITY = "loose";
    private static final String KEY_CARTON_QUANTITY = "carton";
    private static final String KEY_PRICE_PER_PIECE= "price";

    //TABLE_MARK_BA_ATTENDANCE Columns Names
    private static final String KEY_MARK_BA_ATTENDANCE_DATE = "ba_attendance_date";
    private static final String KEY_EMPLOYEE_BA_ID = "employee_id";
    private static final String KEY_MARK_BA_NAME = "ba_name";
    private static final String KEY_MARK_BA_ATTENDANCE_IMAGE = "ba_mark_attendance_image";
    private static final String KEY_MARK_ATTENDANCE_TIME = "ba_start_attendance_time";
    private static final String KEY_MARK_ATTENDANCE_LATITUDE = "ba_start_attendance_latitude";
    private static final String KEY_MARK_ATTENDANCE_LONGITUDE = "ba_start_attendance_longitude";
    private static final String KEY_MARK_BA_ATTENDANCE_STATUS = "ba_attendance_status";
    private static final String KEY_MARK_BA_ATTENDANCE_ID = "mark_ba_attendance_id";


    //TABLE_ORDER_RECORDS Columns Names
    private static final String KEY_ORDER_AUTO_ID = "auto_id";
    private static final String KEY_ORDER_STORE_ID = "store_id";
    private static final String KEY_ORDER_SALES_ID = "sales_id";
    private static final String KEY_ORDER_ON_DATE = "on_date";
    private static final String KEY_ORDER_BRAND = "brand";
    private static final String KEY_ORDER_SKUCATEGORY = "skucategory";
    private static final String KEY_ORDER_SKU = "sku";
    private static final String KEY_ORDER_SALE_TYPE = "sale_type";
    private static final String KEY_ORDER_NO_ITEM = "no_item";
    private static final String KEY_ORDER_PRICE = "price";
    private static final String KEY_ORDER_S_AMOUNT = "samount";
    private static final String KEY_ORDER_STATUS = "status";
    private static final String KEY_ORDER_ORDER_ID = "order_id";

//    private static final String KEY_END_ATTENDANCE_TIME = "ba_end_attendance_time";
//    private static final String KEY_END_ATTENDANCE_LATITUDE = "ba_end_attendance_latitude";
//    private static final String KEY_END_ATTENDANCE_LONGITUDE = "ba_start_attendance_longitude";

    private final String userTable = "create table " + TABLE_LOGIN + "(" + KEY_USER_ID + " INTEGER PRIMARY KEY, " + KEY_NAME +
            " TEXT, " + KEY_BRAND + " TEXT, " + KEY_UT + " TEXT, " + KEY_STORE_ID + " TEXT, " + KEY_STATUS + " INTEGER);";

    private final String perimeterTable = "create table " + TABLE_USER_LOCATION + " (" + KEY_LOCATION_ID + " INTEGER PRIMARY KEY autoincrement, " + KEY_PERIMETER_LATITUDE +
            " float, " + KEY_PERIMETER_LONGITUDE + " float);";

    private final String markAttendanceTable = "create table " + TABLE_MARK_BA_ATTENDANCE + " (" + KEY_EMPLOYEE_BA_ID + " INTEGER PRIMARY KEY, "
            + KEY_MARK_BA_ATTENDANCE_DATE + " TEXT, " + KEY_MARK_BA_NAME + " TEXT, " + KEY_MARK_BA_ATTENDANCE_IMAGE + " BLOB, "
            + KEY_MARK_ATTENDANCE_TIME + " TEXT, " + KEY_MARK_ATTENDANCE_LATITUDE + " float, "
            + KEY_MARK_ATTENDANCE_LONGITUDE + " float, " + KEY_MARK_BA_ATTENDANCE_STATUS + " INTEGER, " + KEY_MARK_BA_ATTENDANCE_ID + " INTEGER);";

    private final String orderRecordsTable = "create table " + TABLE_ORDER_RECORDS + " (" + KEY_ORDER_AUTO_ID + " INTEGER PRIMARY KEY autoincrement, "
            + KEY_ORDER_STORE_ID + " INTEGER, " + KEY_ORDER_SALES_ID + " INTEGER, " + KEY_ORDER_ON_DATE + " TEXT, " + KEY_ORDER_BRAND + " TEXT, "
            + KEY_ORDER_SKUCATEGORY + " INTEGER, " + KEY_ORDER_SKU + " INTEGER, " + KEY_ORDER_SALE_TYPE + " INTEGER, " + KEY_ORDER_NO_ITEM + " INTEGER, "
            + KEY_ORDER_PRICE + " INTEGER, " + KEY_ORDER_S_AMOUNT + " INTEGER, " + KEY_ORDER_STATUS + " INTEGER, " + KEY_ORDER_ORDER_ID + " INTEGER);";


    private final String quantityTable = "create table " + TABLE_ORDER_QUANTITY + " (" + KEY_PRODUCT_NAME + " TEXT PRIMARY KEY, " + KEY_LOOSE_QUANTITY +
            " INTEGER, " + KEY_CARTON_QUANTITY + " INTEGER, " + KEY_PRICE_PER_PIECE + " float);";

    public BeemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("DB", "DB cReated");

        sqLiteDatabase.execSQL(userTable);

        sqLiteDatabase.execSQL(perimeterTable);

        sqLiteDatabase.execSQL(markAttendanceTable);

        sqLiteDatabase.execSQL(orderRecordsTable);

        sqLiteDatabase.execSQL(quantityTable);


//        sqLiteDatabase.execSQL(attendanceTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOCATION);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_QUANTITY);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MARK_BA_ATTENDANCE);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_RECORDS);


//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BA_ATTENDANCE);

        onCreate(sqLiteDatabase);
    }


    public void insertBAInfo(int id, String name,String brand, String ut, String storeID, int status) {
        SQLiteDatabase db = this.getWritableDatabase();

//        DBModel dbModel = new DBModel(id, number,devId);
//
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, id);
        values.put(KEY_NAME, name);
        values.put(KEY_BRAND, brand);
        values.put(KEY_UT, ut);
        values.put(KEY_STORE_ID, storeID);
        values.put(KEY_STATUS, status);

        // Inserting Row
        long check = db.insert(TABLE_LOGIN, null, values);

//        Log.i("ID and Number Added: ", String.valueOf(check));
        db.close();
    }


    public void insertSelectItemDetail(String name, int loose, int carton, float price) {
        SQLiteDatabase db = this.getWritableDatabase();

//        DBModel dbModel = new DBModel(id, number,devId);
//
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, name);
        values.put(KEY_LOOSE_QUANTITY, loose);
        values.put(KEY_CARTON_QUANTITY, carton);
        values.put(KEY_PRICE_PER_PIECE, price);

        // Inserting Row
        long check = db.insert(TABLE_ORDER_QUANTITY, null, values);

        Log.i("Quantity Table: ", String.valueOf(check));
        db.close();
    }

    public boolean checkUserSelectedItem() {

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "select * from " + TABLE_ORDER_QUANTITY;
        Cursor cursor = db.rawQuery(countQuery, null);

        Log.i("DB GETCOUNT", String.valueOf(cursor.getCount()));

        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }

    }

    public void removeSelectedItemTableRaws() {

        SQLiteDatabase db = this.getReadableDatabase();
        String deleteQuery = "delete from "+ TABLE_ORDER_QUANTITY;
        db.execSQL(deleteQuery);

    }

    public void insertMark_BA_attendanceInfo(int id, String date, String name, byte[] markAttendanceImage, String startTime,
                                        float sLatitude, float sLongitude, int status, int mark_BA_attendanceID) throws SQLiteException{

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_BA_ID, id);
        values.put(KEY_MARK_BA_ATTENDANCE_DATE, date);
        values.put(KEY_MARK_BA_NAME, name);
        values.put(KEY_MARK_BA_ATTENDANCE_IMAGE, markAttendanceImage);
        values.put(KEY_MARK_ATTENDANCE_TIME, startTime);
        values.put(KEY_MARK_ATTENDANCE_LATITUDE, sLatitude);
        values.put(KEY_MARK_ATTENDANCE_LONGITUDE, sLongitude);
        values.put(KEY_MARK_BA_ATTENDANCE_STATUS, status);
        values.put(KEY_MARK_BA_ATTENDANCE_ID, mark_BA_attendanceID);

        // Inserting Row
        long check = db.insert(TABLE_MARK_BA_ATTENDANCE, null, values);

        Log.i("raw inserted ", String.valueOf(check));
        db.close();
    }

    public void inserOrderRecords (int storeId, int salesId, String onDate, String brand, int skuCategory, int sku, int saleType,
                                   int noItem, int price, int samount, int status, int order_id) throws SQLiteException{

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ORDER_STORE_ID, storeId);
        values.put(KEY_ORDER_SALES_ID, salesId);
        values.put(KEY_ORDER_ON_DATE, onDate);
        values.put(KEY_ORDER_BRAND, brand);
        values.put(KEY_ORDER_SKUCATEGORY, skuCategory);
        values.put(KEY_ORDER_SKU, sku);
        values.put(KEY_ORDER_SALE_TYPE, saleType);
        values.put(KEY_ORDER_NO_ITEM, noItem);
        values.put(KEY_ORDER_PRICE, price);
        values.put(KEY_ORDER_S_AMOUNT, samount);
        values.put(KEY_ORDER_STATUS, status);
        values.put(KEY_ORDER_ORDER_ID, order_id);

        // Inserting Row
        long check = db.insert(TABLE_ORDER_RECORDS, null, values);

        Log.i("raw inserted ", String.valueOf(check));
        db.close();
    }

    public void updateBA_AttendanceInfo(int id, String endTime, float eLatitude, float eLongitude) {

//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_END_ATTENDANCE_TIME, endTime);
//        values.put(KEY_END_ATTENDANCE_LATITUDE, eLatitude);
//        values.put(KEY_END_ATTENDANCE_LONGITUDE, eLongitude);
//
//        // Updating Row
//        int  done = db.update(TABLE_BA_ATTENDANCE, values, KEY_BA_ATTENDANCE_ID+"="+id, null);
//
//        Log.i("rowUpdated: ", String.valueOf(done));
//        db.close();
    }



//    public int getBA_Attendance_ID() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String countQuery = "select * from " + TABLE_BA_ATTENDANCE + " order by " + KEY_BA_ATTENDANCE_ID + " desc limit 1;";
//
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int id = 0;
//        if (cursor.moveToFirst()) {
//            int getid = cursor.getInt(cursor.getColumnIndex(KEY_BA_ATTENDANCE_ID));
//            id = getid;
//        }
//        cursor.close();
//        db.close();
//         return id;
//    }



    public float getTotalAmount() {

        float amount = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ORDER_QUANTITY, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                amount += cursor.getInt(cursor.getColumnIndex(KEY_PRICE_PER_PIECE));
            }
        }

        Log.i("amount", String.valueOf(amount));
        return amount;
    }


//
//    public int getID() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String countQuery = "select * from " + TABLE_NAME + " order by " + KEY_ID + " desc limit 1;";
//
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int id = 0;
//        if (cursor.moveToFirst()) {
//            int getid = cursor.getInt(cursor.getColumnIndex(KEY_ID));
//            String devId = cursor.getString(cursor.getColumnIndex(DEVICE_ID));
//            Log.i("gotid: ", String.valueOf(getid));
//            Log.i("gotid: ", String.valueOf(devId));
//
//            id = getid;
//        }
//        cursor.close();
//        db.close();
//         return id;
//    }

    public LoginResponse getUserDetail() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "select * from " + TABLE_LOGIN + " order by " + KEY_USER_ID + " desc limit 1;";

        Cursor cursor = db.rawQuery(countQuery, null);
        LoginResponse loginResponse = new LoginResponse();

        String id = "";

        if (cursor.moveToFirst()) {

            String userID = cursor.getString(cursor.getColumnIndex(KEY_USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String userBrand = cursor.getString(cursor.getColumnIndex(KEY_BRAND));
            String storeId = cursor.getString(cursor.getColumnIndex(KEY_STORE_ID));

            loginResponse.setUserId(Integer.valueOf(userID));
            loginResponse.setName(userName);
            loginResponse.setBrand(userBrand);
            loginResponse.setStoreId(storeId);

        }

        cursor.close();
        db.close();
        return loginResponse;
    }

    public boolean checkUserExist (int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "select * from " + TABLE_LOGIN + " where " + KEY_USER_ID + "=" + id;
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            return false;
        }

    }

}
