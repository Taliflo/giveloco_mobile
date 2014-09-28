package com.taliflo.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.taliflo.app.R;
import com.taliflo.app.model.User;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.utilities.ActionBarHelper;

public class UserDetail extends Activity {

    // Layout Views
    private TextView companyName, availableCredit, description, address, phone, supportCount, tags, btnSupportText;
    private Button btnTransact, btnSupport;
    private ImageView image;
    private RelativeLayout btnSupportLayout;
    private TextView redeemDisabledMsg;

    // Member variables
    private User user, loggedInUser;
    //private ImageLoader imageLoader;

    private Activity thisActiv = this;
    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        UserStore userStore = UserStore.getInstance();
        loggedInUser = userStore.getCurrentUser();

        // Wire up views
        companyName = (TextView) findViewById(R.id.userDetail_companyName);
        tags = (TextView) findViewById(R.id.userDetail_tags);
        availableCredit = (TextView) findViewById(R.id.userDetail_availableCredit);
        description = (TextView) findViewById(R.id.userDetail_description);
        address = (TextView) findViewById(R.id.userDetail_address);
        phone = (TextView) findViewById(R.id.userDetail_phoneNumber);
        supportCount = (TextView) findViewById(R.id.userDetail_supportCount);
        btnTransact = (Button) findViewById(R.id.userDetail_btnTransact);
        btnSupportText = (TextView) findViewById(R.id.userDetail_btnSupportText);
        btnSupportLayout = (RelativeLayout) findViewById(R.id.userDetail_btnSupportLayout);
        image = (ImageView) findViewById(R.id.userDetail_profilePicture);
        redeemDisabledMsg = (TextView) findViewById(R.id.userDetail_redeemDisabledMsg);

        //imageLoader = ImageLoader.getInstance();

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
            phone.setText(user.getPhone());
            //imageLoader.displayImage("http://dummyimage.com/700x400/999999/fff.png", image);
            Picasso.with(this).load("http://dummyimage.com/700x400/999999/fff.png").into(image);
            btnSupportLayout.setOnClickListener(openSupport);

            // Set activity title and wire up views based on user role
            if (user.getRole().equals("business")) {
                setTitle("View Business");
                btnSupportText.setText(getResources().getString(R.string.userDetail_btnSupportedCausesText));
                supportCount.setText("" + user.getSupportedCausesCount());
                btnTransact.setText(getResources().getString(R.string.userDetail_btnRedeemText));

                btnTransact.setBackgroundResource(R.drawable.btn_bg_purple);
                btnTransact.setOnClickListener(openRedeem);

                // Default behaviour of disabling the redeem button
                if (user.isRedeemableBusiness()) {
                    btnTransact.setEnabled(true);
                    redeemDisabledMsg.setVisibility(View.GONE);
                } else {
                    btnTransact.setEnabled(false);
                    redeemDisabledMsg.setVisibility(View.VISIBLE);
                }
            }

            if (user.getRole().equals("cause")) {
                setTitle("View Cause");
                btnSupportText.setText(getResources().getString(R.string.userDetail_btnSupportingBusinessesText));
                supportCount.setText("" + user.getSupportersCount());
                btnTransact.setText(getResources().getString(R.string.userDetail_btnDonateText));
                btnTransact.setBackgroundResource(R.drawable.btn_bg_tb);
                btnTransact.setOnClickListener(openDonate);
            }
        }

    }

    private Button.OnClickListener openRedeem = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), Redeem.class);
            i.putExtra("Business", user);
            startActivityForResult(i, 1);
        }
    };

    private Button.OnClickListener openDonate = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), Donate.class);
            i.putExtra("Cause", user);
            startActivityForResult(i, 2);
        }
    };

    private Button.OnClickListener openSupport = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), UserSupport.class);
            if (user.getRole().equals("business")) {
                i.putExtra("Role", "business");
                i.putExtra("Support", user.getSupportedCauses());
            }

            if (user.getRole().equals("cause")) {
                i.putExtra("Role", "cause");
                i.putExtra("Support", user.getSupporters());
            }

            startActivityForResult(i, 3);
        }
    };

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                // Redeem activity result
                break;

            case 2:
                // Donate activity result
                break;

            case 3:
                // User Support result
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.no_search, menu);
        //abHelper.onCreateOptionsMenu(thisActiv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        abHelper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}
