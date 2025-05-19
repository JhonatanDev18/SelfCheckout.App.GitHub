package com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDatafono extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private CuerpoRespuestaDatafono respuestaDatafono;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseDatafono(Boolean esValida, String mensaje, CuerpoRespuestaDatafono respuestaDatafono,
                            List<Object> listado) {
        super(esValida, mensaje);
        this.respuestaDatafono = respuestaDatafono;
        this.listado = listado;
    }

    public CuerpoRespuestaDatafono getRespuestaDatafono() {
        return respuestaDatafono;
    }

    public void setRespuestaDatafono(CuerpoRespuestaDatafono respuestaDatafono) {
        this.respuestaDatafono = respuestaDatafono;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseDatafono{" +
                "respuestaDatafono=" + respuestaDatafono +
                ", listado=" + listado +
                '}';
    }
}
