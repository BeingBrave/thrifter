package com.beingbrave.thrifter.thrifter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class containing names and various definitions for the database
 */
public class ItemIDHelper extends SQLiteOpenHelper {

    // SQL name of table
    public static final String TABLE_NAME = "itemTable";
    // SQL name of column containing row index
    public static final String COLUMN_INDEX = "_id";
    // SQL name of column containing item IDs
    public static final String COLUMN_IDENTIFIER = "identifier";

    // name of database
    private static final String DATABASE_NAME = "ItemID.db";
    // version of database, no idea what this is for
    private static final int DATABASE_VERSION = 1;

    // SQL statement defining how the database is created
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_INDEX
            + " integer primary key autoincrement, " + COLUMN_IDENTIFIER
            + " text not null);";
    // SQL statement defining how a table is deleted
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ItemIDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // SQLiteOpenHelper objects simplify database creation
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}