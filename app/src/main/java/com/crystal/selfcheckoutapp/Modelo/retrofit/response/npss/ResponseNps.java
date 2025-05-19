package com.crystal.selfcheckoutapp.Modelo.retrofit.response.npss;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNps extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private NetPromotorScore dato;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseNps(Boolean esValida, String mensaje, NetPromotorScore dato, List<Object> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    public NetPromotorScore getDato() {
        return dato;
    }

    public void setDato(NetPromotorScore dato) {
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
        return "ResponseNps{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }
}
