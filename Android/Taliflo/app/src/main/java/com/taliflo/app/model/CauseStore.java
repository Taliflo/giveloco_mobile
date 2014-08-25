package com.taliflo.app.model;

import java.util.List;

/**
 * Created by Caswell on 1/25/2014.
 */
public class CauseStore {

    private List<User> causes;

    private static CauseStore instance = null;

    private CauseStore () {}

    public static CauseStore getInstance() {
        if (instance == null) {
            synchronized (CauseStore.class) {
                if (instance == null) {
                    instance = new CauseStore();
                }
            }
        }
        return instance;
    }

    public List<User> getCauses() {
        return causes;
    }

    public void setCauses(List<User> causes) {
        this.causes = causes;
    }
}
