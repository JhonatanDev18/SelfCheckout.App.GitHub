package com.crystal.selfcheckoutapp.Modelo.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTiendas {
    @SerializedName("esValida")
    @Expose
    private boolean esValida;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("tiendas")
    @Expose
    private List<String> tiendas;

    public ResponseTiendas(boolean esValida, String mensaje, List<String> tiendas) {
        this.esValida = esValida;
        this.mensaje = mensaje;
        this.tiendas = tiendas;
    }

    public List<String> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<String> tiendas) {
        this.tiendas = tiendas;
    }

    public boolean isEsValida() {
        return esValida;
    }

    public void setEsValida(boolean esValida) {
        this.esValida = esValida;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}