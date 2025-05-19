package com.crystal.selfcheckoutapp.Modelo.clases;

import java.io.Serializable;

public class DetalleDescuento implements Serializable {
    String nombre;

    String monto;

    boolean isDescuento;

    public DetalleDescuento(String nombre, String monto, boolean isDescuento) {
        this.nombre = nombre;
        this.monto = monto;
        this.isDescuento = isDescuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public boolean isDescuento() {
        return isDescuento;
    }

    public void setDescuento(boolean descuento) {
        isDescuento = descuento;
    }
}
