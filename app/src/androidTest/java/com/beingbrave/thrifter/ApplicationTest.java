package com.beingbrave.thrifter;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.beingbrave.thrifter.database.Identifier;
import com.beingbrave.thrifter.database.IdentifiersDataSource;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        IdentifiersDataSource dataShit = new IdentifiersDataSource(getContext()); // actually needs to be "this"
        dataShit.open();

        dataShit.createIdentifier("gayshit");

        List<Identifier> identifiers = dataShit.getAllIdentifiers();
        System.out.println(identifiers.get(0));
        dataShit.close();
    }
}