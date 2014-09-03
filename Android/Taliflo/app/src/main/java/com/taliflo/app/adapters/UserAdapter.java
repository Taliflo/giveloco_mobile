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
import com.taliflo.app.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Caswell on 1/7/2014.
 */
public class UserAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;
    private static LayoutInflater layoutInflater;
    private ImageLoader imageLoader;

    public UserAdapter(Activity activity, ArrayList<User> users) {
        context = activity.getApplicationContext();
        this.users = users;
        layoutInflater = LayoutInflater.from(activity);
        imageLoader = ImageLoader.getInstance();
    }

    public void setUserList(ArrayList<User> list) {
        users = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() { return users.size(); }

    @Override
    public Object getItem(int position) { return users.get(position); }

    @Override
    public long getItemId(int position) { return Long.parseLong(users.get(position).getId()); }

    @Override
    public int getViewTypeCount() { return 1; }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {

        View v = convertView;
        UserHolder holder = null;
        User user = users.get(position);

        if (v == null) {
            v = layoutInflater.inflate(R.layout.user_list_item_1, parentView, false);
            ImageView image = (ImageView) v.findViewById(R.id.user_image1);
            TextView name = (TextView) v.findViewById(R.id.user_name1);
            TextView summary = (TextView) v.findViewById(R.id.user_summary1);
            //TextView tags = (TextView) v.findViewById(R.id.user_tags1);
            //TextView support = (TextView) v.findViewById(R.id.user_support1);

            holder = new UserHolder(name, summary, image);
            v.setTag(holder);
        } else {
            // Recycle view that already exists
            holder = (UserHolder) v.getTag();
        }

        imageLoader.displayImage(user.getProfilePictureURL(), holder.image);
        holder.name.setText(user.getCompanyName());
        holder.summary.setText(user.getSummary());
        //holder.tags.setText(user.getTagsString());

       if (user.getRole().equals("business")) {
            v.setBackgroundColor(context.getResources().getColor(R.color.taliflo_purple));
            //holder.support.setText(user.getSupportedCausesCount() + " " + context.getResources().getString(R.string.userAdapter_supportedCauses));
        }
        else if (user.getRole().equals("cause")) {
            v.setBackgroundColor(context.getResources().getColor(R.color.taliflo_tiffanyBlue));
            //holder.support.setText(user.getSupportersCount() + " " + context.getResources().getString(R.string.userAdapter_supportingBusinesses));
        }

        return v;

     /*   if (convertView == null) {

            ImageView image;
            TextView name;
            TextView summary;
            TextView tags;
            TextView support;

            if (user.getType() == 0) {
                v = layoutInflater.inflate(R.layout.user_list_item_1, parentView, false);
                image = (ImageView) v.findViewById(R.id.user_image1);
                name = (TextView) v.findViewById(R.id.user_name1);
                summary = (TextView) v.findViewById(R.id.user_summary1);
                tags = (TextView) v.findViewById(R.id.user_tags1);
                support = (TextView) v.findViewById(R.id.user_support1);
            } else {
                v = layoutInflater.inflate(R.layout.user_list_item_2, parentView, false);
                image = (ImageView) v.findViewById(R.id.user_image2);
                name = (TextView) v.findViewById(R.id.user_name2);
                summary = (TextView) v.findViewById(R.id.user_summary2);
                tags = (TextView) v.findViewById(R.id.user_tags2);
                support = (TextView) v.findViewById(R.id.user_support2);
            }

            holder = new UserHolder();
            holder.image = image;
            holder.name = name;
            holder.summary = summary;
            holder.tags = tags;
            holder.support = support;

            v.setTag(holder);
        } else {
            holder = (UserHolder) v.getTag();
        }


        imageLoader.displayImage(user.getImageUrl(), holder.image);
        holder.name.setText(user.getName());
        holder.summary.setText(user.getSummary());

        return v;*/

    }

    private static class UserHolder {
        private ImageView image;
        private TextView name, summary, tags, support;
        private int type;

        private UserHolder(TextView name, TextView summary, ImageView image ) {
            this.image = image;
            this.name = name;
            this.summary = summary;
            this.tags = tags;
            this.support = support;
        }
    }
}
