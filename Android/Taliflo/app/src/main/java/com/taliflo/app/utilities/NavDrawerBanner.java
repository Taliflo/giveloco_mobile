package com.taliflo.app.utilities;

import android.graphics.Bitmap;

import com.taliflo.app.model.User;


/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public class NavDrawerBanner implements NavDrawerInterface {

    public static final int HEADER_TYPE = 0;
    private int id;
    private User user;

    public NavDrawerBanner(User user, int id) {
        this.user = user;
        this.id = id;
    }

    public String getTitle() {
      //  return user.getFirstName();
        return null;
    }

    public String getTitle2() {
      //  return user.getLastName();
        return null;
    }

    public int getIcon() {
        return 0;
    }

    public int getType() {
        return HEADER_TYPE;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean updateActionBarTitle() {
        return false;
    }

    public int getId() {
        return id;
    }


    public Bitmap getImage() {
       // return user.getImage();
        return null;
    }

}
