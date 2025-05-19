package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineCD {
    @SerializedName("discountTypeId")
    @Expose
    private String discountTypeId;

    @SerializedName("netUnitPrice")
    @Expose
    private Double netUnitPrice;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("reference")
    @Expose
    private String reference;

    @SerializedName("salesPersonId")
    @Expose
    private String salesPersonId;

    @SerializedName("unitPrice")
    @Expose
    private Double unitPrice;

    @SerializedName("serialNumberId")
    @Expose
    private String serialNumberId;

    @SerializedName("movementReasonId")
    @Expose
    private String movementReasonId;

    public LineCD(){

    }

    public LineCD(String discountTypeId, Double netUnitPrice, String quantity, String reference,
                  String salesPersonId, Double unitPrice, String serialNumberId, String movementReasonId) {
        this.discountTypeId = discountTypeId;
        this.netUnitPrice = netUnitPrice;
        this.quantity = quantity;
        this.reference = reference;
        this.salesPersonId = salesPersonId;
        this.unitPrice = unitPrice;
        this.serialNumberId = serialNumberId;
        this.movementReasonId = movementReasonId;
    }

    @Override
    public String toString() {
        return "LineCD{" +
                "discountTypeId='" + discountTypeId + '\'' +
                ", netUnitPrice=" + netUnitPrice +
                ", quantity='" + quantity + '\'' +
                ", reference='" + reference + '\'' +
                ", salesPersonId='" + salesPersonId + '\'' +
                ", unitPrice=" + unitPrice +
                ", serialNumberId=" + serialNumberId +
                ", movementReasonId='" + movementReasonId + '\'' +
                '}';
    }

    public String getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(String discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public Double getNetUnitPrice() {
        return netUnitPrice;
    }

    public void setNetUnitPrice(Double netUnitPrice) {
        this.netUnitPrice = netUnitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSerialNumberId() {
        return serialNumberId;
    }

    public void setSerialNumberId(String serialNumberId) {
        this.serialNumberId = serialNumberId;
    }

    public String getMovementReasonId() {
        return movementReasonId;
    }

    public void setMovementReasonId(String movementReasonId) {
        this.movementReasonId = movementReasonId;
    }
}
