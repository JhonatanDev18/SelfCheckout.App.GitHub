package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeaderCD {
    @SerializedName("active")
    @Expose
    private Boolean active;

    @SerializedName("currencyId")
    @Expose
    private String currencyId;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("internalReference")
    @Expose
    private String internalReference;

    @SerializedName("salesPersonId")
    @Expose
    private String salesPersonId;

    @SerializedName("storeId")
    @Expose
    private String storeId;

    @SerializedName("taxExcluded")
    @Expose
    private Boolean taxExcluded;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("warehouseId")
    @Expose
    private String warehouseId;

    @SerializedName("followedReference")
    @Expose
    private String followedReference;

    public HeaderCD(Boolean active, String currencyId, String customerId, String date,
                    String internalReference, String salesPersonId, String storeId,
                    Boolean taxExcluded, Integer type, String warehouseId, String followedReference) {
        this.active = active;
        this.currencyId = currencyId;
        this.customerId = customerId;
        this.date = date;
        this.internalReference = internalReference;
        this.salesPersonId = salesPersonId;
        this.storeId = storeId;
        this.taxExcluded = taxExcluded;
        this.type = type;
        this.warehouseId = warehouseId;
        this.followedReference = followedReference;
    }

    @Override
    public String toString() {
        return "HeaderCD{" +
                "active=" + active +
                ", currencyId='" + currencyId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", date='" + date + '\'' +
                ", internalReference='" + internalReference + '\'' +
                ", salesPersonId='" + salesPersonId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", taxExcluded=" + taxExcluded +
                ", type=" + type +
                ", warehouseId='" + warehouseId + '\'' +
                ", followedReference='" + followedReference + '\'' +
                '}';
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        this.salesPersonId = salesPersonId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getFollowedReference() {
        return followedReference;
    }

    public void setFollowedReference(String followedReference) {
        this.followedReference = followedReference;
    }
}
