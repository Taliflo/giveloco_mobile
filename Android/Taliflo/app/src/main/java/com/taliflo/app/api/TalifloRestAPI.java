package com.taliflo.app.api;

/**
 * Created by Caswell on 1/7/2014.
 */
public final class TalifloRestAPI {

    private final String base = "http://api-dev.taliflo.com/v1";

    // Request URLs
    public final String QUERY_USERS = base + "/users";
    public final String QUERY_BUSINESSES = base + "/users?role=business";
    public final String QUERY_CAUSES = base + "/users?role=cause";
    public final String QUERY_PERSONS = base + "/users?role=person";
    public final String QUERY_TRANSACTIONS = base + "/transactions";

    // Response codes
    public final int STATUS_OK = 200;
    public final int STATUS_OK_NO_CONTENT = 204;
    public final int STATUS_CLIENT_ERROR = 422;
    public final int STATUS_SERVER_ERROR = 500;

    // Timeouts
    public final int CONNECTIONTIMEOUT = 20000;
    public final int SOTIMEOUT = 20000;

    // Intent codes
    public final String DISTRIBUTOR_NAME = "distributor_name";

    private static TalifloRestAPI instance = null;

    private TalifloRestAPI() {}

    public static TalifloRestAPI getInstance() {
        if (instance == null) {
            instance = new TalifloRestAPI();
        }
        return instance;
    }

}
