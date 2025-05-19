package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestConsultarDocumento {
    @SerializedName("referenciaInterna")
    @Expose
    private String referenciaInterna;

    public RequestConsultarDocumento(String referenciaInterna) {
        this.referenciaInterna = referenciaInterna;
    }

    public String getReferenciaInterna() {
        return referenciaInterna;
    }

    public void setReferenciaInterna(String referenciaInterna) {
        this.referenciaInterna = referenciaInterna;
    }

    @Override
    public String toString() {
        return "RequestConsultarDocumento{" +
                "referenciaInterna='" + referenciaInterna + '\'' +
                '}';
    }
}
