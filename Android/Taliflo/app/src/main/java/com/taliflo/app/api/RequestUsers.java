package com.taliflo.app.api;

import android.os.AsyncTask;
import android.util.Log;

import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.model.Business;
import com.taliflo.app.model.Cause;
import com.taliflo.app.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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

/**
 * Created by Caswell on 1/11/2014.
 */
public class RequestUsers extends AsyncTask<String, Integer, String> {

    // Log cat tag
    private final String TAG = "Talifo.RequestBusinesses";

    private ArrayList<User> userList = new ArrayList<User>();
    private UserAdapter adapter;
    private String userRole;

    public RequestUsers (ArrayList<User> userList, UserAdapter adapter, String userRole) {
        this.userList = userList;
        this.adapter = adapter;
        this.userRole = userRole;
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
        adapter.notifyDataSetChanged();
    }

    private void parseUsers () throws IOException, JSONException, Exception {

        // Construct the HTTP request message
        TalifloRestAPI restAPI = TalifloRestAPI.getInstance();
        HttpGet get;
        if (userRole.equals("business")) {
            get = new HttpGet(restAPI.QUERY_BUSINESSES);
        }
        else if (userRole.equals("cause")) {
            get = new HttpGet(restAPI.QUERY_CAUSES);
        }
        else
            throw new Exception("Invalid user role");


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
            User user;
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);

                if (userRole.equals("business"))
                    user = new Business();
                else
                    user = new Cause();

                user.setName(jsonObject.getString("company_name"));
                user.setSummary(jsonObject.getString("summary"));
                user.setType(i % 2);

                // Add to list
                userList.add(user);
            }
        } else {
            Log.e(TAG, "HTTP Request Error");
        }
    }

}
