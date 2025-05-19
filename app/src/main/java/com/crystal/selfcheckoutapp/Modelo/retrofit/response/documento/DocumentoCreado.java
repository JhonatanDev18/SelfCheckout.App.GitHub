package com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentoCreado {
    @SerializedName("number")
    @Expose
    private String numeroTransaccion;

    public DocumentoCreado(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    @Override
    public String toString() {
        return "DocumentoCreado{" +
                "numeroTransaccion='" + numeroTransaccion + '\'' +
                '}';
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }
}
