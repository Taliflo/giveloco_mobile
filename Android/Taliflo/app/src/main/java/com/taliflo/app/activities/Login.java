package com.taliflo.app.activities;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.taliflo.app.R;
import com.taliflo.app.model.UserStore;
import com.taliflo.app.rest.TalifloRestHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Login extends Activity {

    private final String TAG = "Taliflo.Login";

    // Layout views
    private EditText email, password;
    private Button btnLogin;
    private ImageView logo;
    private Activity thisActiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        thisActiv = this;

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        logo = (ImageView) findViewById(R.id.layout_logo);

        email.setSelectAllOnFocus(true);
        password.setSelectAllOnFocus(true);
        password.setTypeface(Typeface.DEFAULT);
        btnLogin.setOnClickListener(btnLoginListener);

    }

    private Button.OnClickListener btnLoginListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean noEmail = email.getText().toString().equals("");
            boolean noPassword = password.getText().toString().equals("");

            if (noEmail) {
                emptyInputError(email);
                return;
            }

            if (noPassword) {
                emptyInputError(password);
                return;
            }

            new AttemptLogin(thisActiv, email.getText().toString(), password.getText().toString(), password).execute();
        }
    };

    @Override
    public void onBackPressed() {
        // Close application on back pressed. Set requestCode to cancelled
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private void emptyInputError(EditText editText) {
        editText.setError(getResources().getString(R.string.login_error_field_required));
    }

    private class AttemptLogin extends AsyncTask<String, Integer, String> {

        // Log tag
        private final String TAG = "Taliflo.AttemptLogin";

        private final String loginUrl = "http://api-dev.taliflo.com/user/login";

        private String email, password;
        private UserStore userStore = UserStore.getInstance();
        private long startTime, endTime;
        private boolean loginFailed = false;
        private EditText passwordView;
        private Activity activity;

        private AttemptLogin(Activity activity, String email, String password, EditText passwordView) {
            this.email = email.trim();
            this.password = password.trim();
            this.passwordView = passwordView;
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                loginUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            endTime = android.os.SystemClock.uptimeMillis();
            Log.i(TAG, "Login execution time: " + (endTime - startTime) + " ms");

            if (loginFailed) {
                showError();
                return;
            }

            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }

        private void loginUser() throws Exception {
            startTime = android.os.SystemClock.uptimeMillis();

            TalifloRestHelper restHelper = TalifloRestHelper.getInstance();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            params.put("password", password);
            String result = restHelper.sessionStrategy("login", params);

            if (result != null) {
                // Successful login
                JSONObject resultObj = new JSONObject(result);
                userStore.setLoggedInCredentials(resultObj);
                loginFailed = false;
            } else {
                // Failed login
                loginFailed = true;
            }
        }

        private void showError() {
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.edittext_shake);
            passwordView.startAnimation(shake);
            passwordView.setText("");
        }
    }
}
