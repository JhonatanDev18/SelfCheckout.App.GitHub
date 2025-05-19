package com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCompraDatafono extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private RequestCompraDatafono compraDatafono;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseCompraDatafono(Boolean esValida, String mensaje, RequestCompraDatafono compraDatafono, List<Object> listado) {
        super(esValida, mensaje);
        this.compraDatafono = compraDatafono;
        this.listado = listado;
    }

    public RequestCompraDatafono getCompraDatafono() {
        return compraDatafono;
    }

    public void setCompraDatafono(RequestCompraDatafono compraDatafono) {
        this.compraDatafono = compraDatafono;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCompraDatafono{" +
                "compraDatafono=" + compraDatafono +
                ", listado=" + listado +
                '}';
    }
}
