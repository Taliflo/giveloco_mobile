package com.taliflo.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.taliflo.app.R;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.BusinessStore;
import com.taliflo.app.model.CauseStore;
import com.taliflo.app.model.User;
import com.taliflo.app.utilities.ActionBarHelper;

import java.util.ArrayList;
import java.util.List;

public class UserSupport extends Activity {

    private final String TAG = "Taliflo.UserSupport";

    // Layout views
    private ListView listView;

    // Member variables
    private UserAdapter adapter;
    private ArrayList<User> support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_user_support);

        // Wire up views
        listView = (ListView) findViewById(R.id.userSupport_listView);

        // Retrieve intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            support = new ArrayList<User>();
            final int[] suppIDs = extras.getIntArray("Support");

            if (extras.getString("Role").equals("business")) {
                setTitle(getResources().getString(R.string.userSupport_titleSupportedCauses));

                // Retrieve list of supported causes
                CauseStore causeStore = CauseStore.getInstance();
                List<User> causes = causeStore.getCauses();
                for (int i = 0; i < suppIDs.length; i++) {

                }

            }

            if (extras.getString("Role").equals("cause")) {
                setTitle(getResources().getString(R.string.userSupport_titleSupportingBusinesses));

                // Retrieve list of supporting businesses
                BusinessStore businessStore = BusinessStore.getInstance();
                List<User> businesses = businessStore.getBusinesses();
                for (int i = 0; i < suppIDs.length; i++) {
                    
                }
            }

            adapter = new UserAdapter(this, support);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(openUserDetail);
        }

        BusinessStore businessStore = BusinessStore.getInstance();
        Log.i(TAG, "First listed business " + businessStore.getBusinesses().get(0).getCompanyName());
    }

    private ListView.OnItemClickListener openUserDetail = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Start User Detail Activity
            Intent i = new Intent(getApplicationContext(), UserDetail.class);
            i.putExtra("User", support.get(position));
            startActivityForResult(i, 1);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        ActionBarHelper helper = ActionBarHelper.getInstance();
        helper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    class UserIdPredicate implements Predicate<User> {

        private int id;

        public UserIdPredicate(int id) {
            this.id = id;
        }

        @Override
        public boolean apply(@Nullable User user) {
            return user.getId().equals(""+id);
        }
    }

}
