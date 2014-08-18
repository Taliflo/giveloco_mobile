package com.taliflo.app.fragments;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.taliflo.app.R;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.api.RequestUsers;
import com.taliflo.app.model.User;

import java.util.ArrayList;

/**
 * Created by Caswell on 1/7/2014.
 */
public class Businesses extends Fragment {

    private static final String TAG = "Taliflo.Businesses";

    private ListView listView;
    private UserAdapter adapter;
    private ArrayList<User> businesses = new ArrayList<User>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "in onCreateView()");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_businesses, container, false);
        listView = (ListView) view.findViewById(R.id.businesses_listView);

        adapter = new UserAdapter(getActivity(), businesses);

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (businesses.size() == 0) {
            //new RequestBusinesses(adapter, businesses).execute();
            RequestUsers requestBusinesses = new RequestUsers(businesses, adapter, "business");
            requestBusinesses.execute();
        }
    }
/*
    private static void parseBusinesses(ArrayList<User> businesses)
            throws ConnectTimeoutException, IOException, JSONException {

        // Construct HTTP request message
        TalifloRestAPI restAPI = TalifloRestAPI.getInstance();
        HttpGet get = new HttpGet(restAPI.QUERY_BUSINESSES);

        // Set the HTTP parameters
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, restAPI.CONNECTIONTIMEOUT); // 5s max for connection
        HttpConnectionParams.setSoTimeout(params, restAPI.SOTIMEOUT); // 6s max to get data

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

            /** Parsing result to retrieve the contents **/ /*

            JSONArray resultArray = new JSONArray(result);

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);

                Business buss = new Business();

                buss.setName(jsonObject.getString("company_name").trim());
                buss.setSummary(jsonObject.getString("summary"));
                buss.setType(i % 2);

                // Add to list
                businesses.add(buss);
            }

        } else {
            Log.e(TAG, "HTTP Request Error");
        }

    } */
/*
    private static class RequestBusinesses extends AsyncTask<String, Integer, String> {

        private UserAdapter adapter;
        private ArrayList<User> businesses;

        public RequestBusinesses (UserAdapter adapter, ArrayList<User> businesses) {
            this.adapter = adapter;
            this.businesses = businesses;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                parseBusinesses(businesses);
            } catch (ConnectTimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            adapter.notifyDataSetChanged();
        }

    }*/
}
