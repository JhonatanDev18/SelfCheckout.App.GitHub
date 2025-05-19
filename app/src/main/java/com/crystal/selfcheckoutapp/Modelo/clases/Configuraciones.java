package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;

public class Configuraciones {
    @Expose
    private String nombre;

    @Expose
    private String codigo;

    @Expose
    private boolean enable;

    public Configuraciones(String nombre, String codigo, boolean enable) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.enable = enable;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Configuraciones{" +
                "nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", enable=" + enable +
                '}';
    }
}
