package com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseParametros extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Object dato;

    @SerializedName("listado")
    @Expose
    private List<Parametro> listado;

    public ResponseParametros(Boolean esValida, String mensaje, Object dato, List<Parametro> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseParametros{" +
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

    public List<Parametro> getListado() {
        return listado;
    }

    public void setListado(List<Parametro> listado) {
        this.listado = listado;
    }
}
