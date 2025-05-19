package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGuardarRespuestaTef {
    @SerializedName ( "horaCreacion" )
    @Expose
    private String horaCreacion ;
    @SerializedName( "minutosCreacion" )
    @Expose
    private String minutosCreacion ;
    @SerializedName ( "numeroTransaccion" )
    @Expose
    private String numeroTransaccion ;
    @SerializedName ( "tienda" )
    @Expose
    private String tienda ;
    @SerializedName ( "caja" )
    @Expose
    private String caja ;
    @SerializedName ( "referenciaInterna" )
    @Expose
    private String referenciaInterna ;
    @SerializedName ( "respuestaTef" )
    @Expose
    private CuerpoRespuestaDatafono respuestaCompleta ;

    public RequestGuardarRespuestaTef(String horaCreacion, String minutosCreacion, String numeroTransaccion,
                                      String tienda, String caja, String referenciaInterna,
                                      CuerpoRespuestaDatafono respuestaCompleta) {
        this.horaCreacion = horaCreacion;
        this.minutosCreacion = minutosCreacion;
        this.numeroTransaccion = numeroTransaccion;
        this.tienda = tienda;
        this.caja = caja;
        this.referenciaInterna = referenciaInterna;
        this.respuestaCompleta = respuestaCompleta;
    }

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public String getMinutosCreacion() {
        return minutosCreacion;
    }

    public void setMinutosCreacion(String minutosCreacion) {
        this.minutosCreacion = minutosCreacion;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getReferenciaInterna() {
        return referenciaInterna;
    }

    public void setReferenciaInterna(String referenciaInterna) {
        this.referenciaInterna = referenciaInterna;
    }

    public CuerpoRespuestaDatafono getRespuestaCompleta() {
        return respuestaCompleta;
    }

    public void setRespuestaCompleta(CuerpoRespuestaDatafono respuestaCompleta) {
        this.respuestaCompleta = respuestaCompleta;
    }

    @Override
    public String toString() {
        return "RequestGuardarRespuestaTef{" +
                "horaCreacion='" + horaCreacion + '\'' +
                ", minutosCreacion='" + minutosCreacion + '\'' +
                ", numeroTransaccion='" + numeroTransaccion + '\'' +
                ", tienda='" + tienda + '\'' +
                ", caja='" + caja + '\'' +
                ", referenciaInterna='" + referenciaInterna + '\'' +
                ", respuestaCompleta=" + respuestaCompleta +
                '}';
    }
}