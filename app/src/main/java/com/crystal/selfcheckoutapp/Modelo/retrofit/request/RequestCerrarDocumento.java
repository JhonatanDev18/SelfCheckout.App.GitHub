package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCerrarDocumento {
    @SerializedName("numeroTransaccion")
    @Expose
    private String numeroTransacion;

    @SerializedName("codigoCaja")
    @Expose
    private String caja;

    public RequestCerrarDocumento(String numeroTransacion, String caja) {
        this.numeroTransacion = numeroTransacion;
        this.caja = caja;
    }

    @Override
    public String toString() {
        return "RequestCerrarDocumento{" +
                "numeroTransacion='" + numeroTransacion + '\'' +
                ", caja='" + caja + '\'' +
                '}';
    }

    public String getNumeroTransacion() {
        return numeroTransacion;
    }

    public void setNumeroTransacion(String numeroTransacion) {
        this.numeroTransacion = numeroTransacion;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
}
