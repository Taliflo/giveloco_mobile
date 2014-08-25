package com.taliflo.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.model.User;
import com.taliflo.app.utilities.ActionBarHelper;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class Donate extends FragmentActivity implements ISimpleDialogListener {

    private final String TAG = "Taliflo.Donate";

    // Layout views
    private Button btnConfirm;
    private TextView companyName;
    private RadioGroup donationGroup;
    private RadioButton radioBtn20, radioBtn40, radioBtn60, radioBtn100;
    private CheckBox donateMonthly;

    // Member variables
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Wire up views
        btnConfirm = (Button) findViewById(R.id.donate_btnConfirm);
        companyName = (TextView) findViewById(R.id.donate_companyName);
        donationGroup = (RadioGroup) findViewById(R.id.donate_radioGroup);
        radioBtn20 = (RadioButton) findViewById(R.id.donate_radioBtn20);
        radioBtn40 = (RadioButton) findViewById(R.id.donate_radioBtn40);
        radioBtn60 = (RadioButton) findViewById(R.id.donate_radioBtn60);
        radioBtn100 = (RadioButton) findViewById(R.id.donate_radioBtn100);
        donateMonthly = (CheckBox) findViewById(R.id.donate_donateMonthly);

        radioBtn20.setChecked(true);

        btnConfirm.setOnClickListener(donate);

        // Retrieve intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getParcelable("Cause");

            companyName.setText(user.getCompanyName());
        }
    }

    private Button.OnClickListener donate = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleDialogFragment.createBuilder(getApplicationContext(), getSupportFragmentManager())
                    .setTitle(getResources().getString(R.string.dialog_title))
                    .setMessage(getResources().getString(R.string.donate_dialogMsg))
                    .setPositiveButtonText(getResources().getString(R.string.dialog_btnPosText))
                    .setNegativeButtonText(getResources().getString(R.string.dialog_btnNegText))
                    .setRequestCode(1)
                    .setCancelableOnTouchOutside(false)
                    .show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        ActionBarHelper helper = ActionBarHelper.getInstance();
        helper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}
