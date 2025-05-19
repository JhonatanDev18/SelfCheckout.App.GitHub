package com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaCompletaTef {
    @SerializedName("header")
    @Expose
    private HeaderTef header;
    @SerializedName("respuestaTef")
    @Expose
    private CuerpoRespuestaDatafono respuestaTef;

    public RespuestaCompletaTef(HeaderTef header, CuerpoRespuestaDatafono respuestaTef) {
        this.header = header;
        this.respuestaTef = respuestaTef;
    }

    public HeaderTef getHeader() {
        return header;
    }

    public void setHeader(HeaderTef header) {
        this.header = header;
    }

    public CuerpoRespuestaDatafono getRespuestaTef() {
        return respuestaTef;
    }

    public void setRespuestaTef(CuerpoRespuestaDatafono respuestaTef) {
        this.respuestaTef = respuestaTef;
    }

    @Override
    public String toString() {
        return "RespuestaCompletaTef{" +
                "header=" + header +
                ", respuestaTef=" + respuestaTef +
                '}';
    }
}
