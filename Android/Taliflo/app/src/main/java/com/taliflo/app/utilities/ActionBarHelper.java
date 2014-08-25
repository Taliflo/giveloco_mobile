package com.taliflo.app.utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.taliflo.app.R;
import com.taliflo.app.activities.BillingInfo;
import com.taliflo.app.activities.MainActivity;

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

    public boolean onOptionsItemSelected(Activity activity, MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                exitApplication(activity);
                return true;

            case R.id.action_search:
                return true;

            case R.id.action_updateBillingInfo:
                Intent i = new Intent(activity, BillingInfo.class);
                activity.startActivityForResult(i, 555);
                return true;
        }
        return true;
    }

}
