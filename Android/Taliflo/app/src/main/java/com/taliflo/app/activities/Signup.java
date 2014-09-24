package com.taliflo.app.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taliflo.app.R;

public class Signup extends Activity {

    private final String TAG = "Taliflo.Signup";

    // Layout views
    private EditText firstName, lastName, email, password;
    private TextView termsAndConditions;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Find views
        firstName = (EditText) findViewById(R.id.signup_firstName);
        lastName = (EditText) findViewById(R.id.signup_lastName);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        termsAndConditions = (TextView) findViewById(R.id.signup_TaC);
        btnSignup = (Button) findViewById(R.id.signup_btnSignup);

        password.setTypeface(Typeface.DEFAULT);

        firstName.setSelectAllOnFocus(true);
        lastName.setSelectAllOnFocus(true);
        email.setSelectAllOnFocus(true);
        password.setSelectAllOnFocus(true);

        btnSignup.setOnClickListener(attemptSignup);
        termsAndConditions.setOnClickListener(openTermsAndConditions);
    }

    private View.OnClickListener attemptSignup = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Handle empty input fields

            // Handle invalid input

            // Attempt signup

        }
    };

    private View.OnClickListener openTermsAndConditions = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "Terms and Conditions clicked");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
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

    private class AttemptSignup extends AsyncTask<String, Integer, String> {

        // Log tag
        private final String TAG = "Taliflo.AttemptSignup";

        private AttemptSignup() {

        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
