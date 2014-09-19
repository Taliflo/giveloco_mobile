package com.taliflo.app.rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Caswell on 1/11/2014.
 */
public class RequestUsers extends AsyncTask<String, Integer, String> {

    // Log tag
    private final String TAG = "Talifo.RequestUsers";

    private ArrayList<User> userList;
    private UserAdapter adapter;
    private String userRole;
    private Activity activity;
    private int progressViewID;
    private RelativeLayout progressView;

    private long startTime;
    private long endTime;

    public RequestUsers (UserAdapter adapter, ArrayList<User> userList, String userRole, Activity activity, int progressViewID) {
        this.userList = userList;
        this.adapter = adapter;
        this.userRole = userRole;
        this.activity = activity;
        this.progressViewID = progressViewID;
        progressView = (RelativeLayout) activity.findViewById(progressViewID);
    }

    @Override
    protected String doInBackground (String... params) {
        try {
            parseUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute (String result) {
        super.onPostExecute(result);
        progressView.setVisibility(View.GONE);
        sortAlphabetically(userList);
        adapter.notifyDataSetChanged();

        endTime = android.os.SystemClock.uptimeMillis();
        Log.i(TAG, "Request users [" + userRole + "] execution time: " + (endTime - startTime) + " ms");

        // Determine redeemable businesses
        if (userRole.equals("business")) {
            User currentUser = UserStore.getInstance().getCurrentUser();
            currentUser.determineRedeemableBusinesses();
        }
    }

    private void parseUsers () throws Exception {
        startTime = android.os.SystemClock.uptimeMillis();

        TalifloRestHelper restAPI = TalifloRestHelper.getInstance();
        String query = restAPI.QUERY_USERS;
        String jsonResult = restAPI.getJsonResult(query);
        /** Parsing result to retrieve the contents **/
        JSONArray resultArray = new JSONArray(jsonResult);
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject jsonObject = resultArray.getJSONObject(i);

            if (jsonObject.getString("role").equals(userRole))
                userList.add(new User(jsonObject));
        }
    }

    private void sortAlphabetically(List<User> userList) {
        Collections.sort(userList, new Comparator<User>(){
            @Override
            public int compare(User arg0, User arg1){
                return new String(arg0.getCompanyName()).compareTo(arg1.getCompanyName());
            }
        });
    }

}
