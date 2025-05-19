package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.clases.Seguridad;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLogin {
    @SerializedName("Seguridad")
    @Expose
    private Seguridad seguridad;

    public RequestLogin(Seguridad seguridad) {
        this.seguridad = seguridad;
    }

    public Seguridad getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Seguridad seguridad) {
        this.seguridad = seguridad;
    }
}
