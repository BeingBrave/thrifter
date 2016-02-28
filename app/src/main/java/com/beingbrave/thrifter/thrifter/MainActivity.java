package com.beingbrave.thrifter.thrifter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static String appToken;
    public static LocationManager locationManager;

    private int[] tabIcons = {
            R.drawable.ic_search_black_24dp,
            R.drawable.ic_star_border_black_24dp,
            R.drawable.ic_perm_identity_black_24dp
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    // public IdentifiersDataSource dataShit; // the item ID database
    // List<Identifier> identifiers; // item ID ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.app_bar_layout, null);

        actionBar.setCustomView(v);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                startActivity(intent);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        ((EditText)findViewById(R.id.search)).setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
    /* When focus is lost check that the text field
    * has valid values.
    */
                if (!hasFocus) {
                    //create intent and start intent on listener add to button open new page
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                coordinates[0] = mLastLocation.getLatitude();
                coordinates[1] = mLastLocation.getLongitude();
            }
        }
        catch(SecurityException exc){
            coordinates[0] = -1;
            coordinates[1] = -1;
        }
    }

    @Override
    public void onConnectionSuspended(int a){
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){
        mGoogleApiClient.disconnect();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                appToken = null;

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                appToken = extras.getString("TOKEN");
            }
        } else {
            appToken = (String) savedInstanceState.getSerializable("TOKEN");
        }

        System.out.println(appToken);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    2);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }

        /* Create and open database
        dataShit = new IdentifiersDataSource(this);
        dataShit.open();
        identifiers = dataShit.getAllIdentifiers();
        */
    }

    /*
    public void getItemIDString(String token) {

        for (int i = 0; i < identifiers.size(); i++) {
            Ion.with(getApplicationContext())
                    .load("http://178.62.117.169:3333/item/" + identifiers.get(i) + "?access_token=" + appToken)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            itemNames.add(i, ((JsonObject) result.get(0)).get("name").toString());
                        }
                    });
        }
    } */

    public void onSearchAnyClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void fabClick(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SearchFragment(), "ONE");
        adapter.addFrag(new StarredFragment(), "TWO");
        adapter.addFrag(new ProfileFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    /*
    @Override
    public void onStop(){
        dataShit.close();
    }

    @Override
    public void onRestart(){
        dataShit.open();
    }
    */

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
