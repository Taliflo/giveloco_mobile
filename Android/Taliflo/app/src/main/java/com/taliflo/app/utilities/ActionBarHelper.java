package com.taliflo.app.utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.activities.BillingInfo;
import com.taliflo.app.activities.MainActivity;
import com.taliflo.app.activities.Search;

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

            case R.id.action_logout:
                exitApplication(activity);
                return true;

            case R.id.action_search:
                //activity.onSearchRequested();
                return true;

            case R.id.action_updateBillingInfo:
                Intent i = new Intent(activity, BillingInfo.class);
                activity.startActivityForResult(i, 555);
                return true;
        }
        return true;
    }

}
