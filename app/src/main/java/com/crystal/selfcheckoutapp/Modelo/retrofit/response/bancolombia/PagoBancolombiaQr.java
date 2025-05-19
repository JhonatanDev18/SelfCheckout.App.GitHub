package com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia;

import com.google.gson.annotations.SerializedName;

public class PagoBancolombiaQr {
    @SerializedName("transferVoucher")
    private String transferVoucher;

    @SerializedName("transferAmount")
    private String transferAmount;

    @SerializedName("transferDescription")
    private String transferDescription;

    @SerializedName("transferState")
    private String transferState;

    @SerializedName("requestDate")
    private String requestDate;

    @SerializedName("transferDate")
    private String transferDate;

    @SerializedName("sign")
    private String sign;

    @SerializedName("transferReference")
    private String transferReference;

    @SerializedName("transferCodeResponse")
    private String transferCodeResponse;

    @SerializedName("transferDescriptionResponse")
    private String transferDescriptionResponse;

    @SerializedName("destinationAccountNumber")
    private String destinationAccountNumber;

    @SerializedName("clientInformation")
    private ClientInformation clientInformation;

    public PagoBancolombiaQr(String transferVoucher, String transferAmount, String transferDescription,
                             String transferState, String requestDate, String transferDate,
                             String sign, String transferReference, String transferCodeResponse,
                             String transferDescriptionResponse, String destinationAccountNumber,
                             ClientInformation clientInformation) {
        this.transferVoucher = transferVoucher;
        this.transferAmount = transferAmount;
        this.transferDescription = transferDescription;
        this.transferState = transferState;
        this.requestDate = requestDate;
        this.transferDate = transferDate;
        this.sign = sign;
        this.transferReference = transferReference;
        this.transferCodeResponse = transferCodeResponse;
        this.transferDescriptionResponse = transferDescriptionResponse;
        this.destinationAccountNumber = destinationAccountNumber;
        this.clientInformation = clientInformation;
    }

    @Override
    public String toString() {
        return "PagoBancolombiaQr{" +
                "transferVoucher='" + transferVoucher + '\'' +
                ", transferAmount='" + transferAmount + '\'' +
                ", transferDescription='" + transferDescription + '\'' +
                ", transferState='" + transferState + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", transferDate='" + transferDate + '\'' +
                ", sign='" + sign + '\'' +
                ", transferReference='" + transferReference + '\'' +
                ", transferCodeResponse='" + transferCodeResponse + '\'' +
                ", transferDescriptionResponse='" + transferDescriptionResponse + '\'' +
                ", destinationAccountNumber='" + destinationAccountNumber + '\'' +
                ", clientInformation=" + clientInformation +
                '}';
    }

    public String getTransferVoucher() {
        return transferVoucher;
    }

    public void setTransferVoucher(String transferVoucher) {
        this.transferVoucher = transferVoucher;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
    }

    public String getTransferState() {
        return transferState;
    }

    public void setTransferState(String transferState) {
        this.transferState = transferState;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public void setTransferReference(String transferReference) {
        this.transferReference = transferReference;
    }

    public String getTransferCodeResponse() {
        return transferCodeResponse;
    }

    public void setTransferCodeResponse(String transferCodeResponse) {
        this.transferCodeResponse = transferCodeResponse;
    }

    public String getTransferDescriptionResponse() {
        return transferDescriptionResponse;
    }

    public void setTransferDescriptionResponse(String transferDescriptionResponse) {
        this.transferDescriptionResponse = transferDescriptionResponse;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public ClientInformation getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }
}
