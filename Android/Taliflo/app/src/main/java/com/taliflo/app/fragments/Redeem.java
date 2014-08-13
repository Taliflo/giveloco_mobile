package com.taliflo.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.taliflo.app.R;
import com.taliflo.app.activities.RedeemVoucher;
import com.taliflo.app.adapters.VoucherAdapter;
import com.taliflo.app.api.RequestVouchers;
import com.taliflo.app.api.TalifloRestAPI;
import com.taliflo.app.model.Voucher;

import java.util.ArrayList;


public class Redeem extends Fragment {

    // Log cat tag
    private static final String TAG = "Taliflo.Redeem";

    // Layout views and member variables
    private ListView listView;
    private VoucherAdapter adapter;
    private ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

    private TalifloRestAPI restAPI = TalifloRestAPI.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "in onCreateView()");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem, container, false);
        listView = (ListView) view.findViewById(R.id.redeem_listView);
        adapter = new VoucherAdapter(getActivity(), vouchers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(openVoucher);
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (vouchers.size() == 0) {
            RequestVouchers requestVouchers = new RequestVouchers(vouchers, adapter);
            requestVouchers.execute();
        }
    }

    private ListView.OnItemClickListener openVoucher = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long Id) {

            // Passing voucher information with the intent
            Intent i = new Intent(getActivity(), RedeemVoucher.class);
            i.putExtra(restAPI.DISTRIBUTOR_NAME, vouchers.get(position).getDistributorName());

            // Start activity
            startActivity(i);
        }
    };


}
