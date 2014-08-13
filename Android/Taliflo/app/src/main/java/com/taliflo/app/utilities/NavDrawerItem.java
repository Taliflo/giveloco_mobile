package com.taliflo.app.utilities;

import android.graphics.Bitmap;

/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public class NavDrawerItem implements NavDrawerInterface {

    public static final int ITEM_TYPE = 2;

    private String title;
    private int icon;
    private int id;
    private boolean enabled = true;
    private boolean updateActionBarTitle;

    public NavDrawerItem (String title, int id, int icon) {
        this.title = title;
        this.id = id;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle2() {
        return null;
    }

    public int getIcon() {
        return icon;
    }

    public int getType() {
        return this.ITEM_TYPE;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setStatus(boolean status) {
        enabled = status;
    }

    public boolean updateActionBarTitle() {
        return false;
    }

    public int getId() {
        return id;
    }

    public Bitmap getImage() {
        return null;
    }
}
