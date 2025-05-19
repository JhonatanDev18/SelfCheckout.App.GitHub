package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestReferidoWsp {
    @SerializedName("codigoWsp")
    @Expose
    private String codigo;

    public RequestReferidoWsp(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "RequestReferidoWsp{" +
                "codigo='" + codigo + '\'' +
                '}';
    }
}
