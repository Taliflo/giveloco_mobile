package com.taliflo.app.api;

import android.os.AsyncTask;

import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.User;

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

    public RequestUsers (UserAdapter adapter, ArrayList<User> userList, String userRole) {
        this.userList = userList;
        this.adapter = adapter;
        this.userRole = userRole;
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
    }

    @Override
    protected void onPostExecute (String result) {
        super.onPostExecute(result);
        sortAlphabetically(userList);
        adapter.notifyDataSetChanged();
    }

    private void parseUsers () throws Exception {

        TalifloRestHelper restAPI = TalifloRestHelper.getSharedInstance();
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
