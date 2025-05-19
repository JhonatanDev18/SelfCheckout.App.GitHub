package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediosPago {
    @SerializedName("pais")
    @Expose
    private String pais;

    @SerializedName("divisa")
    @Expose
    private String divisa;

    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("caja")
    @Expose
    private String caja;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("tpeValue")
    @Expose
    private String tpeValue;

    @SerializedName("tpe")
    @Expose
    private Boolean tpe;

    public MediosPago(String pais, String divisa, String codigo, String nombre, String caja, String tipo,
                      String tpeValue, Boolean tpe) {
        this.pais = pais;
        this.divisa = divisa;
        this.codigo = codigo;
        this.nombre = nombre;
        this.caja = caja;
        this.tipo = tipo;
        this.tpeValue = tpeValue;
        this.tpe = tpe;
    }

    @Override
    public String toString() {
        return "MediosPago{" +
                "pais='" + pais + '\'' +
                ", divisa='" + divisa + '\'' +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", caja='" + caja + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tpeValue='" + tpeValue + '\'' +
                ", tpe=" + tpe +
                '}';
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
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

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTpeValue() {
        return tpeValue;
    }

    public void setTpeValue(String tpeValue) {
        this.tpeValue = tpeValue;
    }

    public Boolean getTpe() {
        return tpe;
    }

    public void setTpe(Boolean tpe) {
        this.tpe = tpe;
    }
}
