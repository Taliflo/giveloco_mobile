package com.taliflo.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taliflo.app.R;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.rest.NetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Signup extends Activity {

    private final String TAG = "Taliflo.Signup";

    // Layout views
    private EditText firstName, lastName, email, password;
    private TextView termsAndConditions;
    private Button btnSignup;
    private Activity thisActivity = this;

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
            boolean emptyField = false;
            if (firstName.getText().toString().equals("")) { shakeEmptyEditText(firstName); emptyField = true; }
            if (lastName.getText().toString().equals("")) { shakeEmptyEditText(lastName); emptyField = true; }
            if (email.getText().toString().equals("")) { shakeEmptyEditText(email); emptyField = true; }
            if (password.getText().toString().equals("")) { shakeEmptyEditText(password); emptyField = true; }

            if (emptyField) return;
            // Handle invalid input

            if (password.getText().toString().length() < 8) {
                password.setError(getResources().getString(R.string.signup_password_short));
                return;
            }

            // Attempt signup
            new AttemptSignup(
                    thisActivity,
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString()
            ).execute();

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

        private HashMap<String, String> params;
        private Activity activity;

        private AttemptSignup(Activity activity, String firstName, String lastName, String email, String password) {
            this.activity = activity;

            params = new HashMap<String, String>();
            params.put("firstName", firstName);
            params.put("lastName", lastName);
            params.put("email", email);
            params.put("password", password);
        }

        @Override
        protected String doInBackground(String... params) {
            NetworkHelper networkHelper = NetworkHelper.getInstance();
            return networkHelper.requestStrategy(networkHelper.ACTION_SIGNUP, this.params);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            if (result != null) {
                try {
                    // Set logged in user
                    UserStore userStore = UserStore.getInstance();
                    userStore.setLoggedInCredentials(activity, result);

                    // Start Main Activity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    setResult(100);
                    startActivity(i);

                    // Signup successful
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.signup_successful), Toast.LENGTH_LONG).show();

                    // Set login status in shared preferences. authToken and uid are saved in shared preferences

                } catch (JSONException e) {
                    // Signup error or unsuccessful
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.signup_failed), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return;
                }
            } else {
                // Signup unsuccessful
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.signup_failed), Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private void shakeEmptyEditText(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.edittext_shake);
        editText.startAnimation(shake);
        editText.setText("");
    }
}
