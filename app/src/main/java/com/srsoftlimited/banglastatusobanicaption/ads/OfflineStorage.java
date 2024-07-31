package com.srsoftlimited.banglastatusobanicaption.ads;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class OfflineStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "status";
    // Constructor
    public OfflineStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table
        String createTable = "CREATE TABLE status (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, null, null);
        db.close();
    }
    public int getIdByTitleAndDescription(String title, String description) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = -1; // Default value if not found

        Cursor cursor = db.query(
                TABLE,          // The table to query
                new String[]{"id"},  // The array of columns to return
                "title = ? AND description = ?",  // The columns for the WHERE clause
                new String[]{title, description},  // The values for the WHERE clause
                null,                // don't group the rows
                null,                // don't filter by row groups
                null                 // The sort order
        );

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }

        cursor.close();
        db.close();

        return id;
    }
    public void deleteRowById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "id = ?";

        String[] whereArgs = {String.valueOf(id)};

        db.delete(TABLE, whereClause, whereArgs);

        db.close();
    }

    public void Insert(String name, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", name);
        values.put("description", status);

        db.insert(TABLE, null, values);
        db.close();
    }
}
