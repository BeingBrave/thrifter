package com.beingbrave.thrifter.thrifter;

import android.provider.BaseColumns;

/**
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

    public String getidentifier() {
        return identifier;
    }

    public void setidentifier(String identifier) {
        this.identifier = identifier;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return identifier;
    }
}