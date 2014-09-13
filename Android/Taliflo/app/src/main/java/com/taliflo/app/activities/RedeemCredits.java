package com.taliflo.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.taliflo.app.R;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.BusinessStore;
import com.taliflo.app.model.CauseStore;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.utilities.ActionBarHelper;

import java.util.ArrayList;
import java.util.List;

public class RedeemCredits extends Activity {

    private final String TAG = "Taliflo.RedeemCredits";

    // Layout
    private ListView listView;
    private UserAdapter adapter;
    private RelativeLayout msgLayout;

    private User currUser = UserStore.getInstance().getCurrentUser();

    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_credits);

        // Get all supporting businesses
        if (currUser.getRedeemableBusinesses().size() != 0) {
            Log.i(TAG, "" + currUser.getRedeemableBusinesses());

            // Layout
            listView = (ListView) findViewById(R.id.redeemCredits_listView);
            adapter = new UserAdapter(this, currUser.getRedeemableBusinesses());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(openRedeem);
        } else {
            msgLayout = (RelativeLayout) findViewById(R.id.redeemCredits_noneMsgLayout);
            msgLayout.setVisibility(View.VISIBLE);
        }
    }

    private ListView.OnItemClickListener openUserDetail = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Start User Detail Activity
            Intent i = new Intent(getApplicationContext(), UserDetail.class);
            i.putExtra("User", (User) adapter.getItem(position));
            startActivityForResult(i, 1);
        }
    };

    private ListView.OnItemClickListener openRedeem = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getApplicationContext(), Redeem.class);
            i.putExtra("Business", (User) adapter.getItem(position));
            startActivity(i);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.redeem_credits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        abHelper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

}
