package com.taliflo.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Caswell on 1/7/2014.
 */
public class Transaction implements Parcelable {

    // Member fields
    private String id, transID, stripeID, transType, fromUserID, toUserID,
    fromName, toName, fromUserRole, toUserRole, amount, status;
    private DateTime cancelledAt, completedAt, createdAt, updatedAt;

    private DateTimeFormatter inputDtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private DateTimeFormatter outputDtf = DateTimeFormat.forPattern("MM/dd/yyyy");

    // Constructors
    public Transaction(){}

    /**
     * Standard constructor for non-parcel object creation
     * @param jsonObject
     */
    public Transaction(JSONObject jsonObject) {
        id = jsonObject.optString("id");
        transID = jsonObject.optString("trans_id");
        stripeID = jsonObject.optString("stripe_id");


        transType = jsonObject.optString("trans_type");


        fromUserID = jsonObject.optString("from_user_id");
        toUserID = jsonObject.optString("to_user_id");
        fromName = jsonObject.optString("from_name");
        toName = jsonObject.optString("to_name");
        fromUserRole = jsonObject.optString("from_user_role");
        toUserRole = jsonObject.optString("to_user_role");
        amount = jsonObject.optString("amount");
        status = jsonObject.optString("status");
        //cancelledAt = inputDtf.parseDateTime(jsonObject.optString("cancelled_at"));
        //completedAt = inputDtf.parseDateTime(jsonObject.optString("completed_at"));
        createdAt = inputDtf.parseDateTime(jsonObject.optString("created_at"));
        updatedAt = inputDtf.parseDateTime(jsonObject.optString("updated_at"));
    }

    /**
     * Constructor to use when reconstructing Transaction object from a parcel
     * @param in
     */
    public Transaction(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(transID);
        dest.writeString(stripeID);
        dest.writeString(transType);
        dest.writeString(fromUserID);
        dest.writeString(toUserID);
        dest.writeString(fromName);
        dest.writeString(toName);
        dest.writeString(fromUserRole);
        dest.writeString(toUserRole);
        dest.writeString(amount);
        dest.writeString(status);
        //dest.writeString(cancelledAt.toString());
        //dest.writeString(completedAt.toString());
        dest.writeString(createdAt.toString());
        dest.writeString(updatedAt.toString());
    }

    /**
     * Called from the constructor to create this object from a parcel.
     * @param in
     */
    private void readFromParcel(Parcel in) {
        // Each field is read back in the order it was written to the parcel
        id = in.readString();
        transID = in.readString();
        stripeID = in.readString();
        transType = in.readString();
        fromUserID = in.readString();
        toUserID = in.readString();
        fromName = in.readString();
        toName = in.readString();
        fromUserRole = in.readString();
        toUserRole = in.readString();
        amount = in.readString();
        status = in.readString();
        //cancelledAt = new DateTime(in.readString());
        //completedAt = new DateTime(in.readString());
        createdAt = new DateTime(in.readString());
        updatedAt = new DateTime(in.readString());
    }

    @Override
    public String toString() {
        return "Type: " + transType + " | From: " + fromName + " | To: " + toName + " | Amount: " + amount;
    }

    /**
     * This field is required by Android to be able to create new objects,
     * individually or as arrays.
     *
     * This allows for the default constructor to be used to create the object
     * and a Creator method to be used to lazily instantiate it.
     */
    public static final Creator CREATOR =
            new Creator<Transaction>() {
                public Transaction createFromParcel(Parcel in) {
                    return new Transaction(in);
                }

                public Transaction[] newArray(int size) {
                    return new Transaction[size];
                }
            };

    public static void sortByDateTimeUpdated(List<Transaction> transList) {
        Collections.sort(transList, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction arg0, Transaction arg1) {
                return new DateTime(arg0.getUpdatedAt()).compareTo(arg1.getUpdatedAt());
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

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getStripeID() {
        return stripeID;
    }

    public void setStripeID(String stripeID) {
        this.stripeID = stripeID;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(String fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getToUserID() {
        return toUserID;
    }

    public void setToUserID(String toUserID) {
        this.toUserID = toUserID;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromUserRole() {
        return fromUserRole;
    }

    public void setFromUserRole(String fromUserRole) {
        this.fromUserRole = fromUserRole;
    }

    public String getToUserRole() {
        return toUserRole;
    }

    public void setToUserRole(String toUserRole) {
        this.toUserRole = toUserRole;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(DateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String cancelledAt() {
        return outputDtf.print(cancelledAt);
    }

    public DateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(DateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String completedAt() {
        return outputDtf.print(completedAt);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String createdAt() {
        return outputDtf.print(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String updatedAt() {
        return outputDtf.print(updatedAt);
    }
}
