package com.taliflo.app.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.taliflo.app.R;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.BusinessStore;
import com.taliflo.app.model.CauseStore;
import com.taliflo.app.model.User;
import com.taliflo.app.utilities.ActionBarHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Search extends Activity {

    // Layout views
    private ListView listView;

    // Member variables
    private UserAdapter adapter;
    private ArrayList<User> businessResults;
    private ArrayList<User> causeResults;

    private Activity thisActiv;
    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        thisActiv = this;

        listView = (ListView) findViewById(R.id.search_listView);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Retrieve the search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            NamePredicate searchByName = new NamePredicate(query);

            BusinessStore businessStore = BusinessStore.getInstance();
            businessResults = new ArrayList<User>(Collections2.filter(businessStore.getBusinesses(), searchByName));

            CauseStore causeStore = CauseStore.getInstance();
            causeResults = new ArrayList<User>(Collections2.filter(causeStore.getCauses(), searchByName));

            businessResults.addAll(causeResults);

            sortAlphabetically(businessResults);
            adapter = new UserAdapter(this, businessResults);

            listView.setAdapter(adapter);

            setTitle(getResources().getString(R.string.title_activity_search) + ": " + query);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        //abHelper.onCreateOptionsMenu(thisActiv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //abHelper.onOptionsItemSelected(thisActiv, item);
        return super.onOptionsItemSelected(item);
    }

    class NamePredicate implements Predicate<User> {

        private String companyName;

        public NamePredicate(String companyName) {
            this.companyName = companyName;
        }

        @Override
        public boolean apply(User user) {
            return user.getCompanyName().contains(companyName);
        }
    }

    private void sortAlphabetically(List<User> userList) {
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User arg0, User arg1) {
                return new String(arg0.getCompanyName()).compareTo(arg1.getCompanyName());
            }
        });
    }
}
