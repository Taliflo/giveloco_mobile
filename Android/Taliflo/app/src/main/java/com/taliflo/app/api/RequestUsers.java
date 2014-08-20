package com.taliflo.app.api;

import android.os.AsyncTask;
import android.util.Log;

import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Caswell on 1/11/2014.
 */
public class RequestUsers extends AsyncTask<String, Integer, String> {

    // Log cat tag
    private final String TAG = "Talifo.RequestBusinesses";

    private ArrayList<User> userList;
    private UserAdapter adapter;
    private String userRole;

    private ArrayList<User> businessList;
    private ArrayList<User> causeList;

    public RequestUsers (ArrayList<User> userList, UserAdapter adapter, String userRole) {
        this.userList = userList;
        this.adapter = adapter;
        this.userRole = userRole;
    }

    public RequestUsers(ArrayList<User> userList, UserAdapter adapter) {
        this.userList = userList;
        this.adapter = adapter;
    }

    public RequestUsers(UserAdapter adapter, ArrayList<User> businessList, ArrayList<User> causeList) {
        this.adapter = adapter;
        this.businessList = businessList;
        this.causeList = causeList;
    }

    @Override
    protected String doInBackground (String... params) {
        try {
            parseUsers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
        sortAlphabetically(businessList);
        sortAlphabetically(causeList);
        adapter.notifyDataSetChanged();
    }

    private void parseUsers () throws IOException, JSONException, Exception {

        // Construct the HTTP request message
        TalifloRestAPI restAPI = TalifloRestAPI.getInstance();
        HttpGet get = new HttpGet(restAPI.QUERY_USERS);
/*        if (userRole.equals("business")) {
            get = new HttpGet(restAPI.QUERY_BUSINESSES);
        }
        else if (userRole.equals("cause")) {
            get = new HttpGet(restAPI.QUERY_CAUSES);
        }
        else
            throw new Exception("Invalid user role"); */


        // Set the HTTP parameters
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, restAPI.CONNECTIONTIMEOUT);
        HttpConnectionParams.setSoTimeout(params, restAPI.SOTIMEOUT);

        HttpClient client = new DefaultHttpClient(params);
        HttpResponse response = client.execute(get);
        int responseStatus = response.getStatusLine().getStatusCode();

        if (responseStatus == restAPI.STATUS_OK) {
            HttpEntity responseEntity = response.getEntity();
            InputStream is = responseEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null)
                sb.append(line + '\n');
            String result = sb.toString();
            is.close();

            /** Parsing result to retrieve the contents **/
            JSONArray resultArray = new JSONArray(result);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);

                if (jsonObject.getString("role").equals("business"))
                    businessList.add(new User(jsonObject));
                else if (jsonObject.getString("role").equals("cause"))
                    causeList.add(new User(jsonObject));
            }
        } else {
            Log.e(TAG, "HTTP Request Error");
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
