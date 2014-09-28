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
    protected String[] tags;
    protected Transaction[] transactionsCreated, transactionsAccepted, transactionsAll;
    private int totalDebits, totalCredits;
    private float balance, totalDebitsValue, totalCreditsValue;
    private int[] supporters, supportedCauses;

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
        firstName = jsonObject.optString("first_name");
        lastName = jsonObject.optString("last_name");
        companyName = jsonObject.optString("company_name");
        summary = jsonObject.optString("summary");
        description = jsonObject.optString("description");
        website = jsonObject.optString("website");
        email = jsonObject.optString("email");
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
        tags = new String[jsonTags.length()];
        for (int i = 0; i < jsonTags.length(); i++) {
            tags[i] = jsonTags.optString(i);
        }

        if (role.equals("individual")) {

            balance = Float.parseFloat(jsonObject.optString("balance"));

            JSONArray jsonTC = jsonObject.optJSONArray("transactions_created");
            transactionsCreated = new Transaction[jsonTC.length()];
            for (int i = 0; i < jsonTC.length(); i++) {
                transactionsCreated[i] = new Transaction(jsonTC.optJSONObject(i));
            }

            JSONArray jsonTA = jsonObject.optJSONArray("transactions_accepted");
            transactionsAccepted = new Transaction[jsonTA.length()];
            for (int i = 0; i < jsonTA.length(); i++) {
                transactionsAccepted[i] = new Transaction(jsonTA.optJSONObject(i));
            }

        }

        if (role.equals("business")) {
            JSONArray jsonSupport = jsonObject.optJSONArray("supported_causes");
            supportedCauses = new int[jsonSupport.length()];
            for (int i = 0; i < jsonSupport.length(); i++) {
                supportedCauses[i] = jsonSupport.optInt(i);
            }
        }

        if (role.equals("cause")) {
            JSONArray jsonSupport = jsonObject.optJSONArray("supporters");
            supporters = new int[jsonSupport.length()];
            for (int i = 0; i < jsonSupport.length(); i++) {
                supporters[i] = jsonSupport.optInt(i);
            }
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

        //dest.writeInt(totalDebits);
        //dest.writeInt(totalCredits);

        dest.writeFloat(balance);
        //dest.writeFloat(totalDebitsValue);
        //dest.writeFloat(totalCreditsValue);

        dest.writeStringArray(tags);
        dest.writeIntArray(supporters);
        dest.writeIntArray(supportedCauses);

        dest.writeTypedArray(transactionsCreated, 0);
        dest.writeTypedArray(transactionsAccepted, 0);

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

        //totalDebits = in.readInt();
        //totalCredits = in.readInt();

        balance = in.readFloat();
        //totalDebitsValue = in.readFloat();
        //totalCreditsValue = in.readFloat();

        tags = in.createStringArray();
        supporters = in.createIntArray();
        supportedCauses = in.createIntArray();

        transactionsCreated = (Transaction[]) in.createTypedArray(Transaction.CREATOR);
        transactionsAccepted = (Transaction[]) in.createTypedArray(Transaction.CREATOR);

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

    public String getTagsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            sb.append("#" + tags[i] + " ");
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

    public void sortTransactionsByDate() {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        trans.addAll(Arrays.asList(transactionsCreated));
        trans.addAll(Arrays.asList(transactionsAccepted));

        Collections.sort(trans, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction, Transaction transaction2) {
                return transaction.getCreatedAt().compareTo(transaction2.getCreatedAt());
            }
        });
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

    public String getFullName() {
        return firstName + " " + lastName;
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

    public int getTotalDebits() {
        return totalDebits;
    }

    public void setTotalDebits(int totalDebits) {
        this.totalDebits = totalDebits;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getTotalDebitsValue() {
        return totalDebitsValue;
    }

    public void setTotalDebitsValue(float totalDebitsValue) {
        this.totalDebitsValue = totalDebitsValue;
    }

    public float getTotalCreditsValue() {
        return totalCreditsValue;
    }

    public void setTotalCreditsValue(float totalCreditsValue) {
        this.totalCreditsValue = totalCreditsValue;
    }

    public int[] getSupporters() {
        return supporters;
    }

    public void setSupporters(int[] supporters) {
        this.supporters = supporters;
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

    public void setSupportedCauses(int[] supportedCauses) {
        this.supportedCauses = supportedCauses;
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

