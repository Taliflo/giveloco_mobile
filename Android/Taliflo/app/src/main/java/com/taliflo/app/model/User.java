package com.taliflo.app.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public class User {

    // Member fields
    protected String id, role, firstName, lastName, companyName,
            email, phone, streetAddress, city, state,
            country, zip, summary, description, website, profilePictureURL = "http://placehold.it/500";
    protected String[] tags;
    protected Transaction[] transactionsCreated, transactionsAccepted;
    public User(){}

    public User(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            role = jsonObject.getString("role");
            companyName = jsonObject.getString("company_name");
            summary = jsonObject.getString("summary");
            description = jsonObject.getString("description");
            website = jsonObject.getString("website");
            email = jsonObject.getString("email");
            phone = jsonObject.getString("phone");
            streetAddress = jsonObject.getString("street_address");
            city = jsonObject.getString("city");
            state = jsonObject.getString("state");
            country = jsonObject.getString("country");
            zip = jsonObject.getString("zip");
            summary = jsonObject.getString("summary");
            description = jsonObject.getString("description");

         /*   ArrayList<String> temp = new ArrayList<String>();
            JSONArray jsonTags = jsonObject.getJSONArray("tags");
            for (int i = 0; i < jsonTags.length(); i++) {
                temp.add(jsonTags.get(i).toString());
            }
            tags = (String[]) temp.toArray(); */
            // tags, supporters, supported_causes, transaction_created, transactions_accepted
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Accessor methods

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Transaction[] getTransactionsCreated() {
        return transactionsCreated;
    }

    public void setTransactionsCreated(Transaction[] transactionsCreated) {
        this.transactionsCreated = transactionsCreated;
    }

    public Transaction[] getTransactionsAccepted() {
        return transactionsAccepted;
    }

    public void setTransactionsAccepted(Transaction[] transactionsAccepted) {
        this.transactionsAccepted = transactionsAccepted;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

}

