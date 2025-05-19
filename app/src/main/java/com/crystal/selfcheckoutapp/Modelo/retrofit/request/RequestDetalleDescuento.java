package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.clases.Detalle;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestDetalleDescuento {
    @SerializedName("codigoTienda")
    @Expose
    private String codigoTienda;

    @SerializedName("numeroTransaccion")
    @Expose
    private int numeroTransaccion;

    @SerializedName("clienteId")
    @Expose
    private String clienteId;

    @SerializedName("detalle")
    @Expose
    private List<Detalle> detalle;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    public RequestDetalleDescuento(String codigoTienda, int numeroTransaccion, String clienteId,
                                   List<Detalle> detalle, String usuario) {
        this.codigoTienda = codigoTienda;
        this.numeroTransaccion = numeroTransaccion;
        this.clienteId = clienteId;
        this.detalle = detalle;
        this.usuario = usuario;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public List<Detalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Detalle> detalle) {
        this.detalle = detalle;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "RequestDetalleDescuento{" +
                "codigoTienda='" + codigoTienda + '\'' +
                ", numeroTransaccion=" + numeroTransaccion +
                ", clienteId='" + clienteId + '\'' +
                ", detalle=" + detalle +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
