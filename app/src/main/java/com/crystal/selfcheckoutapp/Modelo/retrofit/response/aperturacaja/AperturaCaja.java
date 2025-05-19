package com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AperturaCaja implements Serializable {
    @SerializedName("codigoCaja")
    @Expose
    private String codigoCaja;

    @SerializedName("numeroDia")
    @Expose
    private Integer numeroDia;

    @SerializedName("fechaApertura")
    @Expose
    private String fechaApertura;

    @SerializedName("cajero")
    @Expose
    private String cajero;

    @SerializedName("codigoEstadoCaja")
    @Expose
    private String codigoEstadoCaja;

    @SerializedName("estadoCaja")
    @Expose
    private String estadoCaja;

    @SerializedName("nombreVendedor")
    @Expose
    private String nombreVendedor;

    @SerializedName("apellidoVendedor")
    @Expose
    private String apellidoVendedor;

    public AperturaCaja(String codigoCaja, Integer numeroDia, String fechaApertura, String cajero,
                        String codigoEstadoCaja, String estadoCaja, String nombreVendedor,
                        String apellidoVendedor) {
        this.codigoCaja = codigoCaja;
        this.numeroDia = numeroDia;
        this.fechaApertura = fechaApertura;
        this.cajero = cajero;
        this.codigoEstadoCaja = codigoEstadoCaja;
        this.estadoCaja = estadoCaja;
        this.nombreVendedor = nombreVendedor;
        this.apellidoVendedor = apellidoVendedor;
    }

    @Override
    public String toString() {
        return "AperturaCaja{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", numeroDia=" + numeroDia +
                ", fechaApertura='" + fechaApertura + '\'' +
                ", cajero='" + cajero + '\'' +
                ", codigoEstadoCaja='" + codigoEstadoCaja + '\'' +
                ", estadoCaja='" + estadoCaja + '\'' +
                ", nombreVendedor='" + nombreVendedor + '\'' +
                ", apellidoVendedor='" + apellidoVendedor + '\'' +
                '}';
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getCodigoEstadoCaja() {
        return codigoEstadoCaja;
    }

    public void setCodigoEstadoCaja(String codigoEstadoCaja) {
        this.codigoEstadoCaja = codigoEstadoCaja;
    }

    public String getEstadoCaja() {
        return estadoCaja;
    }

    public void setEstadoCaja(String estadoCaja) {
        this.estadoCaja = estadoCaja;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getApellidoVendedor() {
        return apellidoVendedor;
    }

    public void setApellidoVendedor(String apellidoVendedor) {
        this.apellidoVendedor = apellidoVendedor;
    }
}
