package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestXmlDian {
    @SerializedName("NumeroTransaccion")
    @Expose
    private Integer numeroTransaccion;

    @SerializedName("Tienda")
    @Expose
    private String tienda;

    @SerializedName("ClaseDocumento")
    @Expose
    private String claseDocumento;

    @SerializedName("Estado")
    @Expose
    private String estado;

    public RequestXmlDian(Integer numeroTransaccion, String tienda, String claseDocumento, String estado) {
        this.numeroTransaccion = numeroTransaccion;
        this.tienda = tienda;
        this.claseDocumento = claseDocumento;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "RequestXmlDian{" +
                "numeroTransaccion=" + numeroTransaccion +
                ", tienda='" + tienda + '\'' +
                ", claseDocumento='" + claseDocumento + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    public Integer getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(Integer numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getClaseDocumento() {
        return claseDocumento;
    }

    public void setClaseDocumento(String claseDocumento) {
        this.claseDocumento = claseDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
