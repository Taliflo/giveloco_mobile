package com.taliflo.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Ntokozo Radebe on 1/15/2014.
 */
public class User implements Parcelable {

    // Member fields
    protected String id, role, firstName, lastName, companyName,
            email, phone, streetAddress, city, state,
            country, zip, summary, description, website, picOriginal, picMedium, picThumb, profilePictureURL = "http://dummyimage.com/200x200/999999/fff.png";
    protected ArrayList<String> tags;
    protected ArrayList<Transaction> transactionsCreated, transactionsAccepted, transactionsAll;
    private float balance;
    private int[] supporters, supportedCauses;
    private boolean isPublished, isFeatured;

    private ArrayList<User> redeemableBusinesses;
    private boolean redeemableBusiness = false;

    // Constructors

    public User(){}

    /**
     * Standard constructor for non-parcel object creation
     * @param jsonObject
     */
    public User(JSONObject jsonObject) {
        id = jsonObject.optString("id");
        role = jsonObject.optString("role");
        companyName = jsonObject.optString("company_name");
        summary = jsonObject.optString("summary");
        description = jsonObject.optString("description");
        website = jsonObject.optString("website");
        phone = jsonObject.optString("phone");
        streetAddress = jsonObject.optString("street_address");
        city = jsonObject.optString("city");
        state = jsonObject.optString("state");
        country = jsonObject.optString("country");
        zip = jsonObject.optString("zip");
        summary = jsonObject.optString("summary");
        description = jsonObject.optString("description");



        JSONObject images = jsonObject.optJSONObject("images");
        JSONObject profilePicture = images.optJSONObject("profile_picture");
        picOriginal = profilePicture.optString("original");
        picMedium = profilePicture.optString("medium");
        picThumb = profilePicture.optString("thumb");

        JSONArray jsonTags = jsonObject.optJSONArray("tags");
        tags = new ArrayList<String>();
        for (int i = 0; i < jsonTags.length(); i++) {
            tags.add(jsonTags.optString(i));
        }

        retrieveTransactions(jsonObject.optJSONArray("transactions_created"), transactionsCreated);
        retrieveTransactions(jsonObject.optJSONArray("transactions_accepted"), transactionsAccepted);

        if (transactionsAccepted != null || transactionsCreated != null) {
            transactionsAll = sortTransactionsByDate();
        }

        if (role.equals("individual")) {
            firstName = jsonObject.optString("first_name");
            lastName = jsonObject.optString("last_name");
            email = jsonObject.optString("email");

            String bal = jsonObject.optString("balance");
            if (bal != null) {
                balance = Float.parseFloat(bal);
            }

            retrieveAssociationIds(jsonObject.optJSONArray("supported_causes"), supportedCauses, "to_user_id");
        }

        if (role.equals("business")) {
            retrieveAssociationIds(jsonObject.optJSONArray("supported_causes"), supportedCauses, "to_user_id");
        }

        if (role.equals("cause")) {
            retrieveAssociationIds(jsonObject.optJSONArray("supporters"), supporters, "from_user_id");
        }
    }

    /**
     * Constructor to use when re-constructing a User object from a parcel
     * @param in A parcel from which to read this object
     */
    public User(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(role);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(companyName);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(streetAddress);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(zip);
        dest.writeString(summary);
        dest.writeString(description);
        dest.writeString(website);
        dest.writeString(profilePictureURL);
        dest.writeString(picOriginal);
        dest.writeString(picMedium);
        dest.writeString(picThumb);

        dest.writeFloat(balance);

        dest.writeIntArray(supporters);
        dest.writeIntArray(supportedCauses);

        dest.writeSerializable(tags);
        dest.writeSerializable(transactionsCreated);
        dest.writeSerializable(transactionsAccepted);
        dest.writeSerializable(transactionsAll);

        // If redeemableBusiness == true, byte == 1
        dest.writeByte((byte) (redeemableBusiness ? 1 : 0));
    }

    /**
     * Called from the constructor to create User object from a parcel.
     * @param in
     */
    private void readFromParcel(Parcel in) {
        // Each field is read back in the order it was written to the parcel
        id = in.readString();
        role = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        companyName = in.readString();
        email = in.readString();
        phone = in.readString();
        streetAddress = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        zip = in.readString();
        summary = in.readString();
        description = in.readString();
        website = in.readString();
        profilePictureURL = in.readString();
        picOriginal = in.readString();
        picMedium = in.readString();
        picThumb = in.readString();

        balance = in.readFloat();

        supporters = in.createIntArray();
        supportedCauses = in.createIntArray();

        tags = (ArrayList<String>) in.readSerializable();
        transactionsCreated = (ArrayList<Transaction>) in.readSerializable();
        transactionsAccepted = (ArrayList<Transaction>) in.readSerializable();
        transactionsAll = (ArrayList<Transaction>) in.readSerializable();

        // redeemableBusiness == true if byte != 0
        redeemableBusiness = in.readByte() != 0;
    }

