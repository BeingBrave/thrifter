package com.beingbrave.thrifter.thrifter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * For creating instances of databases
 */
public class ItemIDHelper extends SQLiteOpenHelper {

    // if you change the schema, increment version (whatever that means)
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ItemID.db";

    ItemIDHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Called on initial creation of database
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database){

    }


    /**
     * Called when database needs to be upgraded
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){

    }

}