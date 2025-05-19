package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.SeguridadDatafono;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCompraDatafono {
    @SerializedName("valorVenta")
    @Expose
    private String valorVenta;
    @SerializedName("valorIva")
    @Expose
    private String valorIva;
    @SerializedName("valorImpuestoConsumo")
    @Expose
    private String valorImpuestoConsumo;
    @SerializedName("valorPropina")
    @Expose
    private String valorPropina;
    @SerializedName("valorTotal")
    @Expose
    private String valorTotal;
    @SerializedName("idTransaccion")
    @Expose
    private String idTransaccion;
    @SerializedName("identificadorCajero")
    @Expose
    private String identificadorCajero;
    @SerializedName("identificadorDescuento")
    @Expose
    private String identificadorDescuento;
    @SerializedName("valorTotalConDescuento")
    @Expose
    private String valorTotalConDescuento;
    @SerializedName("tipoConbustible")
    @Expose
    private String tipoConbustible;
    @SerializedName("kilometraje")
    @Expose
    private String kilometraje;
    @SerializedName("galones")
    @Expose
    private String galones;
    @SerializedName("codigoTerminal")
    @Expose
    private String codigoTerminal;
    @SerializedName("valorDescuento")
    @Expose
    private String valorDescuento;
    @SerializedName("numeroCaja")
    @Expose
    private String numeroCaja;
    @SerializedName("seguridad")
    @Expose
    private SeguridadDatafono seguridad;
    @SerializedName("recibo")
    @Expose
    private String recibo;

    @SerializedName("contrasenaSupervisor")
    @Expose
    private String pasSupervisor;

    public RequestCompraDatafono(String valorVenta, String valorIva, String valorImpuestoConsumo,
                                 String valorPropina, String valorTotal, String idTransaccion,
                                 String identificadorCajero, String identificadorDescuento,
                                 String valorTotalConDescuento, String tipoConbustible,
                                 String kilometraje, String galones, String codigoTerminal,
                                 String valorDescuento, String numeroCaja, SeguridadDatafono seguridad) {
        this.valorVenta = valorVenta;
        this.valorIva = valorIva;
        this.valorImpuestoConsumo = valorImpuestoConsumo;
        this.valorPropina = valorPropina;
        this.valorTotal = valorTotal;
        this.idTransaccion = idTransaccion;
        this.identificadorCajero = identificadorCajero;
        this.identificadorDescuento = identificadorDescuento;
        this.valorTotalConDescuento = valorTotalConDescuento;
        this.tipoConbustible = tipoConbustible;
        this.kilometraje = kilometraje;
        this.galones = galones;
        this.codigoTerminal = codigoTerminal;
        this.valorDescuento = valorDescuento;
        this.numeroCaja = numeroCaja;
        this.seguridad = seguridad;
    }

    public String getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(String valorVenta) {
        this.valorVenta = valorVenta;
    }

    public String getValorIva() {
        return valorIva;
    }

    public void setValorIva(String valorIva) {
        this.valorIva = valorIva;
    }

    public String getValorImpuestoConsumo() {
        return valorImpuestoConsumo;
    }

    public void setValorImpuestoConsumo(String valorImpuestoConsumo) {
        this.valorImpuestoConsumo = valorImpuestoConsumo;
    }

    public String getValorPropina() {
        return valorPropina;
    }

    public void setValorPropina(String valorPropina) {
        this.valorPropina = valorPropina;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getIdentificadorCajero() {
        return identificadorCajero;
    }

    public void setIdentificadorCajero(String identificadorCajero) {
        this.identificadorCajero = identificadorCajero;
    }

    public String getIdentificadorDescuento() {
        return identificadorDescuento;
    }

    public void setIdentificadorDescuento(String identificadorDescuento) {
        this.identificadorDescuento = identificadorDescuento;
    }

    public String getValorTotalConDescuento() {
        return valorTotalConDescuento;
    }

    public void setValorTotalConDescuento(String valorTotalConDescuento) {
        this.valorTotalConDescuento = valorTotalConDescuento;
    }

    public String getTipoConbustible() {
        return tipoConbustible;
    }

    public void setTipoConbustible(String tipoConbustible) {
        this.tipoConbustible = tipoConbustible;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getGalones() {
        return galones;
    }

    public void setGalones(String galones) {
        this.galones = galones;
    }

    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(String codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
    }

    public String getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(String valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public String getNumeroCaja() {
        return numeroCaja;
    }

    public void setNumeroCaja(String numeroCaja) {
        this.numeroCaja = numeroCaja;
    }

    public SeguridadDatafono getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(SeguridadDatafono seguridad) {
        this.seguridad = seguridad;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getPasSupervisor() {
        return pasSupervisor;
    }

    public void setPasSupervisor(String pasSupervisor) {
        this.pasSupervisor = pasSupervisor;
    }

    @Override
    public String toString() {
        return "RequestCompraDatafono{" +
                "valorVenta='" + valorVenta + '\'' +
                ", valorIva='" + valorIva + '\'' +
                ", valorImpuestoConsumo='" + valorImpuestoConsumo + '\'' +
                ", valorPropina='" + valorPropina + '\'' +
                ", valorTotal='" + valorTotal + '\'' +
                ", idTransaccion='" + idTransaccion + '\'' +
                ", identificadorCajero='" + identificadorCajero + '\'' +
                ", identificadorDescuento='" + identificadorDescuento + '\'' +
                ", valorTotalConDescuento='" + valorTotalConDescuento + '\'' +
                ", tipoConbustible='" + tipoConbustible + '\'' +
                ", kilometraje='" + kilometraje + '\'' +
                ", galones='" + galones + '\'' +
                ", codigoTerminal='" + codigoTerminal + '\'' +
                ", valorDescuento='" + valorDescuento + '\'' +
                ", numeroCaja='" + numeroCaja + '\'' +
                ", seguridad=" + seguridad +
                ", recibo='" + recibo + '\'' +
                ", pasSupervisor='" + pasSupervisor + '\'' +
                '}';
    }
}
