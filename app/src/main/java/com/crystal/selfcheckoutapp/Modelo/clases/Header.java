package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Header {
    @SerializedName("currencyId")
    @Expose
    private String currencyId;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("keyWords")
    @Expose
    private List<KeyWord> keyWords;

    @SerializedName("storeId")
    @Expose
    private String storeId;

    @SerializedName("taxExcluded")
    @Expose
    private Boolean taxExcluded;

    public Header(String currencyId, String customerId, String date, List<KeyWord> keyWords,
                  String storeId, Boolean taxExcluded) {
        this.currencyId = currencyId;
        this.customerId = customerId;
        this.date = date;
        this.keyWords = keyWords;
        this.storeId = storeId;
        this.taxExcluded = taxExcluded;
    }

    @Override
    public String toString() {
        return "Header{" +
                "currencyId='" + currencyId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", date='" + date + '\'' +
                ", keyWords=" + keyWords +
                ", storeId='" + storeId + '\'' +
                ", taxExcluded=" + taxExcluded +
                '}';
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Boolean getTaxExcluded() {
        return taxExcluded;
    }

    public void setTaxExcluded(Boolean taxExcluded) {
        this.taxExcluded = taxExcluded;
    }
}
