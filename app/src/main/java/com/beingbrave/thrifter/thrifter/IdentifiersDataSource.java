package com.beingbrave.thrifter.thrifter;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class IdentifiersDataSource {

    // Database fields
    private SQLiteDatabase database;
    private ItemIDHelper dbHelper;
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
<<<<<<< HEAD
        int index = identifier.getIndex();
        System.out.println("Identifier deleted with id: " + index);

        // calls delete method defined by SQLiteDatabase, with arguments (which table, which row, null)
        // which row is identified as "select by column + item in that column"
        database.delete(ItemIDHelper.TABLE_NAME, ItemIDHelper.COLUMN_INDEX
                + " = " + index, null);
=======
        //long id = identifier.getId();
        //System.out.println("Identifier deleted with id: " + id);
        //database.delete(ItemIDHelper.TABLE_NAME, ItemIDHelper.COLUMN_INDEX
        //        + " = " + id, null);
>>>>>>> 004aae6... Fixed marcellos broken stuff
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
        //Identifier identifier = new Identifier();
        //identifier.setId(cursor.getLong(0));
        //identifier.setIdentifier(cursor.getString(1));
        return null;
    }
}
