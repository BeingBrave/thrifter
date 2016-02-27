package com.beingbrave.thrifter.thrifter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * For creating instances of databases
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper(Context context, String name, int version){
        super(context, name, null, version);

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