package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGenerarCodigoOtp {
    @SerializedName("numeroDocumento")
    @Expose
    private String numeroDocumento;

    @SerializedName("celular")
    @Expose
    private String celular;

    @SerializedName("tienda")
    @Expose
    private String tienda;

    @SerializedName("caja")
    @Expose
    private String caja;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("enviarSms")
    @Expose
    private boolean enviarSms;

    @SerializedName("enviarCorreo")
    @Expose
    private boolean enviarCorreo;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    @SerializedName("asunto")
    @Expose
    private String asunto;

    @SerializedName("tipoConsumo")
    @Expose
    private String tipo;

    public RequestGenerarCodigoOtp(String numeroDocumento, String celular, String tienda, String caja,
                                   String email, boolean enviarSms, boolean enviarCorreo, String mensaje, String asunto, String tipo) {
        this.numeroDocumento = numeroDocumento;
        this.celular = celular;
        this.tienda = tienda;
        this.caja = caja;
        this.email = email;
        this.enviarSms = enviarSms;
        this.enviarCorreo = enviarCorreo;
        this.mensaje = mensaje;
        this.asunto = asunto;
        this.tipo = tipo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnviarSms() {
        return enviarSms;
    }

    public void setEnviarSms(boolean enviarSms) {
        this.enviarSms = enviarSms;
    }

    public boolean isEnviarCorreo() {
        return enviarCorreo;
    }

    public void setEnviarCorreo(boolean enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "RequestGenerarCodigoOtp{" +
                "numeroDocumento='" + numeroDocumento + '\'' +
                ", celular='" + celular + '\'' +
                ", tienda='" + tienda + '\'' +
                ", caja='" + caja + '\'' +
                ", email='" + email + '\'' +
                ", enviarSms=" + enviarSms +
                ", enviarCorreo=" + enviarCorreo +
                ", mensaje='" + mensaje + '\'' +
                ", asunto='" + asunto + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
