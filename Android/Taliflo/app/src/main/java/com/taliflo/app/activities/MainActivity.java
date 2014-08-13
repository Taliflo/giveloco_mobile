package com.taliflo.app.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.taliflo.app.R;
import com.taliflo.app.adapters.NavDrawerAdapter;
import com.taliflo.app.fragments.Businesses;
import com.taliflo.app.fragments.Redeem;
import com.taliflo.app.fragments.Search;
import com.taliflo.app.utilities.NavDrawerBanner;
import com.taliflo.app.utilities.NavDrawerInterface;
import com.taliflo.app.utilities.NavDrawerItem;

import java.util.ArrayList;


public class MainActivity extends Activity {

    // Logcat tag
    private final String TAG = "Taliflo.MainActivity";

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    // Navigation drawer title
    private CharSequence drawerTitle;

    // Used to store app title
    private CharSequence appTitle;

    // Fields for the navigation drawer
    private String[] navDrawerItems;
    private String[] navDrawerSections;
    private TypedArray navDrawerIcons;
    private ArrayList<NavDrawerInterface> navDrawerEntries = new ArrayList<NavDrawerInterface>();
    private NavDrawerAdapter adapter;

    // Fragment placeholder
    private Fragment fragment = null;
    // Fragments
    private Fragment Redeem = new Redeem(), Businesses = new Businesses(), Search = new Search();

    // Boolean variable to ensure proper back navigation. When the user presses the back button on a fragment
    // the user should be returned to home. If the user is at home, then the application should be exited
    private boolean atHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent loginIntent = new Intent(getApplicationContext(), Login.class);
        startActivityForResult(loginIntent, 1);

        if (navDrawerEntries.size() == 0) {

            // Load app title
            appTitle = drawerTitle = getTitle();

            // load drawer titles
            navDrawerItems = getResources().getStringArray(R.array.main_nav_drawer_items);
            navDrawerSections = getResources().getStringArray(R.array.main_nav_drawer_sections);

            // load icons from resources
            navDrawerIcons = getResources().obtainTypedArray(R.array.main_drawer_icons);

            drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
            drawerList = (ListView) findViewById(R.id.main_left_drawer);

            navDrawerEntries = new ArrayList<NavDrawerInterface>();

            // Adding items to the navigation drawer array
            //-- Banner
            navDrawerEntries.add(new NavDrawerBanner(null, 1));
            //-- Redeem
            navDrawerEntries.add(new NavDrawerItem(navDrawerItems[0], 100, navDrawerIcons.getResourceId(0, -1)));
            //-- Businesses
            navDrawerEntries.add(new NavDrawerItem(navDrawerItems[1], 101, navDrawerIcons.getResourceId(1, -1)));
            //-- Causes
            navDrawerEntries.add(new NavDrawerItem(navDrawerItems[2], 102, navDrawerIcons.getResourceId(2, -1)));

            // Recycle the typed array
            navDrawerIcons.recycle();

            drawerList.setOnItemClickListener(new NavDrawerClickListener());

            // setting the nav drawer list adapter
            adapter = new NavDrawerAdapter(this, navDrawerEntries);
            drawerList.setAdapter(adapter);

            // enabling action bar app icon and behaving it as toggle button
            getActionBar().setDisplayHomeAsUpEnabled(true);
            // getActionBar().setHomeButtonEnabled(true);

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    R.drawable.ic_drawer, // nav menu toggle icon
                    R.string.app_name, // nav drawer open
                    R.string.app_name
            ) {
                public void onDrawerClosed(View view) {
                    getActionBar().setTitle(appTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle(drawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }

            };
            drawerLayout.setDrawerListener(drawerToggle);

            if (savedInstanceState == null) {
                // Display the Redeem fragment when the app is first opened
                displayView(1, 100);
            }

            // Create global configuration and initialize ImageLoader with this configuration
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
            ImageLoader.getInstance().init(config);

        }

    }

    private class NavDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Display view for selected navigation drawer item
            Log.d(TAG, "in NavDrawerClickListener start");
            displayView(position, navDrawerEntries.get(position).getId());
            Log.d(TAG, "in NavDrawerClickListener finish");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        // if nav drawer is opened, hide the action items
        	boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        	menu.findItem(R.id.main_action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.main_action_search:
                // Open search fragment
                displayView(-1, 999);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Displays fragment view for selected navigation drawer list item
     * @param position
     * @param id
     */
    private void displayView(int position, int id) {
        Log.d(TAG, "inside DisplayView()");
        String newTitle = "";
        // Update the main content by replacing fragments

        // Fragment unselected
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (fragment != null) {
            ft.detach(fragment);
        }

        switch (id) {

            case 100:
                fragment = Redeem;
                newTitle = navDrawerItems[id%10];
                break;

            case 101:
                fragment = Businesses;
                newTitle = navDrawerItems[id%10];
                break;

            case 999:
                fragment = Search;
                newTitle = getResources().getString(R.string.action_search);
                break;

            default:
                fragment = Redeem;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft2 = getFragmentManager().beginTransaction();

            ft2.replace(R.id.main_frame_container, fragment);
            ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft2.commit();

            // Update selected item and title, then close the drawer
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(newTitle);
            drawerLayout.closeDrawer(drawerList);



            // For back navigation
            if (fragment == Redeem)
                atHome = true;
            else
                atHome = false;

        } else {
            // Error in creating fragment
            Log.e(TAG, "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        appTitle = title;
        getActionBar().setTitle(appTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate()
     * and onConfigurationChanged()
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occured
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (atHome) {
            finish();
        } else {
            displayView(1, 100);
        }
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                // Successful login
                break;
            case 2:
                // If the user has pressed the back button from the Login activity
                finish();
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
