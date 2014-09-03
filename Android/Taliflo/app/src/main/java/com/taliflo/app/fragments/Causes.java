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
import com.taliflo.app.activities.UserDetail;
import com.taliflo.app.adapters.UserAdapter;
import com.taliflo.app.api.RequestUsers;
import com.taliflo.app.model.CauseStore;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UserAdapter(getActivity(), causes);
        if (causes.isEmpty()) {
            Log.i(TAG, "Requesting causes... ");
            RequestUsers usersRequest = new RequestUsers(adapter, causes, "cause");
            usersRequest.execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_causes, container, false);
        listView = (ListView) v.findViewById(R.id.causes_listView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(openUserDetail);

        CauseStore causeStore = CauseStore.getInstance();
        causeStore.setCauses(causes);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        Log.i(TAG, "# of Causes: " + causes.size());
    }

    private ListView.OnItemClickListener openUserDetail = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Start User Detail Activity
            Intent i = new Intent(getActivity(), UserDetail.class);
            i.putExtra("User", causes.get(position));
            startActivityForResult(i, 30);
        }
    };

}
