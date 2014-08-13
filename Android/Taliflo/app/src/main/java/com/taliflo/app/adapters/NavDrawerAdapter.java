package com.taliflo.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taliflo.app.R;
import com.taliflo.app.utilities.NavDrawerBanner;
import com.taliflo.app.utilities.NavDrawerInterface;
import com.taliflo.app.utilities.NavDrawerItem;
import com.taliflo.app.utilities.NavDrawerSection;

import java.util.ArrayList;


/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerInterface> entries;
    private static LayoutInflater inflater;

    public NavDrawerAdapter (Activity activity, ArrayList<NavDrawerInterface> entries) {
        context = activity.getApplicationContext();
        this.entries = entries;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return entries.get(position).getType();
    }

    @Override
    public boolean isEnabled(int position) {
        return entries.get(position).isEnabled();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        NavDrawerInterface entry = entries.get(position);

        if (entry.getType() == NavDrawerBanner.HEADER_TYPE) {
            view = getBannerView(convertView, parent, (NavDrawerBanner) entry);
        }

        if (entry.getType() == NavDrawerSection.SECTION_TYPE) {
            view = getSectionView(convertView, parent, (NavDrawerSection) entry);
        }

        if (entry.getType() == NavDrawerItem.ITEM_TYPE) {
            view = getItemView(convertView, parent, (NavDrawerItem) entry);
        }

        return view;
    }

    public View getSectionView(View convertView, ViewGroup parentView, NavDrawerSection section) {

        View v = convertView;
        SectionHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.nav_drawer_section, parentView, false);
            TextView title = (TextView) v.findViewById(R.id.section_title);

            holder = new SectionHolder();
            holder.title = title;
            v.setTag(holder);
        } else {
            holder = (SectionHolder) v.getTag();
        }

        holder.title.setText(section.getTitle());

        return v;
    }

    public View getItemView(View convertView, ViewGroup parentView, NavDrawerItem item) {

        View v = convertView;
        ItemHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.nav_drawer_item, parentView, false);
            TextView title = (TextView) v.findViewById(R.id.item_title);
            ImageView icon = (ImageView) v.findViewById(R.id.item_icon);

            holder = new ItemHolder();
            holder.title = title;
            holder.icon = icon;
            v.setTag(holder);
        } else {
            holder = (ItemHolder) v.getTag();
        }

        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIcon());

        return v;
    }

    public View getBannerView(View convertView, ViewGroup parentView, NavDrawerBanner banner) {

        View v = convertView;
        BannerHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.nav_drawer_banner, parentView, false);
            TextView first = (TextView) v.findViewById(R.id.banner_first);
            TextView last = (TextView) v.findViewById(R.id.banner_last);
            ImageView picture = (ImageView) v.findViewById(R.id.banner_picture);

            holder = new BannerHolder();
            holder.first = first;
            holder.last = last;
            holder.picture = picture;
            v.setTag(holder);
        } else {
            holder = (BannerHolder) v.getTag();
        }

        holder.first.setText(banner.getTitle2());
        holder.last.setText(banner.getTitle());
        holder.picture.setImageResource(banner.getIcon());

        return v;
    }

    private static class BannerHolder {
        private TextView first, last;
        private ImageView picture;
    }

    private static class SectionHolder {
        private TextView title;

    }

    private static class ItemHolder {
        private TextView title;
        private ImageView icon;

    }


}
