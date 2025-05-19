package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.SeguridadDatafono;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRespuestaDatafono {
    @SerializedName("idTransaccion")
    @Expose
    private String idTransaccion;
    @SerializedName("parte")
    @Expose
    private String parte;
    @SerializedName("seguridad")
    @Expose
    private SeguridadDatafono seguridad;

    public RequestRespuestaDatafono(String idTransaccion, String parte, SeguridadDatafono seguridad) {
        this.idTransaccion = idTransaccion;
        this.parte = parte;
        this.seguridad = seguridad;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public SeguridadDatafono getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(SeguridadDatafono seguridad) {
        this.seguridad = seguridad;
    }

    @Override
    public String toString() {
        return "RequestRespuestaDatafono{" +
                "idTransaccion='" + idTransaccion + '\'' +
                ", parte='" + parte + '\'' +
                ", seguridad=" + seguridad +
                '}';
    }
}
