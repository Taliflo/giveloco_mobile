package com.taliflo.app.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;

/**
 * Created by Caswell on 1/30/2014.
 */
public class MyDatePickerDialog {

    private final String TAG = "taliflo.DatePickerDialogWithoutDay";

    private TextView textView;
    private Context context;

    public MyDatePickerDialog(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public DatePickerDialog getPicker() {

        DatePickerDialog.OnDateSetListener datePickerListener
                = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
                                  int selectedDay) {
                if (view.isShown()) {
                    selectedMonth++;
                    selectedYear -= 2000;
                    String month = "";
                    if (selectedMonth < 10) {
                        month = "0" + Integer.toString(selectedMonth);
                    } else {
                        month = Integer.toString(selectedMonth);
                    }
                    String selectedDate = month + "/" + selectedYear;
                    textView.setText(selectedDate);
                    Log.i(TAG, selectedDate);
                }
            }
        };

        DateTime currDate = new DateTime();

        DatePickerDialog dpd = new DatePickerDialog(context, datePickerListener, currDate.getYear(), currDate.getMonthOfYear(), -1);
        dpd.getDatePicker().setMinDate(currDate.getMillis());
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
            ex.printStackTrace();
        }

        dpd.setTitle("");
        return dpd;
    }
}
