package com.taliflo.app.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.taliflo.app.R;
import com.taliflo.app.adapters.NavDrawerAdapter;
import com.taliflo.app.adapters.TabsPagerAdapter;
import com.taliflo.app.utilities.ActionBarHelper;
import com.taliflo.app.utilities.NavDrawerInterface;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    // Logcat tag
    private final String TAG = "Taliflo.MainActivity";

  //  private DrawerLayout drawerLayout;
  //  private ListView drawerList;
  //  private ActionBarDrawerToggle drawerToggle;

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

    // Boolean variable to ensure proper back navigation. When the user presses the back button on a fragment
    // the user should be returned to home. If the user is at home, then the application should be exited
    //private boolean atHome = false;

    // Member field for the view pager
    private ViewPager viewPager;
    private TabsPagerAdapter tabsAdapter;
    private ActionBar actionBar;
    // Tab titles

    Activity thisActiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);
        thisActiv = this;

//        Intent loginIntent = new Intent(getApplicationContext(), Login.class);
//        startActivityForResult(loginIntent, 1);

        // If user selects "Logout" from another activity
        if (getIntent().getBooleanExtra("EXIT", false)) finish();


        if (savedInstanceState == null) {

            // Initialization
            viewPager = (ViewPager) findViewById(R.id.main_pager);
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

            actionBar.setSelectedNavigationItem(0);
            setTitle(tabs[0]);

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

            // Create global configuration and initialize ImageLoader with this configuration
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
            ImageLoader.getInstance().init(config);

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
    public void onTabUnselected(Tab tab, FragmentTransaction ft){

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
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
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
        ActionBarHelper helper = ActionBarHelper.getInstance();
        helper.onOptionsItemSelected(thisActiv, item);
        return super.onOptionsItemSelected(item);
    }
/*
    /**
     * Displays fragment view for selected navigation drawer list item
     * @param position
     * @param id
     */
/*    private void displayView(int position, int id) {
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
*/
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
        //drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
       // drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
/*        if (atHome) {
            finish();
        } else {
            displayView(1, 100);
        } */
        finish();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 10:
                // Successful login
                break;
            case 11:
                // If the user has pressed the back button from the Login activity
                finish();
                break;

            case 30:
                // UserDetail activity result
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
