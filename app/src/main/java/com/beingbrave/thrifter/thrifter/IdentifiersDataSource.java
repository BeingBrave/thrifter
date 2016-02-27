package com.beingbrave.thrifter.thrifter;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * This is the class the app interacts with directly to put data into the database
 * and get data out of it. In turn this class interacts with ItemIDHelper (an SQLiteOpenHelper
 * subclass) and Identifier (a class defining objects that essentially represent a single row
 * in the database table).
 *
 * Use instructions:
 *
 * 1. Create an instance of this class with a context (getApplicationContext() maybe?)
 * 2. Open the database with database.open()
 * 3. Use createIdentifier and deleteIdentifier when adding or removing item IDs
 * 4. use getAllIdentifiers to get an arraylist with all identifiers
 * 5. Use ArrayList methods to extract item IDs out of the arrayList as needed
 * 6. Close database with database.close()
 */
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
     * Method for deleting an identifier object
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
        // ArrayList that holds all identifier objects
        List<Identifier> identifiers = new ArrayList<Identifier>();

        // Cursors hold data read out of database, fetched using the query() method
        // method arguments: table from which to fetch, array of columns from which to fetch,
        // and then a whole bunch of selection criteria that are unnecessary in this case
        Cursor cursor = database.query(ItemIDHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        // move the cursor to the first row
        cursor.moveToFirst();
        // iterates over rows
        while (!cursor.isAfterLast()) {
            // create new identifier object with the values in the identifier object the cursor points to
            Identifier identifier = cursorToIdentifier(cursor);
            // add identifier to ArrayList
            identifiers.add(identifier);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        // return ArrayList with all identifier objects
        return identifiers;
    }

    /**
     * Creates a new identifier object based on the one a cursor argument points to
     * @param cursor object
     * @return identifier object
     */
    private Identifier cursorToIdentifier(Cursor cursor) {
        // creates a new identifier object to be returned
        Identifier identifier = new Identifier();
        // sets the identifier object's index field to the int found in column 0
        identifier.setIndex(cursor.getInt(0));
        // sets the identifier object's identifier field to the String found in column 1
        identifier.setIdentifier(cursor.getString(1));
        // returns the new identifier object which will be added to the ArrayList
        return identifier;
    }
}
