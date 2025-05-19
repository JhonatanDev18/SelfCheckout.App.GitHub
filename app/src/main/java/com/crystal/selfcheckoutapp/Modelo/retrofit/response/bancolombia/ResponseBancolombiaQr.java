package com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia;

import com.google.gson.annotations.SerializedName;

public class ResponseBancolombiaQr {
    @SerializedName("qr")
    private String qrBancolombia;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("error")
    private Boolean error;

    public ResponseBancolombiaQr(String qrBancolombia, String mensaje, Boolean error) {
        this.qrBancolombia = qrBancolombia;
        this.mensaje = mensaje;
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResponseBancolombiaQr{" +
                "qrBancolombia='" + qrBancolombia + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", error=" + error +
                '}';
    }

    public String getQrBancolombia() {
        return qrBancolombia;
    }

    public void setQrBancolombia(String qrBancolombia) {
        this.qrBancolombia = qrBancolombia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
