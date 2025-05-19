package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseActualizarCliente extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private RequestActualizarCliente cliente;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseActualizarCliente(Boolean esValida, String mensaje, RequestActualizarCliente cliente,
                                     List<Object> listado) {
        super(esValida, mensaje);
        this.cliente = cliente;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseActualizarCliente{" +
                "cliente=" + cliente +
                ", listado=" + listado +
                '}';
    }

    public RequestActualizarCliente getCliente() {
        return cliente;
    }

    public void setCliente(RequestActualizarCliente cliente) {
        this.cliente = cliente;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
