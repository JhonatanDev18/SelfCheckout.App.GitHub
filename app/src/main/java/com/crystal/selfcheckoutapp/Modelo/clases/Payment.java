package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payment implements Serializable {
    @SerializedName("Nombre")
    @Expose
    private String name;

    @SerializedName("Amount")
    @Expose
    private Double amount;

    @SerializedName("CurrencyId")
    @Expose
    private String currencyId;

    @SerializedName("DueDate")
    @Expose
    private String dueDate;

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("IsReceivedPayment")
    @Expose
    private Integer isReceivedPayment;

    @SerializedName("MethodId")
    @Expose
    private String methodId;

    private boolean isTEF;
    private boolean eliminable;

    @SerializedName("BonId")
    @Expose
    private String bonoRegalo;

    public Payment(){

    }

    public Payment(String name, Double amount, String currencyId, String dueDate, Integer id, Integer isReceivedPayment, String methodId) {
        this.name = name;
        this.amount = amount;
        this.currencyId = currencyId;
        this.dueDate = dueDate;
        this.id = id;
        this.isReceivedPayment = isReceivedPayment;
        this.methodId = methodId;
        this.isTEF = false;
        this.eliminable = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsReceivedPayment() {
        return isReceivedPayment;
    }

    public void setIsReceivedPayment(Integer isReceivedPayment) {
        this.isReceivedPayment = isReceivedPayment;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public boolean isTEF() {
        return isTEF;
    }

    public void setTEF(boolean TEF) {
        isTEF = TEF;
    }

    public boolean isEliminable() {
        return eliminable;
    }

    public void setEliminable(boolean eliminable) {
        this.eliminable = eliminable;
    }

    public String getBonoRegalo() {
        return bonoRegalo;
    }

    public void setBonoRegalo(String bonoRegalo) {
        this.bonoRegalo = bonoRegalo;
    }
}
