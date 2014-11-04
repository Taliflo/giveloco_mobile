package com.taliflo.app.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.taliflo.app.R;
import com.taliflo.app.adapters.TabsPagerAdapter;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.utilities.MyViewPager;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    // Logcat tag
    private final String TAG = "Taliflo.MainActivity";

    // Used to store app title
    private CharSequence appTitle;

    // Member fields for the view pager
    public static MyViewPager viewPager;
    private TabsPagerAdapter tabsAdapter;
    private ActionBar actionBar;

    private Activity thisActiv = this;

    private static boolean isPagingEnabled = true;

    private UserStore userStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Clear saved preferences
        //getSharedPreferences(getResources().getString(R.string.savedPrefName), 0).edit().clear().commit();

        setContentView(R.layout.activity_main_pager);

        // Get login status from shared preferences
        // Restore shared preferences if present
        SharedPreferences savedPrefs = getSharedPreferences(getResources().getString(R.string.savedPrefName), 0);
        boolean loggedIn = savedPrefs.getBoolean(getResources().getString(R.string.savedPrefLoggedIn), false);
        String authToken = savedPrefs.getString(getResources().getString(R.string.savedPrefAuthToken), null);
        String uid = savedPrefs.getString(getResources().getString(R.string.savedPrefUid), null);

        Log.i(TAG, "Saved authToken: " + authToken);
        Log.i(TAG, "Saved uid: " + uid);

        // If loggedIn is false, start Login activity
        userStore = UserStore.getInstance();
        if (!loggedIn) {
            Intent loginIntent = new Intent(getApplicationContext(), Login.class);
            startActivityForResult(loginIntent, 10);
        }

        // If current user object has been released, set credentials
        if (userStore.getCurrentUser() == null) {
            userStore.setAuthToken(authToken);
            userStore.setUid(uid);
        }

        if (savedInstanceState == null) {

            // Initialization
            viewPager = (MyViewPager) findViewById(R.id.main_pager);
            // Disable swipe and tab changing
            viewPager.setPagingEnabled(true);
            actionBar = getActionBar();
            tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());

            viewPager.setAdapter(tabsAdapter);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            final String[] tabs = getResources().getStringArray(R.array.main_tab_labels);
            TypedArray tabIcons = getResources().obtainTypedArray(R.array.main_tab_icons);
            // Adding Tabs
            for (int i = 0; i < tabs.length; i++) {
                Tab tab = actionBar.newTab();
                tab.setTabListener(this);
                tab.setCustomView(createTabView(this, tabIcons.getResourceId(i, 0)));
                actionBar.addTab(tab);
            }

            //tabsAdapter.instantiateItem(viewPager, 2);

            actionBar.setSelectedNavigationItem(1);
            setTitle(tabs[1]);

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                    setTitle(tabs[position]);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }

            });

            tabIcons.recycle();
/*
            // Create global configuration and initialize ImageLoader with this configuration
            File cacheDir = StorageUtils.getCacheDirectory(this);
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPoolSize(10)
                    .memoryCacheSize(5 * 1024 * 1024)
                    //.diskCache(new UnlimitedDiscCache(cacheDir))
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileCount(250)
                    .writeDebugLogs()
                    .build();
            ImageLoader.getInstance().init(config);
*/
        } else {
            // Restore logged in user
            Log.i(TAG, "Restore user");
            userStore.setCurrentUser((User)savedInstanceState.getParcelable("loggedInUser"));
        }

    }

    @Override
    public void onTabReselected (Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected (Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    private View createTabView(Context context, int iconSrc) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null, false);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        icon.setImageResource(iconSrc);
        return view;
    }

    private class NavDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Display view for selected navigation drawer item
            Log.d(TAG, "in NavDrawerClickListener start");
        //    displayView(position, navDrawerEntries.get(position).getId());
            Log.d(TAG, "in NavDrawerClickListener finish");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.global, menu);
//        abHelper.onCreateOptionsMenu(thisActiv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        // if nav drawer is opened, hide the action items
       // 	boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
       // 	menu.findItem(R.id.main_action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        abHelper.onOptionsItemSelected(thisActiv, item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        appTitle = title;
        getActionBar().setTitle(appTitle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("loggedInUser", userStore.getCurrentUser());
        //outState.putParcelableArrayList("transactions", transactionsList);
    }

    @Override
    public void onBackPressed() {
        // Set user to null so that it will be reloaded
        userStore.setCurrentUser(null);
        finish();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "in MainActivity#onActivityResult");
        Log.i(TAG, "Request code: " + requestCode + " Result code: " + resultCode);

        switch (requestCode) {

            case 10:
                // If the user has pressed the back button from the Login activity
                if (resultCode == RESULT_OK) {
                    // Successful login
                    Log.i(TAG, "Successful login");
                }

                if (resultCode == RESULT_CANCELED) {
                    Log.i(TAG, "Exit application");
                    finish();
                }
                break;

            case 30:
                // UserDetail activity result
                break;

            case 40:
                // RedeemCredits activity
                break;

            case 555:
                // BillingInfo activity result
                break;
        }
    }

    // Restarts the application
    private void restartMainActivity() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
