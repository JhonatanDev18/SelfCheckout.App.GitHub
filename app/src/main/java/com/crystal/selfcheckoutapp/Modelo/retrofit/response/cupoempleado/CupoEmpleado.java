package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cupoempleado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CupoEmpleado {
    @SerializedName("documentoEmpleado")
    @Expose
    private String documentoEmpleado;

    @SerializedName("cupo")
    @Expose
    private Double cupo;

    @SerializedName("empresa")
    @Expose
    private String empresa;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    public CupoEmpleado(String documentoEmpleado, Double cupo, String empresa, String nombre, String fecha) {
        this.documentoEmpleado = documentoEmpleado;
        this.cupo = cupo;
        this.empresa = empresa;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getDocumentoEmpleado() {
        return documentoEmpleado;
    }

    public void setDocumentoEmpleado(String documentoEmpleado) {
        this.documentoEmpleado = documentoEmpleado;
    }

    public Double getCupo() {
        return cupo;
    }

    public void setCupo(Double cupo) {
        this.cupo = cupo;
    }

    public String getEmpresa() {
        return empresa.trim();
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "CupoEmpleado{" +
                "documentoEmpleado='" + documentoEmpleado + '\'' +
                ", cupo=" + cupo +
                ", empresa='" + empresa + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
