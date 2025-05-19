package com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parametro {
    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("valor")
    @Expose
    private String valor;

    public Parametro(String codigo, String nombre, String valor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Parametro{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
