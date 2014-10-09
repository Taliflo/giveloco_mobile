package com.taliflo.app.network;

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
import java.util.HashMap;
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
    private UserStore userStore;

    private long startTime;
    private long endTime;

    public RequestUsers (UserAdapter adapter, ArrayList<User> userList, String userRole, Activity activity, int progressViewID) {
        this.userList = userList;
        this.adapter = adapter;
        this.userRole = userRole;
        this.activity = activity;
        this.userStore = UserStore.getInstance();
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

        NetworkHelper networkHelper = NetworkHelper.getInstance();

        HashMap<String, String> params = userStore.getLoggedInCredentials();
        int action = 0;
        if (userRole.equals("cause"))
            action = networkHelper.ACTION_REQ_CAUSES;

        if (userRole.equals("business"))
            action = networkHelper.ACTION_REQ_BUSINESSES;

        String jsonResult = networkHelper.requestStrategy(action, params);

        /** Parsing result to retrieve the contents **/
        JSONArray resultArray = new JSONArray(jsonResult);
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject jsonObject = resultArray.getJSONObject(i);
            if (jsonObject.getBoolean("is_published") == true) {
                userList.add(new User(jsonObject));
            }
        }
    }

    private void sortAlphabetically(List<User> userList) {
        Collections.sort(userList, new Comparator<User>(){
            @Override
            public int compare(User arg0, User arg1){
                return arg0.getCompanyName().compareTo(arg1.getCompanyName());
            }
        });
    }

}
