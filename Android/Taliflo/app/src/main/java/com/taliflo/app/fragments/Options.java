package com.taliflo.app.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import com.taliflo.app.R;
import com.taliflo.app.activities.BillingInfo;

import org.joda.time.DateTime;

/**
 * Created by Caswell on 1/20/2014.
 */
public class Options extends Fragment {

    private final String TAG = "Taliflo.Options";

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

    private Button.OnClickListener openBilllingInfo = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), BillingInfo.class);
            //i.putExtra();
            startActivityForResult(i, 300);
        }
    };

    private Button.OnClickListener logout = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // End session
            getActivity().finish();
        }
    };

}
