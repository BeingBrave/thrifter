package com.beingbrave.thrifter.thrifter;

import android.provider.BaseColumns;

/**
 * "Model" class containing the data
 * @author Marcello
 */

public class Identifier {
    private long index;
    private String identifier;

    public long getId() {
        return index;
    }

    public void setId(long index) {
        this.index = index;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return identifier;
    }
}