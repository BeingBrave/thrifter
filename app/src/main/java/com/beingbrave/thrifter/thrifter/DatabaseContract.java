package com.beingbrave.thrifter.thrifter;

import android.provider.BaseColumns;

/**
 * @author Marcello
 *
 * Class defining the contract for the item ID database
 */

public class DatabaseContract {

    // Empty constructor to prevent instantiation
    public DatabaseContract(){
    }

    // Table content definitions
    public static abstract class WhatIsThisShit implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryID";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
