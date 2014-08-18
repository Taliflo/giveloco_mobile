package com.taliflo.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.taliflo.app.R;
import com.taliflo.app.activities.RedeemVoucher;
import com.taliflo.app.api.TalifloRestAPI;


public class Redeem extends Fragment {

    // Log cat tag
    private static final String TAG = "Taliflo.Redeem";

    // Layout views and member variables
    private ListView listView;

    private TalifloRestAPI restAPI = TalifloRestAPI.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "in onCreateView()");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem, container, false);
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private ListView.OnItemClickListener openVoucher = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long Id) {

            // Passing voucher information with the intent
            Intent i = new Intent(getActivity(), RedeemVoucher.class);
            //i.putExtra(restAPI.DISTRIBUTOR_NAME, vouchers.get(position).getDistributorName());

            // Start activity
            startActivity(i);
        }
    };


}
