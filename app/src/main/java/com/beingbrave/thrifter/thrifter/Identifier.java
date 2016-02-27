package com.beingbrave.thrifter.thrifter;

import android.provider.BaseColumns;

/**
 * Class defining objects that model data in the database.
 * Essentially represents a single row in the database table.
 * @author Marcello
 */

public class Identifier {
    private int index; // the row index in the row
    private String identifier; // the item ID in the row

    // TODO: Figure out if "index" is an unnecessary column (keep it for now)

    /**
     * Method for returning the index of this data element
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Method for setting the index of this data element
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Method for returning the actual item ID held by the data object
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Method for setting the item ID held by the data object
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return identifier;
    }
}