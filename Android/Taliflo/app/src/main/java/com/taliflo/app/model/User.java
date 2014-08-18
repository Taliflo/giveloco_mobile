package com.taliflo.app.model;

import java.util.ArrayList;

/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public abstract class User {

    // Member variables
    private int id, zip;
    private String email, streetAddress, city, state, country;
    protected String name, summary, description, imageUrl;
    protected int type;

    // Accessor methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getZip() { return zip; }
    public void setZip(int zip) { this.zip = zip; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }


    public abstract String getName();
    public abstract void setName(String name);

    public abstract String getSummary();
    public abstract void setSummary(String summary);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    public abstract int getType();
    public abstract void setType(int type);

    public abstract String getImageUrl();
    public abstract void setImageUrl(String url);
}

