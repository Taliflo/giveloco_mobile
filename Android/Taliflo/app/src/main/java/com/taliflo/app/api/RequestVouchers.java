package com.taliflo.app.api;

import android.os.AsyncTask;
import android.util.Log;

import com.taliflo.app.adapters.VoucherAdapter;
import com.taliflo.app.model.Voucher;

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
 * Created by Caswell on 1/9/2014.
 */
public class RequestVouchers extends AsyncTask<String, Integer, String> {

    // Log cat tag
    private final String TAG = "Taliflo.RequestVouchers";

    private ArrayList<Voucher> voucherList;
    private VoucherAdapter adapter;

    public RequestVouchers (ArrayList<Voucher> voucherList, VoucherAdapter adapter) {
        this.voucherList = voucherList;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground (String... params) {
        try {
            parseVouchers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute () {
    }

    @Override
    protected void onPostExecute (String result) {
        super.onPostExecute(result);
        adapter.notifyDataSetChanged();
    }

    private void parseVouchers () throws IOException, JSONException {

        // Construct the HTTP request message
        TalifloRestAPI restAPI = TalifloRestAPI.getInstance();
        HttpGet get = new HttpGet(restAPI.QUERY_VOUCHERS);

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
            while((line = reader.readLine()) != null) // Read the response line by line
                sb.append(line + '\n');
            String result = sb.toString(); // The result is here
            is.close();

            /** JSON parsing to retrieve the contents **/

            JSONArray resultArray = new JSONArray(result);

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);

                Voucher voucher = new Voucher();

                voucher.setDistributorName(jsonObject.getString("distributor_name").trim());
                voucher.setValue(Float.parseFloat(jsonObject.getString("value")));

                // Add to list
                voucherList.add(voucher);
            }

        } else {
            Log.e(TAG, "HTTP Request Error");
        }
    }
}
