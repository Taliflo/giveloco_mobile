package com.taliflo.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.taliflo.app.R;
import com.taliflo.app.model.Transaction;

import java.util.ArrayList;

/**
 * Created by Caswell on 1/21/2014.
 */
public class TransactionsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Transaction> transactions;
    private static LayoutInflater inflater;
    private ImageLoader imageloader;

    public TransactionsAdapter(Activity activity, ArrayList<Transaction> transactions) {
        context = activity.getApplicationContext();
        this.transactions = transactions;
        inflater = LayoutInflater.from(activity);
        imageloader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(transactions.get(position).getId());
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {

        View v = convertView;
        TransHolder holder = null;
        Transaction trans = transactions.get(position);

        if (v == null) {
            v = inflater.inflate(R.layout.transaction_list_item, parentView, false);
            TextView party = (TextView) v.findViewById(R.id.trans_party);
            TextView amount = (TextView) v.findViewById(R.id.trans_amount);
            TextView date = (TextView) v.findViewById(R.id.trans_date);
            TextView id = (TextView) v.findViewById(R.id.trans_id);
            TextView type = (TextView) v.findViewById(R.id.trans_type);

            holder = new TransHolder(type, party, amount, date, id);
            v.setTag(holder);
        } else {
            // Recycle
            holder = (TransHolder) v.getTag();
        }

        holder.type.setText(trans.getTransType());
        holder.party.setText(trans.getToName());
        holder.amount.setText("C "+ trans.getAmount());
        holder.date.setText(trans.createdAt());
        holder.id.setText(trans.getTransID());

        if (position%2 == 0)
            v.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            v.setBackgroundColor(context.getResources().getColor(R.color.light_grey_bg));

        return v;
    }

    private static class TransHolder {
        private TextView party, amount, date, id, type;
        private TransHolder(TextView type, TextView party, TextView amount, TextView date, TextView id) {
            this.type = type;
            this.party = party;
            this.amount = amount;
            this.date = date;
            this.id = id;
        }
    }

}
