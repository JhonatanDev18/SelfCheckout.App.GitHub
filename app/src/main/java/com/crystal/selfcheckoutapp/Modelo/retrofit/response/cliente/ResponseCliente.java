package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCliente extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Cliente cliente;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseCliente(Boolean esValida, String mensaje, Cliente cliente, List<Object> listado) {
        super(esValida, mensaje);
        this.cliente = cliente;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCliente{" +
                "cliente=" + cliente +
                ", listado=" + listado +
                '}';
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
