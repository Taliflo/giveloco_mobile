package com.taliflo.app.utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.taliflo.app.R;
import com.taliflo.app.activities.BillingInfo;
import com.taliflo.app.activities.MainActivity;
import com.taliflo.app.activities.Search;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Caswell on 1/24/2014.
 */
public class ActionBarHelper {

    private static ActionBarHelper instance = null;

    private ActionBarHelper() {}

    public static ActionBarHelper getInstance() {
        if (instance == null) {
            synchronized (ActionBarHelper.class) {
                if (instance == null) {
                    instance = new ActionBarHelper();
                }
            }
        }
        return instance;
    }

    public void exitApplication (Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("EXIT", true);
        context.startActivity(i);
    }

    public void fragmentOnCreateOptionsMenu(final Activity activity, Menu menu, final UserAdapter adapter,
                                            final ListView listView, final ArrayList<User> main,
                                            ArrayList<User> filtered,String queryHint) {

        // Style search view
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(queryHint);

        for (TextView textView : findChildrenByClass(searchView, TextView.class)) {
            textView.setTextColor(Color.BLACK);
            textView.setHintTextColor(activity.getResources().getColor(R.color.med_grey));
        }

        int searchIconId = activity.getResources().getIdentifier("android:id/search_button", null, null);
        ImageView imageView = (ImageView) searchView.findViewById(searchIconId);
        imageView.setImageResource(R.drawable.ic_action_search_tb);

        // Configure the search view
       searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                MainActivity.viewPager.setPagingEnabled(false);
            }
        });
       searchView.setOnQueryTextListener(new QueryTextListener(activity, adapter, main, filtered));
       searchView.setOnCloseListener( new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.setUserList(main);
                listView.setAdapter(adapter);
                activity.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                MainActivity.viewPager.setPagingEnabled(true);
                return false;
            }
        });
    }
/*
    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Set the searchable activity
        ComponentName componentName = new ComponentName(activity, Search.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setQueryHint(activity.getResources().getString(R.string.search_hint));

        for (TextView textView : findChildrenByClass(searchView, TextView.class)) {
            textView.setTextColor(Color.BLACK);
            textView.setHintTextColor(activity.getResources().getColor(R.color.med_grey));
        }

        int searchIconId = activity.getResources().getIdentifier("android:id/search_button", null, null);
        ImageView imageView = (ImageView) searchView.findViewById(searchIconId);
        imageView.setImageResource(R.drawable.ic_action_search_tb);
    }
*/
    private class SearchQueryPredicate implements Predicate<User> {

        private String name;

        private SearchQueryPredicate (String name) {
            this.name = name;
        }

        @Override
        public boolean apply(User user) {
            return user.getCompanyName().toLowerCase().startsWith(name.toLowerCase());
        }

    }

    private <V extends View> Collection<V> findChildrenByClass(ViewGroup viewGroup, Class<V> clazz) {

        return gatherChildrenByClass(viewGroup, clazz, new ArrayList<V>());
    }

    private <V extends View> Collection<V> gatherChildrenByClass(ViewGroup viewGroup, Class<V> clazz, Collection<V> childrenFound) {

        for (int i = 0; i < viewGroup.getChildCount(); i++)
        {
            final View child = viewGroup.getChildAt(i);
            if (clazz.isAssignableFrom(child.getClass())) {
                childrenFound.add((V)child);
            }
            if (child instanceof ViewGroup) {
                gatherChildrenByClass((ViewGroup) child, clazz, childrenFound);
            }
        }

        return childrenFound;
    }

    public boolean onOptionsItemSelected(Activity activity, MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                //activity.onSearchRequested();
                return true;

            case R.id.action_redeemCredits:
                return true;

            case R.id.action_updateBillingInfo:
                Intent i = new Intent(activity, BillingInfo.class);
                activity.startActivityForResult(i, 555);
                return true;

            case R.id.action_logout:
                exitApplication(activity);
                return true;
        }
        return true;
    }
/*
    private class OnSearchClickListener implements View.OnClickListener {

        private Activity activity;
        private UserAdapter adapter;
        private ArrayList<User> filtered;

        private OnSearchClickListener(Activity activity, ArrayList<User> filtered, UserAdapter adapter) {
            this.activity = activity;
            this.filtered = filtered;
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
            activity.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            MainActivity.viewPager.setPagingEnabled(false);
            adapter.setFilteredList(filtered);
        }
    }
*/
    private class QueryTextListener implements SearchView.OnQueryTextListener {

        private final String TAG = "Taliflo.SearchQueryTextListener";

        private Activity activity;
        private ArrayList<User> filtered;
        private ArrayList<User> main;
        private UserAdapter adapter;

        private QueryTextListener(Activity activity, UserAdapter adapter, ArrayList<User> main, ArrayList<User> filtered) {
            this.activity = activity;
            this.main = main;
            this.filtered = filtered;
            this.adapter = adapter;
        }

        @Override
        public boolean onQueryTextSubmit(String s) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            if(imm.isAcceptingText()) { // verify if the soft keyboard is open
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            // To prevent method from firing which it does for some odd reason, since each fragment inflates its own menu
            //if (this != null) return false;
            if (!(s.equals(""))) {
                filtered = new ArrayList<User>(Collections2.filter(main, new SearchQueryPredicate(s)));
                adapter.setFilteredList(filtered);
                Log.i(TAG, "" + filtered.size());
                Log.i(TAG, filtered.toString());
            } else {
                if (filtered.size() > 0) {
                    filtered.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            return false;
        }

    }
/*
    private class OnCloseListener implements SearchView.OnCloseListener {

        private Activity activity;
        private ListView listView;
        private UserAdapter adapter;
        private ArrayList<User> main;

        private OnCloseListener(Activity activity, ListView listView, UserAdapter adapter, ArrayList<User> main) {
            this.activity = activity;
            this.listView = listView;
            this.adapter = adapter;
            this.main = main;

        }

        @Override
        public boolean onClose() {
            adapter.setUserList(main);
            listView.setAdapter(adapter);
            activity.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            MainActivity.viewPager.setPagingEnabled(true);

            return false;
        }
    }
*/


}

