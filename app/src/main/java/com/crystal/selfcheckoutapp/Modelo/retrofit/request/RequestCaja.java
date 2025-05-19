package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCaja {
    @SerializedName("codigoCaja")
    @Expose
    private String codigoCaja;

    public RequestCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }
}
