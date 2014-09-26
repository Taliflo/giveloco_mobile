package com.taliflo.app.rest;

import android.support.annotation.IntegerRes;
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
    public final int ACTION_LOGIN = 1;
    public final int ACTION_LOGOUT = 2;
    public final int ACTION_SIGNUP = 3;
    public final int ACTION_REQ_INDV = 4;
    public final int ACTION_REQ_CAUSES = 5;
    public final int ACTION_REQ_BUSINESSES = 6;
    public final int ACTION_REQ_USERS = 7;

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

    public String requestStrategy(int action, HashMap<String, String> params) {

        String result = "", jsonString = "", logAction = "";

        try {

            // Set the HTTP Connection Parameters
            /*HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);*/

            //HttpClient client = new DefaultHttpClient(httpParams);
            HttpClient client = new DefaultHttpClient();
            HttpRequestBase request = null;

            switch (action) {
                case ACTION_LOGIN:
                    JsonObject loginObj = new JsonObject();
                    loginObj.addProperty("email", params.get("email"));
                    loginObj.addProperty("password", params.get("password"));
                    jsonString = loginObj.toString();

                    request = new HttpPost(LOGIN_URL);
                    StringEntity loginEntity = new StringEntity(jsonString, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) request).setEntity(loginEntity);

                    logAction = "Login";
                    break;

                case ACTION_LOGOUT:
                    request = new HttpDelete(LOGOUT_URL);
                    request.setHeader("X-Session-Token", params.get("authToken"));

                    logAction = "Logout";
                    break;

                case ACTION_SIGNUP:
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

                    JsonObject signupObj = new JsonObject();
                    signupObj.add("user", userObj);
                    jsonString = signupObj.toString();

                    request = new HttpPost(SIGNUP_URL);
                    StringEntity signupEntity = new StringEntity(jsonString, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) request).setEntity(signupEntity);

                    logAction = "Singup";
                    break;

                case ACTION_REQ_INDV:
                    request = new HttpGet(USERS_URL + "/" + params.get("uid"));
                    request.setHeader("X-Session-Token", params.get("authToken"));

                    logAction = "Request individual";
                    break;

                case ACTION_REQ_CAUSES:
                    request = new HttpGet("http://api-dev.taliflo.com/v1/users/role/cause?id=" + params.get("uid"));
                    request.setHeader("X-Session-Token", params.get("authToken"));

                    logAction = "Request causes";
                    break;

                case ACTION_REQ_BUSINESSES:
                    request = new HttpGet("http://api-dev.taliflo.com/v1/users/role/business?id=" + params.get("uid"));
                    request.setHeader("X-Session-Token", params.get("authToken"));

                    logAction = "Request buinesses";
                    break;

                default:
                    Log.e(TAG, "Invalid action: " + action);
                    return null;

            }

            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");

            HttpResponse response = client.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            Log.i(TAG, "Response status: " + responseStatus);

            switch(responseStatus) {
                case 200:
                    // GET successful
                    result = getResponseEntity(response.getEntity());
                    Log.i(TAG, logAction + " successful");
                    //Log.i(TAG, "Reponse:\n" + result);
                    break;

                case 201:
                    // Login, Signup successful
                    result = getResponseEntity(response.getEntity());
                    Log.i(TAG, logAction + " successful");
                    Log.i(TAG, "Reponse:\n" + result);
                    break;

                case 204:
                    // Logout successful
                    result = Integer.toString(responseStatus);
                    Log.i(TAG, logAction + " successful");
                    break;

                default:
                    Log.e(TAG, logAction + " unsuccessful");
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
