package com.crystal.selfcheckoutapp.Modelo.retrofit.response.codigootp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodigoOtp {

    @SerializedName("codigo")
    @Expose
    private String codigoGenerado;

    public CodigoOtp(String codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    @Override
    public String toString() {
        return "CodigoOtp{" +
                "codigoGenerado='" + codigoGenerado + '\'' +
                '}';
    }

    public String getCodigoGenerado() {
        return codigoGenerado;
    }

    public void setCodigoGenerado(String codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }
}
