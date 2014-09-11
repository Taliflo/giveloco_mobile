package com.taliflo.app.utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Caswell on 1/9/2014.
 */
public class MyViewPager extends ViewPager {

    private boolean swipeEnabled;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipeEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (swipeEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (swipeEnabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        if (swipeEnabled) {
            super.setCurrentItem(item);
        }
    }


    /**
     * To enable or disable swiping
     * @param isSwipedEnabled True to enable swipe, false to disable
     */
    public void setPagingEnabled(boolean isSwipedEnabled) {
        swipeEnabled = isSwipedEnabled;
    }
}
