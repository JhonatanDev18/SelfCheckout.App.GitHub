package com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseActualizarTransaccion extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Object dato;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseActualizarTransaccion(Boolean esValida, String mensaje, Object dato, List<Object> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseActualizarTransaccion{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }
}
