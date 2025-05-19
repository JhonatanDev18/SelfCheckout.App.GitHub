package com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAperturaCaja extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private AperturaCaja aperturaCaja;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseAperturaCaja(Boolean esValida, String mensaje, AperturaCaja aperturaCaja, List<Object> listado) {
        super(esValida, mensaje);
        this.aperturaCaja = aperturaCaja;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseAperturaCaja{" +
                "aperturaCaja=" + aperturaCaja +
                ", listado=" + listado +
                '}';
    }

    public AperturaCaja getAperturaCaja() {
        return aperturaCaja;
    }

    public void setAperturaCaja(AperturaCaja aperturaCaja) {
        this.aperturaCaja = aperturaCaja;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
