package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestNps {
    @SerializedName("numeroTransaccion")
    @Expose
    private Integer numeroTransaccion;

    @SerializedName("tienda")
    @Expose
    private String tienda;

    @SerializedName("tipoDocumento")
    @Expose
    private String tipoDocumento;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("calificacion")
    @Expose
    private String calificacion;

    @SerializedName("cedulaCajero")
    @Expose
    private String cedulaCajero;

    @SerializedName("cedulaCliente")
    @Expose
    private String cedulaCliente;

    public RequestNps(Integer numeroTransaccion, String tienda, String tipoDocumento, String customerId,
                      String calificacion, String cedulaCajero, String cedulaCliente) {
        this.numeroTransaccion = numeroTransaccion;
        this.tienda = tienda;
        this.tipoDocumento = tipoDocumento;
        this.customerId = customerId;
        this.calificacion = calificacion;
        this.cedulaCajero = cedulaCajero;
        this.cedulaCliente = cedulaCliente;
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getCedulaCajero() {
        return cedulaCajero;
    }

    public void setCedulaCajero(String cedulaCajero) {
        this.cedulaCajero = cedulaCajero;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    @Override
    public String toString() {
        return "RequestNps{" +
                "numeroTransaccion=" + numeroTransaccion +
                ", tienda='" + tienda + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", customerId='" + customerId + '\'' +
                ", calificacion='" + calificacion + '\'' +
                ", cedulaCajero='" + cedulaCajero + '\'' +
                ", cedulaCliente='" + cedulaCliente + '\'' +
                '}';
    }
}