package com.beingbrave.thrifter.thrifter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marce on 2/27/2016.
 */
public class ItemIDHelper extends SQLiteOpenHelper{



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ItemID.db";

    public ItemIDHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(SQL_CREATE_ENTRIES);
    }
}
