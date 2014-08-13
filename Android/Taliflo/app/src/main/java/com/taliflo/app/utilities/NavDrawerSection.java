package com.taliflo.app.utilities;

import android.graphics.Bitmap;

/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public class NavDrawerSection implements NavDrawerInterface {

    public static final int SECTION_TYPE = 1;
    private int id;
    private String title;

    public NavDrawerSection(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle2() {
        return null;
    }

    public int getType() {
        return SECTION_TYPE;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean updateActionBarTitle() {
        return false;
    }

    public int getIcon() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public Bitmap getImage() {
        return null;
    }
}
