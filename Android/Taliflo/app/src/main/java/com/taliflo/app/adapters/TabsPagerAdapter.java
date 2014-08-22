package com.taliflo.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taliflo.app.fragments.Businesses;
import com.taliflo.app.fragments.Causes;
import com.taliflo.app.fragments.MyAccount;

/**
 * Created by Caswell on 1/18/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Fragment myAccount, options, explore;

    public TabsPagerAdapter (FragmentManager fm) {
        super(fm);
        myAccount = new MyAccount();
        options = new Businesses();
        explore = new Causes();
    }

    @Override
    public Fragment getItem (int index) {
        switch(index) {
            case 0:
                return myAccount;
            case 1:
                return explore;
            case 2:
                return options;
        }
        return null;
    }

    @Override
    public int getCount() {
        // The number of tabs
        return 3;
    }


}
