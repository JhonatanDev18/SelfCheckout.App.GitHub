package com.crystal.selfcheckoutapp.Modelo.retrofit.response.consecutivofiscal;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseConsecutivoFiscal extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private ConsecutivoFiscal consecutivoFiscal;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseConsecutivoFiscal(Boolean esValida, String mensaje, ConsecutivoFiscal consecutivoFiscal,
                                     List<Object> listado) {
        super(esValida, mensaje);
        this.consecutivoFiscal = consecutivoFiscal;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseConsecutivoFiscal{" +
                "consecutivoFiscal=" + consecutivoFiscal +
                ", listado=" + listado +
                '}';
    }

    public ConsecutivoFiscal getConsecutivoFiscal() {
        return consecutivoFiscal;
    }

    public void setConsecutivoFiscal(ConsecutivoFiscal consecutivoFiscal) {
        this.consecutivoFiscal = consecutivoFiscal;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
