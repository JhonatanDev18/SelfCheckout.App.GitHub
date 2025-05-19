package com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto;

import com.crystal.selfcheckoutapp.Modelo.clases.DescuentoSimple;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Cloneable, Serializable {
    @SerializedName("ean")
    @Expose
    private String ean;

    @SerializedName("precio")
    @Expose
    private Double precio;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("talla")
    @Expose
    private String talla;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("tipoTarifa")
    @Expose
    private String tipoTarifa;

    @SerializedName("tienda")
    @Expose
    private String tienda;

    @SerializedName("periodoTarifa")
    @Expose
    private String periodoTarifa;

    @SerializedName("ip")
    @Expose
    private String ip;

    @SerializedName("composicion")
    @Expose
    private String composicion;

    @SerializedName("precioUnitario")
    @Expose
    private String precioUnitario;

    @SerializedName("articulo")
    @Expose
    private String articulo;

    @SerializedName("codigoTasaImpuesto")
    @Expose
    private String codigoTasaImpuesto;

    @SerializedName("articuloCerrado")
    @Expose
    private Boolean articuloCerrado;

    @SerializedName("articuloGratuito")
    @Expose
    private Boolean articuloGratuito;

    @SerializedName("tasaImpuesto")
    @Expose
    private String tasaImpuesto;

    @SerializedName("precioSinImpuesto")
    @Expose
    private Double precioSinImpuesto;

    @SerializedName("impuesto")
    @Expose
    private Double impuesto;

    @SerializedName("valorTasa")
    @Expose
    private Double valorTasa;

    @SerializedName("fechaTasa")
    @Expose
    private String fechaTasa;

    @SerializedName("precioOriginal")
    @Expose
    private Double precioOriginal;

    @SerializedName("periodoActivo")
    @Expose
    private Boolean periodoActivo;

    @SerializedName("codigoMarca")
    @Expose
    private String codigoMarca;

    @SerializedName("marca")
    @Expose
    private String marca;

    @SerializedName("serial")
    @Expose
    private String serialNumberId;

    @SerializedName("descontable")
    @Expose
    private Boolean descontable;

    @SerializedName("tipoPrendaCodigo")
    @Expose
    private String tipoPrendaCodigo;

    @SerializedName("tipoPrendaNombre")
    @Expose
    private String tipoPrendaNombre;

    @SerializedName("generoCodigo")
    @Expose
    private String generoCodigo;

    @SerializedName("generoNombre")
    @Expose
    private String generoNombre;

    @SerializedName("subGrupo")
    @Expose
    private String subGrupo;

    @SerializedName("bolsa")
    @Expose
    private boolean bolsa;

    @SerializedName("nombreParfois")
    @Expose
    private String nombreParfois;

    @SerializedName("numeroSerie")
    @Expose
    private String numeroSerie;

    private String descuentoTarifa;

    private String valorFinal;

    private Integer cantidad;

    private double precioBase;

    private double descuentoNeto = 0.0;

    private int line;

    private RespuestaLine descuento = null;

    private DescuentoSimple descuentoSimple = null;

    private List<DescuentoSimple> listaDescuentoSimple = new ArrayList<>();

    public Producto(){

    }

    public Producto(String ean, Double precio, String nombre, String talla, String color, String tipoTarifa,
                    String tienda, String periodoTarifa, String ip, String composicion, String precioUnitario,
                    String articulo, String codigoTasaImpuesto, Boolean articuloCerrado, Boolean articuloGratuito,
                    String tasaImpuesto, Double precioSinImpuesto, Double impuesto, Double valorTasa,
                    String fechaTasa, Double precioOriginal, Boolean periodoActivo, String codigoMarca,
                    String marca, String serialNumberId, Boolean descontable, String tipoPrendaCodigo,
                    String tipoPrendaNombre, String generoCodigo, String generoNombre, String subGrupo,
                    boolean bolsa, String nombreParfois, String descuentoTarifa, String valorFinal) {
        this.ean = ean;
        this.precio = precio;
        this.nombre = nombre;
        this.talla = talla;
        this.color = color;
        this.tipoTarifa = tipoTarifa;
        this.tienda = tienda;
        this.periodoTarifa = periodoTarifa;
        this.ip = ip;
        this.composicion = composicion;
        this.precioUnitario = precioUnitario;
        this.articulo = articulo;
        this.codigoTasaImpuesto = codigoTasaImpuesto;
        this.articuloCerrado = articuloCerrado;
        this.articuloGratuito = articuloGratuito;
        this.tasaImpuesto = tasaImpuesto;
        this.precioSinImpuesto = precioSinImpuesto;
        this.impuesto = impuesto;
        this.valorTasa = valorTasa;
        this.fechaTasa = fechaTasa;
        this.precioOriginal = precioOriginal;
        this.periodoActivo = periodoActivo;
        this.codigoMarca = codigoMarca;
        this.marca = marca;
        this.serialNumberId = serialNumberId;
        this.descontable = descontable;
        this.tipoPrendaCodigo = tipoPrendaCodigo;
        this.tipoPrendaNombre = tipoPrendaNombre;
        this.generoCodigo = generoCodigo;
        this.generoNombre = generoNombre;
        this.subGrupo = subGrupo;
        this.bolsa = bolsa;
        this.nombreParfois = nombreParfois;
        this.descuentoTarifa = descuentoTarifa;
        this.valorFinal = valorFinal;
    }

    public Producto(String ean, Double precio, String nombre, String talla, String color, String tipoTarifa,
                    String tienda, String periodoTarifa, String ip, String composicion, String precioUnitario,
                    String articulo, String codigoTasaImpuesto, Boolean articuloCerrado, Boolean articuloGratuito,
                    String tasaImpuesto, Double precioSinImpuesto, Double impuesto, Double valorTasa,
                    String fechaTasa, Double precioOriginal, Boolean periodoActivo, String codigoMarca,
                    String marca, String serialNumberId, Boolean descontable, String tipoPrendaCodigo,
                    String tipoPrendaNombre, String generoCodigo, String generoNombre, String subGrupo,
                    boolean bolsa, String nombreParfois, String numeroSerie) {
        this.ean = ean;
        this.precio = precio;
        this.nombre = nombre;
        this.talla = talla;
        this.color = color;
        this.tipoTarifa = tipoTarifa;
        this.tienda = tienda;
        this.periodoTarifa = periodoTarifa;
        this.ip = ip;
        this.composicion = composicion;
        this.precioUnitario = precioUnitario;
        this.articulo = articulo;
        this.codigoTasaImpuesto = codigoTasaImpuesto;
        this.articuloCerrado = articuloCerrado;
        this.articuloGratuito = articuloGratuito;
        this.tasaImpuesto = tasaImpuesto;
        this.precioSinImpuesto = precioSinImpuesto;
        this.impuesto = impuesto;
        this.valorTasa = valorTasa;
        this.fechaTasa = fechaTasa;
        this.precioOriginal = precioOriginal;
        this.periodoActivo = periodoActivo;
        this.codigoMarca = codigoMarca;
        this.marca = marca;
        this.serialNumberId = serialNumberId;
        this.descontable = descontable;
        this.tipoPrendaCodigo = tipoPrendaCodigo;
        this.tipoPrendaNombre = tipoPrendaNombre;
        this.generoCodigo = generoCodigo;
        this.generoNombre = generoNombre;
        this.subGrupo = subGrupo;
        this.bolsa = bolsa;
        this.nombreParfois = nombreParfois;
        this.numeroSerie = numeroSerie;
    }

    public String construirNombreProducto(int tamano){
        String pNombre = nombre;
        if (talla != null) {
            pNombre = pNombre + " " + talla;
        }
        if (color != null) {
            pNombre = pNombre + " " + color;
        }

        if (pNombre.length() > tamano) {
            pNombre = pNombre.substring(0, tamano);
        }

        return pNombre;
    }

    public String codigoTasaImpuesto(){
        String pCodigoTasaImpuesto = codigoTasaImpuesto;
        if (pCodigoTasaImpuesto.length() > 1) {
            pCodigoTasaImpuesto = pCodigoTasaImpuesto.substring(0, 1);
        }
        return pCodigoTasaImpuesto;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getPeriodoTarifa() {
        return periodoTarifa;
    }

    public void setPeriodoTarifa(String periodoTarifa) {
        this.periodoTarifa = periodoTarifa;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getCodigoTasaImpuesto() {
        return codigoTasaImpuesto;
    }

    public void setCodigoTasaImpuesto(String codigoTasaImpuesto) {
        this.codigoTasaImpuesto = codigoTasaImpuesto;
    }

    public Boolean getArticuloCerrado() {
        return articuloCerrado;
    }

    public void setArticuloCerrado(Boolean articuloCerrado) {
        this.articuloCerrado = articuloCerrado;
    }

    public Boolean getArticuloGratuito() {
        return articuloGratuito;
    }

    public void setArticuloGratuito(Boolean articuloGratuito) {
        this.articuloGratuito = articuloGratuito;
    }

    public String getTasaImpuesto() {
        return tasaImpuesto;
    }

    public void setTasaImpuesto(String tasaImpuesto) {
        this.tasaImpuesto = tasaImpuesto;
    }

    public Double getPrecioSinImpuesto() {
        return precioSinImpuesto;
    }

    public void setPrecioSinImpuesto(Double precioSinImpuesto) {
        this.precioSinImpuesto = precioSinImpuesto;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getValorTasa() {
        return valorTasa;
    }

    public void setValorTasa(Double valorTasa) {
        this.valorTasa = valorTasa;
    }

    public String getFechaTasa() {
        return fechaTasa;
    }

    public void setFechaTasa(String fechaTasa) {
        this.fechaTasa = fechaTasa;
    }

    public Double getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(Double precioOriginal) {
        this.precioOriginal = precioOriginal;
    }

    public Boolean getPeriodoActivo() {
        return periodoActivo;
    }

    public void setPeriodoActivo(Boolean periodoActivo) {
        this.periodoActivo = periodoActivo;
    }

    public String getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(String codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerialNumberId() {
        return serialNumberId;
    }

    public void setSerialNumberId(String serialNumberId) {
        this.serialNumberId = serialNumberId;
    }

    public Boolean getDescontable() {
        return descontable;
    }

    public void setDescontable(Boolean descontable) {
        this.descontable = descontable;
    }

    public String getTipoPrendaCodigo() {
        return tipoPrendaCodigo;
    }

    public void setTipoPrendaCodigo(String tipoPrendaCodigo) {
        this.tipoPrendaCodigo = tipoPrendaCodigo;
    }

    public String getTipoPrendaNombre() {
        return tipoPrendaNombre;
    }

    public void setTipoPrendaNombre(String tipoPrendaNombre) {
        this.tipoPrendaNombre = tipoPrendaNombre;
    }

    public String getGeneroCodigo() {
        return generoCodigo;
    }

    public void setGeneroCodigo(String generoCodigo) {
        this.generoCodigo = generoCodigo;
    }

    public String getGeneroNombre() {
        return generoNombre;
    }

    public void setGeneroNombre(String generoNombre) {
        this.generoNombre = generoNombre;
    }

    public String getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
    }

    public boolean isBolsa() {
        return bolsa;
    }

    public void setBolsa(boolean bolsa) {
        this.bolsa = bolsa;
    }

    public String getNombreParfois() {
        return nombreParfois;
    }

    public void setNombreParfois(String nombreParfois) {
        this.nombreParfois = nombreParfois;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDescuentoTarifa() {
        return descuentoTarifa;
    }

    public void setDescuentoTarifa(String descuentoTarifa) {
        this.descuentoTarifa = descuentoTarifa;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getDescuentoNeto() {
        return descuentoNeto;
    }

    public void setDescuentoNeto(double descuentoNeto) {
        this.descuentoNeto = descuentoNeto;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public RespuestaLine getDescuento() {
        return descuento;
    }

    public void setDescuento(RespuestaLine descuento) {
        this.descuento = descuento;
    }

    public DescuentoSimple getDescuentoSimple() {
        return descuentoSimple;
    }

    public void setDescuentoSimple(DescuentoSimple descuentoSimple) {
        this.descuentoSimple = descuentoSimple;
    }

    public List<DescuentoSimple> getListaDescuentoSimple() {
        return listaDescuentoSimple;
    }

    public void setListaDescuentoSimple(List<DescuentoSimple> listaDescuentoSimple) {
        this.listaDescuentoSimple = listaDescuentoSimple;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "ean='" + ean + '\'' +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", talla='" + talla + '\'' +
                ", color='" + color + '\'' +
                ", tipoTarifa='" + tipoTarifa + '\'' +
                ", tienda='" + tienda + '\'' +
                ", periodoTarifa='" + periodoTarifa + '\'' +
                ", ip='" + ip + '\'' +
                ", composicion='" + composicion + '\'' +
                ", precioUnitario='" + precioUnitario + '\'' +
                ", articulo='" + articulo + '\'' +
                ", codigoTasaImpuesto='" + codigoTasaImpuesto + '\'' +
                ", articuloCerrado=" + articuloCerrado +
                ", articuloGratuito=" + articuloGratuito +
                ", tasaImpuesto='" + tasaImpuesto + '\'' +
                ", precioSinImpuesto=" + precioSinImpuesto +
                ", impuesto=" + impuesto +
                ", valorTasa=" + valorTasa +
                ", fechaTasa='" + fechaTasa + '\'' +
                ", precioOriginal=" + precioOriginal +
                ", periodoActivo=" + periodoActivo +
                ", codigoMarca='" + codigoMarca + '\'' +
                ", marca='" + marca + '\'' +
                ", serialNumberId='" + serialNumberId + '\'' +
                ", descontable=" + descontable +
                ", tipoPrendaCodigo='" + tipoPrendaCodigo + '\'' +
                ", tipoPrendaNombre='" + tipoPrendaNombre + '\'' +
                ", generoCodigo='" + generoCodigo + '\'' +
                ", generoNombre='" + generoNombre + '\'' +
                ", subGrupo='" + subGrupo + '\'' +
                ", bolsa=" + bolsa +
                ", nombreParfois='" + nombreParfois + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", descuentoTarifa='" + descuentoTarifa + '\'' +
                ", valorFinal='" + valorFinal + '\'' +
                ", cantidad=" + cantidad +
                ", precioBase=" + precioBase +
                ", descuentoNeto=" + descuentoNeto +
                ", line=" + line +
                ", descuento=" + descuento +
                ", descuentoSimple=" + descuentoSimple +
                ", listaDescuentoSimple=" + listaDescuentoSimple +
                '}';
    }
}
