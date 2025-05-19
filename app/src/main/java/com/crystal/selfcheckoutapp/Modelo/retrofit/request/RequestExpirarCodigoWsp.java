package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestExpirarCodigoWsp {
    @SerializedName("codigoWsp")
    @Expose
    private String codigoWsp;

    public RequestExpirarCodigoWsp(String codigoWsp) {
        this.codigoWsp = codigoWsp;
    }

    public String getCodigoWsp() {
        return codigoWsp;
    }

    public void setCodigoWsp(String codigoWsp) {
        this.codigoWsp = codigoWsp;
    }

    @Override
    public String toString() {
        return "RequestExpirarCodigoWsp{" +
                "codigoWsp='" + codigoWsp + '\'' +
                '}';
    }
}
