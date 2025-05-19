package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCrearCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCrearCliente extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private RequestCrearCliente cliente;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseCrearCliente(Boolean esValida, String mensaje, RequestCrearCliente cliente, List<Object> listado) {
        super(esValida, mensaje);
        this.cliente = cliente;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCrearCliente{" +
                "cliente=" + cliente +
                ", listado=" + listado +
                '}';
    }

    public RequestCrearCliente getCliente() {
        return cliente;
    }

    public void setCliente(RequestCrearCliente cliente) {
        this.cliente = cliente;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
