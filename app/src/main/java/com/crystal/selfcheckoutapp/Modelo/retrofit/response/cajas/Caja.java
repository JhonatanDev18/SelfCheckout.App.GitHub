package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Caja {
    @SerializedName("mediosPagoAutorizados")
    @Expose
    private String mediosPagoAutorizados;

    @SerializedName("codigoCaja")
    @Expose
    private String codigoCaja;

    @SerializedName("codigoTienda")
    @Expose
    private String codigoTienda;

    @SerializedName("cajaMovil")
    @Expose
    private String cajaMovil;

    @SerializedName("prefijoCaja")
    @Expose
    private String prefijoCaja;

    @SerializedName("divisa")
    @Expose
    private String divisa;

    @SerializedName("pais")
    @Expose
    private String pais;

    @SerializedName("identificadorCaja")
    @Expose
    private String identificadorCaja;

    @SerializedName("nombreTienda")
    @Expose
    private String nombreTienda;

    public Caja(String mediosPagoAutorizados, String codigoCaja, String codigoTienda, String cajaMovil,
                String prefijoCaja, String divisa, String pais, String identificadorCaja, String nombreTienda) {
        this.mediosPagoAutorizados = mediosPagoAutorizados;
        this.codigoCaja = codigoCaja;
        this.codigoTienda = codigoTienda;
        this.cajaMovil = cajaMovil;
        this.prefijoCaja = prefijoCaja;
        this.divisa = divisa;
        this.pais = pais;
        this.identificadorCaja = identificadorCaja;
        this.nombreTienda = nombreTienda;
    }

    public static Caja cargarDatosCaja(){
        return new Caja(SPM.getString(Constantes.CAJA_MEDIO_PAGO_AUTORIZADO), SPM.getString(Constantes.CAJA_CODIGO),
                SPM.getString(Constantes.CAJA_CODIGO_TIENDA), SPM.getString(Constantes.CAJA_MOVIL),
                SPM.getString(Constantes.CAJA_PREFIJO), SPM.getString(Constantes.CAJA_DIVISA),
                SPM.getString(Constantes.CAJA_PAIS), SPM.getString(Constantes.CAJA_IDENTIFICADOR),
                SPM.getString(Constantes.CAJA_NOMBRE_TIENDA));
    }

    @Override
    public String toString() {
        return "Caja{" +
                "mediosPagoAutorizados='" + mediosPagoAutorizados + '\'' +
                ", codigoCaja='" + codigoCaja + '\'' +
                ", codigoTienda='" + codigoTienda + '\'' +
                ", cajaMovil='" + cajaMovil + '\'' +
                ", prefijoCaja='" + prefijoCaja + '\'' +
                ", divisa='" + divisa + '\'' +
                ", pais='" + pais + '\'' +
                ", identificadorCaja='" + identificadorCaja + '\'' +
                ", nombreTienda='" + nombreTienda + '\'' +
                '}';
    }

    public String getMediosPagoAutorizados() {
        return mediosPagoAutorizados;
    }

    public void setMediosPagoAutorizados(String mediosPagoAutorizados) {
        this.mediosPagoAutorizados = mediosPagoAutorizados;
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getCajaMovil() {
        return cajaMovil;
    }

    public void setCajaMovil(String cajaMovil) {
        this.cajaMovil = cajaMovil;
    }

    public String getPrefijoCaja() {
        return prefijoCaja;
    }

    public void setPrefijoCaja(String prefijoCaja) {
        this.prefijoCaja = prefijoCaja;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getIdentificadorCaja() {
        return identificadorCaja;
    }

    public void setIdentificadorCaja(String identificadorCaja) {
        this.identificadorCaja = identificadorCaja;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }
}
