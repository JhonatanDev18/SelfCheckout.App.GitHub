package com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RespuestaLine implements Serializable {
    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;

    @SerializedName("markDown")
    @Expose
    private String markDown;

    @SerializedName("description")
    private String description;

    @SerializedName("idSaleCondition")
    private String idSaleCondition;

    public RespuestaLine(String amount, String id, String unitPrice, String markDown, String description,
                         String idSaleCondition) {
        this.amount = amount;
        this.id = id;
        this.unitPrice = unitPrice;
        this.markDown = markDown;
        this.description = description;
        this.idSaleCondition = idSaleCondition;
    }

    @Override
    public String toString() {
        return "RespuestaLine{" +
                "amount='" + amount + '\'' +
                ", id='" + id + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", markDown='" + markDown + '\'' +
                ", description='" + description + '\'' +
                ", idSaleCondition='" + idSaleCondition + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMarkDown() {
        return markDown;
    }

    public void setMarkDown(String markDown) {
        this.markDown = markDown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdSaleCondition() {
        return idSaleCondition;
    }

    public void setIdSaleCondition(String idSaleCondition) {
        this.idSaleCondition = idSaleCondition;
    }
}
