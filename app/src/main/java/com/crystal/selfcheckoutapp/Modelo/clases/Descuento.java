package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Descuento implements Serializable {
    @Expose
    private String codigo;

    @Expose
    private String mensaje;

    @Expose
    private String codigoMP;

    public Descuento(String codigo, String mensaje, String codigoMP) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.codigoMP = codigoMP;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigoMP() {
        return codigoMP;
    }

    public void setCodigoMP(String codigoMP) {
        this.codigoMP = codigoMP;
    }
}
