package com.taliflo.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.model.User;
import com.taliflo.app.utilities.ActionBarHelper;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class Redeem extends FragmentActivity implements ISimpleDialogListener {

    private final String TAG = "Taliflo.Redeem";

    // Layout views
    private TextView companyName, location;
    private Button btnConfirm;

    // Member variables
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        companyName = (TextView) findViewById(R.id.redeem_companyName);
        location = (TextView) findViewById(R.id.redeem_location);
        btnConfirm = (Button) findViewById(R.id.redeem_btnConfirm);

        // Retrieve intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getParcelable("Business");

            companyName.setText(user.getCompanyName());
            location.setText(user.getStreetAddress() + '\n' + user.getCity() + ", " + user.getState() );
        }

        btnConfirm.setOnClickListener(confirm);
    }

    private Button.OnClickListener confirm = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleDialogFragment.createBuilder(getApplicationContext(), getSupportFragmentManager())
                    .setTitle(getResources().getString(R.string.dialog_title))
                    .setMessage(getResources().getString(R.string.redeem_dialogMsg))
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
        getMenuInflater().inflate(R.menu.no_search, menu);
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

    // ISimpleDialogListener methods

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == 1) {
            Log.i(TAG, "Positive button clicked");
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
