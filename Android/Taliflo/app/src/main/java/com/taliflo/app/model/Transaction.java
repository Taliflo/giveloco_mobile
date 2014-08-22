package com.taliflo.app.model;

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
public class Transaction {

    // Member fields
    private String id, transID, stripeID, transType, fromUserID, toUserID,
    fromName, toName, fromUserRole, toUserRole, amount, status;
    private DateTime cancelledAt, completedAt, createdAt, updatedAt;

    private DateTimeFormatter inputDtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private DateTimeFormatter outputDtf = DateTimeFormat.forPattern("MM/dd/yyyy");

    // Constructors
    public Transaction(){}

    public Transaction(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            transID = jsonObject.getString("trans_id");
            stripeID = jsonObject.getString("stripe_id");


            transType = jsonObject.getString("trans_type");


            fromUserID = jsonObject.getString("from_user_id");
            toUserID = jsonObject.getString("to_user_id");
            fromName = jsonObject.getString("from_name");
            toName = jsonObject.getString("to_name");
            fromUserRole = jsonObject.getString("from_user_role");
            toUserRole = jsonObject.getString("to_user_role");
            amount = jsonObject.getString("amount");
            status = jsonObject.getString("status");
            //cancelledAt = inputDtf.parseDateTime(jsonObject.getString("cancelled_at"));
            //completedAt = inputDtf.parseDateTime(jsonObject.getString("completed_at"));
            createdAt = inputDtf.parseDateTime(jsonObject.getString("created_at"));
            updatedAt = inputDtf.parseDateTime(jsonObject.getString("updated_at"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
