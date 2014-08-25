package com.taliflo.app.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.taliflo.app.R;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.utilities.ActionBarHelper;

import org.joda.time.DateTime;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class BillingInfo extends FragmentActivity implements ISimpleDialogListener {

    // Log tag
    private final String TAG = "Taliflo.BillingInfo";

    // Layout views
    private EditText name, number, cvv, street, city, zip;
    private Button btnExpiryDate, btnSave;

    private Spinner province, country;
    private ArrayAdapter<String> provinceAdapter;

    // Logged in user
    private User user;

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
        btnExpiryDate = (Button) findViewById(R.id.billingInfo_btnExpiryDate);
        btnSave = (Button) findViewById(R.id.billingInfo_btnSave);
        province = (Spinner) findViewById(R.id.billingInfo_province);
        country = (Spinner) findViewById(R.id.billingInfo_country);


        btnExpiryDate.setOnClickListener(openDatePicker);
        btnSave.setOnClickListener(saveInfo);

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
        street.setText(user.getStreetAddress());
        city.setText(user.getCity());
        zip.setText(user.getZip());

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
  /*      switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
           case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return  true;

            case R.id.action_logout:
                ActionBarHelper helper = ActionBarHelper.getInstance();
                helper.exitApplication(this);
                return true;
        } */
        ActionBarHelper helper = ActionBarHelper.getInstance();
        helper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    private Button.OnClickListener openDatePicker = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePickerWithoutDayField().show();
        }
    };

    private DatePickerDialog datePickerWithoutDayField(){

        DateTime currDate = new DateTime();

        DatePickerDialog dpd = new DatePickerDialog(this, datePickerListener, currDate.getYear(), currDate.getMonthOfYear(), -1);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        //Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }

            }
        }   catch(Exception ex) {

        }

        dpd.setTitle("");
        return dpd;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
                              int selectedDay) {
     /*       year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date on textview
            c = new GregorianCalendar(year, month, day);

            dateOfBirth.setText(sdf.format(c.getTime())); */
            if (view.isShown()) {
                Log.i(TAG, "HEY YEAH");
                selectedMonth++;
                selectedYear -= 2000;
                String selectedDate = selectedMonth + "/" + selectedYear;
                btnExpiryDate.setText(selectedDate);
            }
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
