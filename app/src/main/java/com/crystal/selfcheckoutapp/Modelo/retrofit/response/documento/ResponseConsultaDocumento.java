package com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseConsultaDocumento extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private DocumentoConsulta documento;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseConsultaDocumento(Boolean esValida, String mensaje, DocumentoConsulta documento, List<Object> listado) {
        super(esValida, mensaje);
        this.documento = documento;
        this.listado = listado;
    }

    public DocumentoConsulta getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoConsulta documento) {
        this.documento = documento;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseConsultaDocumento{" +
                "documento=" + documento +
                ", listado=" + listado +
                '}';
    }
}
