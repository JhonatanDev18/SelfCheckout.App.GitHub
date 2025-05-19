package com.crystal.selfcheckoutapp.Modelo.retrofit.response.habeasdata;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestInsertarHabeasData;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInsertarHabeas extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private RequestInsertarHabeasData habeasData;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseInsertarHabeas(Boolean esValida, String mensaje, RequestInsertarHabeasData habeasData,
                                  List<Object> listado) {
        super(esValida, mensaje);
        this.habeasData = habeasData;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseInsertarHabeas{" +
                "habeasData=" + habeasData +
                ", listado=" + listado +
                '}';
    }

    public RequestInsertarHabeasData getHabeasData() {
        return habeasData;
    }

    public void setHabeasData(RequestInsertarHabeasData habeasData) {
        this.habeasData = habeasData;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
