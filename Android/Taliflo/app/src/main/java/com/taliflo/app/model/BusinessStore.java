package com.taliflo.app.model;

import java.util.List;

/**
 * Created by Caswell on 1/25/2014.
 */
public class BusinessStore {

    private List<User> businesses;

    private static BusinessStore instance = null;

    private BusinessStore () {}

    public static BusinessStore getInstance() {
        if (instance == null) {
            synchronized (BusinessStore.class) {
                if (instance == null) {
                    instance = new BusinessStore();
                }
            }
        }
        return instance;
    }

    public List<User> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<User> businesses) {
        this.businesses = businesses;
    }
}
