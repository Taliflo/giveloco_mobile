package com.taliflo.app.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.adapters.TransactionsAdapter;
import com.taliflo.app.network.NetworkHelper;
import com.taliflo.app.model.Transaction;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.utilities.ActionBarHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Caswell on 1/20/2014.
 */
public class MyAccount extends Fragment {

    // Log tag
    private final String TAG = "taliflo.MyAccount";

    private User user;

    private TextView name, credits;
    private ListView listView;
    private ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
    private TransactionsAdapter adapter;
    private UserStore userStore = UserStore.getInstance();

    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MyAccount - onCreate called.");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "MyAccount - onCreateView called.");

        View v = inflater.inflate(R.layout.fragment_myaccount, container, false);

        name = (TextView) v.findViewById(R.id.myAccount_name);
        credits = (TextView) v.findViewById(R.id.myAccount_credits);
        listView = (ListView) v.findViewById(R.id.myAccount_transactions);
        adapter = new TransactionsAdapter(getActivity(), transactionsList);
        listView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (userStore.getCurrentUser() == null) {
            int uid = Integer.parseInt(userStore.getUid());
            RequestUser requestUser = new RequestUser(getActivity(), user, uid, name, credits, transactionsList);
            requestUser.execute();
            Log.i(TAG, "Requesting user...");
        }

        /* else {
            user = savedInstanceState.getParcelable("loggedInUser");
            transactionsList = savedInstanceState.getParcelableArrayList("transactions");
            name.setText(user.getFullName());
            credits.setText("C " + user.getBalance());
            adapter.notifyDataSetChanged();
        }*/

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.no_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        abHelper.onOptionsItemSelected(getActivity(), item);
        return super.onOptionsItemSelected(item);
    }

    private class RequestUser extends AsyncTask<String, Integer, String> {

        // Log tag
        private final String TAG = "taliflo.RequestUser";

        private User user;
        private int userId;
        private Activity activity;
        private RelativeLayout progressView;
        private UserStore userStore = UserStore.getInstance();

        private long startTime;
        private long endTime;

        private TextView name, credits;
        private ArrayList<Transaction> transactionsList;

        public RequestUser(Activity activity, User user, int userId, TextView name, TextView credits, ArrayList<Transaction> transactionsList) {
            this.user = user;
            this.userId = userId;
            this.activity = activity;
            this.name = name;
            this.credits = credits;
            this.transactionsList = transactionsList;
            progressView = (RelativeLayout) activity.findViewById(R.id.myAccount_progressView);
        }

        @Override
        protected String doInBackground (String... params) {
            try {
                parseUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Executing RequestUser");

            progressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected  void onPostExecute(String result) {
            super.onPostExecute(result);
            progressView.setVisibility(View.GONE);

            boolean isNull = (user == null);
            Log.i(TAG, "User is null: " + isNull);

            name.setText(user.getFullName());
            credits.setText("C " + user.getBalance());
            transactionsList = user.getTransactionsAll();
            adapter.notifyDataSetChanged();
            Log.i(TAG, user.getFirstName());
            //Log.i(TAG, user.getTransactionsAccepted()[0].getAmount());

            endTime = android.os.SystemClock.uptimeMillis();
            Log.i(TAG, "Request user execution time: " + (endTime - startTime) + " ms");
        }

        private void parseUser() throws Exception {
            startTime = android.os.SystemClock.uptimeMillis();

            NetworkHelper networkHelper = NetworkHelper.getInstance();
            HashMap<String, String> extras = userStore.getLoggedInCredentials();
            String jsonResult = networkHelper.requestStrategy(NetworkHelper.ACTION_REQ_INDV, extras);
            JSONObject resultObject = new JSONObject(jsonResult);
            Log.i(TAG, resultObject.toString());
            user = new User(resultObject);
            userStore.setCurrentUser(user);
        }
    }
}
