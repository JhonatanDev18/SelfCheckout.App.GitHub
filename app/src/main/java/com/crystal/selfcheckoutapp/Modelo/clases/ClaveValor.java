package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ClaveValor implements Serializable {
    @Expose
    private String clave;

    @Expose
    private String valor;

    public ClaveValor(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "ClaveValor{" +
                "clave='" + clave + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
