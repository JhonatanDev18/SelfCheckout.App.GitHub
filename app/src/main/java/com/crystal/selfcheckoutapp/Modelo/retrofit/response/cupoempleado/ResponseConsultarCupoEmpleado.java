package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cupoempleado;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseConsultarCupoEmpleado extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private CupoEmpleado dato;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseConsultarCupoEmpleado(Boolean esValida, String mensaje, CupoEmpleado dato, List<Object> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    public CupoEmpleado getDato() {
        return dato;
    }

    public void setDato(CupoEmpleado dato) {
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
        return "ResponseConsultarCupoEmpleado{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }
}
