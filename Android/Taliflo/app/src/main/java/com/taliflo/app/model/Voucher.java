package com.taliflo.app.model;

import org.joda.time.DateTime;

/**
 * Created by Caswell on 1/7/2014.
 */
public class Voucher {

    // Member variables
    private int id, distributorId, recipientId;
    private String distributorName, recipientName, imageUrl = "http://placehold.it/256x192";
    private boolean redeemed;
    private float value;
    private DateTime dateTime;

    // Constructors
    public Voucher() {}

    public Voucher(int id, int distributorId, int recipientId, String distributorName, String recipientName, boolean redeemed, float value) {
        this.id = id;
        this.distributorId = distributorId;
        this.recipientId = recipientId;
        this.distributorName = distributorName;
        this.recipientName = recipientName;
        this.redeemed = redeemed;
        this.value = value;
    }

    // Accessor methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDistributorId() { return distributorId; }
    public void setDistributorId(int distributorId) { this.distributorId = distributorId; }

    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public String getDistributorName() { return distributorName; }
    public void setDistributorName(String distributorName) { this.distributorName = distributorName; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public boolean isRedeemed() { return redeemed; }
    public void setRedeemStatus(boolean status) { redeemed = status; }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

}
