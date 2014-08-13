package com.taliflo.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.taliflo.app.R;
import com.taliflo.app.model.Voucher;

import java.util.ArrayList;

/**
 * Created by Caswell on 1/8/2014.
 */
public class VoucherAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Voucher> vouchers;
    private static LayoutInflater inflater;
    private ImageLoader imageLoader;

    public VoucherAdapter(Activity activity, ArrayList<Voucher> vouchers) {
        context = activity.getApplicationContext();
        this.vouchers = vouchers;
        inflater = LayoutInflater.from(activity);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() { return vouchers.size(); }

    @Override
    public Object getItem(int position) { return vouchers.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {
        View v = convertView;
        Voucher voucher = vouchers.get(position);
        VoucherHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.voucher_list_item, parentView, false);
            ImageView image = (ImageView) v.findViewById(R.id.voucher_image);
            TextView distributor = (TextView) v.findViewById(R.id.voucher_distributor);
            TextView value = (TextView) v.findViewById(R.id.voucher_value);
            TextView supportedCauses = (TextView) v.findViewById(R.id.voucher_supported_causes);

            holder = new VoucherHolder(image, distributor, value, supportedCauses);
            v.setTag(holder);
        } else {
            // Recycle view that already exists
            holder = (VoucherHolder) v.getTag();
        }

        imageLoader.displayImage(voucher.getImageUrl(), holder.image);
        holder.distributor.setText(voucher.getDistributorName());
        holder.value.setText("$ " + voucher.getValue());

        return v;
    }

    private static class VoucherHolder {
        private ImageView image;
        private TextView distributor, value, supportedCauses;
        private VoucherHolder (ImageView image, TextView distributor, TextView value, TextView supportedCauses) {
            this.image = image;
            this.distributor = distributor;
            this.value = value;
            this.supportedCauses = supportedCauses;
        }
    }
}
