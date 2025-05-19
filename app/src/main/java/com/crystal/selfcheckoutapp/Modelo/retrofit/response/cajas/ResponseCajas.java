package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class ResponseCajas extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Object dato;

    @SerializedName("listado")
    @Expose
    private List<Caja> listado;

    public ResponseCajas(Boolean esValida, String mensaje, Object dato, List<Caja> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCajas{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public List<Caja> getListado() {
        return listado;
    }

    public void setListado(List<Caja> listado) {
        this.listado = listado;
    }
}
