package com.taliflo.app.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Caswell on 1/7/2014.
 */
public class Transaction {

    // Member fields
    private String id, transID, stripeID, transType, fromUserID, toUserID,
    fromName, toName, fromUserRole, toUserRole, amount, status, cancelledAt,
    completedAt, createdAt, updatedAt;

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
            cancelledAt = jsonObject.getString("cancelled_at");
            completedAt = jsonObject.getString("completed_at");
            createdAt = jsonObject.getString("created_at");
            updatedAt = jsonObject.getString("updated_at");
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

    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