    @Override
    public String toString() {
        return companyName + " | " + id + " | " + role;
    }

    /**
     * This field is required by Android to be able to create new objects,
     * individually or as arrays.
     *
     * This allows for the default constructor to be used to create the object
     * and a Creator method to be used to lazily instantiate it.
     */
    public static final Creator CREATOR =
            new Creator<User>() {
                public User createFromParcel(Parcel in) {
                    return new User(in);
                }

                public User[] newArray(int size) {
                    return new User[size];
                }
            };

    private void retrieveAssociationIds(JSONArray sourceJson, int[] destination, String key) {
        destination = new int[sourceJson.length()];
        for (int i = 0; i < sourceJson.length(); i++) {
            JSONObject association = sourceJson.optJSONObject(i);
            destination[i] = association.optInt(key);
        }
    }

    private void retrieveTransactions(JSONArray sourceJson, ArrayList<Transaction> destination) {
        destination = new ArrayList<Transaction>();
        for (int i = 0; i < sourceJson.length(); i++) {
            destination.add(new Transaction(sourceJson.optJSONObject(i)));
        }
    }

    public String getTagsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            sb.append("#" + tags.get(i) + " ");
        }
        return sb.toString();
    }

    public void determineRedeemableBusinesses() {
        try {
            ArrayList<Predicate<User>> preds = new ArrayList<Predicate<User>>();

            for (int i : supportedCauses) {
                preds.add(new SupportedCausePredicate(i));
            }
            List<User> businesses = BusinessStore.getInstance().getBusinesses();
            redeemableBusinesses = new ArrayList<User>(Collections2.filter(businesses, Predicates.or(preds)));
        } catch (Exception e) {
            e.printStackTrace();
            redeemableBusinesses = null;
        }
    }

    public ArrayList<Transaction> sortTransactionsByDate() {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        trans.addAll(transactionsCreated);
        trans.addAll(transactionsAccepted);

        Collections.sort(trans, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction, Transaction transaction2) {
                return transaction.getCreatedAt().compareTo(transaction2.getCreatedAt());
            }
        });

        return trans;
    }

    // Required accessor methods

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getPicOriginal() {
        return picOriginal;
    }

    public void setPicOriginal(String picOriginal) {
        this.picOriginal = picOriginal;
    }

    public String getPicMedium() {
        return picMedium;
    }

    public void setPicMedium(String picMedium) {
        this.picMedium = picMedium;
    }

    public String getPicThumb() {
        return picThumb;
    }

    public void setPicThumb(String picThumb) {
        this.picThumb = picThumb;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<Transaction> getTransactionsAll() {
        return transactionsAll;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int[] getSupporters() {
        return supporters;
    }

    public int getSupportersCount() {
        if (supporters == null)
            return 0;
        else
            return supporters.length;
    }

    public int[] getSupportedCauses() {
        return supportedCauses;
    }


    public int getSupportedCausesCount() {
        if (supportedCauses == null)
            return 0;
        else
            return supportedCauses.length;
    }

    public ArrayList<User> getRedeemableBusinesses() {
        return redeemableBusinesses;
    }

    public void setRedeemableBusinesses(ArrayList<User> redeemableBusinesses) {
        this.redeemableBusinesses = redeemableBusinesses;
    }

    public boolean isRedeemableBusiness() {
        return redeemableBusiness;
    }

    public void setRedeemableBusiness(boolean redeemableBusiness) {
        this.redeemableBusiness = redeemableBusiness;
    }

    static public class UserIdPredicate implements Predicate<User> {

        private int id;

        public UserIdPredicate(int id) {
            this.id = id;
        }

        @Override
        public boolean apply(User user) {
            return user.getId().equals(Integer.toString(id));
        }
    }

    static public class SupportedCausePredicate implements Predicate<User> {

        private int id;

        public SupportedCausePredicate(int id) {
            this.id = id;
        }

        @Override
        public boolean apply(User user) {
            for (int i : user.getSupportedCauses()) {
                if (i == id) {
                    user.setRedeemableBusiness(true);
                    return true;
                }
            }
            return false;
        }
    }
}

