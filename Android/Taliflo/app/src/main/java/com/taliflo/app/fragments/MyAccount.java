package com.taliflo.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.adapters.TransactionsAdapter;
import com.taliflo.app.api.TalifloRestHelper;
import com.taliflo.app.model.Transaction;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Caswell on 1/20/2014.
 */
public class MyAccount extends Fragment {

    // Log tag
    private final String TAG = "Taliflo.MyAccount";

    private User user;

    private TextView name, credits;
    private ListView listView;
    private ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
    private TransactionsAdapter adapter;

    private UserStore userStore = UserStore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MyAccount - onCreate called.");

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

        if (transactionsList.isEmpty()) {
            RequestUser requestUser = new RequestUser(user, 4, getActivity());
            requestUser.execute();
            Log.i(TAG, "Requesting user...");
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class RequestUser extends AsyncTask<String, Integer, String> {

        // Log tag
        private final String TAG = "Taliflo.RequestUser";

        private User user;
        private int userId;
        private Activity activity;
        private RelativeLayout progressView;

        public RequestUser(User user, int userId, Activity activity) {
            this.user = user;
            this.userId = userId;
            this.activity = activity;
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

            if (user.getRole().equals("individual"))
                name.setText(user.getFullName());
            else
                name.setText(user.getCompanyName());


            credits.setText("C " + user.getBalance());


            transactionsList.addAll(Arrays.asList(user.getTransactionsCreated()));
            transactionsList.addAll(Arrays.asList(user.getTransactionsAccepted()));
            adapter.notifyDataSetChanged();
            Log.i(TAG, user.getFirstName());
            Log.i(TAG, user.getTransactionsAccepted()[0].getAmount());
        }

        private void parseUser() throws Exception {
            TalifloRestHelper restAPI = TalifloRestHelper.getSharedInstance();
            String query = restAPI.queryUserID(userId);
            String jsonResult = restAPI.getJsonResult(query);
            JSONObject resultObject = new JSONObject(jsonResult);
            user = new User(resultObject);
            userStore.setCurrentUser(user);
        }
    }
}
