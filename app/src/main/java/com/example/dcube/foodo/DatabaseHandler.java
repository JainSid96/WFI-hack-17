package com.example.dcube.foodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siddhant Jain on 10/26/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ItemsManager";

    // Contacts table name
    private static final String TABLE_ITEM = "items";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DAYS = "days";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " BLOB NOT NULL, " + KEY_DAYS + " INTEGER " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);

        // Create tables again
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName()); // Contact Name
        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_DAYS, item.getdays());

        // Inserting Row
        db.insert(TABLE_ITEM, null, values);
        db.close(); // Closing database connection
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ITEM, new String[] { KEY_ID,
                        KEY_NAME, KEY_IMAGE,KEY_DAYS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getBlob(2),Integer.parseInt(cursor.getString(3)));
        // return contact
        return item;
    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setImage(cursor.getBlob(2));
                item.setdays(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return itemList;
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ITEM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateContact(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_DAYS,item.getdays());

        // updating row
        return db.update(TABLE_ITEM, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
    }

    // Deleting single contact
    public void deleteContact(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


}