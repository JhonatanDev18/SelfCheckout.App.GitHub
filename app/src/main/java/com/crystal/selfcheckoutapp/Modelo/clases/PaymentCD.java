package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentCD {
    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("currencyId")
    @Expose
    private String currencyId;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("isReceivedPayment")
    @Expose
    private Boolean isReceivedPayment;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("methodId")
    @Expose
    private String methodId;

    public PaymentCD(Double amount, String currencyId, String dueDate, Integer id,
                     Boolean isReceivedPayment, String nombre, String methodId) {
        this.amount = amount;
        this.currencyId = currencyId;
        this.dueDate = dueDate;
        this.id = id;
        this.isReceivedPayment = isReceivedPayment;
        this.nombre = nombre;
        this.methodId = methodId;
    }

    @Override
    public String toString() {
        return "PaymentCD{" +
                "amount=" + amount +
                ", currencyId='" + currencyId + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", id=" + id +
                ", isReceivedPayment=" + isReceivedPayment +
                ", nombre='" + nombre + '\'' +
                ", methodId='" + methodId + '\'' +
                '}';
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

    public Boolean getReceivedPayment() {
        return isReceivedPayment;
    }

    public void setReceivedPayment(Boolean receivedPayment) {
        isReceivedPayment = receivedPayment;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }
}
