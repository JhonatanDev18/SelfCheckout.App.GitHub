package com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono;

import com.crystal.selfcheckoutapp.Modelo.clases.ClaveValor;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CuerpoRespuestaDatafono {
    @SerializedName("aprobacion")
    @Expose
    private String aprobacion;
    @SerializedName("valorTotal")
    @Expose
    private String valorTotal;
    @SerializedName("valorIva")
    @Expose
    private String valorIva;
    @SerializedName("recibo")
    @Expose
    private String recibo;
    @SerializedName("consecutivoInterno")
    @Expose
    private String consecutivoInterno;
    @SerializedName("terminal")
    @Expose
    private String terminal;
    @SerializedName("fechaTransaccion")
    @Expose
    private String fechaTransaccion;
    @SerializedName("horaTransaccion")
    @Expose
    private String horaTransaccion;
    @SerializedName("franquicia")
    @Expose
    private String franquicia;
    @SerializedName("tipoCuentaMedioPago")
    @Expose
    private String tipoCuentaMedioPago;
    @SerializedName("numeroCuotas")
    @Expose
    private String numeroCuotas;
    @SerializedName("ultimosDigitosTarjeta")
    @Expose
    private String ultimosDigitosTarjeta;
    @SerializedName("binTarjeta")
    @Expose
    private String binTarjeta;
    @SerializedName("fechaVencimiento")
    @Expose
    private String fechaVencimiento;
    @SerializedName("codigoComercio")
    @Expose
    private String codigoComercio;
    @SerializedName("direccionComercio")
    @Expose
    private String direccionComercio;
    @SerializedName("caja")
    @Expose
    private String caja;
    @SerializedName("hash")
    @Expose
    private Object hash;
    @SerializedName("idTarjeta")
    @Expose
    private Object idTarjeta;
    @SerializedName("cripto")
    @Expose
    private Object cripto;
    @SerializedName("tvr")
    @Expose
    private Object tvr;
    @SerializedName("tsi")
    @Expose
    private Object tsi;
    @SerializedName("aid")
    @Expose
    private Object aid;
    @SerializedName("aquerencia")
    @Expose
    private Object aquerencia;

    public CuerpoRespuestaDatafono(String aprobacion, String valorTotal, String valorIva,
                                   String recibo, String consecutivoInterno, String terminal,
                                   String fechaTransaccion, String horaTransaccion,
                                   String franquicia, String tipoCuentaMedioPago,
                                   String numeroCuotas, String ultimosDigitosTarjeta,
                                   String binTarjeta, String fechaVencimiento, String codigoComercio,
                                   String direccionComercio, String caja, Object hash,
                                   Object idTarjeta, Object cripto, Object tvr, Object tsi, Object aid,
                                   Object aquerencia) {
        this.aprobacion = aprobacion;
        this.valorTotal = valorTotal;
        this.valorIva = valorIva;
        this.recibo = recibo;
        this.consecutivoInterno = consecutivoInterno;
        this.terminal = terminal;
        this.fechaTransaccion = fechaTransaccion;
        this.horaTransaccion = horaTransaccion;
        this.franquicia = franquicia;
        this.tipoCuentaMedioPago = tipoCuentaMedioPago;
        this.numeroCuotas = numeroCuotas;
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
        this.binTarjeta = binTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoComercio = codigoComercio;
        this.direccionComercio = direccionComercio;
        this.caja = caja;
        this.hash = hash;
        this.idTarjeta = idTarjeta;
        this.cripto = cripto;
        this.tvr = tvr;
        this.tsi = tsi;
        this.aid = aid;
        this.aquerencia = aquerencia;
    }

    public String getAprobacion() {
        return aprobacion;
    }

    public void setAprobacion(String aprobacion) {
        this.aprobacion = aprobacion;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorIva() {
        return valorIva;
    }

    public void setValorIva(String valorIva) {
        this.valorIva = valorIva;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getConsecutivoInterno() {
        return consecutivoInterno;
    }

    public void setConsecutivoInterno(String consecutivoInterno) {
        this.consecutivoInterno = consecutivoInterno;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getHoraTransaccion() {
        return horaTransaccion;
    }

    public void setHoraTransaccion(String horaTransaccion) {
        this.horaTransaccion = horaTransaccion;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getTipoCuentaMedioPago() {
        return tipoCuentaMedioPago;
    }

    public void setTipoCuentaMedioPago(String tipoCuentaMedioPago) {
        this.tipoCuentaMedioPago = tipoCuentaMedioPago;
    }

    public String getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(String numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public String getUltimosDigitosTarjeta() {
        return ultimosDigitosTarjeta;
    }

    public void setUltimosDigitosTarjeta(String ultimosDigitosTarjeta) {
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
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

    public String getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(String codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getDireccionComercio() {
        return direccionComercio;
    }

    public void setDireccionComercio(String direccionComercio) {
        this.direccionComercio = direccionComercio;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public Object getHash() {
        return hash;
    }

    public void setHash(Object hash) {
        this.hash = hash;
    }

    public Object getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Object idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Object getCripto() {
        return cripto;
    }

    public void setCripto(Object cripto) {
        this.cripto = cripto;
    }

    public Object getTvr() {
        return tvr;
    }

    public void setTvr(Object tvr) {
        this.tvr = tvr;
    }

    public Object getTsi() {
        return tsi;
    }

    public void setTsi(Object tsi) {
        this.tsi = tsi;
    }

    public Object getAid() {
        return aid;
    }

    public void setAid(Object aid) {
        this.aid = aid;
    }

    public Object getAquerencia() {
        return aquerencia;
    }

    public void setAquerencia(Object aquerencia) {
        this.aquerencia = aquerencia;
    }

    @Override
    public String toString() {
        return "Aprobacion: "+ aprobacion + "\n"+
                "Valor total: "+ valorTotal + "\n"+
                "Valor iva: "+ valorIva + "\n"+
                "Recibo: "+ recibo + "\n"+
                "Consecutivo interno: "+ consecutivoInterno + "\n"+
                "Terminal: "+ terminal + "\n"+
                "Fecha transaccion: "+ fechaTransaccion + "\n"+
                "Hora transaccion: "+horaTransaccion+"\n"+
                "Franquicia: "+franquicia+"\n"+
                "Tipo de cuenta: "+tipoCuentaMedioPago+"\n"+
                "Cantidad cuotas: "+numeroCuotas+"\n"+
                "Ultimos digitos tarjeta: "+ultimosDigitosTarjeta+"\n"+
                "Bin tarjeta: "+binTarjeta+"\n"+
                "Fecha vencimiento: "+fechaVencimiento+"\n"+
                "Codigo comercio: "+codigoComercio+"\n"+
                "Dirección comercio: "+direccionComercio+"\n"+
                "Caja: "+caja;
    }

    public String toStringJson(){
        return "{"
                + "aprobacion:" + aprobacion + ","
                + "valorTotal:" + valorTotal + ","
                + "valorIva:"+ valorIva + ","
                + "recibo:"+ recibo + ","
                + "consecutivoInterno:"+consecutivoInterno+","
                + "terminal:"+ terminal +","
                + "fechaTransaccion:"+fechaTransaccion+","
                + "horaTransaccion:"+horaTransaccion+","
                + "franquicia:"+franquicia+","
                + "tipoCuentaMedioPago:"+tipoCuentaMedioPago+","
                + "numeroCuotas:"+numeroCuotas+","
                + "fechaVencimiento:"+fechaVencimiento+","
                + "codigoComercio:"+codigoComercio+","
                + "direccionComercio:"+direccionComercio+","
                + "caja:"+caja+
                "}";
    }

    public List<ClaveValor> toStringClaveValor(){
        List<ClaveValor> listaClaveValor = new ArrayList<>();

        listaClaveValor.add(new ClaveValor("Aprobación:", aprobacion));
        listaClaveValor.add(new ClaveValor("Valor total:", Utilidades.formatearPrecio(Double.valueOf(valorTotal))));
        listaClaveValor.add(new ClaveValor("Valor iva:", Utilidades.formatearPrecio(Double.valueOf(valorIva))));
        listaClaveValor.add(new ClaveValor("Recibo:", recibo));
        listaClaveValor.add(new ClaveValor("Consecutivo interno:", consecutivoInterno));
        listaClaveValor.add(new ClaveValor("Terminal:", terminal));
        listaClaveValor.add(new ClaveValor("Fecha transaccion:", fechaTransaccion));
        listaClaveValor.add(new ClaveValor("Hora transaccion:", horaTransaccion));
        listaClaveValor.add(new ClaveValor("Franquicia:", franquicia));
        listaClaveValor.add(new ClaveValor("Tipo de cuenta:", tipoCuentaMedioPago));
        listaClaveValor.add(new ClaveValor("Cantidad cuotas:", numeroCuotas));
        listaClaveValor.add(new ClaveValor("Fecha vencimiento:", fechaVencimiento));
        listaClaveValor.add(new ClaveValor("Código comercio:", codigoComercio));
        listaClaveValor.add(new ClaveValor("Dirección comercio:", direccionComercio));
        listaClaveValor.add(new ClaveValor("Caja:", caja));

        return listaClaveValor;
    }
}
