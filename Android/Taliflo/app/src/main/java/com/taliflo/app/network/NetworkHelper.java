package com.taliflo.app.network;

import android.util.Log;

import com.google.gson.JsonObject;
import com.taliflo.app.model.UserStore;

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

import java.io.BufferedReader;
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

    private final String URL_LOGIN = base + "user/login";
    private final String URL_LOGOUT = base + "user/logout";
    private final String URL_SIGNUP = base + "user/signup";
    private final String URL_CAUSES = base + "users/role/cause";
    private final String URL_BUSINESSES = base + "users/role/business";

    // Response codes
    public final int STATUS_OK = 200;
    public final int STATUS_OK_NO_CONTENT = 204;
    public final int STATUS_CLIENT_ERROR = 422;
    public final int STATUS_SERVER_ERROR = 500;

    // Timeouts
    public final int CONNECTION_TIMEOUT = 5000; // connection timeout, 5s
    public final int SO_TIMEOUT = 30000; // read timeout, 30s

    // Session strategy action strings
    public static final int ACTION_LOGIN = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_SIGNUP = 3;
    public static final int ACTION_REQ_INDV = 4;
    public static final int ACTION_REQ_CAUSES = 5;
    public static final int ACTION_REQ_BUSINESSES = 6;
    public static final int ACTION_REQ_USERS = 7;

    private final String HEADER_AUTH_TOKEN = "X-Session-Token";
    private final String HEADER_CONTENT_TYPE = "Content-type";
    private final String HEADER_ACCEPT = "Accept";

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

    public String requestStrategy(int action, HashMap<String, String> args) {

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
                    loginObj.addProperty("email", args.get("email"));
                    loginObj.addProperty("password", args.get("password"));
                    jsonString = loginObj.toString();

                    request = new HttpPost(URL_LOGIN);
                    StringEntity loginEntity = new StringEntity(jsonString, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) request).setEntity(loginEntity);

                    logAction = "Login";
                    break;

                case ACTION_LOGOUT:
                    request = new HttpDelete("http://api-dev.taliflo.com/user/logout");
                    UserStore userStore = UserStore.getInstance();
                    request.setHeader(HEADER_AUTH_TOKEN, userStore.getAuthToken());

                    logAction = "Logout";
                    break;

                case ACTION_SIGNUP:
                    JsonObject userObj = new JsonObject();
                    userObj.addProperty("role", "individual");
                    userObj.addProperty("first_name", args.get("firstName"));
                    userObj.addProperty("last_name", args.get("lastName"));
                    userObj.addProperty("email", args.get("email"));
                    userObj.addProperty("password", args.get("password"));
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

                    request = new HttpPost(URL_SIGNUP);
                    StringEntity signupEntity = new StringEntity(jsonString, "UTF-8");
                    ((HttpEntityEnclosingRequestBase) request).setEntity(signupEntity);

                    logAction = "Singup";
                    break;

                case ACTION_REQ_INDV:
                    request = new HttpGet(USERS_URL + "/" + args.get("uid"));
                    request.setHeader(HEADER_AUTH_TOKEN, args.get("authToken"));

                    logAction = "Request individual";
                    break;

                case ACTION_REQ_CAUSES:
                    request = new HttpGet("http://api-dev.taliflo.com/v1/users/role/cause?id=" + args.get("uid"));
                    request.setHeader(HEADER_AUTH_TOKEN, args.get("authToken"));

                    logAction = "Request causes";
                    break;

                case ACTION_REQ_BUSINESSES:
                    request = new HttpGet("http://api-dev.taliflo.com/v1/users/role/business?id=" + args.get("uid"));
                    request.setHeader(HEADER_AUTH_TOKEN, args.get("authToken"));

                    logAction = "Request buinesses";
                    break;

                default:
                    Log.e(TAG, "Invalid action: " + action);
                    return null;

            }

            request.setHeader(HEADER_CONTENT_TYPE, "application/json");
            request.setHeader(HEADER_ACCEPT, "application/json");

            HttpResponse response = client.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            Log.i(TAG, "Response status: " + responseStatus);

            switch(responseStatus) {
                case 200:
                    // GET, logout successful
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

                case 401:
                    // Unauthorized
                    Log.i(TAG, logAction + " unauthorized");
                    return null;

                case 500:
                    // Internal server error
                    Log.i(TAG, "Internal server error");
                    return null;

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
