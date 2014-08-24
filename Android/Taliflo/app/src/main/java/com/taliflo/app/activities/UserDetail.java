package com.taliflo.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.taliflo.app.R;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;

public class UserDetail extends Activity {

    // Layout Views
    private TextView companyName, availableCredit, description, address, phoneNumber, supportCount, tags;
    private Button btnTransact, btnSupport;

    // Member variables
    private User user, loggedInUser;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        UserStore userStore = UserStore.getSharedInstance();
        loggedInUser = userStore.getCurrentUser();

        // Wire up views
        companyName = (TextView) findViewById(R.id.userDetail_companyName);
        tags = (TextView) findViewById(R.id.userDetail_tags);
        availableCredit = (TextView) findViewById(R.id.userDetail_availableCredit);
        description = (TextView) findViewById(R.id.userDetail_description);
        address = (TextView) findViewById(R.id.userDetail_address);
        phoneNumber = (TextView) findViewById(R.id.userDetail_phoneNumber);
        supportCount = (TextView) findViewById(R.id.userDetail_supportCount);
        btnTransact = (Button) findViewById(R.id.userDetail_btnTransact);
        btnSupport = (Button) findViewById(R.id.userDetail_btnSupport);

        imageLoader = ImageLoader.getInstance();

        // Retrieve intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Retrieve matter details
            user = extras.getParcelable("User");
            companyName.setText(user.getCompanyName());
            tags.setText(user.getTagsString());
            availableCredit.setText("C " + loggedInUser.getBalance());
            description.setText(user.getDescription());
            address.setText(user.getStreetAddress() + '\n' + user.getCity() + ", " + user.getState() + '\n' + user.getZip());
            phoneNumber.setText(user.getPhone());

            // Set activity title and wire up views based on user role
            if (user.getRole().equals("business")) {
                setTitle("View Business");
                btnSupport.setText(getResources().getString(R.string.userDetail_btnSupportedCausesText));
                btnSupport.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_cause_tb), null, null, null);
                supportCount.setText("" + user.getSupportedCausesCount());
                btnTransact.setText(getResources().getString(R.string.userDetail_btnRedeemText));
                btnTransact.setBackgroundResource(R.drawable.btn_bg_purple);
            }

            if (user.getRole().equals("cause")) {
                setTitle("View Cause");
                btnSupport.setText(getResources().getString(R.string.userDetail_btnSupportingBusinessesText));
                btnSupport.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_business_tb), null, null, null);
                supportCount.setText("" + user.getSupportersCount());
                btnTransact.setText(getResources().getString(R.string.userDetail_btnDonateText));
                btnTransact.setBackgroundResource(R.drawable.btn_bg_tb);
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
