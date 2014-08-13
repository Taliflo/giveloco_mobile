package com.taliflo.app.utilities;

import android.graphics.Bitmap;

/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public interface NavDrawerInterface {
    public String getTitle();
    public String getTitle2();
    public int getType();
    public int getIcon();
    public int getId();
    public boolean isEnabled();
    public boolean updateActionBarTitle();
    public Bitmap getImage();
}
