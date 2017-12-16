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
import com.taliflo.app.model.CauseStore;
import com.taliflo.app.model.User;
import com.taliflo.app.utilities.ActionBarHelper;

import java.util.ArrayList;

/**
 * Created by Caswell on 1/18/2014.
 */
public class Causes extends Fragment {

    // Member variables
    private String TAG = "taliflo.Explore";

    private ArrayList<User> causes = new ArrayList<User>();
    private ListView listView;
    private UserAdapter adapter;
    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    // Search filtering variables
    private ArrayList<User> filtered = new ArrayList<User>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Causes - onCreate called.");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "Causes - onCreateView called.");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_causes, container, false);
        listView = (ListView) v.findViewById(R.id.causes_listView);
        adapter = new UserAdapter(getActivity(), causes);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(openUserDetail);

        CauseStore causeStore = CauseStore.getInstance();
        causeStore.setCauses(causes);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        if (causes.isEmpty()) {
            RequestUsers usersRequest = new RequestUsers(adapter, causes, "cause", getActivity(), R.id.causes_progressView);
            usersRequest.execute();
            Log.i(TAG, "Requesting causes... ");
        }

        Log.i(TAG, "# of Causes: " + causes.size());
    }
//----
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.causes, menu);
        abHelper.fragmentOnCreateOptionsMenu(getActivity(), menu, adapter, listView, causes, filtered,
                getActivity().getResources().getString(R.string.search_hint_causes));
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
