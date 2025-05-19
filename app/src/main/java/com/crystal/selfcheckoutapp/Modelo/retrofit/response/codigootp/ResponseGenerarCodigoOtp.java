package com.crystal.selfcheckoutapp.Modelo.retrofit.response.codigootp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGenerarCodigoOtp extends ResponseBase {

    @SerializedName("dato")
    @Expose
    private CodigoOtp dato;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseGenerarCodigoOtp(Boolean esValida, String mensaje, CodigoOtp dato, List<Object> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseGenerarCodigoOtp{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }

    public CodigoOtp getDato() {
        return dato;
    }

    public void setDato(CodigoOtp dato) {
        this.dato = dato;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
