package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.clases.HeaderCD;
import com.crystal.selfcheckoutapp.Modelo.clases.LineCD;
import com.crystal.selfcheckoutapp.Modelo.clases.PaymentCD;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestCrearDocumento {
    @SerializedName("header")
    @Expose
    private HeaderCD header;

    @SerializedName("lines")
    @Expose
    private List<LineCD> lines;

    @SerializedName("payments")
    @Expose
    private List<PaymentCD> payments;

    @SerializedName("caja")
    @Expose
    private String caja;

    public RequestCrearDocumento(HeaderCD header, List<LineCD> lines, List<PaymentCD> payments, String caja) {
        this.header = header;
        this.lines = lines;
        this.payments = payments;
        this.caja = caja;
    }

    @Override
    public String toString() {
        return "RequestCrearDocumento{" +
                "header=" + header +
                ", lines=" + lines +
                ", payments=" + payments +
                ", caja='" + caja + '\'' +
                '}';
    }

    public HeaderCD getHeader() {
        return header;
    }

    public void setHeader(HeaderCD header) {
        this.header = header;
    }

    public List<LineCD> getLines() {
        return lines;
    }

    public void setLines(List<LineCD> lines) {
        this.lines = lines;
    }

    public List<PaymentCD> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentCD> payments) {
        this.payments = payments;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
}
