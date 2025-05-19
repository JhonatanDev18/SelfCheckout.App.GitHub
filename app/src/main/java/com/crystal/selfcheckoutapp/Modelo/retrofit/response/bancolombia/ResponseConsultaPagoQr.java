package com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseConsultaPagoQr {
    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    @SerializedName("pago")
    @Expose
    private PagoBancolombiaQr pago;

    public ResponseConsultaPagoQr(Boolean error, String mensaje, PagoBancolombiaQr pago) {
        this.error = error;
        this.mensaje = mensaje;
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "ResponseConsultaPagoQr{" +
                "error=" + error +
                ", mensaje='" + mensaje + '\'' +
                ", pago=" + pago +
                '}';
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public PagoBancolombiaQr getPago() {
        return pago;
    }

    public void setPago(PagoBancolombiaQr pago) {
        this.pago = pago;
    }
}
