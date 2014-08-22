package com.taliflo.app.model;

/**
 * Singleton class which holds a field for the currently logged in user
 *
 * Created by Ntokozo Radebe on 1/4/2014.
 */
public class UserStore {

    private User currentUser;

    private static UserStore instance = null;

    private UserStore () {}

    public static synchronized UserStore getSharedInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
