package com.taliflo.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.utilities.ActionBarHelper;
import com.taliflo.app.utilities.MyDatePickerDialog;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class BillingInfo extends FragmentActivity implements ISimpleDialogListener {

    // Log tag
    private final String TAG = "taliflo.BillingInfo";

    // Layout views
    private EditText name, number, cvv, street, city, zip;
    private TextView expiryDate;

    private Spinner province, country;
    private ArrayAdapter<String> provinceAdapter;

    // Logged in user
    private User user;

    private Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.billingInfo_creditCardName);
        number = (EditText) findViewById(R.id.billingInfo_creditCardNumber);
        cvv = (EditText) findViewById(R.id.billingInfo_creditCardCCV);
        street = (EditText) findViewById(R.id.billingInfo_street);
        city = (EditText) findViewById(R.id.billingInfo_city);
        zip = (EditText) findViewById(R.id.billingInfo_zip);
        expiryDate = (TextView) findViewById(R.id.billingInfo_expiryDate);
        province = (Spinner) findViewById(R.id.billingInfo_province);
        country = (Spinner) findViewById(R.id.billingInfo_country);

        expiryDate.setClickable(true);
        expiryDate.setOnClickListener(openDatePicker);

        name.setSelectAllOnFocus(true);
        number.setSelectAllOnFocus(true);
        cvv.setSelectAllOnFocus(true);

        provinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        //province.setAdapter(provinceAdapter);
        country.setOnItemSelectedListener(countryListener);

        // Should put exception handling here to check if the GET user
        // request fails
        UserStore userStore = UserStore.getInstance();
        user = userStore.getCurrentUser();

        name.setText(user.getFullName());

        if (!user.getStreetAddress().equals("null")) {
            street.setText(user.getStreetAddress());
        }

        if (!user.getCity().equals("null")) {
            city.setText(user.getCity());
        }

        if (!user.getZip().equals("null")) {
            zip.setText(user.getZip());
        }


    }

    private AdapterView.OnItemSelectedListener countryListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int spinPosition, long Id) {
            provinceAdapter.clear();

            if (spinPosition == 0) {
                provinceAdapter.addAll(getResources().getStringArray(R.array.billingInfo_canadaEntries));
            }

            if (spinPosition == 1) {
                provinceAdapter.addAll(getResources().getStringArray(R.array.billingInfo_usEntries));
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }

    };

    private Button.OnClickListener saveInfo = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            SimpleDialogFragment.createBuilder(getApplicationContext(), getSupportFragmentManager())
                    .setTitle(getResources().getString(R.string.dialog_title))
                    .setMessage(getResources().getString(R.string.billingInfo_dialogMsg))
                    .setPositiveButtonText(getResources().getString(R.string.dialog_btnPosText))
                    .setNegativeButtonText(getResources().getString(R.string.dialog_btnNegText))
                    .setRequestCode(1)
                    .setCancelableOnTouchOutside(false)
                    .show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.billing_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActionBarHelper helper = ActionBarHelper.getInstance();
        helper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener openDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //datePickerWithoutDayField().show();
            new MyDatePickerDialog(thisActivity, expiryDate).getPicker().show();
        }
    };

    // ISimpleDialogListener methods

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == 1) {
            Log.i(TAG, "Positive button clicked");

            finish();
        }
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if (requestCode == 1) {
            Log.i(TAG, "Negative button clicked");
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {}
}
