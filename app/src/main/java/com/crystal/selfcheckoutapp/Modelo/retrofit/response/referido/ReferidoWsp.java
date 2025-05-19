package com.crystal.selfcheckoutapp.Modelo.retrofit.response.referido;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferidoWsp {
    @SerializedName("codigoWsp")
    @Expose
    private String codigo;

    @SerializedName("tipoDocumento")
    @Expose
    private String tipoDocumento;

    @SerializedName("numeroDocumento")
    @Expose
    private String numeroDocumento;

    @SerializedName("fechaCreacion")
    @Expose
    private String fechaCreacion;

    @SerializedName("checkValid")
    @Expose
    private String checkValid;

    public ReferidoWsp(String codigo, String tipoDocumento, String numeroDocumento, String fechaCreacion, String checkValid) {
        this.codigo = codigo;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaCreacion = fechaCreacion;
        this.checkValid = checkValid;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCheckValid() {
        return checkValid;
    }

    public void setCheckValid(String checkValid) {
        this.checkValid = checkValid;
    }

    @Override
    public String toString() {
        return "ReferidoWsp{" +
                "codigo='" + codigo + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", checkValid='" + checkValid + '\'' +
                '}';
    }
}
