package com.crystal.selfcheckoutapp.Modelo.retrofit.response.referido;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseReferidoWsp extends ResponseBase {

    @SerializedName("dato")
    @Expose
    private ReferidoWsp dato;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseReferidoWsp(Boolean esValida, String mensaje, ReferidoWsp dato, List<Object> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    public ReferidoWsp getDato() {
        return dato;
    }

    public void setDato(ReferidoWsp dato) {
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
        return "ResponseReferidoWsp{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }
}
