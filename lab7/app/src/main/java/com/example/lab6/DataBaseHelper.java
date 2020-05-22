package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_store.db";
    private static final int SCHEMA = 1;
    public static final String TABLE_NAME = "product";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_COUNT = "count";
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                + " TEXT, " + COLUMN_PRICE + " REAL, " + COLUMN_COUNT + " INTEGER);");
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "_id = ?", new String[]{ String.valueOf(id) });
    }

    public void update(long id, String name, String price, String count) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_COUNT, count);

        SQLiteDatabase db = getWritableDatabase();

        if (id > 0) {
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + id, null);
        } else {
            db.insert(TABLE_NAME, null, cv);
        }
    }

    public int decrementCount(String name, String price, int count) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = ?"
                + " and " + COLUMN_PRICE + " = " + price + " and " + COLUMN_COUNT + " = " + count, new String[]{ name });
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        int newCount = cursor.getInt(3);
        newCount--;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_COUNT, newCount);

        db.update(TABLE_NAME, cv, COLUMN_ID + "=" + id, null);
        cursor.close();

        return newCount;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllLines(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public ArrayList<Product> getArrayList(){
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = getAllLines();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                int count = cursor.getInt(3);
                if(count > 0){
                    list.add(new Product(name, price, count));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    public ArrayList<String> getDataByID(long id) {
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("select * from " + DataBaseHelper.TABLE_NAME + " where " +
                DataBaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        list.add(cursor.getString(1));
        list.add(cursor.getString(2));
        list.add(cursor.getString(3));
        cursor.close();

        return list;
    }
}
