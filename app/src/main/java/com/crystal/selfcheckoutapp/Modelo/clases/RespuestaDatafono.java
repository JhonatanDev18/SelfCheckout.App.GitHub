package com.crystal.selfcheckoutapp.Modelo.clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class RespuestaDatafono implements Serializable {
    private int tipoOperacion;
    private String codigoRespuesta;
    private String numeroRecibo;
    private String numeroConfirmacion;
    private String ultimosDigitosTarjeta;
    private String franquicia;
    private String tipoCuenta;
    private String numeroCuotas;
    private String fechaHora;
    private String mensajeError;
    private String ivaCompra;
    private String binTarjeta;
    private String fechaVencimiento;
    private String direccionEstablecimiento;
    private String hora;
    private String minuto;
    private boolean errorHoraMinutos;
    public RespuestaDatafono(int tipoOperacion, String codigoRespuesta, String numeroRecibo,
                             String numeroConfirmacion, String ultimosDigitosTarjeta, String franquicia,
                             String tipoCuenta, String numeroCuotas, String fechaHora,
                            String binTarjeta, String ivaCompra, String fechaVencimiento,
                             String direccionEstablecimiento, String mensajeError) {
        this.tipoOperacion = tipoOperacion;
        this.codigoRespuesta = codigoRespuesta;
        this.numeroRecibo = numeroRecibo;
        this.numeroConfirmacion = numeroConfirmacion;
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
        this.franquicia = franquicia;
        this.tipoCuenta = tipoCuenta;
        this.numeroCuotas = numeroCuotas;
        this.fechaHora = fechaHora;
        this.binTarjeta = binTarjeta;
        this.ivaCompra = ivaCompra;
        this.fechaVencimiento = fechaVencimiento;
        this.direccionEstablecimiento = direccionEstablecimiento;
        this.mensajeError = mensajeError;
    }

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getNumeroConfirmacion() {
        return numeroConfirmacion;
    }

    public void setNumeroConfirmacion(String numeroConfirmacion) {
        this.numeroConfirmacion = numeroConfirmacion;
    }

    public String getUltimosDigitosTarjeta() {
        return ultimosDigitosTarjeta;
    }

    public void setUltimosDigitosTarjeta(String ultimosDigitosTarjeta) {
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(String numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getIvaCompra() {
        return ivaCompra;
    }

    public void setIvaCompra(String ivaCompra) {
        this.ivaCompra = ivaCompra;
    }

    public String getBinTarjeta() {
        return binTarjeta;
    }

    public void setBinTarjeta(String binTarjeta) {
        this.binTarjeta = binTarjeta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDireccionEstablecimiento() {
        return direccionEstablecimiento;
    }

    public void setDireccionEstablecimiento(String direccionEstablecimiento) {
        this.direccionEstablecimiento = direccionEstablecimiento;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public boolean isErrorHoraMinutos() {
        return errorHoraMinutos;
    }

    public void setErrorHoraMinutos(boolean errorHoraMinutos) {
        this.errorHoraMinutos = errorHoraMinutos;
    }
}
