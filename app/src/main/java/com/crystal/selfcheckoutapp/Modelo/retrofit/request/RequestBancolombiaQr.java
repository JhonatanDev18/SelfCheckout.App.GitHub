package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.SerializedName;

public class RequestBancolombiaQr {
    @SerializedName("reference")
    private String reference;

    @SerializedName("amount")
    private String amount;

    @SerializedName("documentType")
    private String documentType;

    @SerializedName("documentNumber")
    private String documentNumber;

    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("proposito")
    private String proposito;
    @SerializedName("merchantID")
    private String merchantID;
    @SerializedName("nombreTienda")
    private String nombreTienda;

    public RequestBancolombiaQr(String reference, String amount, String documentType, String documentNumber, String customerName, String proposito, String merchantID, String nombreTienda) {
        this.reference = reference;
        this.amount = amount;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.customerName = customerName;
        this.proposito = proposito;
        this.merchantID = merchantID;
        this.nombreTienda = nombreTienda;
    }

    @Override
    public String toString() {
        return "RequestBancolombiaQr{" +
                "reference='" + reference + '\'' +
                ", amount='" + amount + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ",customer_name = '" + customerName + '\'' +
                ", proposito = '" + proposito + '\'' +
                ", merchantID = '" + merchantID + '\'' +
                ", nombreTienda = '" + nombreTienda + '\'' +
                '}';
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setProposito(String proposito){
        this.proposito = proposito;
    }
    public String getProposito(){
        return proposito;
    }
    public void setMerchantID(String merchantID){
        this.merchantID = merchantID;
    }
    public String getMerchantID(){
        return merchantID;
    }
    public void setNombreTienda(String nombreTienda){
        this.nombreTienda = nombreTienda;
    }
    public String getNombreTienda(){
        return nombreTienda;
    }
}
