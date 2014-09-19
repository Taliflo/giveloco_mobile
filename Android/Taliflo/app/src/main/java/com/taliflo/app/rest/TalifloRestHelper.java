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
public final class TalifloRestHelper {

    private final String TAG = "Taliflo.TalifloRestAPI";

    private final String base = "http://api-dev.taliflo.com/";
    //private final String base = "http://sheltered-wave-9353.herokuapp.com/";

    // Request URLs
    public final String QUERY_USERS = base + "v1/users";
    public final String QUERY_BUSINESSES = base + "v1/users?role=business";
    public final String QUERY_CAUSES = base + "v1/users?role=cause";
    public final String QUERY_INDIVDUALS = base + "v1/users?role=individual";
    public final String QUERY_TRANSACTIONS = base + "v1/transactions";

    private final String LOGIN_URL = base + "user/login";
    private final String LOGOUT_URL = base + "user/logout";

    // Response codes
    public final int STATUS_OK = 200;
    public final int STATUS_OK_NO_CONTENT = 204;
    public final int STATUS_CLIENT_ERROR = 422;
    public final int STATUS_SERVER_ERROR = 500;

    // Timeouts
    public final int CONNECTIONTIMEOUT = 20000;
    public final int SOTIMEOUT = 20000;

    // Intent codes
    public final String DISTRIBUTOR_NAME = "distributor_name";

    private static TalifloRestHelper instance = null;

    private TalifloRestHelper() {}

    public static TalifloRestHelper getInstance() {
        if (instance == null) {
            synchronized (TalifloRestHelper.class) {
                if (instance == null) {
                    instance = new TalifloRestHelper();
                }
            }
        }
        return instance;
    }

    public String getJsonResult(String query) throws IOException, Exception {
        // Construct the HTTP request message
        HttpGet get = new HttpGet(query);

        // Set the HTTP parameters
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, CONNECTIONTIMEOUT);
        HttpConnectionParams.setSoTimeout(params, SOTIMEOUT);

        HttpClient client = new DefaultHttpClient(params);
        HttpResponse response = client.execute(get);
        int responseStatus = response.getStatusLine().getStatusCode();

        String result = "";
        if (responseStatus == STATUS_OK) {
            HttpEntity responseEntity = response.getEntity();
            InputStream is = responseEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null)
                sb.append(line + '\n');
            result = sb.toString();
            is.close();
        } else {
            throw new Exception("HTTP Request Error");
        }

        return result;
    }

    public String queryUserID(int id) {
        return QUERY_USERS + "/" + id;
    }

    public String sessionStrategy(String action, HashMap<String, String> params) {

        String result = "", jsonString = "";

        if (!( action.equals("login") || action.equals("logout") )) {
            return null;
        }

        if (action.equals("login")) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("email", params.get("email"));
            jsonObj.addProperty("password", params.get("password"));
            jsonString = jsonObj.toString();
        }

        try {
            HttpClient client = new DefaultHttpClient();
            HttpRequestBase request = null;

            if (action.equals("login")) {
                request = new HttpPost("http://api-dev.taliflo.com/user/login");
                StringEntity entity = new StringEntity(jsonString, "UTF-8");
                ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
            }

            if (action.equals("logout")) {
                request = new HttpDelete(LOGOUT_URL);
                request.setHeader("x-session-token", params.get("authToken"));
            }

            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");

            HttpResponse response = client.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            Log.i(TAG, "Response status: " + responseStatus);

            if (responseStatus == 200) {
                HttpEntity resEntity = response.getEntity();
                InputStream is = resEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, line);
                    sb.append(line);
                }
                is.close();
                result = sb.toString();
            }

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

}
