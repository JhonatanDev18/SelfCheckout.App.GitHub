package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCajaSimple {
    @SerializedName("Caja")
    @Expose
    private String caja;

    public RequestCajaSimple(String caja) {
        this.caja = caja;
    }

    @Override
    public String toString() {
        return "RequestCajaSimple{" +
                "caja='" + caja + '\'' +
                '}';
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
}
