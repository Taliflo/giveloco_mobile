package com.taliflo.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * Created by Caswell on 1/18/2014.
 */
public class Causes extends Fragment {

    private String TAG = "Taliflo.Explore";

    private ArrayList<User> causes = new ArrayList<User>();
    private ListView listView;
    private UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_causes, container, false);
        listView = (ListView) v.findViewById(R.id.causes_listView);

        adapter = new UserAdapter(getActivity(), causes);
        listView.setAdapter(adapter);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (causes.isEmpty()) {
            RequestUsers usersRequest = new RequestUsers(adapter, causes, "cause");
            usersRequest.execute();
        }

        Log.i(TAG, "# of Causes: " + causes.size());
    }

}
