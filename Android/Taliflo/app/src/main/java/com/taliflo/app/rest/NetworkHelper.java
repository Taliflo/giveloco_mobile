package com.taliflo.app.rest;

import android.util.Log;

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Caswell on 1/7/2014.
 */
public final class NetworkHelper {

    private final String TAG = "Taliflo.NetworkHelper";

    private final String base = "http://api-dev.taliflo.com/";
    //private final String base = "http://sheltered-wave-9353.herokuapp.com/";

    // Request URLs
    public final String USERS_URL = base + "v1/users";

    private final String LOGIN_URL = base + "user/login";
    private final String LOGOUT_URL = base + "user/logout";
    private final String SIGNUP_URL = base + "user/signup";
    private final String CAUSES_URL = base + "users/role/cause";
    private final String BUSINESSES_URL = base + "users/role/business";

    // Response codes
    public final int STATUS_OK = 200;
    public final int STATUS_OK_NO_CONTENT = 204;
    public final int STATUS_CLIENT_ERROR = 422;
    public final int STATUS_SERVER_ERROR = 500;

    // Timeouts
    public final int CONNECTION_TIMEOUT = 20000;
    public final int SO_TIMEOUT = 20000;

    // Session strategy action strings
    public final String ACTION_LOGIN = "login";
    public final String ACTION_LOGOUT = "logout";
    public final String ACTION_SIGNUP = "signup";
    public final String ACTION_REQ_INDV = "request_individual";
    public final String ACTION_REQ_CAUSES = "request_causes";
    public final String ACTION_REQ_BUSINESSES = "request_businesses";
    public final String ACTION_REQ_USERS = "request_users";

    private static NetworkHelper instance = null;

    private NetworkHelper() {}

    public static NetworkHelper getInstance() {
        if (instance == null) {
            synchronized (NetworkHelper.class) {
                if (instance == null) {
                    instance = new NetworkHelper();
                }
            }
        }
        return instance;
    }

    public String queryUserID(int id) {
        return USERS_URL + "/" + id;
    }

    public String requestStrategy(String action, HashMap<String, String> params) {

        String result = "", jsonString = "";

        if (!( action.equals(ACTION_LOGIN) || action.equals(ACTION_LOGOUT) || action.equals(ACTION_SIGNUP)
                || action.equals(ACTION_REQ_INDV) || action.equals(ACTION_REQ_CAUSES) || action.equals(ACTION_REQ_BUSINESSES))) {
            Log.e(TAG, "Invalid action: " + action);
            return null;
        }

        if (action.equals(ACTION_LOGIN)) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("email", params.get("email"));
            jsonObj.addProperty("password", params.get("password"));
            jsonString = jsonObj.toString();
        }

        if (action.equals(ACTION_SIGNUP)) {
            JsonObject userObj = new JsonObject();
            userObj.addProperty("role", "individual");
            userObj.addProperty("first_name", params.get("firstName"));
            userObj.addProperty("last_name", params.get("lastName"));
            userObj.addProperty("email", params.get("email"));
            userObj.addProperty("password", params.get("password"));
            userObj.add("company_name", null);
            userObj.add("website", null);
            userObj.add("street_address", null);
            userObj.add("country", null);
            userObj.add("state", null);
            userObj.add("zip", null);
            userObj.add("phone", null);
            userObj.add("tags", null);
            userObj.add("description", null);
            userObj.add("summary", null);

            JsonObject jsonObj = new JsonObject();
            jsonObj.add("user", userObj);
            jsonString = jsonObj.toString();
        }

        // Set the HTTP Connection Parameters
        /*HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);*/

        try {
            //HttpClient client = new DefaultHttpClient(httpParams);
            HttpClient client = new DefaultHttpClient();
            HttpRequestBase request = null;

            if (action.equals(ACTION_LOGIN) || action.equals(ACTION_SIGNUP)) {
                request = new HttpPost(LOGIN_URL);
                StringEntity entity = new StringEntity(jsonString, "UTF-8");
                ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
            }

            if (action.equals(ACTION_LOGOUT)) {
                request = new HttpDelete(LOGOUT_URL);
                request.setHeader("X-Session-Token", params.get("authToken"));
            }

            if (action.equals(ACTION_REQ_INDV)) {
                request = new HttpGet(USERS_URL + "/" + params.get("uid"));
                request.setHeader("X-Session-Token", params.get("authToken"));
            }

            if (action.equals(ACTION_REQ_CAUSES)) {
                request = new HttpGet("http://api-dev.taliflo.com/v1/users/role/cause?id=" + params.get("uid"));
                request.setHeader("X-Session-Token", params.get("authToken"));
            }

            if (action.equals(ACTION_REQ_BUSINESSES)) {
                request = new HttpGet("http://api-dev.taliflo.com/v1/users/role/business?id=" + params.get("uid"));
                request.setHeader("X-Session-Token", params.get("authToken"));
            }

            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");

            HttpResponse response = client.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            Log.i(TAG, "Response status: " + responseStatus);

            // GET successful
            if (responseStatus == 200) {

                Log.i(TAG, "Response Status: " + responseStatus);
                result = getResponseEntity(response.getEntity());
                Log.i(TAG, "Reponse:\n" + result);
            }

            // Signup successful
            if (responseStatus == 201) {
                Log.i(TAG, "Signup successful");
                result = getResponseEntity(response.getEntity());
                Log.i(TAG, "Reponse:\n" + result);
            }

            // Logout successful
            if (responseStatus == 204) {
                result = "204 No content.";
            }

            if (responseStatus == 401) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getResponseEntity(HttpEntity responseEntity) throws Exception {
        InputStream is = responseEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}
