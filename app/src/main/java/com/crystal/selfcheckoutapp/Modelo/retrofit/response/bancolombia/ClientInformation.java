package com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia;

import com.google.gson.annotations.SerializedName;

public class ClientInformation {
    @SerializedName("name")
    private String name;

    @SerializedName("payerReference")
    private String payerReference;

    @SerializedName("accountNumber")
    private String accountNumber;

    public ClientInformation(String name, String payerReference, String accountNumber) {
        this.name = name;
        this.payerReference = payerReference;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayerReference() {
        return payerReference;
    }

    public void setPayerReference(String payerReference) {
        this.payerReference = payerReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
