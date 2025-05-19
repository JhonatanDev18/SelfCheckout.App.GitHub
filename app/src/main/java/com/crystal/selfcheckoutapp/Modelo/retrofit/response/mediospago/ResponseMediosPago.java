package com.crystal.selfcheckoutapp.Modelo.retrofit.response.mediospago;

import com.crystal.selfcheckoutapp.Modelo.clases.MediosPago;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMediosPago extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Object dato;

    @SerializedName("listado")
    @Expose
    private List<MediosPago> mediosPagoList;

    public ResponseMediosPago(Boolean esValida, String mensaje, Object dato, List<MediosPago> mediosPagoList) {
        super(esValida, mensaje);
        this.dato = dato;
        this.mediosPagoList = mediosPagoList;
    }

    @Override
    public String toString() {
        return "ResponseMediosPago{" +
                "dato=" + dato +
                ", mediosPagoList=" + mediosPagoList +
                '}';
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public List<MediosPago> getMediosPagoList() {
        return mediosPagoList;
    }

    public void setMediosPagoList(List<MediosPago> mediosPagoList) {
        this.mediosPagoList = mediosPagoList;
    }
}
