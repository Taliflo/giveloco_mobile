package com.taliflo.app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.adapters.TransactionsAdapter;
import com.taliflo.app.api.TalifloRestAPI;
import com.taliflo.app.model.Transaction;
import com.taliflo.app.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

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
            RequestUser requestUser = new RequestUser(user, 5);
            requestUser.execute();
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

        public RequestUser(User user, int userId) {
            this.user = user;
            this.userId = userId;
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
        }

        @Override
        protected  void onPostExecute(String result) {
            super.onPostExecute(result);

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
            TalifloRestAPI restAPI = TalifloRestAPI.getInstance();
            String query = restAPI.queryUserID(userId);
            String jsonResult = restAPI.getJsonResult(query);
            JSONObject resultObject = new JSONObject(jsonResult);
            user = new User(resultObject);
        }
    }
}
