package com.crystal.selfcheckoutapp.Modelo.clases;

import java.io.Serializable;

public class DescuentoSimple implements Serializable {
    private String nombre;
    private double valor;

    public DescuentoSimple(String nombre, double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "DescuentoSimple{" +
                "nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
