package com.beingbrave.thrifter.thrifter;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class IdentifiersDataSource {

    // creates the database object
    private SQLiteDatabase database;
    // creates database helper object
    private ItemIDHelper dbHelper;
    // convenience array, holds the names of both columns in its two cells
    private String[] allColumns = { ItemIDHelper.COLUMN_INDEX,
            ItemIDHelper.COLUMN_IDENTIFIER };

    public IdentifiersDataSource(Context context) {
        dbHelper = new ItemIDHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     *
     * @param identifier
     * @return
     */
    public Identifier createIdentifier(int identifier) {
        ContentValues values = new ContentValues();

        // Adds data to the ContentValues data set
        values.put(ItemIDHelper.COLUMN_IDENTIFIER, identifier);

        //
        long insertId = database.insert(ItemIDHelper.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(ItemIDHelper.TABLE_NAME,
                allColumns, ItemIDHelper.COLUMN_INDEX + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Identifier newIdentifier = cursorToIdentifier(cursor);
        cursor.close();
        return newIdentifier;
    }

    /**
     * Method for deleting an identifier from the database
     * @param identifier
     */
    public void deleteIdentifier(Identifier identifier) {
        //long id = identifier.getId();
        //System.out.println("Identifier deleted with id: " + id);
        //database.delete(ItemIDHelper.TABLE_NAME, ItemIDHelper.COLUMN_INDEX
        //        + " = " + id, null);
        long id = identifier.getIndex();
        System.out.println("Identifier deleted with id: " + id);
        database.delete(ItemIDHelper.TABLE_NAME, ItemIDHelper.COLUMN_INDEX
                + " = " + id, null);
    }

    public List<Identifier> getAllIdentifiers() {
        List<Identifier> identifiers = new ArrayList<Identifier>();

        Cursor cursor = database.query(ItemIDHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Identifier identifier = cursorToIdentifier(cursor);
            identifiers.add(identifier);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return identifiers;
    }

    private Identifier cursorToIdentifier(Cursor cursor) {
        Identifier identifier = new Identifier();
        identifier.setIndex(cursor.getInt(0));
        identifier.setIdentifier(cursor.getString(1));
        return identifier;
    }
}
