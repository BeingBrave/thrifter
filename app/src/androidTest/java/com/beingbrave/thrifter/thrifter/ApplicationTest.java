package com.beingbrave.thrifter.thrifter;

import android.app.Application;
import android.test.ApplicationTestCase;
import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        IdentifiersDataSource dataShit = new IdentifiersDataSource(getContext());
        dataShit.open();

        dataShit.createIdentifier("gayshit");
    }
}