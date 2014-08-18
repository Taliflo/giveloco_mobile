package com.taliflo.app.model;

import java.util.ArrayList;

/**
 * Created by Caswell on 1/7/2014.
 */
public class Business extends User {

    // Member variable
    private String name, summary, description, imageUrl = "http://placehold.it/256x192";
    private Cause[] supportedCauses;
    private Redemption[] redemptions;
    private String[] tags;
    private int type;

    // Constructors
    public Business() {}

    public Business(String name, String summary, String description, String[] tags, String imageUrl, int type) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public Business(String name) {
        this.name = name;
    }

    // Accessor methods

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String url) { imageUrl = url; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
}
