package com.beingbrave.thrifter.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.beingbrave.thrifter.R;
import com.beingbrave.thrifter.ThrifterApplication;
import com.beingbrave.thrifter.data.LocationApi;
import com.beingbrave.thrifter.fragments.ProfileFragment;
import com.beingbrave.thrifter.fragments.SearchFragment;
import com.beingbrave.thrifter.fragments.StarredFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;

    @BindColor(R.color.tabSelectedIconColor) int tabSelectedIconColor;
    @BindColor(R.color.tabUnselectedIconColor) int tabUnselectedIconColor;

    private FirebaseAuth auth;

    private int[] tabIcons = {
            R.drawable.ic_search_black,
            R.drawable.ic_star_black,
            R.drawable.ic_person_black
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Bind views
        ButterKnife.bind(this);

        LocationApi.initialize(this);

        // Use support library action bar instead of default
        setSupportActionBar(toolbar);

        // Setup tabs
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SearchFragment(), "Search");
        adapter.addFrag(new StarredFragment(), "Starred");
        adapter.addFrag(new ProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // Set icons and initial icon colors
        for(int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            tabLayout.getTabAt(i).getIcon().setColorFilter(tabUnselectedIconColor, PorterDuff.Mode.SRC_IN);
        }

        // Set selected tab icon color
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setColorFilter(tabSelectedIconColor, PorterDuff.Mode.SRC_IN);

        // Update colors of tab icons when tab changes
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        tab.getIcon().setColorFilter(tabSelectedIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        tab.getIcon().setColorFilter(tabUnselectedIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            // If not logged in, send user to Login Activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    @OnClick(R.id.upload_fab)
    public void onUploadClick(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

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