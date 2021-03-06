package com.taliflo.app.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.taliflo.app.R;
import com.taliflo.app.utilities.ActionBarHelper;
import com.taliflo.app.utilities.MyDatePickerDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class Payment extends FragmentActivity implements ISimpleDialogListener {

    // Log tag
    private final String TAG = "taliflo.Payment";

    // Layout views
    private EditText cardNumber, cvv;
    private TextView expiryDate;
    private Button btnComplete;

    // Member fields
    private Activity thisActivity = this;
    private ActionBarHelper abHelper = ActionBarHelper.getInstance();

    private boolean keyDel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Find views
        cardNumber = (EditText) findViewById(R.id.payment_cardNumber);
        expiryDate = (TextView) findViewById(R.id.payment_expiryDate);
        cvv = (EditText) findViewById(R.id.payment_cvv);
        btnComplete = (Button) findViewById(R.id.payment_btnComplete);

        expiryDate.setClickable(true);
        expiryDate.setOnClickListener(openDatePicker);

        btnComplete.setOnClickListener(complete);
        /*
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cardNumber.removeTextChangedListener(this);
                appendSpace(cardNumber);
                cardNumber.addTextChangedListener(this);
            }
        });
        */

    }

    private void appendSpace(EditText editText) {
        String newStr = editText.getText().toString() + " ";
        editText.setText(newStr);
        editText.setSelection(newStr.length());
    }

    private Button.OnClickListener complete = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean emprtyField = false;

            if ( cardNumber.getText().equals("") || expiryDate.getText().equals("") || cvv.getText().equals("") ) {
                emprtyField = true;
            }

            if (emprtyField) {
                Toast.makeText(thisActivity, getResources().getString(R.string.payment_emptyField), Toast.LENGTH_SHORT).show();
                return;
            }


            String expiryDateText = expiryDate.getText().toString();
            int expiryMonth = Integer.parseInt(expiryDateText.substring(0, 2));
            int expiryYear = Integer.parseInt(expiryDateText.substring(3));

            Card card = new Card(cardNumber.getText().toString(), expiryMonth, expiryYear, cvv.getText().toString());

            try {
                Stripe stripe = new Stripe(getResources().getString(R.string.stripe_key_test));
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                Log.i(TAG, token.getId());
                                // Send token to backend
                            }

                            public void onError(Exception error) {
                                // Show localized error message
                                SimpleDialogFragment.createBuilder(getApplicationContext(), getSupportFragmentManager())
                                        .setMessage(error.getLocalizedMessage())
                                        .setPositiveButtonText(getResources().getString(R.string.payment_dialogBtnText))
                                        .setRequestCode(1)
                                        .setCancelableOnTouchOutside(false)
                                        .show();
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener openDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new MyDatePickerDialog(thisActivity, expiryDate).getPicker().show();
        }
    };

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == 1) {}
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if (requestCode == 1) {}
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_only, menu);
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
