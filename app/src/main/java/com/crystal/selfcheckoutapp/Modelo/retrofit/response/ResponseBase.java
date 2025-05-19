package com.crystal.selfcheckoutapp.Modelo.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBase {
    @SerializedName("esValida")
    @Expose
    private Boolean esValida;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public ResponseBase(Boolean esValida, String mensaje) {
        this.esValida = esValida;
        this.mensaje = mensaje;
    }

    public Boolean getEsValida() {
        return esValida;
    }

    public void setEsValida(Boolean esValida) {
        this.esValida = esValida;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
