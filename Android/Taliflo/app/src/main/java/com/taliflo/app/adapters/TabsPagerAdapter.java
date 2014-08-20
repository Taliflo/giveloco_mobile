package com.taliflo.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taliflo.app.fragments.Explore;
import com.taliflo.app.fragments.History;
import com.taliflo.app.fragments.Redeem;
import com.taliflo.app.fragments.Search;

/**
 * Created by Caswell on 1/18/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Fragment redeem, history, explore;

    public TabsPagerAdapter (FragmentManager fm) {
        super(fm);
        redeem = new Redeem();
        history = new History();
        explore = new Explore();
    }

    @Override
    public Fragment getItem (int index) {
        switch(index) {
            case 0:
                return redeem;
            case 1:
                return history;
            case 2:
                return explore;
        }
        return null;
    }

    @Override
    public int getCount() {
        // The number of tabs
        return 3;
    }


}
