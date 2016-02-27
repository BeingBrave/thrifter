package com.beingbrave.thrifter.thrifter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemIDHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "itemTable"; // SQL name of table
    public static final String COLUMN_INDEX = "_id"; // SQL name of column containing row index
    public static final String COLUMN_IDENTIFIER = "identifier"; // SQL name of column containing item IDs

    private static final String DATABASE_NAME = "itemID.db"; // name of database
    private static final int DATABASE_VERSION = 1; // version of database

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_INDEX
            + " integer primary key autoincrement, " + COLUMN_IDENTIFIER
            + " text not null);";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ItemIDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // execSQL executes a single database statement, in this case the one defined as
        // DATABASE_CREATE
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