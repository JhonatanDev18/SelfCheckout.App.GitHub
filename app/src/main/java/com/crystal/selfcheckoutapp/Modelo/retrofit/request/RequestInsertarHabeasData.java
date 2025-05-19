package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestInsertarHabeasData {
    @SerializedName("caja")
    @Expose
    private String caja;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("estadoConsentimiento")
    @Expose
    private Integer estadoConsentimiento;

    @SerializedName("numeroDocumento")
    @Expose
    private String numeroDocumento;

    @SerializedName("tipoDocumento")
    @Expose
    private Integer tipoDocumento;

    @SerializedName("marca")
    @Expose
    private String marca;

    public RequestInsertarHabeasData(String caja, String email, Integer estadoConsentimiento,
                                     String numeroDocumento, Integer tipoDocumento, String marca) {
        this.caja = caja;
        this.email = email;
        this.estadoConsentimiento = estadoConsentimiento;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "RequestInsertarHabeasData{" +
                "caja='" + caja + '\'' +
                ", email='" + email + '\'' +
                ", estadoConsentimiento=" + estadoConsentimiento +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", tipoDocumento=" + tipoDocumento +
                ", marca='" + marca + '\'' +
                '}';
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEstadoConsentimiento() {
        return estadoConsentimiento;
    }

    public void setEstadoConsentimiento(Integer estadoConsentimiento) {
        this.estadoConsentimiento = estadoConsentimiento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
