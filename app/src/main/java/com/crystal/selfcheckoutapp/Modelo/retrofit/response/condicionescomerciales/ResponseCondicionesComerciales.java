package com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCondicionesComerciales extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Condiciones condiciones;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseCondicionesComerciales(Boolean esValida, String mensaje, Condiciones condiciones, List<Object> listado) {
        super(esValida, mensaje);
        this.condiciones = condiciones;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCondicionesComerciales{" +
                "condiciones=" + condiciones +
                ", listado=" + listado +
                '}';
    }

    public Condiciones getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(Condiciones condiciones) {
        this.condiciones = condiciones;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
