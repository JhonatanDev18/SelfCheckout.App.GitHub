package com.crystal.selfcheckoutapp.Modelo.retrofit.response;

import com.google.gson.annotations.SerializedName;

public class RespuestaExtraerInfoDocumento {
    @SerializedName("fechaCreacion")
    private String fechaCreacion;

    public RespuestaExtraerInfoDocumento(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
