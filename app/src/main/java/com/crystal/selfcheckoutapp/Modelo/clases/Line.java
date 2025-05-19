package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("ignoreSaleCondition")
    @Expose
    private Boolean ignoreSaleCondition;

    @SerializedName("itemId")
    @Expose
    private String itemId;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("unitPrice")
    @Expose
    private UnitPrice unitPrice;

    public Line(Integer id, Boolean ignoreSaleCondition, String itemId, Integer quantity, UnitPrice unitPrice) {
        this.id = id;
        this.ignoreSaleCondition = ignoreSaleCondition;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", ignoreSaleCondition=" + ignoreSaleCondition +
                ", itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIgnoreSaleCondition() {
        return ignoreSaleCondition;
    }

    public void setIgnoreSaleCondition(Boolean ignoreSaleCondition) {
        this.ignoreSaleCondition = ignoreSaleCondition;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(UnitPrice unitPrice) {
        this.unitPrice = unitPrice;
    }
}
