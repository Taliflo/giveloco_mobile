package com.taliflo.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taliflo.app.R;
import com.taliflo.app.activities.BillingInfo;

/**
 * Created by Caswell on 1/20/2014.
 */
public class Options extends Fragment {

    // Layout views
    private Button btnBillingInfo, btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_options, container, false);

        btnBillingInfo = (Button) v.findViewById(R.id.usermenu_btnBillingInfo);
        btnLogout = (Button) v.findViewById(R.id.usermenu_btnLogout);

        btnBillingInfo.setOnClickListener(openBilllingInfo);
        btnLogout.setOnClickListener(logout);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public Button.OnClickListener openBilllingInfo = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), BillingInfo.class);
            startActivityForResult(i, 300);
        }
    };

    public Button.OnClickListener logout = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // End session
            getActivity().finish();
        }
    };
}
