package com.crystal.selfcheckoutapp.Modelo.retrofit.response.habeasdata;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseConsultaHabeas extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private boolean solicitarHabeas;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseConsultaHabeas(Boolean esValida, String mensaje, boolean solicitarHabeas, List<Object> listado) {
        super(esValida, mensaje);
        this.solicitarHabeas = solicitarHabeas;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseConsultaHabeas{" +
                "solicitarHabeas=" + solicitarHabeas +
                ", listado=" + listado +
                '}';
    }

    public boolean isSolicitarHabeas() {
        return solicitarHabeas;
    }

    public void setSolicitarHabeas(boolean solicitarHabeas) {
        this.solicitarHabeas = solicitarHabeas;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
