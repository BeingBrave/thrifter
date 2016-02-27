package com.beingbrave.thrifter.thrifter;

import android.provider.BaseColumns;

/**
 * @author Marcello
 */

public class Comment {
    private long id;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return comment;
    }
}

/*
public final class Comment {

    // empty constructor to prevent instantiation
    public Comment(){
    }

    // Inner class the defines the table contents
    public static abstract class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        " )";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }
}
*/