package com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRespuestasDatafono extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private Object dato;

    @SerializedName("listado")
    @Expose
    private List<RespuestaCompletaTef> listado;

    public ResponseRespuestasDatafono(Boolean esValida, String mensaje, Object dato, List<RespuestaCompletaTef> listado) {
        super(esValida, mensaje);
        this.dato = dato;
        this.listado = listado;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public List<RespuestaCompletaTef> getListado() {
        return listado;
    }

    public void setListado(List<RespuestaCompletaTef> listado) {
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseRespuestasDatafono{" +
                "dato=" + dato +
                ", listado=" + listado +
                '}';
    }
}
