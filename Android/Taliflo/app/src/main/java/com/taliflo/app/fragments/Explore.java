package com.taliflo.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.taliflo.app.R;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.api.RequestUsers;
import com.taliflo.app.model.User;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Caswell on 1/18/2014.
 */
public class Explore extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private String TAG = "Taliflo.Explore";

    private SegmentedGroup segmentedControl;
    private RadioButton btnBusinesses, btnCauses;
    private ArrayList<User> businesses = new ArrayList<User>();
    private ArrayList<User> causes = new ArrayList<User>();
    private ListView listView;
    private UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore, container, false);

        segmentedControl = (SegmentedGroup) v.findViewById(R.id.explore_segmentedControl);
        segmentedControl.setTintColor(getActivity().getResources().getColor(R.color.taliflo_tiffanyBlue));
        segmentedControl.setOnCheckedChangeListener(this);
        btnBusinesses = (RadioButton) v.findViewById(R.id.explore_segmentBtnBusinesses);
        btnCauses = (RadioButton) v.findViewById(R.id.explore_segmentBtnCauses);
        listView = (ListView) v.findViewById(R.id.explore_listView);

        adapter = new UserAdapter(getActivity(), businesses);
        listView.setAdapter(adapter);

        btnBusinesses.setChecked(true);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (businesses.isEmpty() && causes.isEmpty()) {
            RequestUsers usersRequest = new RequestUsers(adapter, businesses, causes);
            usersRequest.execute();
        }

        Log.i(TAG, "Size of business list: " + businesses.size());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.explore_segmentBtnBusinesses:
                Log.i(TAG, "Businesses checked");
                segmentedControl.setTintColor(getActivity().getResources().getColor(R.color.taliflo_purple));
                adapter.setUserList(businesses);

                return;

            case R.id.explore_segmentBtnCauses:
                Log.i(TAG, "Causes checked");
                segmentedControl.setTintColor(getActivity().getResources().getColor(R.color.taliflo_tiffanyBlue));
                adapter.setUserList(causes);
                return;

            default:
                return;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
