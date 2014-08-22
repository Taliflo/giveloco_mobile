package com.taliflo.app.api;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Caswell on 1/7/2014.
 */
public final class TalifloRestAPI {

    private final String TAG = "Taliflo.TalifloRestAPI";

    private final String base = "http://api-dev.taliflo.com/v1";
    //private final String base = "http://sheltered-wave-9353.herokuapp.com/";

    // Request URLs
    public final String QUERY_USERS = base + "/users";
    public final String QUERY_BUSINESSES = base + "/users?role=business";
    public final String QUERY_CAUSES = base + "/users?role=cause";
    public final String QUERY_INDIVDUALS = base + "/users?role=individual";
    public final String QUERY_TRANSACTIONS = base + "/transactions";

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

    private static TalifloRestAPI instance = null;

    private TalifloRestAPI() {}

    public static TalifloRestAPI getSharedInstance() {
        if (instance == null) {
            instance = new TalifloRestAPI();
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

}
