package com.beingbrave.thrifter.thrifter;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import java.util.List;
import java.util.Random;

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