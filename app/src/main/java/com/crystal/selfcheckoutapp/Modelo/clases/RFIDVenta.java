package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RFIDVenta implements Serializable {
    @SerializedName("Serial")
    private String serial;

    @SerializedName("Ean")
    private String ean;

    @SerializedName("Tienda")
    private String tienda;

    @SerializedName("Cantidad")
    private String cantidad;

    @SerializedName("Fventa")
    private String fventa;

    @SerializedName("Caja")
    private String caja;

    @SerializedName("NumDoc")
    private String numDoc;

    public RFIDVenta(String serial, String ean, String tienda, String cantidad, String fventa, String caja, String numDoc) {
        this.serial = serial;
        this.ean = ean;
        this.tienda = tienda;
        this.cantidad = cantidad;
        this.fventa = fventa;
        this.caja = caja;
        this.numDoc = numDoc;
    }

    @Override
    public String toString() {
        return "RFIDVenta{" +
                "serial='" + serial + '\'' +
                ", ean='" + ean + '\'' +
                ", tienda='" + tienda + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", fventa='" + fventa + '\'' +
                ", caja='" + caja + '\'' +
                ", numDoc='" + numDoc + '\'' +
                '}';
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFventa() {
        return fventa;
    }

    public void setFventa(String fventa) {
        this.fventa = fventa;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }
}
