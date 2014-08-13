package com.taliflo.app.model;

/**
 * Created by Caswell on 1/7/2014.
 */
public class Cause extends User {

    // Member variable
    private String[] tags;
    private Business[] supporters;

    // Constructors
    public Cause(){};

    public Cause(String name, String summary, String description, String[] tags, Business[] supporters) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.tags = tags;
        this.supporters = supporters;
    }

    public Cause(String name) {
        this.name = name;
    }

    // Accessor methods

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public Business[] getSupporters() { return supporters; }
    public void setSupporters(Business[] supporters) { this.supporters = supporters; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String url) { this.imageUrl = url; }
}
