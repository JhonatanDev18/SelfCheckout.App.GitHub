package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PagoTEF implements Serializable {
    @SerializedName("codigoMedioPago")
    @Expose
    private String codigoMedioPago;
    @SerializedName("codigoCaja")
    @Expose
    private String codigoCaja;
    @SerializedName("numeroTransaccion")
    @Expose
    private Integer numeroTransaccion;
    @SerializedName("numeroPago")
    @Expose
    private Integer numeroPago;
    @SerializedName("fechaVencimientoTarjeta")
    @Expose
    private String fechaVencimientoTarjeta;
    @SerializedName("cuatroUltimosDigitosTarjeta")
    @Expose
    private String cuatroUltimosDigitosTarjeta;
    @SerializedName("numeroAutorizacion")
    @Expose
    private String numeroAutorizacion;
    @SerializedName("numeroCuotas")
    @Expose
    private Integer numeroCuotas;
    @SerializedName("tipoTarjeta")
    @Expose
    private String tipoTarjeta;
    @SerializedName("numeroTransaccionDatafono")
    @Expose
    private String numeroTransaccionDatafono;
    @SerializedName("respuestaTEF")
    @Expose
    private String respuestaTEF;

    public PagoTEF(String codigoCaja, String codigoMedioPago, String cuatroUltimosDigitosTarjeta,
                   String fechaVencimientoTarjeta, String numeroAutorizacion, Integer numeroCuotas,
                   Integer numeroPago, Integer numeroTransaccion, String numeroTransaccionDatafono,
                   String respuestaTEF, String tipoTarjeta) {
        this.codigoCaja = codigoCaja;
        this.codigoMedioPago = codigoMedioPago;
        this.cuatroUltimosDigitosTarjeta = cuatroUltimosDigitosTarjeta;
        this.fechaVencimientoTarjeta = fechaVencimientoTarjeta;
        this.numeroAutorizacion = numeroAutorizacion;
        this.numeroCuotas = numeroCuotas;
        this.numeroPago = numeroPago;
        this.numeroTransaccion = numeroTransaccion;
        this.numeroTransaccionDatafono = numeroTransaccionDatafono;
        this.respuestaTEF = respuestaTEF;
        this.tipoTarjeta = tipoTarjeta;
    }

    @Override
    public String toString() {
        return "PagoTEF{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", codigoMedioPago='" + codigoMedioPago + '\'' +
                ", cuatroUltimosDigitosTarjeta='" + cuatroUltimosDigitosTarjeta + '\'' +
                ", fechaVencimientoTarjeta='" + fechaVencimientoTarjeta + '\'' +
                ", numeroAutorizacion='" + numeroAutorizacion + '\'' +
                ", numeroCuotas=" + numeroCuotas +
                ", numeroPago=" + numeroPago +
                ", numeroTransaccion=" + numeroTransaccion +
                ", numeroTransaccionDatafono='" + numeroTransaccionDatafono + '\'' +
                ", respuestaTEF='" + respuestaTEF + '\'' +
                ", tipoTarjeta='" + tipoTarjeta + '\'' +
                '}';
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public String getCodigoMedioPago() {
        return codigoMedioPago;
    }

    public void setCodigoMedioPago(String codigoMedioPago) {
        this.codigoMedioPago = codigoMedioPago;
    }

    public String getCuatroUltimosDigitosTarjeta() {
        return cuatroUltimosDigitosTarjeta;
    }

    public void setCuatroUltimosDigitosTarjeta(String cuatroUltimosDigitosTarjeta) {
        this.cuatroUltimosDigitosTarjeta = cuatroUltimosDigitosTarjeta;
    }

    public String getFechaVencimientoTarjeta() {
        return fechaVencimientoTarjeta;
    }

    public void setFechaVencimientoTarjeta(String fechaVencimientoTarjeta) {
        this.fechaVencimientoTarjeta = fechaVencimientoTarjeta;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public Integer getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(Integer numeroPago) {
        this.numeroPago = numeroPago;
    }

    public Integer getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(Integer numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getNumeroTransaccionDatafono() {
        return numeroTransaccionDatafono;
    }

    public void setNumeroTransaccionDatafono(String numeroTransaccionDatafono) {
        this.numeroTransaccionDatafono = numeroTransaccionDatafono;
    }

    public String getRespuestaTEF() {
        return respuestaTEF;
    }

    public void setRespuestaTEF(String respuestaTEF) {
        this.respuestaTEF = respuestaTEF;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }
}
