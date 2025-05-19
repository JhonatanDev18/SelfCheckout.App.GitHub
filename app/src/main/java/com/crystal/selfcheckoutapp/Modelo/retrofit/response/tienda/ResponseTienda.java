package com.crystal.selfcheckoutapp.Modelo.retrofit.response.tienda;

import com.google.gson.annotations.SerializedName;

public class ResponseTienda {

    @SerializedName("dato")
    public TiendaItem tienda;

    @SerializedName("mensaje")
    public String mensaje;
    @SerializedName("esValida")
    private Boolean esValida;

    public TiendaItem getTienda() {
        return tienda;
    }

    public void setTienda(TiendaItem tienda) {
        this.tienda = tienda;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getEsValida() {
        return esValida;
    }

    public void setEsValida(Boolean error) {
        this.esValida = error;
    }

    @Override
    public String toString() {
        return "ResponseTienda{" +
                "tienda=" + tienda +
                ", mensaje='" + mensaje + '\'' +
                ", error=" + esValida +
                '}';
    }
}