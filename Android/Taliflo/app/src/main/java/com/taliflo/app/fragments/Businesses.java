package com.taliflo.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.taliflo.app.R;
import com.taliflo.app.activities.UserDetail;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.network.RequestUsers;
import com.taliflo.app.model.BusinessStore;
import com.taliflo.app.model.User;
import com.taliflo.app.utilities.ActionBarHelper;

import java.util.ArrayList;

/**
 * Created by Caswell on 1/20/2014.
 */
public class Businesses extends Fragment {

    private final String TAG = "taliflo.Businesses";

    // Layout views
    private ArrayList<User> businesses = new ArrayList<User>();
    private ListView listView;
    private UserAdapter adapter;
    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    // Search filtering
    private ArrayList<User>filtered = new ArrayList<User>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Businesses - onCreate called.");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "Businesses - onCreateView called.");

        View v = inflater.inflate(R.layout.fragment_businesses, container, false);
        listView = (ListView) v.findViewById(R.id.businesses_listView);
        adapter = new UserAdapter(getActivity(), businesses);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(openUserDetail);

        BusinessStore businessStore = BusinessStore.getInstance();
        businessStore.setBusinesses(businesses);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        if (businesses.isEmpty()) {
            RequestUsers usersRequest = new RequestUsers(adapter, businesses, "business", getActivity(), R.id.businesses_progressView);
            usersRequest.execute();
            Log.i(TAG, "Requesting businesses... ");
        }

        Log.i(TAG, "# of Businesses: " + businesses.size());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.businesses, menu);
        abHelper.fragmentOnCreateOptionsMenu(getActivity(), menu, adapter, listView, businesses, filtered,
                getActivity().getResources().getString(R.string.search_hint_businesses));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        abHelper.onOptionsItemSelected(getActivity(), item);
        return super.onOptionsItemSelected(item);
    }

    private ListView.OnItemClickListener openUserDetail = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Start User Detail Activity
            Intent i = new Intent(getActivity(), UserDetail.class);
            i.putExtra("User", (User) adapter.getItem(position));
            startActivityForResult(i, 30);
        }
    };

}
