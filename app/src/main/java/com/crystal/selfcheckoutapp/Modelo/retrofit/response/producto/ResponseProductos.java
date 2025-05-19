package com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProductos extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Object dato;

    @SerializedName("listado")
    @Expose
    private List<Producto> listaProductos;

    public ResponseProductos(Boolean esValida, String mensaje, Object dato, List<Producto> listaProductos) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listaProductos = listaProductos;
    }

    @Override
    public String toString() {
        return "ResponseProductos{" +
                "dato=" + dato +
                ", listaProductos=" + listaProductos +
                '}';
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
}
