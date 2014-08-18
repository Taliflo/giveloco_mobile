package com.taliflo.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taliflo.app.fragments.Businesses;
import com.taliflo.app.fragments.Redeem;
import com.taliflo.app.fragments.Search;

/**
 * Created by Caswell on 1/18/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Fragment redeem, businesses, search;

    public TabsPagerAdapter (FragmentManager fm) {
        super(fm);
        redeem = new Redeem();
        businesses = new Businesses();
        search = new Search();
    }

    @Override
    public Fragment getItem (int index) {
        switch(index) {
            case 0:
                return redeem;
            case 1:
                return businesses;
            case 2:
                return search;
        }
        return null;
    }

    @Override
    public int getCount() {
        // The number of tabs
        return 3;
    }


}
