package com.taliflo.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.api.TalifloRestAPI;

public class RedeemVoucher extends Activity {

    // Layout views
    private TextView businessName, businessAddress;
    private EditText enteredAmount;
    private Button btnConfirm;

    private TalifloRestAPI restAPI = TalifloRestAPI.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_voucher);

        // Find views
        businessName = (TextView) findViewById(R.id.redeemVoucher_businessName);
        businessAddress = (TextView) findViewById(R.id.redeemVoucher_businessAddress);
        enteredAmount = (EditText) findViewById(R.id.redeemVoucher_enteredAmount);
        btnConfirm = (Button) findViewById(R.id.redeemVoucher_btnConfirm);

        // Retrieve intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            businessName.setText(extras.getString(restAPI.DISTRIBUTOR_NAME));
        }

        enteredAmount.setSelectAllOnFocus(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.redeem_voucher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
