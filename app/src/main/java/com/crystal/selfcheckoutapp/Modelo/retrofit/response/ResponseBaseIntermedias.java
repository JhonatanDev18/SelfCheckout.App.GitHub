package com.crystal.selfcheckoutapp.Modelo.retrofit.response;

import com.google.gson.annotations.Expose;

public class ResponseBaseIntermedias {
    @Expose
    private Boolean error;
    @Expose
    private String mensaje;

    public ResponseBaseIntermedias(Boolean error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
